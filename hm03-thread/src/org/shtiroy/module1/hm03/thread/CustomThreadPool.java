package org.shtiroy.module1.hm03.thread;

import java.util.LinkedList;
import java.util.List;

public class CustomThreadPool {

    private final List<Worker> workers = new LinkedList<>();
    private final LinkedList<Runnable> tasks = new LinkedList<>();

    private volatile boolean isShutdown = false;

    public CustomThreadPool(int poolSize) {
        if (poolSize <= 0) {
            throw new IllegalArgumentException("Размер должен быть > 0");
        }

        for (int i = 0; i < poolSize; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            worker.start();
        }
    }

    public void execute(Runnable task) {
        synchronized (tasks) {
            if (isShutdown) {
                throw new IllegalStateException("CustomThreadPool is shutdown");
            }
            tasks.add(task);
            tasks.notify();
        }
    }

    public void shutdown() {
        synchronized (tasks) {
            isShutdown = true;
            tasks.notifyAll();
        }
    }

    public void awaitTermination() {
        for (Worker worker : workers) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            while (true) {
                Runnable task;

                synchronized (tasks) {
                    while (tasks.isEmpty() && !isShutdown) {
                        try {
                            tasks.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }

                    if (tasks.isEmpty() &&  isShutdown) {
                        break;
                    }

                    task = tasks.removeFirst();
                }

                try {
                    task.run();
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
