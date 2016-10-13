package hwk2.cis350.upenn.edu.stylo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Moreno
 * This class allows outfits to be displayed by creating a Recycler View that is populated by
 * Post objects
 */


/**
 * Provide views to RecyclerView with data from outfitDataSet.
 */
public class FeedCustomAdapter extends RecyclerView.Adapter<FeedCustomAdapter.OutfitViewHolder> {
    private static final String TAG = "FeedCustomAdapter";

    private List<Post> outfitDataSet;
    private int counter;

    /**
     * This defines a View Holder that is the visual representation of a post object
     * Once connected to Firebase, this will allow us to pull all of the information regarding
     * a post so that we can display it correctly
     */
    public static class OutfitViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView accountName;
        TextView numLikes;
        ImageView personPhoto;

        ImageView upvote;
        Post post;

        OutfitViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            accountName = (TextView)itemView.findViewById(R.id.account);
            numLikes = (TextView)itemView.findViewById(R.id.num_likes);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
            upvote = (ImageView) itemView.findViewById(R.id.upvote);

            upvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    post.increment();
                    numLikes.setText(post.getUpvotes() + "");

                    BackendAPI.getInstance().updateUpvotes(post.getUpvotes(), post.getUniqueId());

                }
            });

        }

        private Bitmap postBitmap(Post p) {
            //convert string to bitmap
            byte[] loadedBytes = Base64.decode(p.getImageURL(), Base64.DEFAULT);
            Bitmap loadedBitmap = BitmapFactory.decodeByteArray(loadedBytes, 0, loadedBytes.length);
            return loadedBitmap;
        }

        public void setPost(Post p) {

            post = p;
            try {
                personPhoto.setImageBitmap(postBitmap(p));
            } catch (NullPointerException e) {
                System.out.println("NULL IMAGE URL: " + post.getImageURL());
            }
        }

    }

    /**
     * Initialize the dataset of the Adapter. In the next iteration this will be populated
     * by the posts in the Firebase database.
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public FeedCustomAdapter(List<Post> dataSet) {
        outfitDataSet = dataSet;
    }

    // This creates the new Outfit Holders defined above
    @Override
    public OutfitViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.outfit_layout, viewGroup, false);
        OutfitViewHolder customImageVH = new OutfitViewHolder(v);
        customImageVH.setPost(outfitDataSet.get(counter));
        counter++;

        return customImageVH;
    }

    // Replace the contents of a view with data from FireBase. Not implemented yet.
    @Override
    public void onBindViewHolder(OutfitViewHolder viewHolder, final int position) { }

    // Return the size of the outfit data set
    @Override
    public int getItemCount() {

        return outfitDataSet.size();
    }


}