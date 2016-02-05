package ng.com.jcedar.jambprep.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import ng.com.jcedar.jambprep.R;
import ng.com.jcedar.jambprep.helper.PrefUtils;
import ng.com.jcedar.jambprep.ui.BaseActivity;

import static android.os.AsyncTask.*;


/**
 * Created by oluwafemi.bamisaye on 1/26/2016.
 */
public class LoginActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, View.OnClickListener {

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
    private TextView txtName, txtEmail;
    private LinearLayout llProfileLayout, llWelcome;
    EditText editText, etName;
    LinearLayout buttonContainer;
    NetworkResponse response;
    String personPhotoUrl,email,personName;
    Bitmap bResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btnSignIn = (CircularProgressButton) findViewById(R.id.btn_enter);
        btnSignOut = (Button) findViewById(R.id.btn_sign_out);
        btnRevokeAccess = (Button) findViewById(R.id.btn_revoke_access);
        imgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);
        txtName = (TextView) findViewById(R.id.txtName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        llProfileLayout = (LinearLayout) findViewById(R.id.llProfile);
        llWelcome = (LinearLayout) findViewById(R.id.llWelcomeIC);
        editText = (EditText) findViewById(R.id.etPhone);
        etName = (EditText) findViewById(R.id.etName_login);
        buttonContainer = (LinearLayout) findViewById(R.id.button_container);
//        btEnter = (Button) findViewById(R.id.btn_enter);

        // Button click listeners
        btnSignIn.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);
        btnRevokeAccess.setOnClickListener(this);
        btEnter.setOnClickListener(this);

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


    /**
     * Sign-in into google
     * */
    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }

    /**
     * Method to resolve any signin errors
     * */
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

    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
        updateUI(false);
    }

    /**
     * Updating the UI, showing/hiding buttons and profile layout
     * */
    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            btnSignIn.setVisibility(View.GONE);
            btnSignOut.setVisibility(View.VISIBLE);
            btnRevokeAccess.setVisibility(View.VISIBLE);
            llProfileLayout.setVisibility(View.VISIBLE);
            llWelcome.setVisibility(View.GONE);
        } else {
            btnSignIn.setVisibility(View.VISIBLE);
            btnSignOut.setVisibility(View.GONE);
            btnRevokeAccess.setVisibility(View.GONE);
            llProfileLayout.setVisibility(View.GONE);
            llWelcome.setVisibility(View.VISIBLE);
        }
    }


    /**
     * Fetching user's information name, email, profile pic
     * */
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

    /**
     * Background Async task to load user profile picture from url
     * */
    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
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
            PrefUtils.setPhoto(LoginActivity.this,result);

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
                String phoneString = editText.getText().toString();
                if (TextUtils.isEmpty(phoneString)){
                    // show snackbar to enter credentials
                    Snackbar.make(v, "Please enter your Phone Number", Snackbar.LENGTH_SHORT).show();
                    editText.setError("Phone Number cannot be empty");
                    focusView = editText;
                    cancel = true;
                }

                if (!(TextUtils.getTrimmedLength(phoneString) > 11)){
                    Snackbar.make(v, "Phone Number Cannot be less than 11 characters", Snackbar.LENGTH_SHORT).show();
                    editText.setError("Invalid Phone Number");
                    focusView = editText;
                    cancel = true;
                }

                if (TextUtils.isEmpty(nameString)){
                    // show snackbar to enter credentials
                    Snackbar.make(v, "Please enter your Alias or Name", Snackbar.LENGTH_SHORT).show();
                    editText.setError("Invalid Name");
                    focusView = editText;
                    cancel = true;
                }

                else
                //save gmail
//                btEnter.isEnabled();
                    signInWithGplus();

                break;
        }

    }

    /**
     * Revoking access from google
     * */

    private void sendDetails(){
        if (!(TextUtils.isEmpty(email)) || !(TextUtils.isEmpty(personPhotoUrl)) || !(TextUtils.isEmpty(personName))){

            PrefUtils.setEmail(this, email);
            PrefUtils.setPersonKey(this, personName);

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

    /**
     * Sign-out from google
     * */
    private void signOutFromGplus() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
            updateUI(false);
        }
    }

    class LoginCheck extends AsyncTask<String,Void,Void> {

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
    }


    public class PushToServer extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Loadin");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... params) {
            return null;
        }
    }

}
