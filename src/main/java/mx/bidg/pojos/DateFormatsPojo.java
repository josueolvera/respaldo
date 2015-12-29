package mx.bidg.pojos;

import org.apache.commons.lang3.LocaleUtils;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author Rafael Viveros
 * Created on 29/12/15.
 */
public class DateFormatsPojo {
    private String iso;

    private String dateNumber;
    private String dateTextShort;
    private String dateTextLong;

    private String time12;
    private String time24;

    private DateElements dateElements;

    public DateFormatsPojo(@NotNull LocalDateTime dateTime) {
        Locale locale = LocaleUtils.toLocale("es_MX");
        this.iso = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        this.dateNumber = dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        this.dateTextShort = dateTime.format(DateTimeFormatter.ofPattern("EE dd, MMM yyyy").withLocale(locale));
        this.dateTextLong = dateTime.format(DateTimeFormatter.ofPattern("EEEE dd, MMMM yyyy").withLocale(locale));

        this.time12 = dateTime.format(DateTimeFormatter.ofPattern("hh:mm a"));
        this.time24 = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));

        this.dateElements = new DateElements(dateTime);
    }

    public DateFormatsPojo(@NotNull LocalDate date) {}

    public DateFormatsPojo(@NotNull LocalTime time) {}

    public String getIso() {
        return iso;
    }

    public String getDateNumber() {
        return dateNumber;
    }

    public String getDateTextShort() {
        return dateTextShort;
    }

    public String getDateTextLong() {
        return dateTextLong;
    }

    public String getTime12() {
        return time12;
    }

    public String getTime24() {
        return time24;
    }

    public DateElements getDateElements() {
        return dateElements;
    }

    private static class DateElements {
        private String day;
        private String dayNameShort;
        private String dayNameLong;
        private String month;
        private String monthNameShort;
        private String monthNameLong;
        private String year;

        public DateElements(LocalDateTime localDateTime) {
            Locale locale = LocaleUtils.toLocale("es_MX");
            this.day = localDateTime.format(DateTimeFormatter.ofPattern("dd"));
            this.dayNameShort = localDateTime.format(DateTimeFormatter.ofPattern("EE").withLocale(locale));
            this.dayNameLong = localDateTime.format(DateTimeFormatter.ofPattern("EEEE").withLocale(locale));

            this.month = localDateTime.format(DateTimeFormatter.ofPattern("MM"));
            this.monthNameShort = localDateTime.format(DateTimeFormatter.ofPattern("MMM").withLocale(locale));
            this.monthNameLong = localDateTime.format(DateTimeFormatter.ofPattern("MMMM").withLocale(locale));

            this.year = localDateTime.format(DateTimeFormatter.ofPattern("yyyy"));
        }

        public DateElements(LocalTime localTime) {}

        public DateElements(LocalDate localDate) {}

        public String getDay() {
            return day;
        }

        public String getDayNameShort() {
            return dayNameShort;
        }

        public String getDayNameLong() {
            return dayNameLong;
        }

        public String getMonth() {
            return month;
        }

        public String getMonthNameShort() {
            return monthNameShort;
        }

        public String getMonthNameLong() {
            return monthNameLong;
        }

        public String getYear() {
            return year;
        }
    }
}
