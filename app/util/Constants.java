package util;

import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by eddie on 11/21/16.
 */
public class Constants {
    public static DateTimeFormatter DB_DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd").withZone(DateTimeZone.UTC);
    public static DateTimeFormatter DB_DATETIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(DateTimeZone.UTC);;
}
