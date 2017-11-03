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
    String[] mat_list, mat_desc, mat_owner, mat_location, mat_gps, mat_status, mat_barcode, mat_img, mat_loan_name, mat_loan_contact, mat_loan_until, mat_loan_note;
    public RecyclerAdapter(String[] mat_list, String[] mat_desc, String[] mat_owner, String[] mat_location, String[] mat_gps, String[] mat_status,
                           String[] mat_barcode, String[] mat_img, String[] mat_loan_name, String[] mat_loan_contact, String[] mat_loan_until, String[] mat_loan_note)
    {
        this.mat_list = mat_list;
        this.mat_desc = mat_desc;
        this.mat_owner = mat_owner;
        this.mat_location = mat_location;
        this.mat_gps = mat_gps;
        this.mat_status = mat_status;
        this.mat_barcode = mat_barcode;
        this.mat_img = mat_img;
        this.mat_loan_name = mat_loan_name;
        this.mat_loan_contact = mat_loan_contact;
        this.mat_loan_until = mat_loan_until;
        this.mat_loan_note = mat_loan_note;
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

