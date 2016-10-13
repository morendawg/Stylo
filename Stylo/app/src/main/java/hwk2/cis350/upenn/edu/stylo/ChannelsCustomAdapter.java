package hwk2.cis350.upenn.edu.stylo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * @author Daniel Moreno
 * This class allows channels to be displayed by creating a Recycler View
 */

/**
 * Provide views to the RecyclerView with data from channelDataSet.
 */
public class ChannelsCustomAdapter extends RecyclerView.Adapter<ChannelsCustomAdapter.ChannelViewHolder> {
    private ArrayList<Channel> channelDataSet;
    private Context context;
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     * This allows us to define and manipulate the fields of the Layout that represents
     * a channel in our application.
     */
    public static class ChannelViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView channelName;
        TextView numOutfits;
        Button leave;


        ChannelViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cv);
            channelName = (TextView)itemView.findViewById(R.id.channel_name);
            numOutfits = (TextView)itemView.findViewById(R.id.numOutfits);
            leave = (Button)itemView.findViewById(R.id.leave);
        }

        public Button getLeaveButton(){
            return leave;
        }

    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public ChannelsCustomAdapter(ArrayList<Channel> dataSet, Context context) {
        this.context = context;
        channelDataSet = dataSet;
    }

    //This creates the new custom Views for the Adapter
    @Override
    public ChannelViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.channel_layout, viewGroup, false);
        ChannelViewHolder channelVH = new ChannelViewHolder(v);
        return channelVH;
    }

    // Replace the contents of a view based on the channel information
    @Override
    public void onBindViewHolder(final ChannelViewHolder viewHolder, final int position) {
        viewHolder.channelName.setText(channelDataSet.get(position).getTitle());
        viewHolder.numOutfits.setText("0 outfits");
        viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(context, ChannelFeedActivity.class);
                i.putExtra("name", viewHolder.channelName.getText());
                context.startActivity(i);
            }
        });
        viewHolder.getLeaveButton().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                leaveChannel("" + viewHolder.channelName.getText());
            }
        });
    }

    public void leaveChannel(String name){
        Channel c = null;
        for(Channel k : channelDataSet) {
            if(k.getTitle().equals(name)) {
                c = k;
            }
        }
        BackendAPI.getInstance().leaveChannel(c);
        channelDataSet.remove(c);
        this.notifyDataSetChanged();
    }

    // Return the size of channelDataSet
    @Override
    public int getItemCount() {

        return channelDataSet.size();
    }

}