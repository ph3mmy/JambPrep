package ng.com.jcedar.jambprep.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.dd.CircularProgressButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
//import com.google.android.gms.plus.Plus;
//import com.google.android.gms.plus.model.people.Person;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ng.com.jcedar.jambprep.R;
import ng.com.jcedar.jambprep.helper.AppSettings;
import ng.com.jcedar.jambprep.helper.PrefUtils;
import ng.com.jcedar.jambprep.ui.BaseActivity;


/**
 * Created by oluwafemi.bamisaye on 1/26/2016.
 */
public class LoginActivity extends BaseActivity /*implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, View.OnClickListener*/ {

    private static final String TAG = LoginActivity.class.getName();
    private static final int RC_SIGN_IN = 0;

    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    /**
     * A flag indicating that a PendingIntent is in progress and prevents us
     * from starting further intents.
     */
    private boolean mIntentInProgress;

    private boolean mSignInClicked;

    private ConnectionResult mConnectionResult;

//    private SignInButton
            CircularProgressButton btnSignIn;
             Button btnSignOut, btnRevokeAccess, btEnter;
    private ImageView imgProfilePic;
    private TextView txtName, txtEmail, title;
    private LinearLayout llProfileLayout, llWelcome, llField;
    ImageView g_icon;
    EditText etPhone, etName;
    LinearLayout buttonContainer;
    NetworkResponse response;
    String personPhotoUrl,personName;
    String email = "";
    Boolean isSignedIn;
    Bitmap bResult;
    String register_url = AppSettings.SERVER_URL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        setContentView(R.layout.activity_login);


        btnSignIn = (CircularProgressButton) findViewById(R.id.btn_enter);
        btnSignOut = (Button) findViewById(R.id.btn_sign_out);
        btnRevokeAccess = (Button) findViewById(R.id.btn_revoke_access);
        imgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);
        g_icon = (ImageView) findViewById(R.id.google_icon);
        txtName = (TextView) findViewById(R.id.txtName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        title = (TextView) findViewById(R.id.title_text);
        llProfileLayout = (LinearLayout) findViewById(R.id.llProfile);
        llWelcome = (LinearLayout) findViewById(R.id.llWelcomeIC);
        llField = (LinearLayout) findViewById(R.id.llField);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etName = (EditText) findViewById(R.id.etName_login);
//        buttonContainer = (LinearLayout) findViewById(R.id.button_container);
//        btEnter = (Button) findViewById(R.id.btn_enter);

        // Button click listeners
        btnSignIn.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);
        btnRevokeAccess.setOnClickListener(this);
//        btEnter.setOnClickListener(this);

        // Initializing google plus api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
    }


    public void onStart() {
        super.onStart();


        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (!isConnected){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("No Internet Connectivity detected! Check your Internet Connectivity settings")
                    .setCancelable(false)
                    .setPositiveButton("Check Settings", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(Settings.ACTION_SETTINGS));
                        }
                    })
                    .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

            return;

        }else

        mGoogleApiClient.connect();
    }

    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }


    *//**
     * Sign-in into google
     * *//*
    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }

    *//**
     * Method to resolve any signin errors
     * *//*
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }


    @Override
    public void onConnected(Bundle arg0) {
        mSignInClicked = false;
        Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();

        // Get user's information
        getProfileInformation();

        // Update the UI after signin
        updateUI(true);

        Log.e(TAG, "SET email b4 registering in HIDEuI  " + email);
        if (!(email == "")){
            registerUser();
        }


    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
        updateUI(false);
    }

    *//**
     * Updating the UI, showing/hiding buttons and profile layout
     * *//*
    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
