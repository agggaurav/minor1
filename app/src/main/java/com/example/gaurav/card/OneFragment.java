package com.example.gaurav.card;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gaurav on 04-10-2016.
 */

public class OneFragment extends Fragment {

    public String actionbar_title;
    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private List<Post> postList;
    private RecyclerViewPositionHelper mRecyclerViewHelper;
    String url = "http://192.168.1.102:8000/post/?format=json";
    String data = "";
    // Defining the Volley request queue that handles the URL request concurrently
    RequestQueue requestQueue;
    private NetworkImageView imageView;
    private ImageLoader imageLoader;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    Toolbar imagetitle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        imageView=(NetworkImageView) view.findViewById(R.id.imageView);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        imagetitle=(Toolbar) view.findViewById(R.id.toolbar2);
        //imagetitle.setTitle("image name");
        postList = new ArrayList<>();
        adapter = new PostAdapter(this, postList);
        //Toast.makeText(getContext(),"1",Toast.LENGTH_SHORT).show();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        prepareAlbums();
        //Toast.makeText(getContext(),"2",Toast.LENGTH_SHORT).show();
        mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(recyclerView);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int ypos = recyclerView.computeVerticalScrollOffset();
                if (ypos == 50) {
          //          Toast.makeText(getContext(), "tttt", Toast.LENGTH_SHORT).show();
                }
                int visibleItemCount = recyclerView.getChildCount();
                if (dy > 0) {
                    int firstVisibleItem = mRecyclerViewHelper.findFirstCompletelyVisibleItemPosition();

                    Log.e("up", String.valueOf(firstVisibleItem));
                    if (firstVisibleItem != -1) {
                        actionbar_title = adapter.gettitle(firstVisibleItem);
                        imagetitle.setTitle(actionbar_title);
                        //updatetitle();
                    }
                } else if (dy < 0) {
                    int lastVisibleItem = mRecyclerViewHelper.findLastCompletelyVisibleItemPosition();
                    Log.e("down", String.valueOf(lastVisibleItem));
                    if (lastVisibleItem != -1) {
                        actionbar_title = adapter.gettitle(lastVisibleItem);
                        imagetitle.setTitle(actionbar_title);

                    }
                }

            }
        });


        try {
            //     Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
return view;
    }



    private void prepareAlbums() {

        requestQueue = Volley.newRequestQueue(getContext());
        //Toast.makeText(getContext(),"requestQueue",Toast.LENGTH_SHORT).show();
       JsonArrayRequest arrayreq = new JsonArrayRequest(url,
                // The second parameter Listener overrides the method onResponse() and passes
                //JSONArray as a parameter
                new Response.Listener<JSONArray>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i = 0; i < response.length(); i++) {

          //                      Toast.makeText(getContext(),"3",Toast.LENGTH_SHORT).show();
                                JSONObject postObj = response.getJSONObject(i);

                               String name=postObj.getString("id");
                               String imageurl=postObj.getString("image_posted");
            //                    Toast.makeText(getContext(),"4",Toast.LENGTH_SHORT).show();
                               BitmapDrawable img= loadImage(imageurl);
              //                  Toast.makeText(getContext(),"5",Toast.LENGTH_SHORT).show();
                               //BitmapDrawable drawable=new BitmapDrawable(img);
                               Post a = new Post(name,img,imageurl);
                                //Toast.makeText(getContext(),"aaaa"+a.getName(),Toast.LENGTH_SHORT).show();
                                postList.add(a);
                //                Toast.makeText(getContext(),"6",Toast.LENGTH_SHORT).show();
                                adapter.notifyDataSetChanged();

                            }

                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(arrayreq);
        adapter.notifyDataSetChanged();
       // Toast.makeText(getContext(),"7",Toast.LENGTH_SHORT).show();

    }

    private BitmapDrawable loadImage(String media){

        url="http://192.168.1.102:8000";
        url=url+media;
        if(url.equals("")){
            Toast.makeText(getContext(),"Please enter a URL", Toast.LENGTH_LONG).show();
            return null;
        }

        imageLoader = CustomVolleyRequest.getInstance(this.getContext()).getImageLoader();
        imageLoader.get(url, ImageLoader.getImageListener(imageView, R.drawable.image, android.R.drawable.ic_dialog_alert));

      //  Drawable bp = null;//=imageLoader.get(url, ImageLoader.getImageListener(imageView, R.drawable.image, android.R.drawable.ic_dialog_alert)).getBitmap();

        imageView.setImageUrl(url, imageLoader);
        BitmapDrawable bp=null;
        return bp;
    }




    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }



}