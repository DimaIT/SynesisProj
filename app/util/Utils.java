//package util;
//
//import com.google.common.base.Functions;
//import com.google.common.collect.ImmutableSortedMap;
//import com.google.common.collect.Ordering;
//import org.springframework.util.CollectionUtils;
//
//import java.sql.Date;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Collection;
//import java.util.Map;
//
//public final class Utils {
//    public static final String ERROR = "error";
//    public static final String SUCCESS = "success";
//    public static final SimpleDateFormat WEB_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
//    public static final SimpleDateFormat WEB_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
//    public static int DAY = 1000 * 60 * 60 * 24;
//
////    Timer
////    long start = System.currentTimeMillis();
////    System.out.println("\n\nProcessed in " + (System.currentTimeMillis() - start) + "ms\n\n");
//
//
//    public static String mkString(Collection collection, String divider) {
//        if (CollectionUtils.isEmpty(collection)) return "";
//        StringBuilder builder = new StringBuilder();
//        for (Object obj: collection) {
//            builder.append(obj);
//            builder.append(divider);
//        }
//        builder.delete(builder.length() - divider.length(), builder.length());
//        return builder.toString();
//    }
//
//    public static <K extends Comparable, V extends Comparable> Map<K, V> sortMapByValue(Map<K, V> map) {
//        return ImmutableSortedMap.copyOf(map, Ordering.natural().onResultOf(Functions.forMap(map)).compound(Ordering.natural()));
//    }
//
//    public static Date parseWebDate(String string) {
//        return logException(() -> new Date(WEB_DATE_FORMAT.parse(string).getTime()));
//    }
//
//    public static Date dateAddDay(Date date, int days) {
//        return new Date(date.getTime() + days * DAY);
//    }
//
//    public static Date yesterday() {
//        return new Date(Calendar.getInstance().getTimeInMillis() - DAY);
//    }
//
//    public static Date today() {
//        return new Date(Calendar.getInstance().getTimeInMillis());
//    }
//
//    public static <T> T logException(TrySupplier<T> expression) {
//        try {
//            return expression.get();
//        } catch (Exception e) {
//            Reflect.errorLog(Utils.class, e);
//            return null;
//        }
//    }
//
//    public static void logException(TryFunction expression) {
//        try {
//            expression.execute();
//        } catch (Exception e) {
//            Reflect.errorLog(Utils.class, e);
//        }
//    }
//
//    public interface TrySupplier<T> {
//        T get() throws Exception;
//    }
//
//    public interface TryFunction {
//        void execute() throws Exception;
//    }
//}
