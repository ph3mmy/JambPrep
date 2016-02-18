package ng.com.jcedar.jambprep.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.dd.CircularProgressButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;
import java.util.logging.Handler;

import ng.com.jcedar.jambprep.R;
import ng.com.jcedar.jambprep.helper.AppSettings;
import ng.com.jcedar.jambprep.helper.PrefUtils;
import ng.com.jcedar.jambprep.ui.BaseActivity;

/**
 * Created by oluwafemi.bamisaye on 2/11/2016.
 */
public class Login extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final String TAG = LoginActivity.class.getName();
    private static int RC_SIGN_IN = 0;


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

//       private SignInButton btnSignIn;
    CircularProgressButton btEnter;
    Button btnSignOut, btnRevokeAccess, btnSignIn;
    private ImageView imgProfilePic;
    private TextView txtName, txtEmail, title;
    private LinearLayout llProfileLayout, llWelcome, llField;
    ImageView g_icon;
    EditText etPhone, etName;
    LinearLayout buttonContainer;
    NetworkResponse response;
    String personPhotoUrl,personName;
    String email = "";
    private Boolean isSignedIn = false;
    static Bitmap bResult;
    Snackbar snackbar;
    private View snackView;
    Context context;
    String register_url = AppSettings.SERVER_URL+"register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
//        btnSignOut = (Button) findViewById(R.id.btn_sign_out);
//        btnRevokeAccess = (Button) findViewById(R.id.btn_revoke_access);
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
        btEnter = (CircularProgressButton) findViewById(R.id.btn_enter);
        btEnter.setIndeterminateProgressMode(true);

        // Button click listeners
        btnSignIn.setOnClickListener(this);
//        btEnter.setOnClickListener(this);
//        btnRevokeAccess.setOnClickListener(this);


        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // [END build_client]


    }

    @Override
    public void onStart() {
        super.onStart();


        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");/*
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);*/

            Intent dashbIntent = new Intent(this, ProfileActivity.class);
            dashbIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(dashbIntent);

        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
//            showProgressDialog();
            /*opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
//                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });*/
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }else {
//        Toast.makeText(this, "An Error Occured, Check your Network Connection Settings",Toast.LENGTH_SHORT).show();
//        btEnter.setVisibility(View.GONE);
//        btnSignIn.setVisibility(View.VISIBLE);
        }
    }
    // [END onActivityResult]

    // [START handleSignInResult]
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String emailPref = acct.getEmail();

            Log.d(TAG, " Handle signIn email of user" + emailPref);
            PrefUtils.setEmail(this, emailPref);
            //save Acct Name
            String namePref = acct.getDisplayName();
            PrefUtils.setPersonKey(this, namePref);


            Uri mPhoto = acct.getPhotoUrl();
            if (mPhoto != null) {

//                personPhotoUrl = acct.getPhotoUrl().toString();
                personPhotoUrl = mPhoto.toString();
                Log.e(TAG, "Profile Image" + personPhotoUrl);
                personPhotoUrl = personPhotoUrl.substring(0,
                        personPhotoUrl.length() - 2)
                        + PROFILE_PIC_SIZE;

                new LoadProfileImage().execute(personPhotoUrl);
            }else

            {
                Bitmap def = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

                PrefUtils.setPhoto(this, def);

                Log.e(TAG, "Converted Launcher ICOn   " + def);

                Toast.makeText(this, "Profile Loaded, Registering User",Toast.LENGTH_SHORT).show();

                registerUser();
            }

        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(this, "Ensure you are connected to a Network to be able to Sign In",Toast.LENGTH_SHORT).show();
//            signOutUser();
            RC_SIGN_IN++;
//            btEnter.setVisibility(View.GONE);
//            btnSignIn.setVisibility(View.VISIBLE);
            /*signOutUser();
            Toast.makeText(this, "An Error Occured, Check your Network Connection Settings",Toast.LENGTH_SHORT).show();
            btEnter.setVisibility(View.GONE);
            btnSignIn.setVisibility(View.VISIBLE);*/
//            updateUI(false);
        }
    }
    // [END handleSignInResult]

//    public void signOutUser()
//    {
//        Auth.GoogleSignInApi.signOut(mGoogleApiClient)
//                .setResultCallback(new ResultCallback<Status>() {
//                    @Override
//                    public void onResult(Status status) {
//                        Snackbar.make(snackView, "Enter your details and sign in",Snackbar.LENGTH_LONG).show();
//                    }
//                });
//    }

    private void signOutUser() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
