package tumblr.mimic.com.activity;

import android.Manifest;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.net.ConnectivityManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.vistrav.ask.Ask;
import com.vistrav.ask.annotations.AskDenied;
import com.vistrav.ask.annotations.AskGranted;

import java.util.ArrayList;
import java.util.List;

import tumblr.mimic.com.adapter.PostsDataAdapter;
import tumblr.mimic.com.bean.PostBean;
import tumblr.mimic.com.myapplication.R;
import tumblr.mimic.com.util.DividerUtil;
import tumblr.mimic.com.util.NetworkReceiver;
import tumblr.mimic.com.util.NetworkUtil;
import tumblr.mimic.com.util.TumblrUtil;


public class PostsActivity extends AppCompatActivity {

    public static PostsDataAdapter adapter;
    private static CoordinatorLayout coordinatorLayout;

    private static final String TAG = PostsActivity.class.getSimpleName();

    //optional
    @AskGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void fileAccessGranted() {
        Log.i(TAG, "FILE  GRANTED");
    }

    //optional
    @AskDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void fileAccessDenied() {
        Log.i(TAG, "FILE  DENiED");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        boolean isNetworkAvailable = NetworkUtil.isNetworkAvailable(this);
        if (!isNetworkAvailable) {
            showSnackBar();
        }

        Ask.on(this)
                .forPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withRationales("Storage permission required",
                        "In order to save file you will need to grant storage permission") //optional
                .go();
        ArrayList posts = new ArrayList();

        initView(posts);
        TumblrUtil task = new TumblrUtil(this);
        task.execute();


    }

    public static void showSnackBar() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "No internet connection.", Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    public void initView(ArrayList posts) {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerUtil(this, LinearLayoutManager.VERTICAL));
        adapter = new PostsDataAdapter(getApplicationContext(), posts);
        recyclerView.setAdapter(adapter);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.go_to_top);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(0);
            }
        });
    }

    public void updateAdapter(List<PostBean> postBeanList) {
        adapter.notifyDataSetChanged();
    }


}
