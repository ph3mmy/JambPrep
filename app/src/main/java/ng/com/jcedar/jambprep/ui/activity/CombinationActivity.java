package ng.com.jcedar.jambprep.ui.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dd.CircularProgressButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import ng.com.jcedar.jambprep.R;
import ng.com.jcedar.jambprep.adapter.SubjectListAdapter;
import ng.com.jcedar.jambprep.helper.AppSettings;
import ng.com.jcedar.jambprep.helper.PrefUtils;
import ng.com.jcedar.jambprep.model.Subject;
import ng.com.jcedar.jambprep.ui.BaseActivity;
import ng.com.jcedar.jambprep.volley.AppController;

/**
 * Created by oluwafemi.bamisaye on 2/3/2016.
 */
public class CombinationActivity extends BaseActivity {

    private static final String TAG = CombinationActivity.class.getSimpleName();

    LinearLayout linearLayout;
    Button btProceed;

    Snackbar snackbar;
    private View view;
    Combi c = new Combi();

    HashMap<String, Integer> selection = new HashMap<String, Integer>();
    TextView final_text;
    String questionUrl;
    CircularProgressButton pDialog;
    String jsonResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combination);

        final_text = (TextView) findViewById(R.id.final_result);
        final_text.setEnabled(false);

        pDialog = (CircularProgressButton) findViewById(R.id.pdialog);
        pDialog.setIndeterminateProgressMode(true);


    }

    public void selectItems(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        selection.put("English Language", 2);
        switch (view.getId())

        {
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

        FragmentManager manager = getFragmentManager();
        selection.clear();
        c.show(manager, "combi Dialog");


        /*String final_fruit_selection = "";

        if (selection.size() == 4) {
            for (Integer selections : selection.values()) {
                final_fruit_selection = final_fruit_selection + selections + "\n";

            }
            String result = selection.toString();
            Toast.makeText(this, "Your selected Subjects are \n" + result, Toast.LENGTH_SHORT).show();
            final_text.setText(final_fruit_selection);
            final_text.setEnabled(true);
        }else
            snackbar.make(view,"You can only select 4 Subjects",Snackbar.LENGTH_SHORT).show();*/
    }

    public void Selection(View view) {


        ArrayList<Integer> selectedIds = new ArrayList<>();
        Integer[] selected;
        String final_fruit_selection = "";
//        selected = new Integer[4];
//        selected = selection.values();
//        selected = selectedIds.toArray( selected );

        if (selection.size() == 4) {
            for (Integer selections : selection.values()) {
                final_fruit_selection = final_fruit_selection + selections + "\n";

            }
             selected = selection.values().toArray(new Integer[0]);
            String result = selection.toString();
            Toast.makeText(this, "Your selected Subjects are \n" + result, Toast.LENGTH_SHORT).show();
            /*final_text.setText(final_fruit_selection);
            final_text.setEnabled(true);*/

            String uId = PrefUtils.getUniqueId(this);
            String forUrl="";
            int urlIndex = 1;
            for( int i=0; i < selected.length; i++ ){
                if ( selected[i] == 2) continue;
                if ( i == selected.length - 1)
                    forUrl += "subject"+urlIndex++ +"="+selected[i];
                else
                    forUrl += "subject"+urlIndex++ +"=" +selected[i]+"&";
            }
            Log.e(TAG, "Converted arrays  " + forUrl);
            questionUrl = AppSettings.SERVER_URL+"InsertUserSubject.php?"+forUrl+"&userId="+uId;
   /*         Intent dash = new Intent(CombinationActivity.this, ProfileActivity.class);

            Log.e(TAG, " question url  " + questionUrl);
            dash.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(dash);*/

            Log.e(TAG, "Converted questionUrl arrays  " + questionUrl);
            final_text.setText(questionUrl);
            c.dismiss();


            makeSubjectRequest();
//            requ(questionUrl);
        }else
            snackbar.make(view,"You can only select 4 Subjects",Snackbar.LENGTH_SHORT).show();
    }

    public void makeSubjectRequest(){

        Log.d(TAG, "inside make subject   ");

        RequestQueue rq = Volley.newRequestQueue(this);
        showpDialog();
        JsonArrayRequest subjectArrayRequest = new JsonArrayRequest(questionUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e(TAG, "inside make subject   " + jsonArray.toString());

                try {
                    // Parsing json array response
                    // loop through each json object
                    jsonResponse = "";
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject question = (JSONObject) jsonArray.get(i);

                        String year = question.getString("exam_year");
                        String questions = question.getString("questions");
                        String optionA = question.getString("option_a");
                        String optionB = question.getString("option_b");
                        String optionC = question.getString("option_c");
                        String optionD = question.getString("option_d");
                        String optionE = question.getString("option_e");
                        String answer = question.getString("answer");
                        String explanation = question.getString("explanation");
                        String photo = question.getString("photo");
                        String answerPhoto = question.getString("answer_photo");

                        jsonResponse += "Year: " + year + "\n\n";
                        jsonResponse += "Question: " + questions + "\n\n";
                        jsonResponse += "OptionD: " + optionD + "\n\n";
                        jsonResponse += "Answer: " + answer + "\n\n\n";

                    }

                    Log.e(TAG, "Printed JSONARRAY  " + jsonResponse);

//                    txtResponse.setText(jsonResponse);

                } catch (JSONException e) {
                    e.printStackTrace();
                    hidepDialog();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidepDialog();
                Toast.makeText(CombinationActivity.this,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        rq.add(subjectArrayRequest);

//        AppController.getInstance().addToRequestQueue(subjectArrayRequest);
    }

    private void showpDialog() {
        if (pDialog.getProgress() == 0) {
            pDialog.setVisibility(View.VISIBLE);
            pDialog.setProgress(50);
        }
    }

    private void hidepDialog() {
        if (pDialog.getProgress() == 100) {
            pDialog.setProgress(0);
            pDialog.setVisibility(View.GONE);
        }
    }

//    @Override
//    public void requ(String url) {
//        makeSubjectRequest(url);
//    }
}


