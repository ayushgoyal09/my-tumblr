package tumblr.mimic.com.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static PostsDataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        initView();

        TumblrUtil task = new TumblrUtil(this);
        task.execute();

    }

    private void initView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        ArrayList posts = getData();
        adapter = new PostsDataAdapter(getApplicationContext(),posts);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList getData() {
        ArrayList posts = new ArrayList<>();
        for(int i=0;i<postImages.length;i++){
            PostBean post = new PostBean(postTitles[i],postImages[i]);
            posts.add(post);
        }

        return posts;
    }

    public  void updateAdapter(List<PostBean> postBeanList){
        adapter.notifyDataSetChanged();
    }


    public class TumblrUtil extends AsyncTask<Object, Object, List<PostBean>> {

        private Context context;

        public TumblrUtil(Context context){
            this.context = context;
        }

        @Override
        protected List<PostBean> doInBackground(Object... params) {
            JumblrClient client = new JumblrClient(
                    "DCx2S5yJ4M4dxZDFWBe5NdXsn0IVxpk8zlwTCv7VuiOYsNCw2D",
                    "6xEA1dbYbO7R9miIr2leJCRc1Xwd5jWV091px1mQDVNPYYYgll"
            );
            client.setToken(
                    "CkY55Vvy4o1NZIV4SqnuKmRvKIW4sr5lvKV3ikfpfgSmy6cFJ6",
                    "GSmiuP1rPtE0Z5mOe4ZnrpWLdH8TUdxgcQsDe7KkVOxM7vxAoS"
            );

            // Make the request
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("type", "photo");
            List<Post> posts = client.userDashboard(param);


            List<PostBean> postBeanList = new ArrayList<>();
            for (Post post : posts) {
                PhotoPost photoPost = (PhotoPost) post;
                String caption = photoPost.getCaption();
                String imageUrl = photoPost.getPhotos().get(0).getOriginalSize().getUrl();
                PostBean postBean = new PostBean(caption, imageUrl);
                postBeanList.add(postBean);
                System.out.println("POST - " + post.getPostUrl());
            }

            return postBeanList;
        }

        @Override
        protected void onPostExecute(List<PostBean> postBean) {
            PostsActivity.adapter.notifyDataSetChanged();        }
    }



}
