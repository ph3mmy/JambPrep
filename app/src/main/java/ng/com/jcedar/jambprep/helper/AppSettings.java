package ng.com.jcedar.jambprep.helper;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Afolayan on 05/08/15.
 */
public class AppSettings {


    public static final String DATABASE_NAME = "jamb_prep";
    public static final int DATABASE_VERSION = 1;
    public static final String PROVIDER_AUTHORITY = "ng.com.jcedar.jambprep.provider";

    public static DateFormat serverDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
    public static DecimalFormat decimalFormatter = new DecimalFormat("###,###.##");

    public static final String SERVER_URL = "http://jambprep.jcedar.com.ng/";

    public static String getDateOneMonthAgo(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date oneMonthAgo = calendar.getTime();
        return  AppSettings.serverDateFormat.format(oneMonthAgo);
    }

    public static String getDateOneYearAgo(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        Date oneYearAgo = calendar.getTime();
        return  AppSettings.serverDateFormat.format(oneYearAgo);
    }


    public interface Role{

    }

}

