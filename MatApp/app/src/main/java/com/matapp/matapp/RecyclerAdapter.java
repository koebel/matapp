package com.matapp.matapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.util.Map;


/**
 *
 * This class is used to create an adapter that binds Material items to the recycler view
 * of the Material List (displaying all items).
 * In addition, it offers an implementation of RecyclerViewHolder
 *
 * Created by kathrinkoebel on 26.10.17.
 *
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    /* Attributes */
    private List<Map<String,String>> items;

    /* Constructor */
    public RecyclerAdapter (List<Map<String,String>> items){
        this.items = items;
    }

    /* Implementation of Adapter Methods */
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.matlist_row, parent, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.rowMatTitle.setText(items.get(position).get("title"));
        holder.rowMatDesc.setText(items.get(position).get("description"));

        // TODO thumb muss in Bitmap umgewandelt werden!!!
        if(items.get(position).get("thumb").length() > 0) {
            byte[] imageBytes = Base64.decode(items.get(position).get("thumb"), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            int height = holder.rowMatThumb.getLayoutParams().height;
            //int length = Math.round(64*getResources().getDisplayMetrics().density);
            Bitmap smallProfile = Bitmap.createScaledBitmap(decodedImage, height, height, false);
            holder.rowMatThumb.setImageBitmap(smallProfile);
        }
    }

    @Override
    public int getItemCount() {
        if(items != null) {
            return items.size();
        } else {
            return 0;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /* RecyclerViewHolder implementation */
    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView rowMatTitle;
        TextView rowMatDesc;
        ImageButton rowMatThumb;

        /* Constructor */
        public RecyclerViewHolder(View view){
            super(view);
            rowMatTitle = (TextView)view.findViewById(R.id.row_mat_title);
            rowMatDesc = (TextView)view.findViewById(R.id.row_mat_desc);
            rowMatThumb = (ImageButton)view.findViewById(R.id.row_mat_img);

            // set listener to each list item
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    Context context = v.getContext();

                    // put extras into intent to pass them on to the Material Detail
                    Intent intent = new Intent(context, MatDetailActivity.class);
                    intent.putExtra("ITEM_KEY", items.get(position).get("itemKey"));

                    // start Material Detail Activity
                    context.startActivity(intent);
                }
            });
        }
    }

    /* Getter & Setter */
    public List<Map<String, String>> getItems() {
        return items;
    }

    public void setItems(List<Map<String, String>> items) {
        this.items = items;
    }
}


