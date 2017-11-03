package com.matapp.matapp;

import android.view.View;

/**
 * Created by kathrinkoebel on 03.11.17.
 */

public interface RecyclerViewItemClickListener {
    public void onClick(View view, int position);
    public void onLongClick(View view, int position);
}