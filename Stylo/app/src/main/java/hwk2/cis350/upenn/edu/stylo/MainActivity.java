package hwk2.cis350.upenn.edu.stylo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.firebase.client.Firebase;

/**
 * Created by parthchopra
 *
 */
public class MainActivity extends AppCompatActivity {

    private TextView accessToken;
    private Firebase firebaseReference;
    @Override
    /**
     * main onCreate method that sets up Firebase and instantiates the
     * first activity the user will see.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);

        firebaseReference = new Firebase(getString(R.string.firebase_uri));

        Channel c1 = new Channel("HELLO HELLO HELLO", "facebook:10153695704764584");
        BackendAPI back = BackendAPI.getInstance();
        System.out.println("BACKEND CHANS: " +  back.chans.get(0).toString());

        // back.createChannel(c1);

        setContentView(R.layout.activity_main);
        accessToken = (TextView) findViewById(R.id.accessToken);
        Intent logInIntent = new Intent(this, LoginActivity.class);
        startActivityForResult(logInIntent, 1);

    }

    @Override
    /**
     * Handles requestCode to allow optimal flow between activities
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
               accessToken.setText(data.getStringExtra("AccessToken"));
            }
        }
    }

    /**
     * Launches the Menu Activity when the button that implements
     * this method is clicked.
     * @param view
     */
    public void launchMenuActivity(View view){
        Intent menuIntent = new Intent(this, MenuActivity.class);
        startActivity(menuIntent);
    }


}
