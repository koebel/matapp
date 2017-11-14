package com.matapp.matapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * This activity is used to display a list of all Material items using a Recycler View.
 *
 * Created by kathrinkoebel on 25.10.17.
 *
 */

public class MatListFragment extends Fragment {

    /* Variables */
    private List<Map<String,String>> materials;

    private FirebaseDatabase database;
    private DatabaseReference itemReference;

    /* static Variables */
    public static final String LOG_MAT_LIST_FRAGMENT = "MatListFragment";


    /* Constructor */
    // Creates a new fragment given an int and title
    public static MatListFragment newInstance(int someInt, String someTitle) {
        MatListFragment fragment = new MatListFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        args.putString("someTitle", someTitle);
        fragment.setArguments(args);
        return fragment;
    }




    /* Lifecycle Methods */

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.fragment_mat_list, parent, false);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_matlist);

        //Get Firebase database instance
        database = FirebaseDatabase.getInstance();
        //Get reference to material
        itemReference = database.getReference("material/" + MatAppSession.getInstance().getListKey() + "/item");
        //Read at reference
        itemReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                materials = new ArrayList<Map<String,String>>();
                for(DataSnapshot item: dataSnapshot.getChildren()) {
                    //Add a HashMap to ArrayList for each item
                    Map<String,String> map = new HashMap<String,String>();
                    map.put("itemKey", item.getKey());
                    map.put("title", item.child("title").getValue(String.class));
                    map.put("description", item.child("description").getValue(String.class));
                    map.put("thumb", item.child("thumb").getValue(String.class));
                    materials.add(map);
                }
                Log.i(LOG_MAT_LIST_FRAGMENT, "items in list: " + materials);

                // check if list is empty (first use)
                if (materials.isEmpty()) {
                    // toast some Text for first use case
                    Toast.makeText(getContext(), getResources().getString(R.string.first_use_title) + getResources().getString(R.string.first_use_text), Toast.LENGTH_LONG).show();
                }

                //Pass ArrayList to RecyclerView
                RecyclerView.Adapter recyclerViewAdapter = new RecyclerAdapter(materials);
                RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(MatListFragment.this.getContext());
                recyclerView.setLayoutManager(recyclerViewLayoutManager);
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // FAB neues Material erstellen
        FloatingActionButton fabAddItem = (FloatingActionButton) view.findViewById(R.id.fab_add_item);
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, MatAddActivity.class);
                startActivity(intent);
            }
        });

        if(!MatAppSession.getInstance().isListWriteable()) {
            fabAddItem.setVisibility(View.GONE);
        }
    }

    /* Getter & Setter */
    public List<Map<String, String>> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Map<String, String>> materials) {
        this.materials = materials;
    }

}
