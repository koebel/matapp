package com.matapp.matapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.matapp.matapp.MatAddActivity;
import com.matapp.matapp.MatListDetailActivity;
import com.matapp.matapp.RecyclerAdapter;
import com.matapp.matapp.R;
import com.matapp.matapp.other.Material;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kathrinkoebel on 25.10.17.
 */

public class MatListFragment extends Fragment implements MatAddAlertDialogFragment.MatAddDialogListener {

    public List<Material> materials;

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
        recyclerViewAdapter = new RecyclerAdapter(materials);
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
        // set position of the fab
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) fabAddItem.getLayoutParams();
        lp.gravity = Gravity.END;
        lp.gravity = Gravity.BOTTOM;
        fabAddItem.setLayoutParams(lp);

        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO korrekte inmplementation von Material hinzufügen!!!
                /*
                Intent intent = new Intent(getContext(), MatAddActivity.class);
                startActivity(intent);
                */

                Context context = getContext();
                CharSequence text = "Artikel erstellen";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }

    // This method creates an ArrayList that contains some Dummy Material objects
    public void initializeData(){
        materials = new ArrayList<>();
        materials.add(new Material("Digital Camera", "lorem ipsum", "Kathrin Koebel", "5.2A14", Material.STATUS_AVAILABLE ));
        materials.add(new Material("VR Headset", "dolor sit amet", "Rafael Reimann", "5.2A14", Material.STATUS_AVAILABLE));
        materials.add(new Material("portable Beamer", "consetetur sadipscing elitr", "Christoph Meyer", "5.2A14", Material.STATUS_LENT));
        materials.add(new Material("Android Book", "sed diam nonumy", "Kathrin Koebel", "5.2A14", Material.STATUS_LENT, "gps placeholder", "barcode placeholder", "img placeholder",
                "Name Ausleiher", "Telefonnummer oder Email Ausleiher", "Ausleihe bis Datum", "ggf Notiz zur Ausleihe... Bsp für langer Text: " +
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. "));
        materials.add(new Material("3D Printer", "eirmod tempor invidunt ut", "Rafael Reimann", "5.2A14", Material.STATUS_UNAVAILABLE));
        materials.add(new Material("Coffee Machine", "labore et dolore", "Christoph Meyer", "5.2A14", Material.STATUS_AVAILABLE));
        materials.add(new Material("GoPro", "magna aliquyam erat", "Kathrin Koebel", "5.2A14", Material.STATUS_AVAILABLE));
        materials.add(new Material("Pixel Phone", "sed diam voluptua", "Rafael Reimann", "5.2A14", Material.STATUS_LENT));
        materials.add(new Material("Adapterset", "at vero eos et accusam", "Christoph Meyer", "5.2A14", Material.STATUS_LENT));
        materials.add(new Material("Screen", "et justo duo dolores", "Kathrin Koebel", "5.2A14", Material.STATUS_UNAVAILABLE));
        materials.add(new Material("Powerboard", "et ea rebum", "Rafael Reimann", "5.2A14", Material.STATUS_AVAILABLE));
        materials.add(new Material("Tripod", "stet clita kasd gubergren", "Christoph Meyer", "5.2A14", Material.STATUS_AVAILABLE));
        materials.add(new Material("Spotlight", "no sea takimata sanctus", "Kathrin Koebel", "5.2A14", Material.STATUS_AVAILABLE ));
    }

    /* Methods */
    public Material getMaterialAtPosition (int index) {
        return materials.get(index);
    }

    public void addMaterial (Material m) {
        int index = materials.size();
        materials.add(index, m);
    }

    public void deleteMaterial (int uniqueId) {
        Material foundMaterial = null;
        for(Material m : materials){
            if(m.getUniqueId() == uniqueId){
                foundMaterial = m;
            }
        }

        if (foundMaterial != null && materials.contains(foundMaterial)) {
            materials.remove(foundMaterial);
        }
    }

    /* Implementation of MatAddDialogListener */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // TODO delete Material with this id
        // deleteMaterial(id);
        dialog.dismiss();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }
}
