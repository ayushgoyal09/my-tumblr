package tumblr.mimic.com.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import tumblr.mimic.com.adapter.PostsDataAdapter;
import tumblr.mimic.com.bean.PostBean;
import tumblr.mimic.com.myapplication.R;

public class PostsActivity extends AppCompatActivity {

    private final String postTitles[] = {
            "Donut",
            "Eclair",
            "Froyo",
            "Gingerbread",
            "Honeycomb",
            "Ice Cream Sandwich",
            "Jelly Bean",
            "KitKat",
            "Lollipop",
            "Marshmallow"
    };
    private final String postImages[] = {
            "http://api.learn2crack.com/android/images/donut.png",
            "http://api.learn2crack.com/android/images/eclair.png",
            "http://api.learn2crack.com/android/images/froyo.png",
            "http://api.learn2crack.com/android/images/ginger.png",
            "http://api.learn2crack.com/android/images/honey.png",
            "http://api.learn2crack.com/android/images/icecream.png",
            "http://api.learn2crack.com/android/images/jellybean.png",
            "http://api.learn2crack.com/android/images/kitkat.png",
            "http://api.learn2crack.com/android/images/lollipop.png",
            "http://api.learn2crack.com/android/images/marshmallow.png"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        initView();


    }

    private void initView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        ArrayList posts = getData();
        PostsDataAdapter adapter = new PostsDataAdapter(getApplicationContext(),posts);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList getData() {
        ArrayList posts = new ArrayList<>();
        for(int i=0;i<postImages.length;i++){
            PostBean post = new PostBean();
            post.setPostImage(postImages[i]);
            post.setPostTitle(postTitles[i]);
            posts.add(post);
        }

        return posts;
    }
}
