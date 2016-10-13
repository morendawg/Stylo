package hwk2.cis350.upenn.edu.stylo;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author DanielMoreno
 * This class is used to display the UI of the Feed page when you swipe to it
 * on the Menu.
 */
public class FeedFragment extends Fragment {

    protected RecyclerView recyclerView;
    protected FeedCustomAdapter feedCustomAdapter;
    protected RecyclerView.LayoutManager layoutManager;
//    protected ArrayList<Post> postDataSet2

    protected List<Post> postDataSet;

    /**
     * Initializes the downloading of outfits from FireBase
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        downloadImages();
    }

    /**
     * Creates the UI from the layout file for this fragment, and connects the appropriate managers
     * and recyclerView so that the outfits can be displayed
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feed, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.rv);
        layoutManager = new LinearLayoutManager(getActivity());
        feedCustomAdapter = new FeedCustomAdapter(postDataSet);
        recyclerView.setAdapter(feedCustomAdapter);
        recyclerView.setLayoutManager(layoutManager);
        return v;
    }

    /**
     * Creates two test Post objects. In the next iteration, this will download all of the user
     * posts from Firebase.
     */
    public void downloadImages(){
        Bitmap inputBitmap =  BitmapFactory.decodeResource(this.getResources(), R.drawable.pinkpromdress); // the image
        ByteArrayOutputStream inputImage = new ByteArrayOutputStream();
        inputBitmap.compress(Bitmap.CompressFormat.PNG, 0, inputImage);

        //convert image to byte array and then to a string
        byte[] byteArray = inputImage.toByteArray();
        String imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);
        Post p = new Post(imageFile, new Date(), "facebook:10207771886187658");
//        BackendAPI.getInstance().createPost("-KGUi5SrdxyC9EDDuqgQ", p);

        Map<String, List<Post>> allPosts = BackendAPI.getInstance().channelToPosts;
        List<Post> currChanPosts = allPosts.get("-KGUi5SrdxyC9EDDuqgQ");
        currChanPosts.addAll(allPosts.get("-KGUn2si819mq0SlYLS7"));

//        List<Post> currChanPosts = new LinkedList<Post>();
//        currChanPosts.add(new Post("dummy", new Date(), "facebook:10207771886187658"));
        postDataSet = currChanPosts;

    }

}
