package hwk2.cis350.upenn.edu.stylo;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TestActivity extends AppCompatActivity {

    ImageView pulled; // for displaying purposes

    // for testing purposes
    String testImage;
    Bitmap testBitmap;

    /**
     * this method runs the storeImage method which contains the code to push and pull an image to
     * and from Firebase
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        RelativeLayout layout = new RelativeLayout(this);

        CustomImage image = new CustomImage(this, R.drawable.login);

        List<Bitmap> bmp = storeImage();
        pulled = new ImageView(this);

        layout.addView(pulled);
        setContentView(layout);

        Log.v("oncreate", "here");

    }

    /**
     * This method pushes an image that is converted to a string to firebase and then
     * retrieves that image from the backend and displays it.
     *
     * @return
     */
    public List<Bitmap> storeImage() {

        Bitmap inputBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.materialicon); // the image
        ByteArrayOutputStream inputImage = new ByteArrayOutputStream();
        inputBitmap.compress(Bitmap.CompressFormat.PNG, 0, inputImage);

        //convert image to byte array and then to a string
        byte[] byteArray = inputImage.toByteArray();
        String imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);

        testImage = imageFile;
        testBitmap = inputBitmap;

        String loadedImage = "";


       /* final BackendAPI back = new BackendAPI(getString(R.string.firebase_uri));
        back.addImage("facebook:10207771886187658", imageFile);
        Log.v("storeimage", "contact backend");
        // store this string in firebase
        final BackendAPI back = new BackendAPI(getString(R.string.firebase_uri));
//        back.addImage("facebook:10207771886187658", imageFile);

        // store this string in firebase

=======
        final BackendAPI back = new BackendAPI(getString(R.string.firebase_uri));
        back.addImage("facebook:10207771886187658", imageFile);
        Log.v("storeimage", "contact backend");

        // store this string in firebase

>>>>>>> origin/master
=======
        final BackendAPI back = new BackendAPI(getString(R.string.firebase_uri));
        back.addImage("facebook:10207771886187658", imageFile);
        Log.v("storeimage", "contact backend");

        // store this string in firebase

>>>>>>> origin/master
=======
        final BackendAPI back = new BackendAPI(getString(R.string.firebase_uri));
        back.addImage("facebook:10207771886187658", imageFile);
        Log.v("storeimage", "contact backend");

        // store this string in firebase

>>>>>>> origin/master

        // alert dialogue to show that work is in progress (backend is being accessed)
        final AlertDialog a = new AlertDialog.Builder(this)
                .setMessage("Loading ")
                .show();
        final List<String> imageStrings = new LinkedList<String>();
        final List<String> data = new LinkedList<String>();

        // asynctask object to access backend
        AsyncTask<Void, Void, List<String>> task = new AsyncTask<Void, Void, List<String>>(){
            @Override
            protected List<String> doInBackground(Void... params) {

                final Map<String, Object> temp = new HashMap<String, Object>();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final Firebase ref = new Firebase(getString(R.string.firebase_uri));

                // retrieve data from specfic nodes in firebase
                ref.child("images").child("facebook:10207771886187658").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildren() != null) {
                            // get each image under the user and display it
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                String im = (String) d.getChildren().iterator().next().getValue();
                                Log.v("trying", im);
                                data.add(im);

                                byte[] loadedBytes = Base64.decode(im, Base64.DEFAULT);
                                Bitmap loadedBitmap = BitmapFactory.decodeByteArray(loadedBytes, 0, loadedBytes.length);

                                //pulled.setImageBitmap(loadedBitmap);

                                GlobalData.getInstance().addBitmap(loadedBitmap);
                                //add images to global data arraylist

//                                pulled.setImageBitmap(loadedBitmap);

                            }

                            Bitmap drawnBitmap = Bitmap.createBitmap(5000, 5000, Bitmap.Config.ARGB_8888);
                            Canvas can = new Canvas(drawnBitmap);
                            int width = 0;
                            for(int i = 0; i < GlobalData.getInstance().getBitmaps().size(); i++) {
                                can.drawBitmap(GlobalData.getInstance().getBitmaps().get(i), width, 0, null);
                                width += GlobalData.getInstance().getBitmaps().get(i).getWidth();
                            }
                            pulled.setImageBitmap(drawnBitmap);

                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });

                return data;
            }
            @Override
            protected void onPostExecute(List<String> result) {
                imageStrings.addAll(result);
                a.dismiss(); // remove loading bar
            }
        };
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

<<<<<<< HEAD
<<<<<<< HEAD
        */

        return null;

    }

}
