package hwk2.cis350.upenn.edu.stylo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Objects;

/**
 * created by Meghana Jayam on 2/17/2016
 */
public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";
    private Firebase firebase;
    private String userId;
    private EditText nameET;

    /**
     * when this activity is launched, the name of the user should be retrieved and displayed
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Log.v(TAG, "in SettingsActivity");

        Intent received = getIntent();
        Bundle extras = received.getExtras();
        if (extras == null) {
            Log.v(TAG, "intent extras are null");
            return; //TODO: handle errors here somehow
        } else {
            Log.v(TAG, "intents are not null");
        }
        // get name from intent
        String nameString = (String) extras.get("displayName");
//        String pictureURL = (String) extras.get("profileImageURL");
        userId = (String) extras.get("uid");
        Log.v("HERE?", userId);

        GlobalData.getInstance().setUserID(userId);


        //settingsView.setNameAndPicture(nameString, pictureURL);
        //TextView nameTV = (TextView) findViewById(R.id.userName);
        nameET = (EditText) findViewById(R.id.editText);
        //nameTV.setText("Name: " + settingsView.getName());

        // change the name
        nameET.setText(nameString);

        //TextView name = (TextView) findViewById(R.id.userName);
        //name.setText("NAME: " + nameString);

        Log.v(TAG, "name is " + nameString);
//        setContentView(settingsView);




    }

    /**
     * update the backend to reflect the change
     * @param view
     */
    public void update(View view) {

        Log.v("HERE?", userId);
        HashMap<String, Object> update = new HashMap<String, Object>();
        update.put("displayName", nameET.getText().toString());

        Firebase usersRef = firebase.child("users");
        Firebase specificRef = usersRef.child(userId);
        specificRef.updateChildren(update);

    }

    /**
     * launch main activity
     * @param view
     */
    public void main(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}