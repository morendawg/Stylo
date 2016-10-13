package hwk2.cis350.upenn.edu.stylo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * TODO: document your custom view class.
 */
public class SettingsView extends View {

    private TextView nameTV;
    private Bitmap picture;
    private Paint p = new Paint();

    private String name;
    private static final String TAG = "SettingsView";


    /**
     * created by Meghana Jayam on 2/17/2016
     */
    public SettingsView(Context context) {
        super(context);
    }

    public SettingsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onDraw(Canvas canvas) {

        if (picture != null) {
            canvas.drawBitmap(picture, 10, 175, null);
        }

        /*
        if(canvas != null) {
            nameTV = (TextView) findViewById(R.id.userName);
            //picture = getProfilePicture(pictureURL);
            Log.v("TESTING", "Name: " + name);
            //System.out.println(nameString +" "+ emailString +" "+ pictureURL);
            nameTV.setText("Name: " + name);
            invalidate();
        }
        */

//        nameTV.append("Name: " + name);
        invalidate();

    }

    public void setNameAndPicture(String nameString, String pictureURL) {
        Log.v("TESTING", "Name: " + nameString + " URL: " + pictureURL);

//        nameTV = (TextView) findViewById(R.id.userName);
        picture = getProfilePicture(pictureURL);
        name = nameString;


        //System.out.println(nameString +" "+ emailString +" "+ pictureURL);

//        nameTV.setText("Name: " + nameString);
        invalidate();

    }

    private Bitmap getProfilePicture(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        this.name = s;
    }

}