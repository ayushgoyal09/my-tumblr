package tumblr.mimic.com.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import tumblr.mimic.com.bean.PostBean;

/**
 * Created by Ananta on 9/23/2016.
 */

public class SaveToDbTask extends AsyncTask<PostBean, Void, Void> {

    Context context;

    public SaveToDbTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(PostBean... params) {
        PostBean post = params[0];
        DatabaseHelper db = new DatabaseHelper(context);

        boolean isPresent = db.isPresent(post.getId());

        if (!isPresent) {
            boolean res = db.insertData(post.getId(), post.getPostImage(), post.getPostCaption());

            if (res) {
                Log.i("saved to db", "success");
            } else {
                Log.i("saved to db", "fail");
            }
        }
        db.close();


        return null;
    }
}