//            btnSignIn.setVisibility(View.GONE);
//            btnSignOut.setVisibility(View.VISIBLE);
//            btnRevokeAccess.setVisibility(View.VISIBLE);
//            llProfileLayout.setVisibility(View.VISIBLE);
            imgProfilePic.setVisibility(View.VISIBLE);
            llField.setVisibility(View.GONE);
            g_icon.setVisibility(View.GONE);
            etName.setVisibility(View.GONE);
            etPhone.setVisibility(View.GONE);
            txtName.setVisibility(View.VISIBLE);
            txtEmail.setVisibility(View.VISIBLE);
            title.setVisibility(View.GONE);


        } else {

            Toast.makeText(LoginActivity.this, "Check Your Network", Toast.LENGTH_SHORT).show();
//            btnSignIn.setVisibility(View.VISIBLE);
            btnSignOut.setVisibility(View.GONE);
            btnRevokeAccess.setVisibility(View.GONE);
            llProfileLayout.setVisibility(View.GONE);
            llWelcome.setVisibility(View.VISIBLE);
        }
    }


    *//**
     * Fetching user's information name, email, profile pic
     * *//*
    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                personName = currentPerson.getDisplayName();
                 personPhotoUrl = currentPerson.getImage().getUrl();
                String personGooglePlusProfile = currentPerson.getUrl();
                 email = Plus.AccountApi.getAccountName(mGoogleApiClient);

                Log.e(TAG, "Name: " + personName + ", plusProfile: "
                        + personGooglePlusProfile + ", email: " + email
                        + ", Image: " + personPhotoUrl);

                txtName.setText(personName);
                txtEmail.setText(email);

                // by default the profile url gives 50x50 px image only
                // we can replace the value with whatever dimension we want by
                // replacing sz=X
                personPhotoUrl = personPhotoUrl.substring(0,
                        personPhotoUrl.length() - 2)
                        + PROFILE_PIC_SIZE;

                new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);

            } else {
                Toast.makeText(getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    *//**
     * Background Async task to load user profile picture from url
     * *//*
    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            buttonContainer.setBackgroundResource(R.drawable.t_button);
            btnSignIn.setIndeterminateProgressMode(true);
            btnSignIn.setProgress(50);
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            buttonContainer.setBackgroundResource(R.drawable.transparent_button);
            btnSignIn.setProgress(0);
            Toast.makeText(LoginActivity.this,"Profile Loaded",Toast.LENGTH_SHORT).show();

//            btnSignIn.setEnabled(true);
            PrefUtils.setPhoto(LoginActivity.this, result);
            *//*String m = PrefUtils.encodeTobase64(result);
            Log.e(TAG, "Encoded Profile image " + m);*//*

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in:
                // Signin button clicked
//                signInWithGplus();
                break;
            case R.id.btn_sign_out:
                // Signout button clicked
                signOutFromGplus();
                break;
            case R.id.btn_revoke_access:
                // Revoke access button clicked
                revokeGplusAccess();
                break;
            case R.id.btn_enter:
                // Submit phone number and data

                boolean cancel = false;
                View focusView = null;
                String nameString = etName.getText().toString();
                String phoneString = etPhone.getText().toString();

                if (TextUtils.isEmpty(nameString) & TextUtils.isEmpty(phoneString)){
                    Snackbar.make(v, "Please enter your Alias and Phone Number", Snackbar.LENGTH_SHORT).show();
                    etName.setError("Name field cannot be empty");
                    etPhone.setError("Phone Number cannot be empty");

                }
                if (TextUtils.isEmpty(phoneString)){
                    // show snackbar to enter credentials
                    Snackbar.make(v, "Please enter your Phone Number", Snackbar.LENGTH_SHORT).show();
                    etPhone.setError("Phone Number cannot be empty");
                    focusView = etPhone;
                    cancel = true;
                    break;
                }

                if (!(TextUtils.getTrimmedLength(phoneString) > 10)){
                    Snackbar.make(v, "Phone Number Cannot be less than 11 characters", Snackbar.LENGTH_SHORT).show();
                    etPhone.setError("Invalid Phone Number");
                    focusView = etPhone;
                    cancel = true;
                    break;
                }

                if (TextUtils.isEmpty(nameString)){
                    // show snackbar to enter credentials
                    Snackbar.make(v, "Please enter your Alias or Name", Snackbar.LENGTH_SHORT).show();
                    etName.setError("Invalid Name");
                    focusView = etName;
                    cancel = true;
                    break;
                }

                else
                    for (int i = 0; i < 1000; i++) {
                        Log.e("waste some time ", "" + i);

                    }
                    signInWithGplus();

                break;
        }

    }

    *//**
     * Revoking access from google
     * *//*


    private void registerUser() {
        String name = etName.getText().toString().trim().toLowerCase();
        String phone = etPhone.getText().toString().trim().toLowerCase();
        String email = this.email;

        Log.e(TAG, "REGISTERING EMAIL " + email);

        register(name, phone, email);
    }

    private void register(String name, String phone, String email) {
        String urlSuffix = "?name=" + name + "&phone=" + phone + "&email=" + email;
        class RegisterUser extends AsyncTask<String, Void, String>{
//            ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                *//*super.onPreExecute();
                progressDialog.setMessage("Verifying email address....");
                progressDialog.setTitle("Please Wait");
                progressDialog.setCancelable(false);
                progressDialog.setIndeterminate(true);
                progressDialog.show();*//*

                buttonContainer.setBackgroundResource(R.drawable.t_button);
                btnSignIn.setIndeterminateProgressMode(true);
                btnSignIn.setProgress(50);
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader reader = null;
                try
                {
                    URL url = new URL(register_url+s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String result;
                    result = reader.readLine();

                    for (int i = 0; i < 1000; i++) {
                        Log.e("waste some time ", "" + i);

                    }

                    Log.e(TAG, "INSIDE REGISTER ASYNC  " + result);

                    return result;
                } catch (Exception e){
                    return null;
                }
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                progressDialog.dismiss();
                buttonContainer.setBackgroundResource(R.drawable.transparent_button);
                btnSignIn.setProgress(0);
                Toast.makeText(LoginActivity.this,s,Toast.LENGTH_SHORT).show();

                btnSignIn.setEnabled(true);
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(urlSuffix);

        saveDetails();
    }


    private void saveDetails(){
        if (!(TextUtils.isEmpty(email)) || !(TextUtils.isEmpty(personPhotoUrl)) || !(TextUtils.isEmpty(personName))){

            PrefUtils.setEmail(this, email);
            String m = PrefUtils.getEmail(this);
            PrefUtils.setPersonKey(this, personName);

            Log.e(TAG, "gotten email " + m + personName);

            Intent mIntent = new Intent(LoginActivity.this, CombinationActivity.class);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mIntent);
            finish();
        }
    }

    private void revokeGplusAccess() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                    .setResultCallback(new ResultCallback() {
                        @Override
                        public void onResult(Result result) {
                            Toast.makeText(getApplicationContext(),
                                    "User permissions revoked",
                                    Toast.LENGTH_LONG).show();
                            mGoogleApiClient.connect();
                            updateUI(false);
                        }

                    });

        }

    }

    *//**
     * Sign-out from google
     * *//*
    private void signOutFromGplus() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
            updateUI(false);
        }
    }*/

