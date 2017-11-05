package com.matapp.matapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.matapp.matapp.other.Material;

import java.util.List;

/**
 * Created by kathrinkoebel on 26.10.17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    List<Material> items;

    public RecyclerAdapter (List<Material> items){
        this.items = items;
    }


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
    }

    @Override
    public int getItemCount() {
        return items.size();
        //return mat_list.length;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView row_mat_title;
        TextView row_mat_desc;
        public RecyclerViewHolder(View view){
            super(view);
            row_mat_title=(TextView)view.findViewById(R.id.row_mat_title);
            row_mat_desc=(TextView)view.findViewById(R.id.row_mat_desc);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    System.out.println(position);

                    Context context = v.getContext();
                    Intent intent = new Intent(context, MatListDetailActivity.class);
                    //intent.putExtra("POSITION_KEY", getAdapterPosition());
                    intent.putExtra("TITLE_KEY", items.get(position).getTitle());
                    intent.putExtra("DESCRIPTION_KEY", items.get(position).getDescription());
                    intent.putExtra("OWNER_KEY", items.get(position).getOwner());
                    intent.putExtra("LOCATION_KEY", items.get(position).getLocation());
                    intent.putExtra("STATUS_KEY", items.get(position).getStatus());
                    intent.putExtra("GPS_KEY", items.get(position).getGps());
                    intent.putExtra("BARCODE_KEY", items.get(position).getBarcode());
                    intent.putExtra("IMAGE_KEY", items.get(position).getImg());
                    intent.putExtra("LOAN_NAME_KEY", items.get(position).getLoanName());
                    intent.putExtra("LOAN_CONTACT_KEY", items.get(position).getLoanContact());
                    intent.putExtra("LOAN_UNTIL_KEY", items.get(position).getLoanUntil());
                    intent.putExtra("LOAN_NOTE_KEY", items.get(position).getLoanNote());

                    context.startActivity(intent);
                }
            });
        }
    }

}


