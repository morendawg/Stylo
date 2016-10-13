package hwk2.cis350.upenn.edu.stylo;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by noah on 3/15/16.
 */
public class Post implements Comparable<Post> {
    private String imageURL;
    private String dataPosted;
    private List<Comment> comments;
    private String uniqueId;
    private int upvotes;
    private String userId;

    public Post() {

    }

    public Post(String imageURL, Date dataPosted, String userId) {
        this.imageURL = imageURL;
        this.dataPosted = dataPosted.toString();
        this.comments = new LinkedList<Comment>();
        this.upvotes = 0;
        this.userId = userId;
    }
    public String getImageURL() {
        return this.imageURL;
    }
    public String getDataPosted() {
        return this.dataPosted.toString();
    }

    public String getUserId() {
        return userId;
    }

    public String getUniqueId() {
        return uniqueId;
    }
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void increment() {
        upvotes += 1;
    }

    public int hashCode() {
        int hash = 17;
        hash = 31 * imageURL.hashCode();
        hash = 31 * dataPosted.hashCode();
        return hash;
    }

    // used to store channel in db

    public Map<String, String> getPostAsMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("imageURL", imageURL);
        map.put("dataPosted", dataPosted.toString());
        map.put("upvotes", upvotes + "");
        map.put("uniqueId", uniqueId);
        map.put("userId", userId);
        return map;

    }
    // sort by upvotes!
    public int compareTo(Post that) {
        if (that == null) {
            throw new NullPointerException();
        }
        return this.upvotes - that.upvotes;

    }

    public String toString() {
        return this.uniqueId + " " + this.imageURL;
    }


}