/*    class LoginCheck extends AsyncTask<String,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            buttonContainer.setBackgroundResource(R.drawable.t_button);
            btnSignIn.setIndeterminateProgressMode(true);
            btnSignIn.setProgress(50);
            // signIn.setEnabled(false);
        }

        @Override
        protected Void doInBackground(String... params) {

            //Looper.prepare();

//            checkLogin(params[0], params[1]);
            getProfileInformation();
            for (int i = 0; i < 1000; i++) {
                Log.e("waste some time ", "" + i);

            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            buttonContainer.setBackgroundResource(R.drawable.transparent_button);
//            if (session.isLoggedIn())
            {
                Intent intent = new Intent(LoginActivity.this, CombinationActivity.class);
                startActivity(intent);
                finish();
            }

//            else
            {
                btnSignIn.setProgress(0);
                showErrorMessages(response);

            }

            btnSignIn.setEnabled(true);
        }

    }


    //Somewhere that has access to a context
    public void displayMessage(String toastString) {
        Toast toast=Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        //toast.getView().setPadding(10, 10, 10, 10);
        TextView toastView=(TextView)toast.getView().findViewById(android.R.id.message);
        toastView.setTextColor(getResources().getColor(R.color.theme_primary));
        //toastView.setTextSize(12);
        toast.show();

    }


    //Method that extract 400 error message from Json
    public String trimMessage(String json, String key) {
        String trimmedString = null;
        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return trimmedString;
    }


    public void showErrorMessages(NetworkResponse response) {
        String json = null;
        if (response != null && response.data != null) {
            switch (response.statusCode) {
                case 400:
                    displayMessage("Login Authentication failed");
                    break;
                case 401:
                    displayMessage("Unauthorized User.");
                    break;
                case 404:
                    json = new String(response.data);
                    json = trimMessage(json, "msg");
                    if (json != null) displayMessage(json);
                    break;
                case 408:
                    displayMessage("Request timeout, Please try again");
                    break;
                case 500:
                    displayMessage("The server encountered an unexpected condition");
                    break;
                case 503:
                    displayMessage("The server is currently unable to handle the request.");
                    break;
                case 504:
                    displayMessage("The server gateway timeout.");
                    break;
                default:
                    displayMessage("Something happened, Try again");
            }
            //Additional cases
        } else {
            displayMessage("Server down, please try again");
        }
    }*/


/*    public class PushToServer extends AsyncTask<String, Void, String>{

    }*/

}}
