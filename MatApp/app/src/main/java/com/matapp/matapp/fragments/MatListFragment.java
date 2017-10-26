package com.matapp.matapp.fragments;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.matapp.matapp.RecyclerAdapter;
import com.matapp.matapp.R;

/**
 * Created by kathrinkoebel on 25.10.17.
 */

public class MatListFragment extends Fragment {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter recyclerViewAdapter;
    public RecyclerView.LayoutManager recyclerViewLayoutManager;
    String[] mat_title={"Digital Camera","VR Headset","portable Beamer","Android Book","3D Printer",
            "Coffee Machine","GoPro", "Pixel Phone","Adapterset","Screen","Powerboard","Tripod",
            "Spotlight"};
    String[] mat_desc={"lorem ipsum","dolor sit amet","consetetur sadipscing elitr","sed diam nonumy ",
            "eirmod tempor invidunt ut ","labore et dolore ","magna aliquyam erat","sed diam voluptua",
            "at vero eos et accusam","et justo duo dolores ","et ea rebum","stet clita kasd gubergren",
            "no sea takimata sanctus"};

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.fragment_matlist, parent, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_matlist);
        recyclerViewLayoutManager = new LinearLayoutManager(this.getActivity());
        Log.d("debugMode", "The application stopped after this");
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        recyclerViewAdapter = new RecyclerAdapter(mat_title,mat_desc);
        // recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }

}
