package com.matapp.matapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.matapp.matapp.other.Material;

public class MatListDetail extends AppCompatActivity {

    TextView det_title, det_desc, det_owner, det_location, det_gps, det_status, det_barcode, det_img, det_loan_name, det_loan_contact, det_loan_until, det_loan_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_matdetail);

        det_title = (TextView) findViewById(R.id.det_title);
        det_desc = (TextView) findViewById(R.id.det_desc);
        det_owner = (TextView) findViewById(R.id.det_owner);
        det_location = (TextView) findViewById(R.id.det_location);

        // Get the Intent that started this activity and extract the string
        Intent intent = this.getIntent();

        // receive Extras
        String title = intent.getExtras().getString("TITLE_KEY");
        String description = intent.getExtras().getString("DESCRIPTION_KEY");
        String owner = intent.getExtras().getString("OWNER_KEY");
        String location = intent.getExtras().getString("LOCATION_KEY");

        //String title = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        //String description = intent.getStringExtra(MainActivity.EXTRA_DUMMY);

        // Bind
        det_title.setText(title);
        det_desc.setText(description);
        det_owner.setText(owner);
        det_location.setText(location);
    }


    public void onEditItem(View view) {
        Context context = getApplicationContext();
        CharSequence text = "edit this Material";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void onLendItem(View view) {
        Context context = getApplicationContext();
        CharSequence text = "Artikel ausleihen";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void onReturnItem(View view) {
        Context context = getApplicationContext();
        CharSequence text = "Artikel zurückgeben";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
