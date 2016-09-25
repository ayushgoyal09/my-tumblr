package tumblr.mimic.com.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tumblr.mimic.com.adapter.PostsDataAdapter;
import tumblr.mimic.com.bean.PostBean;
import tumblr.mimic.com.myapplication.R;
import tumblr.mimic.com.util.DividerUtil;
import tumblr.mimic.com.util.NetworkUtil;
import tumblr.mimic.com.util.TumblrUtil;


public class PostsActivity extends AppCompatActivity {

    public static PostsDataAdapter adapter;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        ArrayList posts = new ArrayList();
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        boolean isNetworkAvailable = NetworkUtil.isNetworkAvailable(this);
        if(!isNetworkAvailable){
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "No internet connection.", Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }
        initView(posts);
        TumblrUtil task = new TumblrUtil(this);
        task.execute();
    }

    public void initView(ArrayList posts) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerUtil(this, LinearLayoutManager.VERTICAL));
        adapter = new PostsDataAdapter(getApplicationContext(), posts);
        recyclerView.setAdapter(adapter);
    }

    public void updateAdapter(List<PostBean> postBeanList) {
        adapter.notifyDataSetChanged();
    }


}
