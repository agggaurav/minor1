package com.example.gaurav.card;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Gaurav on 18-10-2016.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    public OneFragment mContext;
    public List<Post> postList;
    public ImageLoader imageLoader;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView  overflow;
        public NetworkImageView thumbnail;
        public String imgurl;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            //count = (TextView) view.findViewById(R.id.count);
            thumbnail = (NetworkImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }

    public String gettitle(int a)
    {
        String rname;
        if(a>0) {
            rname = postList.get(a - 1).getName();
        }
        else
            rname=postList.get(a).getName();
        return rname;
    }

    public PostAdapter(OneFragment mContext, List<Post> postList) {
        this.mContext = mContext;
        this.postList = postList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.title.setText(post.getName());

        Log.e("image loading", "qwwwwwwwwwwwwwwwwwwww");
     //   Glide.with(mContext).load(post.getThumbnail()).placeholder(post.getThumbnail()).into(holder.thumbnail);
        (holder.thumbnail).setImageDrawable(post.getThumbnail());
        final ImageView thumbnails=holder.thumbnail;
        thumbnails.setImageDrawable(post.getThumbnail());
        loadImage(post.getImageUrl(),holder.thumbnail);
        thumbnails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Glide.with(mContext).load(R.drawable.album11).placeholder(R.mipmap.ic_launcher).crossFade(1500).into(holder.thumbnail);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPopupMenu(holder.overflow);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */

    private void loadImage(String media, NetworkImageView imageView){

        String url="http://192.168.1.102:8000";
        url=url+media;
        if(url.equals("")){
            Toast.makeText(mContext.getContext(),"Please enter a URL", Toast.LENGTH_LONG).show();

        }

        imageLoader = CustomVolleyRequest.getInstance(mContext.getContext()).getImageLoader();
        imageLoader.get(url, ImageLoader.getImageListener(imageView,R.drawable.image, android.R.drawable.ic_dialog_alert));
        imageView.setImageUrl(url, imageLoader);
    }



    private void showPopupMenu(View view) {
        Toast.makeText(view.getContext(), "overflow", Toast.LENGTH_SHORT).show();
        // inflate menu
      /*  PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();*/
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    // Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    // Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}