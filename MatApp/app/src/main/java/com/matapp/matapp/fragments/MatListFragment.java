package com.matapp.matapp.fragments;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Toast;

import com.matapp.matapp.MatListDetail;
import com.matapp.matapp.RecyclerAdapter;
import com.matapp.matapp.R;
import com.matapp.matapp.other.Material;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kathrinkoebel on 25.10.17.
 */

public class MatListFragment extends Fragment {

    private List<Material> items;
    public int curPosition = 0; // checked position in list


    public RecyclerView recyclerView;
    public RecyclerView.Adapter recyclerViewAdapter;
    public RecyclerView.LayoutManager recyclerViewLayoutManager;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.fragment_matlist, parent, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_matlist);
        recyclerViewLayoutManager = new LinearLayoutManager(this.getContext());
        Log.d("debugMode", "The application stopped after this");
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        initializeData();
        recyclerViewAdapter = new RecyclerAdapter(items);
        // if size of recycler view does not change add setHasFixedSize for better performance
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

        FloatingActionButton fabAddItem = (FloatingActionButton) view.findViewById(R.id.fab_add_item);
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO korrekte inmplementation von Material hinzufügen!!!
                Context context = getContext();
                CharSequence text = "neues Material hinzufügen";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

    }

    // This method creates an ArrayList that contains some Dummy Material objects
    public void initializeData(){
        items = new ArrayList<>();
        items.add(new Material("Digital Camera", "lorem ipsum", "Kathrin Koebel", "5.2A14" ));
        items.add(new Material("VR Headset", "dolor sit amet", "Rafael Reimann", "5.2A14"));
        items.add(new Material("portable Beamer", "consetetur sadipscing elitr", "Christoph Meyer", "5.2A14"));
        items.add(new Material("Android Book", "sed diam nonumy", "Kathrin Koebel", "5.2A14" ));
        items.add(new Material("3D Printer", "eirmod tempor invidunt ut", "Rafael Reimann", "5.2A14"));
        items.add(new Material("Coffee Machine", "labore et dolore", "Christoph Meyer", "5.2A14"));
        items.add(new Material("GoPro", "magna aliquyam erat", "Kathrin Koebel", "5.2A14" ));
        items.add(new Material("Pixel Phone", "sed diam voluptua", "Rafael Reimann", "5.2A14"));
        items.add(new Material("Adapterset", "at vero eos et accusam", "Christoph Meyer", "5.2A14"));
        items.add(new Material("Screen", "et justo duo dolores", "Kathrin Koebel", "5.2A14" ));
        items.add(new Material("Powerboard", "et ea rebum", "Rafael Reimann", "5.2A14"));
        items.add(new Material("Tripod", "stet clita kasd gubergren", "Christoph Meyer", "5.2A14"));
        items.add(new Material("Spotlight", "no sea takimata sanctus", "Kathrin Koebel", "5.2A14" ));
    }

    /*
    private void initializeAdapter(){
        RecyclerAdapter adapter = new RecyclerAdapter(items);
        rv.setAdapter(adapter);
    }
    */

    /**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a whole new
     * activity in which it is displayed.
     */
    void showDetails(int index) {
        curPosition = index;

        // launch a new activity to display
        // the dialog fragment with selected text.
        Intent intent = new Intent();
        Log.i("fragmentdemo", "TitlesFragment:showDetails activity is " + getActivity().toString() + "class is " + MatListDetail.class.toString());
        intent.setClass(getActivity(), MatListDetail.class);
        intent.putExtra("index", index);
        startActivity(intent);
    }

    /* Methods */
    public Material getMaterialAtPosition (int index) {
        return items.get(index);
    }

    public void addMaterial (Material m) {
        int index = items.size();
        items.add(index, m);
    }


    /** Called when the user clicks on the button */
    /*
    public void onMatListItemClick(View view) {
        Context context = getContext();
        CharSequence text = "Detail anzeigen";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    */



}
