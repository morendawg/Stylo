package hwk2.cis350.upenn.edu.stylo;

import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

import java.util.HashMap;

/**
 * This class creates a fragment of the settings page as a part of the menu (see menu activity)
 */
public class SettingsFragment extends Fragment {

    private static final String TAG = "SettingsActivity";
    private Firebase firebase;
    private String userId;
    private EditText nameET;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View v = inflater.inflate(R.layout.activity_settings, container, false);
        Button update = (Button) v.findViewById(R.id.updateBtn);
        userId = GlobalData.getInstance().getUserID();
        Log.v("Hey", " "+userId);
        nameET = (EditText) v.findViewById(R.id.editText);
        nameET.setText(GlobalData.getInstance().getUserID());
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(v);
            }
        });
        GlobalData.getInstance().setUserID(userId);
        return v;
    }

    /**
     * update the backend to reflect the change
     * @param view
     */
    public void update(View view) {
        HashMap<String, Object> update = new HashMap<String, Object>();
        update.put("displayName", nameET.getText().toString());
        Firebase usersRef = firebase.child("users");
        Firebase specificRef = usersRef.child(userId);
        specificRef.updateChildren(update);

    }

}
