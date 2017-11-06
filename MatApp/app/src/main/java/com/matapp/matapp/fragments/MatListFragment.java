package com.matapp.matapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
        items.add(new Material("Digital Camera", "lorem ipsum", "Kathrin Koebel", "5.2A14", Material.STATUS_AVAILABLE ));
        items.add(new Material("VR Headset", "dolor sit amet", "Rafael Reimann", "5.2A14", Material.STATUS_AVAILABLE));
        items.add(new Material("portable Beamer", "consetetur sadipscing elitr", "Christoph Meyer", "5.2A14", Material.STATUS_LENT));
        items.add(new Material("Android Book", "sed diam nonumy", "Kathrin Koebel", "5.2A14", Material.STATUS_LENT, "gps placeholder", "barcode placeholder", "img placeholder",
                "Name Ausleiher", "Telefonnummer oder Email Ausleiher", "Ausleihe bis Datum", "ggf Notiz zur Ausleihe... Bsp für langer Text: " +
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. "));
        items.add(new Material("3D Printer", "eirmod tempor invidunt ut", "Rafael Reimann", "5.2A14", Material.STATUS_UNAVAILABLE));
        items.add(new Material("Coffee Machine", "labore et dolore", "Christoph Meyer", "5.2A14", Material.STATUS_AVAILABLE));
        items.add(new Material("GoPro", "magna aliquyam erat", "Kathrin Koebel", "5.2A14", Material.STATUS_AVAILABLE));
        items.add(new Material("Pixel Phone", "sed diam voluptua", "Rafael Reimann", "5.2A14", Material.STATUS_LENT));
        items.add(new Material("Adapterset", "at vero eos et accusam", "Christoph Meyer", "5.2A14", Material.STATUS_LENT));
        items.add(new Material("Screen", "et justo duo dolores", "Kathrin Koebel", "5.2A14", Material.STATUS_UNAVAILABLE));
        items.add(new Material("Powerboard", "et ea rebum", "Rafael Reimann", "5.2A14", Material.STATUS_AVAILABLE));
        items.add(new Material("Tripod", "stet clita kasd gubergren", "Christoph Meyer", "5.2A14", Material.STATUS_AVAILABLE));
        items.add(new Material("Spotlight", "no sea takimata sanctus", "Kathrin Koebel", "5.2A14", Material.STATUS_AVAILABLE ));
    }

    /* Methods */
    public Material getMaterialAtPosition (int index) {
        return items.get(index);
    }

    public void addMaterial (Material m) {
        int index = items.size();
        items.add(index, m);
    }

    public void deleteMaterial (int uniqueId) {
        Material foundMaterial = null;
        for(Material m : items){
            if(m.getUniqueId() == uniqueId){
                foundMaterial = m;
            }
        }

        if (foundMaterial != null && items.contains(foundMaterial)) {
            items.remove(foundMaterial);
        }
    }
}
