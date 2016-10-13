package hwk2.cis350.upenn.edu.stylo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by DanielMoreno on 3/29/16.
 *
 * This class handles both the UI and logic for creating a new channel
 */

public class NewChannelDialogFragment extends DialogFragment {
    @Override

    /**
     * Creates the UI for the dialogue of creating a new channel (aka asking for
     * channel name and such) 
     */

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setMessage("Enter a name for your new channel:");
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String value = input.getText().toString();
                        Intent intent = new Intent();
                        intent.putExtra("name", value);
                        getTargetFragment().onActivityResult(getTargetRequestCode(), 1, intent);
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    /**
     * Creates a new instance for the fragment and adds it to the appropriate bundle
     * @return
     */
    public static NewChannelDialogFragment newInstance() {

        Bundle args = new Bundle();

        NewChannelDialogFragment fragment = new NewChannelDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
