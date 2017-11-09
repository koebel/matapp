package com.matapp.matapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.matapp.matapp.other.Material;

import java.util.List;


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
    List<Material> items;
    public RecyclerAdapter (List<Material> items){
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
        holder.row_mat_title.setText(items.get(position).title);
        holder.row_mat_desc.setText(items.get(position).description);

        // TODO thumb muss in Bitmap umgewandelt werden!!!
        //holder.row_mat_thumb.setImageBitmap(items.get(position).thumb);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /* RecyclerViewHolder implementation */
    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView row_mat_title;
        TextView row_mat_desc;
        ImageButton row_mat_thumb;

        /* Constructor */
        public RecyclerViewHolder(View view){
            super(view);
            row_mat_title = (TextView)view.findViewById(R.id.row_mat_title);
            row_mat_desc = (TextView)view.findViewById(R.id.row_mat_desc);
            row_mat_thumb = (ImageButton)view.findViewById(R.id.row_mat_img);

            // set listener to each list item
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    Context context = v.getContext();

                    // put extras into intent to pass them on to the Material Detail
                    Intent intent = new Intent(context, MatDetailActivity.class);
                    intent.putExtra("ID_KEY", items.get(position).getUniqueId());
                    intent.putExtra("TITLE_KEY", items.get(position).getTitle());
                    intent.putExtra("DESCRIPTION_KEY", items.get(position).getDescription());
                    intent.putExtra("OWNER_KEY", items.get(position).getOwner());
                    intent.putExtra("LOCATION_KEY", items.get(position).getLocation());
                    intent.putExtra("STATUS_KEY", items.get(position).getStatus());
                    intent.putExtra("GPS_KEY", "");
                    intent.putExtra("BARCODE_KEY", items.get(position).getBarcode());
                    intent.putExtra("IMAGE_KEY", items.get(position).getImg());
                    intent.putExtra("THUMB_KEY", items.get(position).getThumb());
                    intent.putExtra("LOAN_NAME_KEY", items.get(position).getLoanName());
                    intent.putExtra("LOAN_CONTACT_KEY", items.get(position).getLoanContact());
                    intent.putExtra("LOAN_UNTIL_KEY", items.get(position).getLoanUntil());
                    intent.putExtra("LOAN_NOTE_KEY", items.get(position).getLoanNote());

                    // start Material Detail Activity
                    context.startActivity(intent);
                }
            });
        }
    }
}


