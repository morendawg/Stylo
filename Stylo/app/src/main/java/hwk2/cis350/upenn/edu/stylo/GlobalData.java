package hwk2.cis350.upenn.edu.stylo;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parthchopra on 4/7/16.
 *
 * Stores all the data for each instance of the app.
 */
public final class GlobalData {

    //stores the FB id of the user
    private String userID;

    public List<Bitmap> bitmaps = new ArrayList<Bitmap>();
    //list of bitmaps

    public List<Post> posts = new ArrayList<Post>();


    /**
     * Singleton design pattern used for efficiency
     */
    private static class GlobalDataSingleton {

        private static final GlobalData INSTANCE = new GlobalData();
    }

    /**
     * Returns the singleton instance of the GlobalData, in line with
     * singleton design patterns
     * @return
     */
    public static GlobalData getInstance() {
        return GlobalDataSingleton.INSTANCE;
    }


    /**
     * Sets the user id (==FB id of the user)
     * @param s
     */
    public void setUserID(String s) {

        userID = s;
    }

    /**
     * Getter method
     * @return
     */
    public String getUserID() {
        return userID;
    }

    public void addBitmap(Bitmap b) {
        bitmaps.add(b);
    }

    public List<Bitmap> getBitmaps() {
        return bitmaps;
    }

    public void addPosts(Post input) {
        posts.add(input);
    }
}
