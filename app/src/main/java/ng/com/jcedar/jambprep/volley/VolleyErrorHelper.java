package ng.com.jcedar.jambprep.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;

import ng.com.jcedar.jambprep.R;


public class VolleyErrorHelper {

    private static final String TAG = VolleyErrorHelper.class.getSimpleName();

    public static String getMessage(Object error, Context context){
        if (error instanceof TimeoutError){
            return context.getResources().getString(R.string.timed_out);
        }/*else if(isAuthError(error)){
            return handleClientError(error, context);
            //return context.getResources().getString(R.string.authentication_error);

        }*/
        else if(isServerError(error)){
            return handleServerError(error, context);

        }else if(isNetworkProblem(error)){

            //return context.getResources().getString(R.string.no_internet);
            return handleNetworkError(error, context);

        }else {
            return context.getResources().getString(R.string.generic_error);
        }
    }

    private static String handleServerError(Object error, Context context) {
        VolleyError volleyError = (VolleyError) error;
        NetworkResponse response = volleyError.networkResponse;
        if(response != null){
            Log.d(TAG, "" + response.statusCode);
            switch (response.statusCode) {
                case 400:
                    try {
                        HashMap<String, String> result =
                                new Gson().fromJson(new String(response.data),
                                        new TypeToken<HashMap<String, String>>() {
                                        }.getType());
                        return result.get("Message");
                    } catch (Exception e) {
                        e.printStackTrace();
                        return new String(response.data);
                    }

                case 404:
                case 401:
                    return new String(response.data);
                case 503:
                    //do a retry
                    return context.getResources().getString(R.string.server_error);
                case 201:
                    //successfully created
                    Log.d(TAG, "created");
                default:
                    return context.getResources().getString(R.string.generic_error);
            }

        }
        return null;
    }
    private static String handleNetworkError(Object error, Context context) {
        VolleyError volleyError = (VolleyError) error;
        String message = volleyError.getMessage();
        if (message.contains("No authentication challenges found")){
            return "Sign in to Basic to continue";
        }
        return context.getResources().getString(R.string.no_internet);
    }

    private static boolean isAuthError(Object error) {
        return (error instanceof AuthFailureError);
    }

    private static boolean isServerError(Object error) {
        return (error instanceof ServerError);
    }

    private static boolean isNetworkProblem(Object error){
        Log.d(TAG, error.toString());
        return (error instanceof NetworkError) || (error instanceof NoConnectionError);
    }

}
