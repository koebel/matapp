package com.matapp.matapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


/**
 *
 * This activity is used to edit Materials. It allows modification of existing attributes
 * and adding additional attributes to items.
 *
 * Created by kathrinkoebel on 07.11.17.
 *
 **/


public class MatEditActivity extends AppCompatActivity {

    /* Variables for Mat Detail Edit */
    EditText det_title, det_desc, det_owner, det_location, det_gps, det_barcode, det_img,
            det_loan_name, det_loan_contact, det_loan_until, det_loan_note;
    Spinner det_status;
    int itemId, status;
    String title, description, owner, location, gps, barcode, img, loanName, loanContact, loanUntil, loanNote;
    FloatingActionButton fabAddImg;

    Intent intent;

    /* Lifecycle Methods */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mat_edit);

        // binding of UI elements
        det_title = (EditText) findViewById(R.id.det_title_edit);
        det_desc = (EditText) findViewById(R.id.det_desc_edit);
        det_owner = (EditText) findViewById(R.id.det_owner_edit);
        det_location = (EditText) findViewById(R.id.det_location_edit);
        det_status = (Spinner) findViewById(R.id.det_status_edit);

        det_loan_name = (EditText) findViewById(R.id.det_loan_name_edit);
        det_loan_contact = (EditText) findViewById(R.id.det_loan_contact_edit);
        det_loan_until = (EditText) findViewById(R.id.det_loan_until_edit);
        det_loan_note = (EditText) findViewById(R.id.det_loan_note_edit);

        // Get the Intent that started this activity and extract values
        intent = this.getIntent();

        // receive Extras
        itemId = intent.getExtras().getInt("ID_KEY");
        title = intent.getExtras().getString("TITLE_KEY");
        description = intent.getExtras().getString("DESCRIPTION_KEY");
        owner = intent.getExtras().getString("OWNER_KEY");
        location = intent.getExtras().getString("LOCATION_KEY");
        status = intent.getExtras().getInt("STATUS_KEY");
        gps = intent.getExtras().getString("GPS_KEY");
        barcode = intent.getExtras().getString("BARCODE_KEY");
        img = intent.getExtras().getString("IMAGE_KEY");
        loanName = intent.getExtras().getString("LOAN_NAME_KEY");
        loanContact = intent.getExtras().getString("LOAN_CONTACT_KEY");
        loanUntil = intent.getExtras().getString("LOAN_UNTIL_KEY");
        loanNote = intent.getExtras().getString("LOAN_NOTE_KEY");

        // binding of extracted values to the UI elements
        // if no value is stored for attributes show default value for that field
        det_title.setText(title);

        if (description != null && description.trim().length() > 0) {
            det_desc.setText(description);
        } else {
            det_desc.setHint(R.string.det_desc_hint);
            //det_desc.setTextColor(getResources().getColor(R.color.colorPlaceholder));
        }

        if (owner != null && owner.trim().length() > 0) {
            det_owner.setText(owner);
        } else {
            det_owner.setHint(R.string.det_owner_hint);
            //det_owner.setTextColor(getResources().getColor(R.color.colorPlaceholder));
        }

        if (location != null && location.trim().length() > 0) {
            det_location.setText(location);
        } else {
            det_location.setHint(R.string.det_location_hint);
            //det_location.setTextColor(getResources().getColor(R.color.colorPlaceholder));
        }

        det_status.setSelection(status);

        if (loanName != null && loanName.trim().length() > 0) {
            det_loan_name.setText(loanName);
        } else {
            det_loan_name.setHint(R.string.det_loan_name_hint);
            //det_loan_name.setTextColor(getResources().getColor(R.color.colorPlaceholder));
        }
        if (loanContact != null && loanContact.trim().length() > 0) {
            det_loan_contact.setText(loanContact);
        } else {
            det_loan_contact.setHint(R.string.det_loan_contact_hint);
            //det_loan_contact.setTextColor(getResources().getColor(R.color.colorPlaceholder));
        }
        if (loanUntil != null && loanUntil.trim().length() > 0) {
            det_loan_until.setText(loanUntil);
        } else {
            det_loan_until.setHint(R.string.det_loan_until_hint);
            //det_loan_until.setTextColor(getResources().getColor(R.color.colorPlaceholder));
        }
        if (loanNote != null && loanNote.trim().length() > 0) {
            det_loan_note.setText(loanNote);
        } else {
            det_loan_note.setHint(R.string.det_loan_note_hint);
            //det_loan_note.setTextColor(getResources().getColor(R.color.colorPlaceholder));
        }

        // add Image FAB
        fabAddImg = (FloatingActionButton) findViewById(R.id.fab_add_img);
        fabAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO add image
                // same as adding image on Add Material?!?
                Toast.makeText(getApplicationContext(), "Bild hinzufügen", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Update Button:
    // store changes into DB by updating the modified Material item attributes
    public void onEditItem(View v) {
        // get Content from Input fields
        // if fields contain Helpertext reset to empty String
        title = det_title.getText().toString();

        if (title.equals(getText(R.string.det_title).toString())) {
            title = "";
        }

        if (title.trim().length() == 0) {
            // TODO: to something, title shouldn't be empty!!!
            // don't allow material to be saved unless there is a valid title
            // see add material for implementation of this
        }

        description = det_desc.getText().toString();
        owner = det_owner.getText().toString();
        location = det_location.getText().toString();
        status = det_status.getSelectedItemPosition();
        loanName = det_loan_name.getText().toString();
        loanContact = det_loan_contact.getText().toString();
        loanUntil = det_loan_until.getText().toString();
        loanNote = det_loan_note.getText().toString();

        // TODO save changes of this material
        // important: item should keep the same uniqueId!
        // Toast.makeText(getApplicationContext(), "Änderungen wurde gespeichert", Toast.LENGTH_SHORT).show();

        // put extras into intent to pass them on to the Material Detail
        Intent intent = new Intent(this, MatDetailActivity.class);
        intent.putExtra("ID_KEY", itemId);
        intent.putExtra("TITLE_KEY", title);
        intent.putExtra("DESCRIPTION_KEY", description);
        intent.putExtra("OWNER_KEY", owner);
        intent.putExtra("LOCATION_KEY", location);
        intent.putExtra("STATUS_KEY", status);
        intent.putExtra("GPS_KEY", gps);
        intent.putExtra("BARCODE_KEY", barcode);
        intent.putExtra("IMAGE_KEY", img);
        intent.putExtra("LOAN_NAME_KEY", loanName);
        intent.putExtra("LOAN_CONTACT_KEY", loanContact);
        intent.putExtra("LOAN_UNTIL_KEY", loanUntil);
        intent.putExtra("LOAN_NOTE_KEY", loanNote);

        // start Material Detail Activity
        startActivity(intent);
    }

    // Cancel Button:
    // return to Material Detail view without making any changes
    public void onCancel(View view) {

        // create intent with original values passed on to this view
        // and load Mat Detail activity
        Intent intent = new Intent(this, MatDetailActivity.class);
        intent.putExtra("ID_KEY", this.itemId);
        intent.putExtra("TITLE_KEY", this.title);
        intent.putExtra("DESCRIPTION_KEY", this.description);
        intent.putExtra("OWNER_KEY", this.owner);
        intent.putExtra("LOCATION_KEY", this.location);
        intent.putExtra("STATUS_KEY", this.status);
        intent.putExtra("GPS_KEY", this.gps);
        intent.putExtra("BARCODE_KEY", this.barcode);
        intent.putExtra("IMAGE_KEY", this.img);
        intent.putExtra("LOAN_NAME_KEY", this.loanName);
        intent.putExtra("LOAN_CONTACT_KEY", this.loanContact);
        intent.putExtra("LOAN_UNTIL_KEY", this.loanUntil);
        intent.putExtra("LOAN_NOTE_KEY", this.loanNote);

        // start Material Detail Activity
        startActivity(intent);
    }
}
