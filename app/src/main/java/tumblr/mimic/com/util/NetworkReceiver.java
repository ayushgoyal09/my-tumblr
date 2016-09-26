package tumblr.mimic.com.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.tumblr.jumblr.types.Post;

import tumblr.mimic.com.activity.PostsActivity;

/**
 * Created by Ayush on 9/25/2016.
 */

public class NetworkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager
                .getActiveNetworkInfo();

        // Check internet connection and accrding to state change the
        // text of activity by calling method
        if (networkInfo != null && networkInfo.isConnected()) {
            PostsActivity.adapter.notifyDataSetChanged();
        } else {
            PostsActivity.showSnackBar();
        }



    }
}