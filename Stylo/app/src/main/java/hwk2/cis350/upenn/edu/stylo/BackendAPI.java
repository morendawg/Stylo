package hwk2.cis350.upenn.edu.stylo;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by noah on 3/15/16.
 */


/**
 *  This class manages our app's data in Firebase
 */
public final class BackendAPI {
    private Firebase base;
    List<String> chans;
    ArrayList<Channel> channelObjects;
    Map<String, List<Post>> channelToPosts;
    private List<String> imgs;
    private String userName;


    public BackendAPI(String url) {
        channelToPosts = new HashMap<String, List<Post>>();
        this.base = new Firebase(url);
        this.chans = new LinkedList<String>();
        this.channelObjects = new ArrayList<Channel>();
        this.imgs = new LinkedList<String>();
    }
    private static class BackendSingleton {
        private static final BackendAPI INSTANCE = new BackendAPI("https://new-stylo-350.firebaseio.com/");
    }
    public static BackendAPI getInstance() {
        return BackendSingleton.INSTANCE;
    }

    // adds an event listener that updates the local list of channels on startup
    public void getChannels(String userId) {
        Log.v("backend", "entered channels ");
        Log.v("backend", userId);
        final Firebase ref = base.child("users").child(userId).child("channels");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.v("backend", "found some channels!");
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    String chanId = postSnapShot.getKey();
                    chans.add(chanId);
                    getChannelPosts(chanId);
                }
                getChannelObjects();
                getAllChannelPosts();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void getChannelObjects() {

        final Firebase ref = base.child("channels");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.v("backend", "found some channels!");
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    Log.v("something", "" + postSnapShot.getKey());
                    if(chans.contains(postSnapShot.getKey())) {
                        String admin = "";
                        String title = "";
                        for(DataSnapshot k : postSnapShot.getChildren()) {
                            if(k.getKey() == "title")
                                title = (String) k.getValue();
                            else if (k.getKey() == "admin")
                                admin = (String) k.getValue();
                        }
                        Channel c = new Channel(title, admin);
                        channelObjects.add(c);
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    //  Adds an image to the Images section of the db
    public void addImage(String userId, String encodedImg) {
        final Firebase ref = base.child("images").child(userId);
        Map<String, String> data = new HashMap<String, String>();
        data.put("encoding", encodedImg);
        ref.push().setValue(data);
    }

    public void getImages(String userId) {
        Log.v("backend", "backend reached");
        final Firebase ref = base.child("images").child(userId).child("encoding");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    imgs.add(dataSnapshot.getValue().toString());
                    Log.v("backend", "reached ondatachange");

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.v("backend", "CANCELLED");
            }
        });
    }

    // creates a channel in the db

    //Tested

    public void createChannel(Channel c) {

        final Firebase channelRef = base.child("channels");
        final Firebase newChannelRef = channelRef.push(); // creates a new id for the channel

        Map<String, String> data = c.getChannelAsMap();
        newChannelRef.setValue(data, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    Log.v("Data", "Data could not be saved. " + firebaseError.getMessage());
                } else {
                    Log.v("Data", "Data saved successfully.");
                }
            }
        });
        c.setUniqueId(newChannelRef.getKey());  // get the unique ID created by push, set it in the channel

        // add channel to User data
        addChannelToUser(c);

    }

    public void addChannelToUser(Channel c) {

        final Firebase userChannelsRef = base.child("users").child(c.getAdminId()).child("channels");

        Map<String, Object> chans = new HashMap<String, Object>();
        chans.put(c.getUniqueId(), true);
        userChannelsRef.updateChildren(chans);
    }

    public void leaveChannel(Channel c) {

        final Firebase userChannelsRef = base.child("users").child(c.getAdminId()).child("channels");

        userChannelsRef.child("" + c.getUniqueId()).removeValue();
        base.child("channels").child("" + c.getUniqueId()).removeValue();

    }

    public void getAllChannelPosts() {

        for(String k : chans) {
            getChannelPosts(k);
        }
    }

    // Retrieves a list of posts for a given channel
    public void getChannelPosts(final String channelId) {
        // navigate to channel in db
        Firebase postsRef = base.child("channels").child(channelId).child("posts");
        Log.v("backend", "trying to get this channel (" + channelId + ")'s posts");
        final List<Post> posts = new LinkedList<Post>();
        Query q = postsRef.orderByKey();
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Post> data = (Map<String, Post>) dataSnapshot.getValue();
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    Post post = postSnapShot.getValue(Post.class);
                    Log.v("backend", "post's upvotes: "+ post.getUpvotes());
                    posts.add(post);
                }
                channelToPosts.put(channelId, posts);
                Log.v("backend", "got this channel (" + channelId + ")'s " + channelToPosts.get(channelId).size() + " posts");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("ChannelPostsReadError", firebaseError.getMessage());

            }
        });

    }

    // creates a post, adds the post's image to the images section of the db as well
    public String createPost(String channelId, Post p) {
        // add post to Channel "posts"
        Firebase postsRef = base.child("channels").child(channelId).child("posts");

        Firebase newPostRef = postsRef.push(); // creates a new unique location
        p.setUniqueId(newPostRef.getKey());
        Map<String, String> data = p.getPostAsMap();
        newPostRef.setValue(data);

        Firebase posts = base.child("posts").child(p.getUniqueId());
        data.put("channelId", channelId);
        posts.setValue(data);

        // add image to images
        this.addImage(p.getUserId(), p.getImageURL());
        return p.getUniqueId();

    }

    public void updateUpvotes(int upvotes, String uniquePostId) {
        Firebase postsRef = base.child("posts").child(uniquePostId);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("upvotes", upvotes);
        postsRef.updateChildren(data);

    }
    // for internal use only
    public boolean doesChannelExist(Channel c) {
        Firebase chan = base.child("channels").child(c.getUniqueId());

        chan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean exists = dataSnapshot.exists();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return true;
    }

    // for internal use only
    public void getUserName(String userId) {
        Log.v("backend", "entered username");
        final Firebase ref = base.child("users").child(userId).child("displayName");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userName = (String) dataSnapshot.getValue();
                Log.v("backend","THIS IS YOUR USERNAME " + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    // for internal use only
    private String getProfileImageURL(String userId) {
        final Firebase ref = base.child("users").child(userId).child("profileImageURL");
        final Map<String, String> data = new HashMap<String, String>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data.put("profileImageURL", dataSnapshot.getValue().toString());

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return data.get("profileImageURL");
    }


    // for internal use
    private List<Post> getPosts(String userId) {
        // Get a reference to our posts
        final Firebase ref = base.child(userId);

        // Attach an listener to read the data at our posts reference

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        return new LinkedList<Post>();
    }

    public void checkChannelExists(Channel c) {

        Firebase chan = base.child("channels").child(c.getUniqueId());

        chan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean exists = dataSnapshot.exists();
                channelExistsCallback(exists);
            }

            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void channelExistsCallback(boolean exists) {

    }

    public List<String> getChans() {
        return chans;
    }




}
