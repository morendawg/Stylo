package hwk2.cis350.upenn.edu.stylo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by noah on 3/15/16.
 */
public class Comment implements Comparable<Comment> {
    private String message;
    private String author;
    private Date creationDate;

    public Comment(String message, String author, Date creationDate) {
        this.message = message;
        this.author = author;
        this.creationDate = creationDate;
    }

    public String getMessage() {
        return this.message;
    }

    public String getAuthor() {
        return this.author;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public int hashCode() {
        int hash = 17;
        hash = 31 * message.hashCode();
        hash = 31 * author.hashCode();
        hash = 31 * creationDate.hashCode();
        return hash;
    }
    public Map<String, String> getCommentAsMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("author", author);
        map.put("message", message);
        map.put("creationDate", creationDate.toString());
        return map;
    }
    public int compareTo(Comment that) {
        if (that == null) {
            throw new NullPointerException();
        }
        return this.creationDate.compareTo(that.creationDate);


    }
}

