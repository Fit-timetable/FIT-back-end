package ru.nsu.fit.schedule.port;

public interface ScheduleUrl {
    String NSU_GROUP_URL = "https://table.nsu.ru/group/";
    String NSU_ROOM_URL = "https://table.nsu.ru/room/";
    String SCHEDULE_URL = "/schedule";
    String RESET_URL = "/reset";
    String PIN_URL = "/pin";
    String GROUP_URL = "/group/{group}";
    String ROOM_URL = "/room/{room}";
}
