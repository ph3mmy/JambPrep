package ng.com.jcedar.jambprep.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Seyi.Afolayan on 2/19/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "jamb_prep.db";
    private static final int DATABASE_VERSION = 101;
    private static String TAG = DatabaseHelper.class.getName();
    private final Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENGLISH_TABLE);
        db.execSQL(SQL_CREATE_PASSAGE_TABLE);
        db.execSQL(SQL_CREATE_SUBJECT_1_TABLE);
        db.execSQL(SQL_CREATE_SUBJECT_2_TABLE);
        db.execSQL(SQL_CREATE_SUBJECT_3_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public static final String SQL_CREATE_ENGLISH_TABLE = "CREATE TABLE "
            +Tables.ENGLISH + " ("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DataContract.EnglishLanguage.ID + " INTEGER, "
            + DataContract.EnglishLanguage.SUBJECT_ID + " VARCHAR NOT NULL, "
            + DataContract.EnglishLanguage.EXAM_YEAR + " VARCHAR NOT NULL, "
            + DataContract.EnglishLanguage.REF_TEXT_ID + " VARCHAR, "
            + DataContract.EnglishLanguage.QUESTION + " VARCHAR NOT NULL, "
            + DataContract.EnglishLanguage.OPTION_A + " VARCHAR NOT NULL, "
            + DataContract.EnglishLanguage.OPTION_B + " VARCHAR NOT NULL, "
            + DataContract.EnglishLanguage.OPTION_C + " VARCHAR NOT NULL, "
            + DataContract.EnglishLanguage.OPTION_D + " VARCHAR NOT NULL, "
            + DataContract.EnglishLanguage.OPTION_E + " VARCHAR NOT NULL, "
            + DataContract.EnglishLanguage.ANSWER + " VARCHAR NOT NULL, "
            + DataContract.EnglishLanguage.EXPLANATION + " VARCHAR, "
            + DataContract.EnglishLanguage.PHOTO + " VARCHAR, "
            + DataContract.EnglishLanguage.ANSWER_PHOTO + " VARCHAR, "
            + DataContract.EnglishLanguage.UPDATED + " LONG DEFAULT 0 )" ;

    public static final String SQL_CREATE_SUBJECT_1_TABLE = "CREATE TABLE "
            +Tables.SUBJECT_1 + " ("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DataContract.Subject1._ID + " INTEGER, "
            + DataContract.Subject1.SUBJECT_ID + " VARCHAR NOT NULL, "
            + DataContract.Subject1.EXAM_YEAR + " VARCHAR NOT NULL, "
            + DataContract.Subject1.REF_TEXT_ID + " VARCHAR, "
            + DataContract.Subject1.QUESTION + " VARCHAR NOT NULL, "
            + DataContract.Subject1.OPTION_A + " VARCHAR NOT NULL, "
            + DataContract.Subject1.OPTION_B + " VARCHAR NOT NULL, "
            + DataContract.Subject1.OPTION_C + " VARCHAR NOT NULL, "
            + DataContract.Subject1.OPTION_D + " VARCHAR NOT NULL, "
            + DataContract.Subject1.OPTION_E + " VARCHAR NOT NULL, "
            + DataContract.Subject1.ANSWER + " VARCHAR NOT NULL, "
            + DataContract.Subject1.EXPLANATION + " VARCHAR, "
            + DataContract.Subject1.PHOTO + " VARCHAR, "
            + DataContract.Subject1.ANSWER_PHOTO + " VARCHAR, "
            + DataContract.Subject1.UPDATED + " LONG DEFAULT 0 )" ;

   public static final String SQL_CREATE_SUBJECT_2_TABLE = "CREATE TABLE "
            +Tables.SUBJECT_2 + " ("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DataContract.Subject2.ID + " INTEGER, "
            + DataContract.Subject2.SUBJECT_ID + " VARCHAR NOT NULL, "
            + DataContract.Subject2.EXAM_YEAR + " VARCHAR NOT NULL, "
            + DataContract.Subject2.REF_TEXT_ID + " VARCHAR, "
            + DataContract.Subject2.QUESTION + " VARCHAR NOT NULL, "
            + DataContract.Subject2.OPTION_A + " VARCHAR NOT NULL, "
            + DataContract.Subject2.OPTION_B + " VARCHAR NOT NULL, "
            + DataContract.Subject2.OPTION_C + " VARCHAR NOT NULL, "
            + DataContract.Subject2.OPTION_D + " VARCHAR NOT NULL, "
            + DataContract.Subject2.OPTION_E + " VARCHAR NOT NULL, "
            + DataContract.Subject2.ANSWER + " VARCHAR NOT NULL, "
            + DataContract.Subject2.EXPLANATION + " VARCHAR, "
            + DataContract.Subject2.PHOTO + " VARCHAR, "
            + DataContract.Subject2.ANSWER_PHOTO + " VARCHAR, "
            + DataContract.Subject2.UPDATED + " LONG DEFAULT 0 )" ;

   public static final String SQL_CREATE_SUBJECT_3_TABLE = "CREATE TABLE "
            +Tables.SUBJECT_3 + " ("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DataContract.Subject3.ID + " INTEGER, "
            + DataContract.Subject3.SUBJECT_ID + " VARCHAR NOT NULL, "
            + DataContract.Subject3.EXAM_YEAR + " VARCHAR NOT NULL, "
            + DataContract.Subject3.REF_TEXT_ID + " VARCHAR, "
            + DataContract.Subject3.QUESTION + " VARCHAR NOT NULL, "
            + DataContract.Subject3.OPTION_A + " VARCHAR NOT NULL, "
            + DataContract.Subject3.OPTION_B + " VARCHAR NOT NULL, "
            + DataContract.Subject3.OPTION_C + " VARCHAR NOT NULL, "
            + DataContract.Subject3.OPTION_D + " VARCHAR NOT NULL, "
            + DataContract.Subject3.OPTION_E + " VARCHAR NOT NULL, "
            + DataContract.Subject3.ANSWER + " VARCHAR NOT NULL, "
            + DataContract.Subject3.EXPLANATION + " VARCHAR, "
            + DataContract.Subject3.PHOTO + " VARCHAR, "
            + DataContract.Subject3.ANSWER_PHOTO + " VARCHAR, "
            + DataContract.Subject3.UPDATED + " LONG DEFAULT 0 )" ;

public static final String SQL_CREATE_PASSAGE_TABLE = "CREATE TABLE "
            +Tables.PASSAGE + " ("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DataContract.EnglishPassage.ID + " INTEGER, "
            + DataContract.EnglishPassage.REF_TEXT + " VARCHAR NOT NULL, "
            + DataContract.EnglishPassage.COUNT + " VARCHAR NOT NULL, "
            + DataContract.EnglishPassage.UPDATED + " LONG DEFAULT 0 )" ;


    interface Tables{
        String ENGLISH = "english";
        String PASSAGE = "passage";
        String SUBJECT_1 = "subject1";
        String SUBJECT_2 = "subject2";
        String SUBJECT_3 = "subject3";

    }
    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }

}
