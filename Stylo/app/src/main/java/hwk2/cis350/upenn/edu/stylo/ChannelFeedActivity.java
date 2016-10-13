package hwk2.cis350.upenn.edu.stylo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by DanielMoreno on 4/28/16.
 */
public class ChannelFeedActivity extends AppCompatActivity {
    protected RecyclerView recyclerView;
    protected FeedCustomAdapter feedCustomAdapter;
    protected RecyclerView.LayoutManager layoutManager;

    protected List<Post> postDataSet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        downloadImages();
        setContentView(R.layout.activity_feed);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        layoutManager = new LinearLayoutManager(this);
        feedCustomAdapter = new FeedCustomAdapter(postDataSet);
        recyclerView.setAdapter(feedCustomAdapter);
        recyclerView.setLayoutManager(layoutManager);
        // sets the adapter and adds a listener to the page swiper
        //swiper no swiping
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.channel_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_close:
                finish();
                break;
        }
        return true;
    }

    public void downloadImages() {

        Log.v("channelfeed", getIntent().getStringExtra("name"));

        postDataSet = new LinkedList<Post>();
//        String channID = "";
//        for (Channel c : BackendAPI.getInstance().channelObjects) {
//            if (c.getTitle().equals(getIntent().getStringExtra("name")))
//                channID = c.getUniqueId();
//        }

//        Log.v("channelfeed", channID);
        postDataSet = BackendAPI.getInstance().channelToPosts.get("-KGUi5SrdxyC9EDDuqgQ");
    }
}
