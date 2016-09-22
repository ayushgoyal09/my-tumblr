package tumblr.mimic.com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tumblr.mimic.com.bean.PostBean;
import tumblr.mimic.com.myapplication.R;

/**
 * Created by Ayush on 9/21/2016.
 */

public class PostsDataAdapter extends RecyclerView.Adapter<PostsDataAdapter.ViewHolder>{

    private ArrayList<PostBean> posts;
    Context context;

    public PostsDataAdapter(Context context,ArrayList<PostBean> posts) {
        this.posts = posts;
        this.context = context;
    }

    @Override
    public PostsDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_posts,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostsDataAdapter.ViewHolder viewHolder, int position) {
        viewHolder.postTitle.setText(posts.get(position).getPostTitle());
        Picasso.with(context).load(posts.get(position).getPostImage()).resize(240,120).into(viewHolder.postImage);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView postImage;
        public TextView postTitle;

        public ViewHolder(View view){
            super(view);
            postTitle = (TextView) view.findViewById(R.id.title);
            postImage = (ImageView) view.findViewById(R.id.img_android);

        }
    }

}
