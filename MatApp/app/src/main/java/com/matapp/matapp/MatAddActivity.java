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

import com.matapp.matapp.fragments.MatListFragment;
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

        // change color of all "optional" attributes to Placeholder color
        // det_title.setTextColor(getResources().getColor(R.color.colorPlaceholder));
        // det_desc.setTextColor(getResources().getColor(R.color.colorPlaceholder));
        // det_owner.setTextColor(getResources().getColor(R.color.colorPlaceholder));
        // det_location.setTextColor(getResources().getColor(R.color.colorPlaceholder));

        btn_create = (Button) findViewById(R.id.btn_create);
        btn_delete = (Button) findViewById(R.id.btn_delete_material);

        // Get the Intent that started this activity (and extract values)
        // there are no values passed into this activity
        Intent intent = this.getIntent();

        // add Image FAB
        fabAddImg = (FloatingActionButton) findViewById(R.id.fab_add_img);
        fabAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO add image
                // same as update image on Edit Material?!?

                Toast.makeText(getApplicationContext(), "Bild hinzufügen", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // create new Material
    public void onCreateItem (View view){
        // get Content from Input fields
        // if fields contain Helpertext reset to empty String
        title = det_title.getText().toString();

        if (title.equals(getText(R.string.det_title).toString())) {
            title = "";
        }

        if (title.trim().length() == 0) {
            // TODO make certain fields (e.g. Title) required?
            // do something, title shouldn't be empty!!!
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

        // create new Material Object
        Material newMat = new Material(title, description, owner, location, status);

        // TODO save newMat into DB!!!

        // load Mat Detail activity
        Intent intent = new Intent(this, MatDetailActivity.class);
        intent.putExtra("ID_KEY", newMat.getUniqueId());
        intent.putExtra("TITLE_KEY", newMat.getTitle());
        intent.putExtra("DESCRIPTION_KEY", newMat.getDescription());
        intent.putExtra("OWNER_KEY", newMat.getOwner());
        intent.putExtra("LOCATION_KEY", newMat.getLocation());
        intent.putExtra("STATUS_KEY", newMat.getStatus());
        intent.putExtra("GPS_KEY", newMat.getGps());
        intent.putExtra("BARCODE_KEY", newMat.getBarcode());
        intent.putExtra("IMAGE_KEY", newMat.getImg());
        intent.putExtra("LOAN_NAME_KEY", newMat.getLoanName());
        intent.putExtra("LOAN_CONTACT_KEY", newMat.getLoanContact());
        intent.putExtra("LOAN_UNTIL_KEY", newMat.getLoanUntil());
        intent.putExtra("LOAN_NOTE_KEY", newMat.getLoanNote());

        // start Material Detail Activity
        startActivity(intent);
    }
}
