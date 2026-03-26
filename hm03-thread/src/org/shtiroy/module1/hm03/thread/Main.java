package org.shtiroy.module1.hm03.thread;

public class Main {
    public static void main(String[] args) {
        CustomThreadPool pool = new CustomThreadPool(3);

        for (int i = 0; i < 10; i++) {
            int taskId = i;
            pool.execute(() -> {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println(Thread.currentThread().getName() + " выполняет " + taskId);
            });
        }

        pool.shutdown();
        pool.awaitTermination();
        System.out.println("Все задачи выполнены");
    }
}
