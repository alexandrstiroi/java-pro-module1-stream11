package org.shtiroy.module1.hm02.stream;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        //1. Найдите в списке целых чисел 3-е наибольшее число (пример: 5 2 10 9 4 3 10 1 13 =>10)
        List<Integer> intList = List.of(5, 2, 10, 9, 4, 3, 10, 1, 13);

        Integer result1 = intList.stream().sorted(Comparator.reverseOrder()).skip(2).findFirst().orElse(null);
        System.out.println(result1);

        //2. Найдите в списке целых чисел 3-е наибольшее «уникальное» число (пример: 5 2 10 9 4 3 10 1 13 => 9, в отличие от прошлой задачи здесь разные 10 считает за одно число)
        Integer result2 = intList.stream().distinct().sorted(Comparator.reverseOrder()).skip(2).findFirst().orElse(null);
        System.out.println(result2);

        //3. Имеется список объектов типа Сотрудник (имя, возраст, должность),
        // необходимо получить список имен 3 самых старших сотрудников с должностью «Инженер», в порядке убывания возраста
        List<Employee> employees = List.of(new Employee("Александр", 25, "Инженер"),
                new Employee("Алексей", 55, "Директор"),
                new Employee("Василий", 30, "Инженер"),
                new Employee("Валентина", 25, "Бухгалтер"),
                new Employee("Егор", 29, "Инженер"),
                new Employee("Мария", 24, "Маркетолог"),
                new Employee("Николай", 33, "Водитель"),
                new Employee("Антон", 25, "Сантехник"),
                new Employee("Денис", 25, "Разработчик"),
                new Employee("Сергей", 32, "Инженер"));

        List<String> names = employees.stream()
                .filter(employee -> employee.getTitle().equalsIgnoreCase("Инженер"))
                .sorted(Comparator.comparing(Employee::getAge).reversed())
                .limit(3)
                .map(Employee::getName)
                .toList();
        System.out.println(names);

        //4. Имеется список объектов типа Сотрудник (имя, возраст, должность), посчитайте средний возраст сотрудников с должностью «Инженер»
        double result4 = employees.stream()
                .filter(employee -> employee.getTitle().equalsIgnoreCase("Инженер"))
                .mapToInt(Employee::getAge)
                .average()
                .orElse(0);
        System.out.println("Средний возраст: " + result4);

        //5. Найдите в списке слов самое длинное
        List<String> list = List.of("Имеется", "список", "объектов", "типа", "Сотрудник", "имя", "возраст",
                "должность", "посчитайте", "средний", "возраст", "сотрудников", "с", "должностью", "Инженер");
        String result5 = list.stream()
                .max(Comparator.comparingInt(String::length))
                .orElse(null);
        System.out.println("Самое длинное слово: " + result5);

        //6. Имеется строка с набором слов в нижнем регистре, разделенных пробелом. Постройте хеш-мапы,
        // в которой будут хранится пары: слова сколько раз оно встречается во входной строке
        String str = "солнце море песок небо солнце берег море волна песок ветер";

        Map<String, Long> result6 = Stream.of(str.split(" "))
                .collect(Collectors.groupingBy(elem -> elem, Collectors.counting()));

        System.out.println(result6);

        //7. Отпечатайте в консоль строки из списка в порядке увеличения длины слова,
        // если слова имеют одинаковую длины, то должен быть сохранен алфавитный порядок
        Stream.of(str.split(" "))
                .sorted(Comparator.comparingInt(String::length).thenComparing(String::compareTo))
                .forEach(System.out::println);

        //8. Имеется массив строк, в каждой из которых лежит набор из 5 слов,
        // разделенных пробелом найдите среди всех слов самое длинное, если таких слов несколько, получите любое из них.
        String[] array = {"яблоко банан груша арбуз лимон",
                "кошка собака хомяк птица енот",
                "город улица дом окно дверь",
                "книга ручка стол стул лампа",
                "зима весна лето осень дождь"};

        //IDEA предлагает исправить на .flatMap(elem -> Stream.of(elem.split(" "))).max(Comparator.comparingInt(String::length))
        String result8 = Stream.of(array)
                .flatMap(elem -> Stream.of(elem.split(" ")))
                .sorted(Comparator.comparingInt(String::length))
                .findFirst()
                .orElse(null);
        System.out.println("Самое длинное слово: " + result8);
    }
}