//                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
        btnSignIn.setVisibility(View.VISIBLE);
    }
    // [END signOut]

    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage = (ImageView) findViewById(R.id.imgProfilePicNull);
        Bitmap bitmap = Login.bResult;

        public LoadProfileImage() {
//            this.bmImage = bmImage;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();/*
            btEnter.setVisibility(View.VISIBLE);
            btEnter.setIndeterminateProgressMode(true);
            btEnter.setProgress(50);*/
            showpDialog();
            Toast.makeText(Login.this, "Please Wait.....", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            hidepDialog();
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);

                Thread.sleep(1000);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }


            Log.e(TAG, "Bitmap returned" + mIcon11);
            return mIcon11;

        }

        protected void onPostExecute(Bitmap result) {
            this.bitmap=result;

            Log.e(TAG, "ONPOSTEXECUTE result    " +result);
            bmImage.setImageBitmap(result);
//            buttonContainer.setBackgroundResource(R.drawable.transparent_button);
//            btnSignIn.setProgress(0);
//            Toast.makeText(Login.this,"Profile Loaded", Toast.LENGTH_SHORT).show();
            if (result != null){
            PrefUtils.setPhoto(Login.this, result);
            String m = PrefUtils.encodeTobase64(result);
            Log.e(TAG, "Encoded Profile image " + m);


                registerUser();
            }else {
                /*Bitmap def = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
                PrefUtils.setPhoto(context,def);

                Log.e(TAG, "Converted Launcher ICOn   " + def);
                Toast.makeText(Login.this, "Invalid image", Toast.LENGTH_SHORT).show();*/
            }

//            registerUser();
        }
    }

    // [START signIn]
    private void signIn() {
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

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

        startActivityForResult(signInIntent, RC_SIGN_IN);
        btnSignIn.setVisibility(View.GONE);/*
        btEnter.setVisibility(View.VISIBLE);
        btEnter.setIndeterminateProgressMode(true);
        btEnter.setProgress(50);*/
        showpDialog();
        Toast.makeText(this, "Signing In" ,  Toast.LENGTH_SHORT).show();
    }
    // [END signIn]

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    private void registerUser() {
        String name = etName.getText().toString().trim().toLowerCase();
        String phone = etPhone.getText().toString().trim().toLowerCase();
        String email = PrefUtils.getEmail(this);
        String mName = PrefUtils.getPersonal(this);
        String rName = "";
        try {
            rName = URLEncoder.encode(mName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String uuid = UUID.randomUUID().toString();

        Log.e(TAG, "REGISTERING EMAIL " + email);

        register(name, phone, email, uuid, rName);
    }

    private void register(String name, String phone, String email, String uuid, String rName) {
        String urlSuffix = "?name=" + rName+":"+name + "&phone=" + phone + "&email=" + email + "&regId=" + uuid;
        class RegisterUser extends AsyncTask<String, Void, String> {
//            ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
               /* btEnter.setVisibility(View.VISIBLE);
                btEnter.setIndeterminateProgressMode(true);
                btEnter.setProgress(50);*/
                showpDialog();
                Toast.makeText(Login.this, "Registering User, Please be Patient", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader reader = null;
                Log.e(TAG, "INSIDE REGISTER ASYNC  "+register_url+s);
                try {
                    URL url = new URL(register_url + s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String result;
                    result = reader.readLine();

                    Log.e(TAG, "INSIDE REGISTER ASYNC  " + result);


                    return result;
                } catch (Exception e) {
                    return null;
                }
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                progressDialog.dismiss();
//                btEnter.setVisibility(View.GONE);
                Toast.makeText(Login.this, s, Toast.LENGTH_SHORT).show();
                hidepDialog();

                Log.e(TAG, "isSignedIn before Subject combination  " + isSignedIn);
                if (s.contains("success")){
                    try {
                        JSONObject json = new JSONObject(s);
                        String id = json.getString("id");
                        Log.e(TAG, "Printed ID   "  + id);
                        PrefUtils.setUniqueId(Login.this,id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    subjectCombination();
                }else

                    Log.e(TAG, "Signed out in doIn background  " + isSignedIn);
                    signOutUser();
            }
        }

        RegisterUser ru = new RegisterUser();
//        ru.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, urlSuffix);
//        ru.execute(urlSuffix);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            ru.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, urlSuffix);
        else
            ru.execute(urlSuffix);
    }

    private void subjectCombination(){
        Log.e(TAG,"INSIDE SUBJECT COMBINATION INTENT ");
//        isSignedIn = true;
        if (!isSignedIn){
            isSignedIn = true;
            Log.e(TAG,"INSIDE isSignedIn COMBINATION INTENT " + isSignedIn);

            Intent combinationIntent = new Intent(Login.this, CombinationActivity.class);
            combinationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(combinationIntent);
        }else
            signOutUser();
        btnSignIn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sign_in:
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
                signIn();

                break;
        }


    }

    // [START revokeAccess]
/*    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }*/
    // [END revokeAccess]

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showpDialog() {
        if (btEnter.getProgress() == 0) {
            btEnter.setVisibility(View.VISIBLE);
            btEnter.setProgress(50);
        }
    }

    private void hidepDialog() {
        if (btEnter.getProgress() == 100) {
            btEnter.setProgress(0);
            btEnter.setVisibility(View.GONE);
        }
    }
}
