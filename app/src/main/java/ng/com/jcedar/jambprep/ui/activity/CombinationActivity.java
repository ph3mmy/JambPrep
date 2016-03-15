package ng.com.jcedar.jambprep.ui.activity;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dd.CircularProgressButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import ng.com.jcedar.jambprep.R;
import ng.com.jcedar.jambprep.helper.AppSettings;
import ng.com.jcedar.jambprep.helper.PrefUtils;
import ng.com.jcedar.jambprep.jsonhandlers.QuestionHandler;
import ng.com.jcedar.jambprep.provider.DataContract;
import ng.com.jcedar.jambprep.ui.BaseActivity;
import ng.com.jcedar.jambprep.volley.PrepApi;

/**
 * Created by oluwafemi.bamisaye on 2/3/2016.
 */
public class CombinationActivity extends BaseActivity {

    private static final String TAG = CombinationActivity.class.getSimpleName();

    Snackbar snackbar;

    HashMap<String, Integer> selection = new HashMap<String, Integer>();
    String questionUrl;
    CircularProgressButton pDialog;
    final Context mContext = CombinationActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combination);

        pDialog = (CircularProgressButton) findViewById(R.id.pdialog);
        pDialog.setIndeterminateProgressMode(true);


    }

    public void selectItems(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        selection.put("English Language", 2);

        switch (view.getId()) {
            case R.id.accounting:
                if (checked) {
                    selection.put("Accounting", 10);
                } else {
                    selection.remove("Accounting");
                }
                break;

            case R.id.agric:
                if (checked) {
                    selection.put("Agricultural Science", 14);
                } else {
                    selection.remove("Agricultural Science");
                }
                break;

            case R.id.biology:
                if (checked) {
                    selection.put("Biology", 5);
                } else {
                    selection.remove("Biology");
                }
                break;
            case R.id.chemistry:
                if (checked) {
                    selection.put("Chemistry", 3);
                } else {
                    selection.remove("Chemistry");
                }
                break;
            case R.id.comm:
                if (checked) {
                    selection.put("Commerce", 9);
                } else {
                    selection.remove("Commerce");
                }
                break;

            case R.id.crk:
                if (checked) {
                    selection.put("Christian Religious Knowledge", 12);
                } else {
                    selection.remove("Christian Religious Knowledge");
                }
                break;
            case R.id.economics:
                if (checked) {
                    selection.put("Economics", 8);
                } else {
                    selection.remove("Economics");
                }
                break;
            case R.id.geography:
                if (checked) {
                    selection.put("Geography", 6);
                } else {
                    selection.remove("Geography");
                }
                break;

            case R.id.govt:
                if (checked) {
                    selection.put("Government", 11);
                } else {
                    selection.remove("Government");
                }
                break;
            case R.id.literature:
                if (checked) {
                    selection.put("Literature-In-English", 7);
                } else {
                    selection.remove("Literature-In-English");
                }
                break;
            case R.id.maths:
                if (checked) {
                    selection.put("Mathematics", 1);
                } else {
                    selection.remove("Mathematics");
                }
                break;

            case R.id.physics:
                if (checked) {
                    selection.put("Physics", 4);
                } else {
                    selection.remove("Physics");
                }
                break;
        }
    }

    public void FinalSelection(View view) {

        /*FragmentManager manager = getFragmentManager();
        selection.clear();
        c.show(manager, "combi Dialog");*/

    }

    public void selection(View view) {

        final Uri.Builder uriBuilder = Uri.parse(AppSettings.SERVER_URL).buildUpon()
                .appendPath("InsertUserSubject.php");

        ArrayList<Integer> selectedIds = new ArrayList<>();
        final Integer[] selected;
        String final_fruit_selection = "";

        if (selection.size() == 4) {
            /*for (Integer selections : selection.values()) {
                final_fruit_selection = final_fruit_selection + selections + "\n";

            }*/
            selected = selection.values().toArray(new Integer[0]);
            String result = selection.toString();
            Toast.makeText(this, "Your selected Subjects are \n" + result, Toast.LENGTH_SHORT).show();
            String uId = PrefUtils.getUniqueId(this);

            int urlIndex = 1;
            for (Integer aSelected : selected) {
                if (aSelected == 2) continue;
                uriBuilder.appendQueryParameter("subject" + urlIndex, aSelected + "");
                urlIndex++;
            }
            uriBuilder.appendQueryParameter("userId", uId);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.create().setMessage("You have selected " + selection + "\nAre you sure you want to continue?");
            builder.setTitle("Selected Subjects")
                    .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    questionUrl = uriBuilder.build().toString();
                    Log.e(TAG, "Converted questionUrl  " + questionUrl);
                    makeSubjectRequest( questionUrl );

                }
            }).setNegativeButton("Change", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    uriBuilder.clearQuery();
                    Log.e(TAG, "Converted questionUrl clear " + uriBuilder.build().toString());
                }
            }).show();


        }else
            snackbar.make(view,"You can only select 4 Subjects",Snackbar.LENGTH_SHORT).show();
    }

    public void makeSubjectRequest(String url){
        Log.d(TAG, "inside make subject   " + url);

        showDialog();

        Response.Listener<String> onSuccess = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //parse something







                pullAndSaveQuestions(s);
                hideDialog();
                startActivity( new Intent( mContext, ProfileActivity.class));
                finish();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Error: " + volleyError.getMessage());
                hideDialog();

            }
        };
        PrepApi.getInstance().getSubjects(url, onSuccess, errorListener);
    }

    private void pullAndSaveQuestions(final String json) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray english, subject1, subject2, subject3;

                    english = jsonObject.getJSONArray("1");
                    Log.e(TAG, "array " +english.toString());

                    subject1 = jsonObject.getJSONArray("2");
                    Log.e(TAG, "array1 " +subject1.toString());

                    subject2 = jsonObject.getJSONArray("3");
                    Log.e(TAG, "array2 " +subject2.toString());

                    subject3 = jsonObject.getJSONArray("4");
                    Log.e(TAG, "array3 " +subject3.toString());


                    ArrayList<ContentProviderOperation> operation =
                            new QuestionHandler(mContext)
                                    .parse(english.toString(), 1);
                    if (operation.size() > 0) {
                        ContentResolver resolver = getContentResolver();
                        resolver.applyBatch(DataContract.CONTENT_AUTHORITY, operation);

                    }



                    ArrayList<ContentProviderOperation> operation1 =
                            new QuestionHandler(mContext)
                                    .parse(subject1.toString(), 2);
                    if (operation1.size() > 0) {
                        ContentResolver resolver = getContentResolver();
                        resolver.applyBatch(DataContract.CONTENT_AUTHORITY, operation1);

                    }

                      ArrayList<ContentProviderOperation> operation2 =
                            new QuestionHandler(mContext)
                                    .parse(subject2.toString(), 3);
                    if (operation2.size() > 0) {
                        ContentResolver resolver = getContentResolver();
                        resolver.applyBatch(DataContract.CONTENT_AUTHORITY, operation2);

                    }


                    ArrayList<ContentProviderOperation> operations =
                            new QuestionHandler(mContext)
                                    .parse(subject3.toString(), 4);
                    if (operations.size() > 0) {
                        ContentResolver resolver = getContentResolver();
                        resolver.applyBatch(DataContract.CONTENT_AUTHORITY, operations);

                    }


                    PrefUtils.markDataBootstrapDone( mContext );
                } catch (RemoteException | OperationApplicationException | JSONException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void showDialog() {
        if (pDialog.getProgress() == 0) {
            pDialog.setVisibility(View.VISIBLE);
            pDialog.setProgress(50);
        }
    }

    private void hideDialog() {
        if (pDialog.getProgress() == 100) {
            pDialog.setProgress(0);
            pDialog.setVisibility(View.GONE);
        }
    }

}


