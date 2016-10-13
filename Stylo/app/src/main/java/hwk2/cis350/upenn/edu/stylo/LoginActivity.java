package hwk2.cis350.upenn.edu.stylo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LOGIN ACTIVITY";
    private Intent toSettings;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private TextView info;
    private AccessTokenTracker accessTokenTracker;
    private Firebase theBase;
    private String accessToken;
    private String displayName;
    private Map<String, Object> map;
    private AuthData mAuthData;

    /**
     * when this activity is launched, it should connect with the backend
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);

        theBase = new Firebase("https://new-stylo-350.firebaseio.com/");

        GlobalData.getInstance().setUserID("facebook:10207771886187658");
        BackendAPI.getInstance().getChannels(GlobalData.getInstance().getUserID());
        BackendAPI.getInstance().getChannelPosts("-KGUn2si819mq0SlYLS7");
        BackendAPI.getInstance().getChannelPosts("-KGUi5SrdxyC9EDDuqgQ");

        FacebookSdk.sdkInitialize(getApplicationContext());
        //theBase = new Firebase(getString(R.string.firebase_uri));
        setContentView(R.layout.activity_login);
        //toSettings = new Intent(this, SettingsActivity.class);
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.i(TAG, "Facebook.AccessTokenTracker.OnCurrentAccessTokenChanged");
                LoginActivity.this.onFacebookAccessTokenChange(currentAccessToken);
            }
        };

        // TESTING

        /*
        final BackendAPI back = BackendAPI.getInstance();
        final AlertDialog a = new AlertDialog.Builder(this)
                .setMessage("Loading ")
                .show();
        final Channel c = new Channel("Voodoo Child", "facebook:10153695704764584"); //test
        final Map<String, String> data = new HashMap<String, String>();

        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return null;
            }
            @Override
            protected void onPostExecute(String result) {
                a.dismiss();
            }
        };
        task.execute();
        */


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                accessToken = loginResult.getAccessToken().getToken();
            }

            @Override
            public void onCancel() {
                Log.d("Result", "Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Result", "Login attempt failed.");
            }
        });

    }

    /**
     * update backend when a chnge is made
     * @param token is the user's unique id
     */
    private void onFacebookAccessTokenChange(AccessToken token) {

        if (token != null) {
            theBase.authWithOAuthToken("facebook", token.getToken(), new Firebase.AuthResultHandler() {

                @Override
                public void onAuthenticated(AuthData authData) {
                    //BackendAPI.getInstance().getChannels("facebook:10153695704764584");


                    map = new HashMap<String, Object>();
                    if (authData.getProviderData().containsKey("displayName")) {
                        displayName = authData.getProviderData().get("displayName").toString();
                        map.put("displayName", displayName);
                    }

                    if (authData.getProviderData().containsKey("profileImageURL")) {
                        map.put("profileImageURL", authData.getProviderData().get("profileImageURL").toString());
                    }
                    if (authData.getProviderData().containsKey("email")) {
                        map.put("email", authData.getProviderData().get("email").toString());
//                        toSettings.putExtra("email", authData.getProviderData().get("email").toString());
                    }


                    theBase.child("users").child(authData.getUid()).updateChildren(map);
                    /*BackendAPI back = BackendAPI.getInstance();
                    Channel c = new Channel("Testing Chan", authData.getUid());
                    back.createChannel(c);
                    back.createPost(c.getUniqueId(), new Post("imgurl", new Date(), authData.getUid()));
                    */
                    //  Log.v("UserID ", authData.getUid());

                    //toSettings.putExtra("uid", "" + authData.getUid());

                    GlobalData.getInstance().setUserID(authData.getUid());

                    // The Facebook user is now authenticated with your Firebase app

                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    Log.e(TAG, "ERROR AUTH");
                }

            });

            // refresh data
            theBase.addAuthStateListener(new Firebase.AuthStateListener() {
                @Override
                public void onAuthStateChanged(AuthData authData) {
                    // Log.v(TAG, "Auth State Changed");
                    if (authData != null) {
                        loginButton.setVisibility(View.GONE);
                        if (authData.getProviderData().containsKey("displayName")) {
                            // toSettings.putExtra("displayName", authData.getProviderData().get("displayName").toString());
                        }
                        if (authData.getProviderData().containsKey("profileImageURL")) {
                            //  toSettings.putExtra("profileImageURL", authData.getProviderData().get("profileImageURL").toString());
                        }
                        if (authData.getProviderData().containsKey("email")) {
                            // toSettings.putExtra("email", authData.getProviderData().get("email").toString());
                        }

                        //toSettings.putExtra("uid", "" + authData.getUid());

                    } else {
                        loginButton.setVisibility(View.VISIBLE);
                    }
                    mAuthData = authData;
                    supportInvalidateOptionsMenu();
                    //startActivity(toSettings);
                }

            });


        } else {
        /* Logged out of Facebook so do a logout from the Firebase app */
            theBase.unauth();
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Intent intent = new Intent();
        intent.putExtra("AccessToken", accessToken);

        setResult(RESULT_OK, intent);
//        finish();
    }

    public void proceed(View view) {
        //gonna test the menu
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    public void like(View view) {

        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
        finish();

    }

    public void skip(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        finish();

    }
}