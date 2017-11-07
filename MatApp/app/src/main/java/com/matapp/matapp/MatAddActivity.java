package com.matapp.matapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
        det_status = (Spinner) findViewById(R.id.det_status);

        btn_create = (Button) findViewById(R.id.btn_create);
        btn_delete = (Button) findViewById(R.id.btn_delete_material);

        // Get the Intent that started this activity and extract the string
        Intent intent = this.getIntent();

        // attach AlertDialog to Mat Delete Button
        btn_create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO save new Material
                Context context = getApplicationContext();
                CharSequence text = "Artikel speichern";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                // TODO load Material Detail or MatList?!?

            }
        });
    }

}
