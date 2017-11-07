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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.matapp.matapp.fragments.MatDeleteAlertDialogFragment;
import com.matapp.matapp.other.Material;


/**
 *
 * This activity is used to display the details of a Material item.
 * Depending on its status, an item can be lent or returned through this activity.
 * This activity also allows to delete items and pass them on to MatEditActivity
 * for further modification.
 *
 * Created by kathrinkoebel on 07.11.17.
 *
 **/


public class MatDetailActivity extends AppCompatActivity
        implements MatDeleteAlertDialogFragment.MatDeleteDialogListener {

    /* Variables for Mat Detail */
    TextView det_title, det_desc, det_owner, det_location, det_gps, det_status, det_barcode, det_img,
            det_loan, det_loan_name, det_loan_contact, det_loan_until, det_loan_note;

    Spinner det_status_edit;
    Button btn_loan, btn_return, btn_update, btn_delete;
    FloatingActionButton fabEditItem;
    int itemId, status;
    String title, description, owner, location, gps, barcode, img, loanName, loanContact, loanUntil, loanNote;

    FragmentManager fm = getSupportFragmentManager();


    /* Lifecycle Methods */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matdetail);

        // binding of UI elements
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

        // Get the Intent that started this activity and extract values
        Intent intent = this.getIntent();

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
        det_title.setText(title);
        det_desc.setText(description);
        det_owner.setText(owner);
        det_location.setText(location);
        det_loan_name.setText(loanName);
        det_loan_contact.setText(loanContact);
        det_loan_until.setText(loanUntil);
        det_loan_note.setText(loanNote);

        // display loan subtitle only if any of the loan attributes contains a value
        // if (loanName.trim().length() == 0 && loanContact.trim().length() == 0 && loanUntil.trim().length() == 0 && loanNote.trim().length() == 0 )
        String loanDetails = loanName + loanContact + loanUntil + loanNote;
        if (loanDetails.trim().length() == 0) {
            det_loan.setVisibility(View.GONE);
        }
        // hide empty fields
        if (loanName != null && loanName.trim().length() > 0) {
            det_loan_name.setVisibility(View.VISIBLE);
        } else {
            det_loan_name.setVisibility(View.GONE);
        }
        if (loanContact != null && loanContact.trim().length() > 0) {
            det_loan_contact.setVisibility(View.VISIBLE);
        } else {
            det_loan_contact.setVisibility(View.GONE);
        }
        if (loanUntil != null && loanUntil.trim().length() > 0) {
            det_loan_until.setVisibility(View.VISIBLE);
        } else {
            det_loan_until.setVisibility(View.GONE);
        }
        if (loanNote != null && loanNote.trim().length() > 0) {
            det_loan_note.setVisibility(View.VISIBLE);
        } else {
            det_loan_note.setVisibility(View.GONE);
        }

        // status dependent bindings & visibility settings
        // det_status: transform status value into text variable
        // buttons: show/hide depending on status
        if (status == Material.STATUS_AVAILABLE) {
            det_status.setText(R.string.det_status_available);
            btn_loan.setVisibility(View.VISIBLE);
            btn_return.setVisibility(View.GONE);
        }

        else if (status == Material.STATUS_LENT) {
            det_status.setText(R.string.det_status_lent);
            btn_loan.setVisibility(View.GONE);
            btn_return.setVisibility(View.VISIBLE);
        }

        // 2 = material nicht verfügbar (default)
        else {
            det_status.setText(R.string.det_status_unavailable);
            btn_loan.setVisibility(View.GONE);
            btn_return.setVisibility(View.GONE);
        }

        // edit Item FAB
        // on click all attributes of current item are passed on to the edit material activity
        fabEditItem = (FloatingActionButton) findViewById(R.id.fab_edit_item);
        fabEditItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                // put extras into intent to pass them on to the Material Detail
                Intent intent = new Intent(context, MatEditActivity.class);
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

                // start Material Edit Activity
                context.startActivity(intent);

                // change view
                setContentView(R.layout.fragment_mat_edit);
            }
        });

        // attach AlertDialog to Delete Button
        // and pass the item's uniqueId and title on to the AlertDialog
        btn_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MatDeleteAlertDialogFragment alertDialog = new MatDeleteAlertDialogFragment();
                Bundle args = new Bundle();
                args.putInt("ID", itemId);
                args.putString("TITLE", title);
                alertDialog.setArguments(args);
                alertDialog.show(fm, "MatDeleteAlertDialogFragment");
            }
        });

        // attach AlertDialog to Return Button
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

        // attach AlertDialog to Loan Button
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

    }

    public void onEditItem(View view) {
        Context context = getApplicationContext();
        CharSequence text = "edit this Material";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
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
