package com.matapp.matapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.matapp.matapp.other.Material;


/**
 *
 * This activity is used to add new Materials to the app.
 *
 * Created by kathrinkoebel on 07.11.17.
 *
 **/

public class MatAddActivity extends AppCompatActivity {

    /* Variables for Mat Detail Add */
    EditText det_title, det_desc, det_owner, det_location, det_gps, det_barcode, det_img;
    Spinner det_status;
    int status;
    String title, description, owner, location, gps, barcode, img;
    Button btn_create, btn_delete;
    FloatingActionButton fabAddImg;


    /* Lifecycle Methods */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mat_add);

        // binding of UI elements
        det_title = (EditText) findViewById(R.id.det_title_add);
        det_desc = (EditText) findViewById(R.id.det_desc_add);
        det_owner = (EditText) findViewById(R.id.det_owner_add);
        det_location = (EditText) findViewById(R.id.det_location_add);
        det_status = (Spinner) findViewById(R.id.det_status_add);

        btn_create = (Button) findViewById(R.id.btn_create);
        btn_delete = (Button) findViewById(R.id.btn_delete_material);

        // Get the Intent that started this activity (and extract values)
        // there are no values passed into this activity
        Intent intent = this.getIntent();

        // attach AlertDialog to Mat Delete Button
        btn_create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // get Content from Input fields
                // if fields contain Helpertext reset to empty String
                title = det_title.getText().toString();

                if (title.equals(getText(R.string.det_title).toString())) {
                    title = "";
                }

                if (title.trim().length() == 0) {
                    // TODO: to something, title shouldn't be empty!!!
                    // don't allow material to be saved unless there is a valid title
                    {
                        Toast.makeText(getApplicationContext(), R.string.det_title_error, Toast.LENGTH_SHORT).show();
                    }
                }

                description = det_desc.getText().toString();
                if (description.equals(getText(R.string.det_desc).toString())) {
                    description = "";
                }
                owner = det_owner.getText().toString();
                if (owner.equals(getText(R.string.det_owner).toString())) {
                    owner = "";
                }
                location = det_location.getText().toString();
                if (location.equals(getText(R.string.det_location).toString())) {
                    location = "";
                }
                status = det_status.getSelectedItemPosition();

                Material newMaterial = new Material(title, description, owner, location, status);

                // TODO save newMaterial
                Toast.makeText(getApplicationContext(), "Gegenstand wurde gespeichert", Toast.LENGTH_SHORT).show();

                // TODO make certain fields (e.g. Title) required?
                // TODO load Material Detail or MatList?!?
            }
        });

        // add Image FAB
        fabAddImg = (FloatingActionButton) findViewById(R.id.fab_add_img);
        fabAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO add image
                // same as update image on Edit Material?!?

                Context context = getApplicationContext();
                CharSequence text = "Bild hinzufügen";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }
}
