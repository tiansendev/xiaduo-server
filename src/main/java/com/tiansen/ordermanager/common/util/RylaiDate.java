package com.tiansen.ordermanager.common.util;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期时间工具类
 *
 * @author nie
 *
 */
public class RylaiDate {

    /**
     * yyyyMMddHHmmss
     */
    public static final DateTimeFormatter formatter_DateTimestamp = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final DateTimeFormatter formatter_DateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取当前日期
     *
     * @return
     */
    public static LocalDate getLocalDate() {
        return LocalDate.now();
    }
    /**
     * 获取当前日期
     *
     * @return
     */
    public static Date getCurrentDate() {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime localDateTime=LocalDateTime.now().atZone(zoneId);
        Date date = Date.from(localDateTime.toInstant());
        return date;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static LocalTime getLocalTime() {
        return LocalTime.now();
    }

    /**
     * 获取当前日期时间
     *
     * @return
     */
    public static LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 获取当前的微秒数
     *
     * @return
     */
    public static long getClockMillis() {
        Clock clock = Clock.systemDefaultZone();
        return clock.millis();
    }

    public static Timestamp getCurrTimstamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 返回当前时间yyyyMMddHHmmss
     *
     * @return
     */
    public static String getDateTimestamp() {
        return getLocalDateTime().format(formatter_DateTimestamp);
    }

    /**
     * 返回当前时间yyyy-MM-dd
     *
     * @return
     */
    public static String getDate() {
        return getLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * 返回当前系统时间 yyyy-MM-dd HH:mm:ss
     *
     * @return 返回当前系统时间
     */
    public static String getDateTime() {
        return getLocalDateTime().format(formatter_DateTime);
    }

    /**
     * 获取当月第一天 yyyy-MM-dd
     *
     * @return
     */
    public static String getFirstDayOfMonth() {
        return getLocalDate().withDayOfMonth(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * 获取本月最后一天 yyyy-MM-dd
     *
     * @return
     */
    public static String getLastDayOfMonth() {
        LocalDate localDate = getLocalDate();
        return localDate.withDayOfMonth(localDate.lengthOfMonth()).format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * 将yyyyMMddHHmmss转为 yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @return
     */
    public static String formatDateTimestamp(String dateTimestamp) {
        return LocalDateTime.parse(dateTimestamp, formatter_DateTimestamp).format(formatter_DateTime);
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss转为 yyyyMMddHHmmss
     */
    public static String formatDateTime(String dateTime) {
        return parseLocalDateTime(dateTime).format(formatter_DateTimestamp);
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss转为 LocalDateTime
     */
    public static LocalDateTime parseLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, formatter_DateTime);
    }

    /**
     * 将yyyyMMddHHmmss转为 LocalDateTime
     */
    public static LocalDateTime parseLocalDateTimestamp(String dateTimestamp) {
        return LocalDateTime.parse(dateTimestamp, formatter_DateTimestamp);
    }

    /**
     * yyyy-MM-dd字符串转LocalDate
     *
     * @param dateString
     * @return
     */
    public static LocalDate parseLocalDate(String dateString) {
        return LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * yyyy-MM-dd 增加日期
     *
     * @param date
     * @param day
     * @return
     */
    public static String plusDays(String date, int days) {
        LocalDate localDate = parseLocalDate(date);
        return localDate.plusDays(days).format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
    /**
     * yyyy-MM-dd 增加日期
     *
     * @param date
     * @param days
     * @return
     */
    public static Date plusDays(Date date, int days) {
        LocalDateTime localDateTime = date2LocalDateTime(date);
        return Date.from(localDateTime.plusDays(days).toInstant(ZoneOffset.UTC));
    }
    /**
     * Date转换为LocalDateTime
     * @param date
     */
    public static LocalDateTime date2LocalDateTime(Date date){
        Instant instant = date.toInstant();//An instantaneous point on the time-line.(时间线上的一个瞬时点。)
        ZoneId zoneId = ZoneId.systemDefault();//A time-zone ID, such as {@code Europe/Paris}.(时区)
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
    }
    /**
     * Date转换为LocalDateTime
     * @param date
     */
    public static LocalDate date2LocalDate(Date date){
        Instant instant = date.toInstant();//An instantaneous point on the time-line.(时间线上的一个瞬时点。)
        ZoneId zoneId = ZoneId.systemDefault();//A time-zone ID, such as {@code Europe/Paris}.(时区)
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        return localDate;
    }

    /**
     * LocalDateTime转换为Date
     * @param localDateTime
     */
    public static Date localDateTime2Date( LocalDateTime localDateTime){
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }
    /**
     * 计算两个日期之间相差的天数
     *
     * @param startDate
     *            较小的时间 yyyy-MM-dd
     * @param endDate
     *            较大的时间 yyyy-MM-dd
     * @return 相差天数
     */
    public static int dateCompareTo(String startDate, String endDate) {
        LocalDate startLocalDate = LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endLocalDate = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);
        Period period = Period.between(startLocalDate, endLocalDate);
        return period.getDays();
    }

    public static void main(String[] args) {
        System.out.println(getDateTimestamp());
        System.out.println(getDateTime());
        System.out.println(getLocalDate().withDayOfMonth(1));
        System.out.println(getLocalDate().withDayOfMonth(getLocalDate().lengthOfMonth()));
        System.out.println(plusDays("2016-08-31", 1));
        System.out.println(dateCompareTo("2016-08-21", "2016-08-30"));
        System.out.println(getClockMillis());
        System.out.println(LocalDateTime.parse("2016-08-31 11:39:14", formatter_DateTime)
                .format(DateTimeFormatter.ISO_LOCAL_DATE));

    }

}