package com.narmware.homecare.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.slider.library.Transformers.RotateDownTransformer;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.narmware.homecare.MyApplication;
import com.narmware.homecare.R;
import com.narmware.homecare.helper.Constants;
import com.narmware.homecare.helper.SharedPreferencesHelper;
import com.narmware.homecare.helper.SupportFunctions;
import com.narmware.homecare.pojo.Login;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoogleLoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 007;
    private static final String TAG = LoginActivity.class.getSimpleName();

    String personName;
    String personPhotoUrl ;
    String email;

    @BindView(R.id.btn_signin) protected Button mBtnSignIn;
    Button mBtnFbSignIn;
    // @BindView(R.id.intro_pager) protected ViewPager mViewPager;
   // PagerAdapter mAdapter;
    RequestQueue mVolleyRequest;
    CallbackManager callbackManager;

    private static final String EMAIL = "email";

    private void requestPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE

                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}
            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_google_login);
        getSupportActionBar().hide();

        requestPermission();

        init();

        mBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        callbackManager = CallbackManager.Factory.create();
        mBtnFbSignIn=findViewById(R.id.btn_fb_signin);

        /*LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(GoogleLoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(GoogleLoginActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {

                        Toast.makeText(GoogleLoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        Log.e("Error",exception.toString());
                        // App code
                    }
                });*/


        /*mBtnFbSignIn.setReadPermissions(Arrays.asList("public_profile"));
        // Callback registration
        mBtnFbSignIn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(GoogleLoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                Log.e("Facebook data",loginResult.getAccessToken().getUserId());
               // LoginManager.getInstance().logInWithReadPermissions(GoogleLoginActivity.this, Arrays.asList("public_profile", "user_friends"));

                // App code
            }

            @Override
            public void onCancel() {
                Toast.makeText(GoogleLoginActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(GoogleLoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                // App code
            }
        });*/

        ProfileTracker profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                // App code
            }
        };
    }


    private void init() {
        ButterKnife.bind(this);
        mVolleyRequest = Volley.newRequestQueue(GoogleLoginActivity.this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }


/*
    public class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int index) {

            switch (index) {
                case 0:
                    return new IntroductionFragment().newInstance("title",R.drawable.bol85);
                case 1:
                    return new IntroductionFragment().newInstance("title1",R.drawable.bol85);
                case 2:
                    return new IntroductionFragment().newInstance("title2",R.drawable.bol85);
            }
            return null;
        }

        @Override
        public int getCount() {
            // get item count - equal to number of tabs
            return 3;
        }
    }
*/


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                    }
                });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                    }
                });
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

            try {
                personName = acct.getDisplayName();
                email = acct.getEmail();
                personPhotoUrl = acct.getPhotoUrl().toString();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            Log.e(TAG, "Name: " + personName + ", email: " + email
                    + ", Image: " + personPhotoUrl);

            if(personName==null || email==null)
            {
                Toast.makeText(this, "Unsupported format", Toast.LENGTH_SHORT).show();
            }

            else{

                if(personPhotoUrl==null)
                {
                    personPhotoUrl="http://narmware.com/kp/avatar.png";
                }
                else {

                }

                if(SharedPreferencesHelper.getIsLogin(GoogleLoginActivity.this)==false)
                {
                    LoginUser();
                }

            }


        } else {
            // Signed out, show unauthenticated UI.

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);

        }
        else {
            try {
                callbackManager.onActivityResult(requestCode, resultCode, data);
                Log.e("Facebook data", data + " ");
            } catch (Exception e) {

            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            //showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    //hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void LoginUser() {


        Gson gson=new Gson();

        Login login=new Login();
        login.setName(personName);
        login.setEmail(email);

        String json_string=gson.toJson(login);
        Log.e("Login data",json_string);

        HashMap<String,String> param = new HashMap();
        param.put(Constants.JSON_STRING,json_string);

        //url with params
        String url= SupportFunctions.appendParam(MyApplication.URL_REGISTER,param);

        //url without params
        //String url= MyApplication.GET_CATEGORIES;

        Log.e("Login url",url);
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {

                            Log.e("Login Json_string",response.toString());
                            Gson gson = new Gson();

                            Login loginResponse= gson.fromJson(response.toString(), Login.class);

                            int res= Integer.parseInt(loginResponse.getResponse());
                            if(res==Constants.NEW_ENTRY || res==Constants.ALREADY_PRESENT) {
                                SharedPreferencesHelper.setIsLogin(true,GoogleLoginActivity.this);
                                SharedPreferencesHelper.setBookName(personName,GoogleLoginActivity.this);
                                SharedPreferencesHelper.setBookMail(email,GoogleLoginActivity.this);
                                SharedPreferencesHelper.setUserId(loginResponse.getUser_id(),GoogleLoginActivity.this);

                                Intent intent = new Intent(GoogleLoginActivity.this, BottomNavigationActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            if(res==Constants.ERROR) {
                                Toast.makeText(GoogleLoginActivity.this, "Oops Something went wrong!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {

                            e.printStackTrace();
                            //Toast.makeText(NavigationActivity.this, "Invalid album id", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Test Error");
                        // showNoConnectionDialog();

                    }
                }
        );
        mVolleyRequest.add(obreq);
    }

}
