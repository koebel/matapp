package com.matapp.matapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by kathrinkoebel on 26.10.17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    String[] mat_list, mat_desc;
    public RecyclerAdapter(String[] mat_list, String[] mat_desc)
    {
        this.mat_list=mat_list;this.mat_desc=mat_desc;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.matlist_row, parent, false);
        RecyclerViewHolder RVH=new RecyclerViewHolder(view);

        return RVH;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.row_mat_title.setText(mat_list[position]);
        holder.row_mat_desc.setText(mat_desc[position]);

    }

    @Override
    public int getItemCount() {
        return mat_list.length;
    }
    public  class  RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView row_mat_title;
        TextView row_mat_desc;
        public RecyclerViewHolder(View view){
            super(view);
            row_mat_title=(TextView)view.findViewById(R.id.row_mat_title);
            row_mat_desc=(TextView)view.findViewById(R.id.row_mat_desc);
        }
    }
}

