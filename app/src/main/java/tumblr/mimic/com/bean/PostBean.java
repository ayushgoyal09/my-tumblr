package tumblr.mimic.com.bean;

/**
 * Created by Ayush on 9/21/2016.
 */

public class PostBean {
    String postCaption;
    String postImage;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PostBean(String id, String postCaption, String postImage) {
        this.postCaption = postCaption;
        this.postImage = postImage;
        this.id = id;
    }

    public String getPostCaption() {
        return postCaption;
    }

    public void setPostCaption(String postCaption) {
        this.postCaption = postCaption;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }
}
