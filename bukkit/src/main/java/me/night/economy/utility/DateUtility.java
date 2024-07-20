package me.night.economy.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtility {
    private static final SimpleDateFormat dateFormat;
    private static final SimpleDateFormat timeFormat;
    private static final LinkedHashMap<Integer, String> values;

    static {
        dateFormat = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss");
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        (values = new LinkedHashMap<>(6)).put(31104000, "y");
        DateUtility.values.put(2592000, "msc");
        DateUtility.values.put(86400, "d");
        DateUtility.values.put(3600, "h");
        DateUtility.values.put(60, "m");
        DateUtility.values.put(1, "s");
    }


    public static int parseToSeconds(String time) {
        final Pattern timePattern = Pattern.compile("(\\d+)\\s*(\\w+)");
        final Matcher matcher = timePattern.matcher(time);
        int totalSeconds = 0;

        while (matcher.find()) {
            int value = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2).toLowerCase();

            switch (unit) {
                case "s":
                    totalSeconds += value;
                    break;
                case "m":
                    totalSeconds += value * 60;
                    break;
                case "h":
                    totalSeconds += value * 60 * 60;
                    break;
                case "d":
                    totalSeconds += value * 24 * 60 * 60;
                    break;
                case "msc":
                    totalSeconds += value * 7 * 24 * 60 * 60;
                    break;
            }
        }

        return totalSeconds;
    }

    public static String secondsToString(int seconds) {
        final StringBuilder sb = new StringBuilder();
        for (final Map.Entry<Integer, String> e : DateUtility.values.entrySet()) {
            final int iDiv = seconds / e.getKey();
            if (iDiv >= 1) {
                final int x = (int) Math.floor(iDiv);
                sb.append(x).append(e.getValue());
                seconds -= x * e.getKey();
            }
        }
        return sb.toString();
    }

    public static String getDate(final long time) {
        return DateUtility.dateFormat.format(new Date(time));
    }

    public static String getTime(final long time) {
        return DateUtility.timeFormat.format(new Date(time));
    }

    public static int parseDateDiff(final String time, final boolean future) {
        try {
            final Pattern timePattern = Pattern.compile("(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?(?:([0-9]+)\\s*(?:s[a-z]*)?)?", Pattern.CASE_INSENSITIVE);
            final Matcher m = timePattern.matcher(time);
            int years = 0;
            int months = 0;
            int weeks = 0;
            int days = 0;
            int hours = 0;
            int minutes = 0;
            int seconds = 0;
            boolean found = false;
            while (m.find()) {
                if (m.group() != null && !m.group().isEmpty()) {
                    for (int i = 0; i < m.groupCount(); ++i) {
                        if (m.group(i) != null && !m.group(i).isEmpty()) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        continue;
                    }
                    if (m.group(1) != null && !m.group(1).isEmpty()) {
                        years = Integer.parseInt(m.group(1));
                    }
                    if (m.group(2) != null && !m.group(2).isEmpty()) {
                        months = Integer.parseInt(m.group(2));
                    }
                    if (m.group(3) != null && !m.group(3).isEmpty()) {
                        weeks = Integer.parseInt(m.group(3));
                    }
                    if (m.group(4) != null && !m.group(4).isEmpty()) {
                        days = Integer.parseInt(m.group(4));
                    }
                    if (m.group(5) != null && !m.group(5).isEmpty()) {
                        hours = Integer.parseInt(m.group(5));
                    }
                    if (m.group(6) != null && !m.group(6).isEmpty()) {
                        minutes = Integer.parseInt(m.group(6));
                    }
                    if (m.group(7) == null) {
                        break;
                    }
                    if (m.group(7).isEmpty()) {
                        break;
                    }
                    seconds = Integer.parseInt(m.group(7));
                    break;
                }
            }
            if (!found) {
                return -1;
            }
            final Calendar c = new GregorianCalendar();
            if (years > 0) {
                c.add(Calendar.YEAR, years * (future ? 1 : -1));
            }
            if (months > 0) {
                c.add(Calendar.MONTH, months * (future ? 1 : -1));
            }
            if (weeks > 0) {
                c.add(Calendar.WEEK_OF_YEAR, weeks * (future ? 1 : -1));
            }
            if (days > 0) {
                c.add(Calendar.DATE, days * (future ? 1 : -1));
            }
            if (hours > 0) {
                c.add(Calendar.HOUR_OF_DAY, hours * (future ? 1 : -1));
            }
            if (minutes > 0) {
                c.add(Calendar.MINUTE, minutes * (future ? 1 : -1));
            }
            if (seconds > 0) {
                c.add(Calendar.SECOND, seconds * (future ? 1 : -1));
            }
            final Calendar max = new GregorianCalendar();
            max.add(Calendar.YEAR, 10);
            if (c.after(max)) {
                return (int) (max.getTimeInMillis() / 1000L);
            }
            return (int) (c.getTimeInMillis() / 1000L);
        } catch (Exception e) {
            return -1;
        }
    }
}