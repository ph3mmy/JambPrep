package ng.com.jcedar.jambprep.volley;


import android.content.Context;
import android.net.Uri;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import ng.com.jcedar.jambprep.helper.AppSettings;

public class PrepApi {

    private final String TAG = getClass().getSimpleName();
    private static PrepApi mInstance;

    private static String API_BASE = AppSettings.SERVER_URL;

    public static PrepApi getInstance() {
        if (mInstance == null) {
            mInstance = new PrepApi();
        }
        return mInstance;
    }

    public static void cancelRequest(String tag) {
        RequestManager.getRequestQueue().cancelAll(tag);
    }


    public void getSubjects( String subjectUrl, Response.Listener<String> onSuccessCallback,
                          Response.ErrorListener onErrorCallback) {

        StringRequest stringRequest
                = new StringRequest(Request.Method.GET, subjectUrl, onSuccessCallback, onErrorCallback) {

        };
        RequestManager.getRequestQueue().add(stringRequest);
    }



   /* public static void pushGCMDataToServer(String endpoint, final GCMUser gcmUser, Context context,
                                    Response.Listener<String> onSuccessCallback,
                                    Response.ErrorListener onErrorCallback){
        final String authToken = AccountUtils.getAuthToken(context);
        String gcmUserString = new Gson().toJson(gcmUser);

        String url = URI.create(endpoint).toString();
        StringRequest stringRequest
                = new StringRequest(Request.Method.POST, url, onSuccessCallback, onErrorCallback) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", authToken);
                header.put("content-type", "application/json");

                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("device", gcmUser.getDevice());
                params.put("os_version", gcmUser.getVersion());
                params.put("reg_id", gcmUser.getRegId());

                return params;
            }
        };

        RequestManager.getRequestQueue().add(stringRequest);

        }*/


}
