package ng.com.jcedar.jambprep.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

/**
 * Created by Seyi.Afolayan on 2/19/2016.
 */
public class DataContract {

    public static final String CONTENT_AUTHORITY = "ng.com.jcedar.jambprep.provider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final String PATH_ENGLISH = "english";
    public static final String PATH_PASSAGE = "passage";
    public static final String PATH_SUBJECT_1 = "subject_1";
    public static final String PATH_SUBJECT_2 = "subject_2";
    public static final String PATH_SUBJECT_3 = "subject_3";

    private static final String CALLER_IS_SYNCADAPTER = "caller_is_sync_adapter";

    public static class EnglishLanguage implements QuestionColumns, BaseColumns, SyncColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ENGLISH).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE +"/vnd.ng.com.jcedar.jambprep.provider.english";

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +"/vnd.ng.com.jcedar.jambprep.provider.english";

        public static final String[] PROJECTION_ALL = {
                _ID, ID, SUBJECT_ID, EXAM_YEAR, QUESTION, OPTION_A, OPTION_B,
                OPTION_C, OPTION_D, OPTION_E, ANSWER,
                ANSWER_PHOTO, EXPLANATION, PHOTO, UPDATED
        };
        public static Uri buildSubjectUri(String id){
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static final String SORT_ORDER_DEFAULT = EXAM_YEAR +" ASC";

    }

      public static class EnglishPassage implements PassageColumns, BaseColumns, SyncColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PASSAGE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE +"/vnd.ng.com.jcedar.jambprep.provider.passage";

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +"/vnd.ng.com.jcedar.jambprep.provider.passage";

        public static final String[] PROJECTION_ALL = {
                _ID, ID, REF_TEXT, COUNT
        };
        public static Uri buildSubjectUri(String id){
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static final String SORT_ORDER_DEFAULT = _ID +" ASC";

    }

     public static class Subject1 implements QuestionColumns, BaseColumns, SyncColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SUBJECT_1).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE +"/vnd.ng.com.jcedar.jambprep.provider.subject1";

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +"/vnd.ng.com.jcedar.jambprep.provider.subject1";

        public static final String[] PROJECTION_ALL = {
                _ID, ID, SUBJECT_ID, EXAM_YEAR, QUESTION, OPTION_A, OPTION_B,
                OPTION_C, OPTION_D, OPTION_E, ANSWER,
                ANSWER_PHOTO, EXPLANATION, PHOTO
        };
        public static Uri buildSubjectUri(String id){
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static final String SORT_ORDER_DEFAULT = EXAM_YEAR +" ASC";

    }

     public static class Subject2 implements QuestionColumns, BaseColumns, SyncColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SUBJECT_2).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE +"/vnd.ng.com.jcedar.jambprep.provider.subject2";

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +"/vnd.ng.com.jcedar.jambprep.provider.subject2";

        public static final String[] PROJECTION_ALL = {
                _ID, ID, SUBJECT_ID, EXAM_YEAR, QUESTION, OPTION_A, OPTION_B,
                OPTION_C, OPTION_D, OPTION_E, ANSWER,
                ANSWER_PHOTO, EXPLANATION, PHOTO
        };
        public static Uri buildSubjectUri(String id){
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static final String SORT_ORDER_DEFAULT = EXAM_YEAR +" ASC";

    }

     public static class Subject3 implements QuestionColumns, BaseColumns, SyncColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SUBJECT_3).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE +"/vnd.ng.com.jcedar.jambprep.provider.subject3";

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +"/vnd.ng.com.jcedar.jambprep.provider.subject3";

        public static final String[] PROJECTION_ALL = {
                _ID, ID, SUBJECT_ID, EXAM_YEAR, QUESTION, OPTION_A, OPTION_B,
                OPTION_C, OPTION_D, OPTION_E, ANSWER,
                ANSWER_PHOTO, EXPLANATION, PHOTO
        };
        public static Uri buildSubjectUri(String id){
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static final String SORT_ORDER_DEFAULT = EXAM_YEAR +" ASC";

    }




    public interface QuestionColumns {
        String ID = "id";
        String SUBJECT_ID = "subject_id";
        String EXAM_YEAR = "exam_year";
        String REF_TEXT_ID = "ref_text_id";
        String QUESTION = "question";
        String OPTION_A = "option_a";
        String OPTION_B = "option_b";
        String OPTION_C = "option_c";
        String OPTION_D = "option_d";
        String OPTION_E = "option_e";

        String ANSWER = "answer";
        String EXPLANATION = "explanation";
        String PHOTO = "photo";
        String ANSWER_PHOTO = "answer_photo";

    }

    interface PassageColumns{
        String ID = "id";
        String REF_TEXT = "ref_text";
        String COUNT = "count";
    }

    public interface SyncColumns{
        String UPDATED = "updated";
    }

    public static boolean hasCallerIsSyncAdapterParameter(Uri uri) {
        return TextUtils.equals("true",
                uri.getQueryParameter(DataContract.CALLER_IS_SYNCADAPTER));
    }

    public static Uri addCallerIsSyncAdapterParameter(Uri uri) {
        return uri.buildUpon().appendQueryParameter(
                DataContract.CALLER_IS_SYNCADAPTER, "true").build();
    }
}
