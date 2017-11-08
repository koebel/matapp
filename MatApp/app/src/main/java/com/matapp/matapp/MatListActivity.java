package com.matapp.matapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.matapp.matapp.MatAddActivity;
import com.matapp.matapp.RecyclerAdapter;
import com.matapp.matapp.R;
import com.matapp.matapp.other.Material;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * This activity is used to display a list of all Material items using a Recycler View.
 *
 * Created by kathrinkoebel on 25.10.17.
 *
 */

public class MatListActivity extends AppCompatActivity {

    public List<Material> materials;

    public RecyclerView recyclerView;
    public RecyclerView.Adapter recyclerViewAdapter;
    public RecyclerView.LayoutManager recyclerViewLayoutManager;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    /* Lifecycle Methods */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matlist);

        // set Statusbar Color
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        // set Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.rv_matlist);

        initializeData();
        recyclerViewAdapter = new RecyclerAdapter(materials);
        // if size of recycler view does not change add setHasFixedSize for better performance
        // recyclerView.setHasFixedSize(true);

        recyclerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        // FAB neues Material erstellen
        FloatingActionButton fabAddItem = (FloatingActionButton) findViewById(R.id.fab_add_item);
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, MatAddActivity.class);
                startActivity(intent);
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

    /* Helper Methods */
    public Material getMaterialAtPosition (int index) {
        return materials.get(index);
    }

    public int getMaterialIndex (int uniqueId) {
        int index = -1;
        int count = 0;
        for(Material m : materials){
            if (m.getUniqueId() == uniqueId){ return index = count; }
            count++;
        }
        return index;
    }

    public void addMaterial (Material m) {
        int index = materials.size();
        materials.add(index, m);
    }

    public void deleteMaterial (int uniqueId) {
        int index = getMaterialIndex(uniqueId);
        if (index >= 0 && index < materials.size()) {
            materials.remove(index);
        }
    }
}
