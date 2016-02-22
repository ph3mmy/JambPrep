package ng.com.jcedar.jambprep.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import ng.com.jcedar.jambprep.helper.SelectionBuilder;

/**
 * Created by Seyi.Afolayan on 2/19/2016.
 */
public class DataProvider extends ContentProvider{

    private static final String TAG = DataProvider.class.getName();
    DatabaseHelper mOpenHelper = null;
    private static UriMatcher uriMatcher = buildUriMatcher();

    private static final int ENGLISH_ID = 111;
    private static final int ENGLISH_LIST = 112;

    private static final int SUBJECT_1_ID = 113;
    private static final int SUBJECT_1_LIST = 114;

    private static final int SUBJECT_2_ID = 115;
    private static final int SUBJECT_2_LIST = 116;

    private static final int SUBJECT_3_ID = 117;
    private static final int SUBJECT_3_LIST = 118;

    private static final int PASSAGE_ID = 119;
    private static final int PASSAGE_LIST = 120;


    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DataContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, DataContract.PATH_ENGLISH, ENGLISH_LIST);
        matcher.addURI(authority, DataContract.PATH_ENGLISH+"/#", ENGLISH_ID);

         matcher.addURI(authority, DataContract.PATH_PASSAGE, PASSAGE_LIST);
        matcher.addURI(authority, DataContract.PATH_PASSAGE+"/#", PASSAGE_ID);

        matcher.addURI(authority, DataContract.PATH_SUBJECT_1, SUBJECT_1_LIST);
        matcher.addURI(authority, DataContract.PATH_SUBJECT_1+"/#", SUBJECT_1_ID);

         matcher.addURI(authority, DataContract.PATH_SUBJECT_2, SUBJECT_2_LIST);
        matcher.addURI(authority, DataContract.PATH_SUBJECT_2+"/#", SUBJECT_2_ID);

         matcher.addURI(authority, DataContract.PATH_SUBJECT_3, SUBJECT_3_LIST);
        matcher.addURI(authority, DataContract.PATH_SUBJECT_3+"/#", SUBJECT_3_ID);

        return matcher;

    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        int match = uriMatcher.match(uri);
        // The bulk of the setting is done inside DataProvider.buildSelection()
        final SelectionBuilder builder = buildSelection(uri, match);

        switch (match){
            case ENGLISH_LIST:
                if(TextUtils.isEmpty(sortOrder)){
                    sortOrder = DataContract.EnglishLanguage.SORT_ORDER_DEFAULT;
                }
                break;
            case PASSAGE_LIST:
                if(TextUtils.isEmpty(sortOrder)){
                    sortOrder = DataContract.EnglishPassage.SORT_ORDER_DEFAULT;
                }
                break;
            case SUBJECT_1_LIST:
                if(TextUtils.isEmpty(sortOrder)){
                    sortOrder = DataContract.Subject1.SORT_ORDER_DEFAULT;
                }
                break;
            case SUBJECT_2_LIST:
                if(TextUtils.isEmpty(sortOrder)){
                    sortOrder = DataContract.Subject2.SORT_ORDER_DEFAULT;
                }
                break;
            case SUBJECT_3_LIST:
                if(TextUtils.isEmpty(sortOrder)){
                    sortOrder = DataContract.Subject3.SORT_ORDER_DEFAULT;
                }
                break;

            default:
                break;
        }
        return builder.where(selection, selectionArgs).query(db, projection, sortOrder);
    }

    private SelectionBuilder buildSelection(Uri uri, int match) {

        final SelectionBuilder builder = new SelectionBuilder();
        switch (match) {
            case ENGLISH_LIST:
                return builder.table(DatabaseHelper.Tables.ENGLISH);
            case ENGLISH_ID: {
                final String id = uri.getLastPathSegment();
                return builder.table(DatabaseHelper.Tables.ENGLISH)
                        .where(DataContract.EnglishLanguage._ID + "=?", id);
            }
            case SUBJECT_1_LIST:
                return builder.table(DatabaseHelper.Tables.SUBJECT_1);
            case SUBJECT_1_ID: {
                final String id = uri.getLastPathSegment();
                return builder.table(DatabaseHelper.Tables.SUBJECT_1)
                        .where(DataContract.Subject1._ID + "=?", id);
            }
            case SUBJECT_2_LIST:
                return builder.table(DatabaseHelper.Tables.SUBJECT_2);
            case SUBJECT_2_ID: {
                final String id = uri.getLastPathSegment();
                return builder.table(DatabaseHelper.Tables.SUBJECT_2)
                        .where(DataContract.Subject2._ID + "=?", id);
            }
            case SUBJECT_3_LIST:
                return builder.table(DatabaseHelper.Tables.SUBJECT_3);
            case SUBJECT_3_ID: {
                final String id = uri.getLastPathSegment();
                return builder.table(DatabaseHelper.Tables.SUBJECT_3)
                        .where(DataContract.Subject3._ID + "=?", id);
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri for " + match);
            }
        }

    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch ( uriMatcher.match(uri) ) {
            case ENGLISH_LIST:
                return DataContract.EnglishLanguage.CONTENT_TYPE;
            case ENGLISH_ID:
                return DataContract.EnglishLanguage.CONTENT_ITEM_TYPE;
            case PASSAGE_LIST:
                return DataContract.EnglishPassage.CONTENT_TYPE;
            case PASSAGE_ID:
                return DataContract.EnglishPassage.CONTENT_ITEM_TYPE;
            case SUBJECT_1_LIST:
                return DataContract.Subject1.CONTENT_TYPE;
            case SUBJECT_1_ID:
                return DataContract.Subject1.CONTENT_ITEM_TYPE;
            case SUBJECT_2_LIST:
                return DataContract.Subject2.CONTENT_TYPE;
            case SUBJECT_2_ID:
                return DataContract.Subject2.CONTENT_ITEM_TYPE;
            case SUBJECT_3_LIST:
                return DataContract.Subject3.CONTENT_TYPE;
            case SUBJECT_3_ID:
                return DataContract.Subject3.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.v(TAG, "insert(uri=" + uri + ")");
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        long id = 0;
        boolean syncToNetwork = DataContract.hasCallerIsSyncAdapterParameter(uri);
        switch (match) {
            case ENGLISH_LIST: {
                id = db.insertOrThrow(DatabaseHelper.Tables.ENGLISH, null, values);
                notifyChange(uri, syncToNetwork);
                return getUriForId(id, uri);
            }
            case PASSAGE_LIST: {
                id = db.insertOrThrow(DatabaseHelper.Tables.PASSAGE, null, values);
                notifyChange(uri, syncToNetwork);
                return getUriForId(id, uri);
            }
            case SUBJECT_1_LIST: {
                id = db.insertOrThrow(DatabaseHelper.Tables.SUBJECT_1, null, values);
                notifyChange(uri, syncToNetwork);
                return getUriForId(id, uri);
            }
            case SUBJECT_2_LIST: {
                id = db.insertOrThrow(DatabaseHelper.Tables.SUBJECT_2, null, values);
                notifyChange(uri, syncToNetwork);
                return getUriForId(id, uri);
            }
            case SUBJECT_3_LIST: {
                id = db.insertOrThrow(DatabaseHelper.Tables.SUBJECT_3, null, values);
                notifyChange(uri, syncToNetwork);
                return getUriForId(id, uri);
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    private void notifyChange(Uri uri, boolean syncToNetwork) {
        Context context = getContext();
        context.getContentResolver().notifyChange(uri, null, syncToNetwork);

    }

    private static Uri getUriForId(long id, Uri uri) {
        if (id > 0) {
            return ContentUris.withAppendedId(uri, id);
        }
        throw new SQLException("Problem inserting into uri: " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.v(TAG, "delete(uri=" + uri + ", values=" + selectionArgs + ")");
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        // TODO: Handle signOut
        if (uri == DataContract.BASE_CONTENT_URI) {
            // Handle whole database deletes (e.g. when signing out)
            deleteDatabase();
            notifyChange(uri, false);
            return 1;
        }


        int match = uriMatcher.match(uri);

        if (match == -1 ) {
            deleteDatabase();
            notifyChange(uri, false);
            return 1;
        }

        final SelectionBuilder builder = buildSelection(uri, match);
        int value = builder.where(selection, selectionArgs).delete(db);
        notifyChange(uri, !DataContract.hasCallerIsSyncAdapterParameter(uri));
        return value;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.i(TAG, "update(uri=" + uri + ", values=" + values.toString() + ")");
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        Log.d(TAG, "uri and match in update : " + uri + "match" + match);

        final SelectionBuilder builder = buildSelection(uri, match);
        int value = builder.where(selection, selectionArgs).update(db, values);
        boolean syncToNetwork = !DataContract.hasCallerIsSyncAdapterParameter(uri);
        notifyChange(uri, syncToNetwork);
        return value;
    }

    private void deleteDatabase() {
        mOpenHelper.close();
        Context context = getContext();
        DatabaseHelper.deleteDatabase(context);
        mOpenHelper = new DatabaseHelper(getContext());
    }
}
