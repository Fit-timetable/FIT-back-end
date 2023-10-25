package ru.nsu.fit.parse.schedule;

public enum DayOfWeek {
    MONDAY(1, "Понедельник"),
    TUESDAY(2, "Вторник"),
    WEDNESDAY(3, "Среда"),
    THURSDAY(4, "Четверг"),
    FRIDAY(5, "Пятница"),
    SATURDAY(6, "Суббота"),
    SUNDAY(7, "Воскресенье");

    private final int number;
    private final String name;

    DayOfWeek(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public static DayOfWeek fromNumber(int number) {
        for (DayOfWeek day : values()) {
            if (day.getNumber() == number) {
                return day;
            }
        }
        throw new IllegalArgumentException("Неверный номер дня недели");
    }
}
