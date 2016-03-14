package mx.bidg.pojos;

import org.apache.commons.lang3.LocaleUtils;

import javax.validation.constraints.NotNull;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
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
    private PeriodDiff dateDiff;
    private static Locale locale = LocaleUtils.toLocale("es_MX");
    private static WeekFields weekFields = WeekFields.of(locale);

    public DateFormatsPojo() {}

    public DateFormatsPojo(@NotNull LocalDateTime dateTime) {
        this.iso = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        build(dateTime.toLocalDate());
        build(dateTime.toLocalTime());
        buildPeriod(LocalDate.now(), dateTime.toLocalDate());

        this.dateElements = new DateElements(dateTime.toLocalDate());
    }

    public DateFormatsPojo(@NotNull LocalDate date) {
        this.iso = date.format(DateTimeFormatter.ISO_DATE);
        build(date);
        buildPeriod(LocalDate.now(), date);

        this.dateElements = new DateElements(date);
    }

    public DateFormatsPojo(@NotNull LocalTime time) {}

    public void build(LocalDate localDate) {
        this.dateNumber = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        this.dateTextShort = localDate.format(DateTimeFormatter.ofPattern("EE dd, MMM yyyy").withLocale(locale));
        this.dateTextLong = localDate.format(DateTimeFormatter.ofPattern("EEEE dd, MMMM yyyy").withLocale(locale));
    }

    public void build(LocalTime time) {
        this.time12 = time.format(DateTimeFormatter.ofPattern("hh:mm a"));
        this.time24 = time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public void buildPeriod(LocalDate from, LocalDate to) {
        Period period = Period.between(from, to).normalized();
        this.dateDiff = new PeriodDiff(period);
        this.dateDiff.setTotalDays(ChronoUnit.DAYS.between(from, to));
    }

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

    public void setDateElements(DateElements elements) {
        this.dateElements = elements;
    }

    public PeriodDiff getDateDiff() {
        return dateDiff;
    }

    private static class DateElements {
        private String day;
        private String dayNameShort;
        private String dayNameLong;
        private String month;
        private String monthNameShort;
        private String monthNameLong;
        private Integer year;
        private Integer week;
        private DateInterval interval;

        public DateElements() {}

        public DateElements(LocalDate localDate) {
            build(localDate);
            this.interval = new DateInterval(localDate);
        }

        public void build(LocalDate localDate) {
            this.day = localDate.format(DateTimeFormatter.ofPattern("dd"));
            this.dayNameShort = localDate.format(DateTimeFormatter.ofPattern("EE").withLocale(locale));
            this.dayNameLong = localDate.format(DateTimeFormatter.ofPattern("EEEE").withLocale(locale));

            this.month = localDate.format(DateTimeFormatter.ofPattern("MM"));
            this.monthNameShort = localDate.format(DateTimeFormatter.ofPattern("MMM").withLocale(locale));
            this.monthNameLong = localDate.format(DateTimeFormatter.ofPattern("MMMM").withLocale(locale));

            this.year = localDate.getYear();
            this.week = localDate.get(weekFields.weekOfWeekBasedYear());
        }

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

        public Integer getYear() {
            return year;
        }

        public Integer getWeek() {
            return week;
        }

        public DateInterval getInterval() {
            return interval;
        }
    }

    private static class DateInterval {
        private String name;
        private LocalDate top;
        private LocalDate bottom;
        private Boolean sameDay = false;
        private Boolean sameWeek = false;
        private Boolean sameMonth = false;
        private Boolean sameYear = false;

        public DateInterval(LocalDate date){

            LocalDate currentDate = LocalDate.now();
            if (currentDate.getYear() == date.getYear()) {
                this.sameDay = date.isEqual(currentDate);
                this.sameWeek = currentDate.get(weekFields.weekOfWeekBasedYear()) == date.get(weekFields.weekOfWeekBasedYear());
                this.sameMonth = currentDate.getMonthValue() == date.getMonthValue();
                this.sameYear = true;

                if (sameDay) {
                    this.name = "Hoy";
                    this.top = date;
                    this.bottom = date;
                } else if (sameWeek) {
                    this.name = "Esta semana";
                    top = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
                    bottom = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
                } else if (sameMonth) {
                    this.name = "Este mes";
                    top = date.with(TemporalAdjusters.firstDayOfMonth());
                    bottom = date.with(TemporalAdjusters.lastDayOfMonth());
                } else {
                    this.name = "Este año";
                    top = date.with(TemporalAdjusters.firstDayOfYear());
                    bottom = date.with(TemporalAdjusters.lastDayOfYear());
                }
            } else {
                this.name = "En el futuro";
                top = date.with(TemporalAdjusters.firstDayOfYear());
            }
        }

        public String getName() {
            return name;
        }

        public DateFormatsPojo getTop() {
            if (top != null) {
                return build(top);
            }
            return null;
        }

        public DateFormatsPojo getBottom() {
            if (bottom != null) {
                return build(bottom);
            }
            return null;
        }

        public Boolean getSameDay() {
            return sameDay;
        }

        public Boolean getSameWeek() {
            return sameWeek;
        }

        public Boolean getSameMonth() {
            return sameMonth;
        }

        public Boolean getSameYear() {
            return sameYear;
        }

        private DateFormatsPojo build(LocalDate date) {
            DateFormatsPojo formats = new DateFormatsPojo();
            DateElements elements = new DateElements();
            formats.build(date);
            elements.build(date);
            formats.setDateElements(elements);
            return formats;
        }
    }

    private static class PeriodDiff {
        private Period period;
        private boolean isNegative;
        private boolean isZero;
        private Long totalDays;

        public PeriodDiff(Period period) {
            this.isZero = period.isZero();
            this.isNegative = period.isNegative();

            if (period.isNegative()) {
                this.period = period.negated();
            } else {
                this.period = period;
            }
        }

        public MyPeriod getPeriod() {
            return new MyPeriod(period);
        }

        public Long getTotalDays() {
            return totalDays;
        }

        public void setTotalDays(Long totalDays) {
            this.totalDays = totalDays;
        }

        public boolean isNegative() {
            return isNegative;
        }

        public boolean isZero() {
            return isZero;
        }
    }

    private static class MyPeriod {
        private Period period;

        public MyPeriod(Period period) {
            this.period = period;
        }

        public Integer getDays() {
            return period.getDays();
        }

        public Integer getMonths() {
            return period.getMonths();
        }

        public Integer getYears() {
            return period.getYears();
        }
    }
}
