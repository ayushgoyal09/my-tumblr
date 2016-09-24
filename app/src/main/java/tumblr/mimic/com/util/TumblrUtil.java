package tumblr.mimic.com.util;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tumblr.mimic.com.activity.PostsActivity;
import tumblr.mimic.com.bean.PostBean;
import tumblr.mimic.com.database.DatabaseHelper;

public class TumblrUtil extends AsyncTask<Object, Void, List<PostBean>> {

    Context context;


    PostsActivity activity;

    public TumblrUtil(Activity activity) {
        this.activity = (PostsActivity) activity;
        this.context = activity;
    }

    @Override
    protected List<PostBean> doInBackground(Object... params) {
        List<PostBean> postBeanList = new ArrayList<>();
        boolean networkAvailable = NetworkUtil.isNetworkAvailable(context);
        Log.i("Network status - ",""+networkAvailable);
        if(networkAvailable){
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
            final List<Post> posts = client.userDashboard(param);



            for (Post post : posts) {
                PhotoPost photoPost = (PhotoPost) post;
                Spanned result;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    result = Html.fromHtml(photoPost.getCaption(), Html.FROM_HTML_MODE_LEGACY);
                } else {
                    result = Html.fromHtml(photoPost.getCaption());
                }



                String caption = result.toString();
                final String imageUrl = photoPost.getPhotos().get(0).getOriginalSize().getUrl();
                final String id = photoPost.getId().toString();


                PostBean postBean = new PostBean(id, caption, imageUrl);
                postBeanList.add(postBean);

                //save to file and db.


        }


        } else  {
            DatabaseHelper db = new DatabaseHelper(activity);//retrieve from db
            Cursor res = db.getAllPhotos();
            if(res.getCount()==0){
                //no data
            }else{
                while(res.moveToNext()){
                    String id = res.getString(0);
                    String location = res.getString(1);
                    String caption = res.getString(2);
                    PostBean post = new PostBean(id,caption,location);
                    postBeanList.add(post);
                }

            }
            db.close();

        }

        return postBeanList;
    }

    @Override
    protected void onPostExecute(List<PostBean> postBean) {
        activity.initView((ArrayList) postBean);
    }
}