package hwk2.cis350.upenn.edu.stylo;


import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


/**
 * @author DanielMoreno
 *         This class is used to display the UI of the channels page when you swipe to it
 *         on the Menu.
 */


public class ChannelsFragment extends Fragment {

    protected RecyclerView recyclerView;
    protected ChannelsCustomAdapter channelsCustomAdapter;
    protected RecyclerView.LayoutManager layoutManager;
    protected ArrayList<Channel> channelDataSet;

    /**
     * This method initializes the downloading of the data once the Fragment is created
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        downloadChannels();
    }

    /**
     * Sets the correct UI for the fragment based on Layout File
     *
     * @param infl
     * @param cont
     * @param savInst
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater infl, @Nullable ViewGroup cont, @Nullable Bundle savInst) {
        View v = infl.inflate(R.layout.fragment_channels, cont, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.rv);
        layoutManager = new LinearLayoutManager(getActivity());
        channelsCustomAdapter = new ChannelsCustomAdapter(channelDataSet, getContext());

        /**
         * Sets the layout manager and the adapter of the fragment
         */
        recyclerView.setAdapter(channelsCustomAdapter);
        recyclerView.setLayoutManager(layoutManager);

        /**
         * Creates button that is used to create a channel from layout file
         */
        FloatingActionButton add = (FloatingActionButton) v.findViewById(R.id.addChannel);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewChannel();
            }
        });

        return v;
    }

    /**
     * This method downloads the Channels from Firebase. Our app doesn' have that functionality yet,
     * but this method will ideally load all of the users channels to display them on the screen
     */
    public void downloadChannels() {

        /*
        channelDataSet = new ArrayList<Channel>();
        List<String> temp = BackendAPI.getInstance().getChans();

        for (String k : temp) {

            Channel c = new Channel(k, "");
            channelDataSet.add(c);
        }
        */
        channelDataSet = BackendAPI.getInstance().channelObjects;

    }

    /**
     * Creates Channel Dialog Fragments so that user can create or join channels
     */

    public void joinChannel() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        NewChannelDialogFragment ncdf = new NewChannelDialogFragment();
        ncdf.setTargetFragment(this, 2);
        ncdf.show(fm, "name");
        channelsCustomAdapter.notifyDataSetChanged();

    }

    public void createNewChannel() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        NewChannelDialogFragment ncdf = new NewChannelDialogFragment();
        ncdf.setTargetFragment(this, 1);
        ncdf.show(fm, "name");
    }

    /**
     * Executes appropriate code depending on the result of the dialog in the previous methods
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //User is trying to create a new channel
        if (requestCode == 1) {
            String response = data.getStringExtra("name");
            Channel c = new Channel(response, "");

            c.setAdmin(GlobalData.getInstance().getUserID());
           // c.setAdminId(GlobalData.getInstance().getUserID());


            channelDataSet.add(c);
            BackendAPI.getInstance().createChannel(c);
            channelsCustomAdapter.notifyDataSetChanged();
        } else if (requestCode == 2) {
            // User is trying to find a channel that already exists
            String response = data.getStringExtra("name");
            Channel c = new Channel(response, "");

            //Log.v("id", GlobalData.getInstance().getUserID());
            c.setAdmin(GlobalData.getInstance().getUserID());
           // c.setAdminId(GlobalData.getInstance().getUserID());



            BackendAPI.getInstance().checkChannelExists(c);
            channelsCustomAdapter.notifyDataSetChanged();

        }
    }
}
