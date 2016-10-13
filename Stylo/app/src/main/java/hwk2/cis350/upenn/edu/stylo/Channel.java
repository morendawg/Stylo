package hwk2.cis350.upenn.edu.stylo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by noah on 3/15/16.
 */
public class Channel {
    private String uniqueId; // need to figure out with Firebase
    private String title;
    private String adminId;
    private List<Post> posts; // should change to posts id to retrieve from another part of DB
    private HashSet<String> userIds;

    public Channel(String title, String adminId) {
        this.title = title;
        this.adminId = adminId;
        this.posts = new LinkedList<Post>();
        this.userIds = new HashSet<String>();
        userIds.add(adminId);
    }

    public boolean addUser(String userId) {
        return userIds.add(userId);
    }
    public void setUniqueId(String id){
        this.uniqueId = id;
    }
    public void setAdmin(String s) {
        this.adminId = s;
    }


    public void changeTitle(String newTitle) {
        this.title = newTitle;
    }

    public String getUniqueId() {
        return this.uniqueId;
    }

    public HashSet<String> getUserIds() {
        return userIds;
    }

    public String getTitle() {
        return title;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public String getAdminId() {
        return adminId;
    }

    // checks if the user is an admin
    public boolean isAdmin(String someUserId) {
        return this.adminId.equals(someUserId);
    }

    // used to store channel in db
    public Map<String, String> getChannelAsMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("title", this.title);
        map.put("admin", this.adminId);
        return map;
    }

}

