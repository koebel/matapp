package com.matapp.matapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.matapp.matapp.fragments.MatDeleteAlertDialogFragment;
import com.matapp.matapp.other.Material;

/**
 * Created by kathrinkoebel on 07.11.17.
 */

public class MatAddActivity extends AppCompatActivity {

    /* Variables for Mat Detail Add */
    EditText det_title, det_desc, det_owner, det_location, det_gps, det_barcode, det_img;

    Spinner det_status;

    Button btn_create, btn_delete;

    int currentItemID, status;

    String title, description, owner, location, gps, barcode, img;

    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mat_add);

        det_title = (EditText) findViewById(R.id.det_title_add);
        det_desc = (EditText) findViewById(R.id.det_desc_add);
        det_owner = (EditText) findViewById(R.id.det_owner_add);
        det_location = (EditText) findViewById(R.id.det_location_add);
        det_status = (Spinner) findViewById(R.id.det_status_add);

        /*
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.det_status_dropdown, null);
        det_status.setAdapter(adapter);
        */

        btn_create = (Button) findViewById(R.id.btn_create);
        btn_delete = (Button) findViewById(R.id.btn_delete_material);

        // Get the Intent that started this activity and extract the string
        Intent intent = this.getIntent();

        // attach AlertDialog to Mat Delete Button
        btn_create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // get Content from Input fields
                // if fields contain Helpertext reset to empty String
                title = det_title.getText().toString();
                if (title.equals(R.string.det_title)) {
                    title = "";
                }
                description = det_desc.getText().toString();
                if (description.equals(R.string.det_desc)) {
                    description = "";
                }
                owner = det_owner.getText().toString();
                if (owner.equals(R.string.det_owner)) {
                    owner = "";
                }
                location = det_location.getText().toString();
                if (location.equals(R.string.det_location)) {
                    location = "";
                }
                status = det_status.getSelectedItemPosition();

                Material newMaterial = new Material(title, description, owner, location, status);

                // TODO save newMaterial
                // TODO load Material Detail or MatList?!?
            }
        });
    }
}
