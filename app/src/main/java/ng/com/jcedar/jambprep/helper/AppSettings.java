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


//    public static final String API_KEY = "key";
    //public static final String API_CODE = "f25f8de1-6bc5-4c6c-b2cd-eca1baa9bad0"; //OAUCDL
//    public static final String API_CODE = "c1f7c1b9-89a1-4128-89c2-4e87bf666c0e";  //CRUTECH
//    public static final String GRANT_TYPE = "password";
    public static final String DATABASE_NAME = "NOWAREVAMP";
    public static final int DATABASE_VERSION = 1;
    public static final String PROVIDER_AUTHORITY = "ng.com.jcedar.jambprep.provider";
    public static DateFormat spriteDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
    public static DateFormat monthFormat = new SimpleDateFormat("MMMM");
    public static DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    public static final String REMOTE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static DateFormat serverDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
    public static DecimalFormat decimalFormatter = new DecimalFormat("###,###.##");

    public static final String SERVER_URL = "http://jambprep.jcedar.com.ng/register.php";


    //public static final String PROVIDER_AUTHORITY = "";

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

