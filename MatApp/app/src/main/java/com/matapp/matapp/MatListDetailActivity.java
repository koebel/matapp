package com.matapp.matapp;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.matapp.matapp.fragments.MatDeleteAlertDialogFragment;
import com.matapp.matapp.other.Material;

public class MatListDetailActivity extends AppCompatActivity implements MatDeleteAlertDialogFragment.MatDeleteDialogListener {

    /* Variables for Mat Detail */
    TextView det_title, det_desc, det_owner, det_location, det_gps, det_status, det_barcode, det_img,
            det_loan, det_loan_name, det_loan_contact, det_loan_until, det_loan_note;

    /* Variables for Mat Detail Edit */
    EditText det_title_edit, det_desc_edit, det_owner_edit, det_location_edit, det_gps_edit, det_barcode_edit, det_img_edit,
            det_loan_edit, det_loan_name_edit, det_loan_contact_edit, det_loan_until_edit, det_loan_note_edit;
    Spinner det_status_edit;

    Button btn_loan, btn_return, btn_update, btn_delete;

    int currentItemID, status;
    String title, description, owner, location, gps, barcode, img, loanName, loanContact, loanUntil, loanNote;

    FragmentManager fm = getSupportFragmentManager();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_matdetail);

        det_title = (TextView) findViewById(R.id.det_title);
        det_desc = (TextView) findViewById(R.id.det_desc);
        det_owner = (TextView) findViewById(R.id.det_owner);
        det_location = (TextView) findViewById(R.id.det_location);
        det_status = (TextView) findViewById(R.id.det_status);
        det_loan = (TextView) findViewById(R.id.det_loan);
        det_loan_name = (TextView) findViewById(R.id.det_loan_name);
        det_loan_contact = (TextView) findViewById(R.id.det_loan_contact);
        det_loan_until = (TextView) findViewById(R.id.det_loan_until);
        det_loan_note = (TextView) findViewById(R.id.det_loan_note);

        btn_loan = (Button) findViewById(R.id.btn_loan);
        btn_return = (Button) findViewById(R.id.btn_return);
        //btn_update = (Button) findViewById(R.id.btn_update);
        btn_delete = (Button) findViewById(R.id.btn_delete_material);

        // Get the Intent that started this activity and extract the string
        Intent intent = this.getIntent();

        // receive Extras
        currentItemID = intent.getExtras().getInt("ID_KEY");
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

        // attach
        FloatingActionButton fabEditItem = (FloatingActionButton) findViewById(R.id.fab_edit_item);
        fabEditItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // change view
                setContentView(R.layout.fragment_mat_edit);
            }
        });

        /*
        FloatingActionButton fabAddImg = (FloatingActionButton) findViewById(R.id.fab_add_img);
        fabAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO add image
                Context context = getApplicationContext();
                CharSequence text = "Bild hinzufügen";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
        */

        // attach AlertDialog to Mat Delete Button
        btn_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MatDeleteAlertDialogFragment alertDialog = new MatDeleteAlertDialogFragment();
                Bundle args = new Bundle();
                args.putInt("ID", currentItemID);
                args.putString("TITLE", title);
                alertDialog.setArguments(args);
                alertDialog.show(fm, "MatDeleteAlertDialogFragment");
            }
        });

        // attach AlertDialog to Mat Return Button
        btn_return.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO implement Return Item
                Context context = getApplicationContext();
                CharSequence text = "Artikel zurückgeben";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        // attach AlertDialog to Mat Loan Button
        btn_loan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO implement borrow Item
                Context context = getApplicationContext();
                CharSequence text = "Artikel ausleihen";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        /*
        // attach AlertDialog to Mat Loan Button
        btn_update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO implement save changes
                Context context = getApplicationContext();
                CharSequence text = "Änderungen speichern";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
        */

        // Bind
        det_title.setText(title);
        det_desc.setText(description);
        det_owner.setText(owner);
        det_location.setText(location);
        det_loan.setText(R.string.det_loan_sub);
        det_loan_name.setText(loanName);
        det_loan_contact.setText(loanContact);
        det_loan_until.setText(loanUntil);
        det_loan_note.setText(loanNote);

        // status dependent bindings & visibility settings
        // det_status: transform status value into text variable
        // buttons: show/hide depending on status
        // loan: show/hide section

        if (status == Material.STATUS_AVAILABLE) {
            det_status.setText(R.string.det_status_available);
            btn_loan.setVisibility(View.VISIBLE);
            btn_return.setVisibility(View.GONE);

            det_loan.setVisibility(View.GONE);
            det_loan_name.setVisibility(View.GONE);
            det_loan_contact.setVisibility(View.GONE);
            det_loan_until.setVisibility(View.GONE);
            det_loan_note.setVisibility(View.GONE);
        }

        else if (status == Material.STATUS_LENT) {
            det_status.setText(R.string.det_status_lent);
            btn_loan.setVisibility(View.GONE);
            btn_return.setVisibility(View.VISIBLE);

            // display only if not empty
            if(loanName != null && loanName.length() == 0) {
                det_loan_name.setVisibility(View.GONE);
            }
            if(loanContact != null && loanContact.length() == 0) {
                det_loan_contact.setVisibility(View.GONE);
            }
            if(loanUntil != null && loanUntil.length() == 0) {
                det_loan_contact.setVisibility(View.GONE);
            }
            if(loanNote != null && loanNote.length() == 0) {
                det_loan_note.setVisibility(View.GONE);
            }
        }

        // 2 = material nicht verfügbar (default)
        else {
            det_status.setText(R.string.det_status_unavailable);
            btn_loan.setVisibility(View.GONE);
            btn_return.setVisibility(View.GONE);
            //btn_loan.setVisibility(View.INVISIBLE);
            //btn_return.setVisibility(View.INVISIBLE);

            det_loan.setVisibility(View.GONE);
            det_loan_name.setVisibility(View.GONE);
            det_loan_contact.setVisibility(View.GONE);
            det_loan_until.setVisibility(View.GONE);
            if(loanNote != null && loanNote.length() == 0) {
                det_loan_note.setVisibility(View.GONE);
            }
        }
    }


    public void onEditItem(View view) {
        Context context = getApplicationContext();
        CharSequence text = "edit this Material";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void onDeleteItem() {
        // TODO  Delete
        // if yes, delete Item and return to overview
        Context context = getApplicationContext();
        CharSequence text = "Artikel löschen";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public int getCurrentItemID() {
        return currentItemID;
    }

    public void setCurrentItemID(int currentItemID) {
        this.currentItemID = currentItemID;
    }


    /* Implementation of MatDeleteDialogListener */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, int id) {
        // TODO delete Material with this id
        // deleteMaterial(id);
        dialog.dismiss();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

}
