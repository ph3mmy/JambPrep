package ng.com.jcedar.jambprep.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by user1 on 11/07/2014.
 */
public class PrefUtils {
    public static  final String SOUND_KEY = "sounds";
    public static  final String VIBE_KEY = "vibration";
    public static  final String NOTIFY_KEY = "notification";

    public static  final String ACTIVATION_CODE = "";
    public static  final String ACTIVATION_STATUS = "activation_status";
    public static  final String INITIALIZATION_STATUS = "initialization_status";
    public static  final String POST_TO_SPRITE_STATUS = "post_to_sprite_status";
    public static  final String ACCOUNT_TYPE_STATUS = "ACCOUNT_TYPE_status";
    public static  final String CHOOSE_ACCOUNT_STATUS = "choose_account_status";
    public static  final String FAB_ICON_SHOW_STATUS = "fab_icon_show_status";
    public static  final String PREF_WELCOME_DONE = "welcome_done";
    public static  final String ACCOUNT_NOTIFY_KEY = "account";
    public static  final String PERSON_KEY = "personal_key";
    public static  final String EMAIL_KEY = "email_key";
    public static  final String PHOTO_KEY = "photo_key";
    public static  final String UNIQUE_KEY = "unique_id";




    public static boolean hasSound(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(SOUND_KEY, true); // by default we want sound
    }

    public static boolean hasVibration(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(VIBE_KEY, true); // by default we want vibes
    }

    public static boolean hasNotification(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(NOTIFY_KEY, true); // by default we are notified
    }

    public static boolean hasAccountNotification(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(ACCOUNT_NOTIFY_KEY, true); // by default we want vibes
    }


    public static void setActivationCode(Context context, String code){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(ACTIVATION_CODE, code).commit();
    }
    public static boolean isActivated(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(ACTIVATION_STATUS, false);
    }
    public static void setActivationStatus(Context context, boolean activationStatus){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putBoolean(ACTIVATION_STATUS, activationStatus).commit();
    }
    public static void setInitializationStatus(Context context, boolean initializationStatus){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putBoolean(INITIALIZATION_STATUS, initializationStatus).commit();
    }

    public static boolean isInitialized(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(INITIALIZATION_STATUS, false);
    }
    public static void setPostToSprite(Context context, boolean postToSprite){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putBoolean(POST_TO_SPRITE_STATUS, postToSprite).commit();
    }
    public static boolean isPostToSprite(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(POST_TO_SPRITE_STATUS, false);
    }
    public static void setAccountTypeToSavings(Context context, boolean accountType){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putBoolean(ACCOUNT_TYPE_STATUS, accountType).commit();
    }
    public static boolean isSavingsAccount(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(ACCOUNT_TYPE_STATUS, false);
    }
    public static void setUserHasChosenAccount(Context context, boolean hasChosenAccount){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putBoolean(CHOOSE_ACCOUNT_STATUS, hasChosenAccount).commit();
    }
    public static boolean userHasChosenAccount(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(CHOOSE_ACCOUNT_STATUS, false);
    }
    public static void setFabIconShowStatus(Context context, boolean ShowFabIcon){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putBoolean(FAB_ICON_SHOW_STATUS, ShowFabIcon).commit();
    }
    public static boolean isFabIconShown(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(FAB_ICON_SHOW_STATUS, false);
    }
    public static boolean isWelcomeDone(final Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_WELCOME_DONE, false);
    }

    public static void markWelcomeDone(final Context context){
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putBoolean(PREF_WELCOME_DONE, true).commit();
    }

    public static void setPersonKey(Context context, String key){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(PERSON_KEY, key).commit();
    }

    public static String getPersonal(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(PERSON_KEY, "0");
    }

    public static void setUniqueId(Context context, String key){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(UNIQUE_KEY, key).commit();
    }

    public static String getUniqueId(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(UNIQUE_KEY, "0");
    }

    public static void setEmail(Context context, String key){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(EMAIL_KEY, key).commit();
    }

    public static String getEmail(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(EMAIL_KEY, "0");
    }

    public static void setPhoto(Context context, Bitmap image){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(PHOTO_KEY, encodeTobase64(image)).commit();
    }

    public static String getPhoto(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(PHOTO_KEY, "0");
    }


    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    // method for base64 to bitmap
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

}
