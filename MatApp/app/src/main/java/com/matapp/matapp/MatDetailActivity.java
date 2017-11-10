package com.matapp.matapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.matapp.matapp.fragments.MatDeleteAlertDialogFragment;
import com.matapp.matapp.fragments.MatLoanAlertDialogFragment;
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
        implements MatDeleteAlertDialogFragment.MatDeleteDialogListener, MatLoanAlertDialogFragment.LoanDialogListener {

    /* Variables for Mat Detail */
    TextView det_title, det_desc, det_owner, det_location, det_status, det_barcode, det_barcode_result,
            det_loan, det_loan_name, det_loan_contact, det_loan_until, det_loan_note;
    Button btn_loan, btn_return, btn_delete;
    FloatingActionButton fabEditItem;
    ImageView det_img;

    private String itemKey;
    private Material item;

    private FirebaseDatabase database;
    private DatabaseReference itemReference;

    FragmentManager fm_delete = getSupportFragmentManager();
    FragmentManager fm_loan = getSupportFragmentManager();


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
        det_img = (ImageView) findViewById(R.id.img_mat);
        det_barcode = (TextView) findViewById(R.id.det_barcode);
        det_barcode_result = (TextView) findViewById(R.id.barcode_result);

        btn_loan = (Button) findViewById(R.id.btn_loan);
        btn_return = (Button) findViewById(R.id.btn_return);
        btn_delete = (Button) findViewById(R.id.btn_delete_material);

        // Get the Intent that started this activity and extract values
        Intent intent = this.getIntent();

        // receive Extras
        itemKey = intent.getExtras().getString("ITEM_KEY");

        //Get Firebase database instance
        database = FirebaseDatabase.getInstance();
        //Get reference to material
        itemReference = database.getReference("material/" + MatAppSession.getInstance().listKey + "/item");
        //Read from database
        itemReference.child(itemKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                item = dataSnapshot.getValue(Material.class);
                Log.i("MatDetailActivity", "item: " + item);

                // binding of extracted values to the UI elements
                det_title.setText(item.getTitle());
                det_desc.setText(item.getDescription());
                det_barcode.setText(item.getBarcode());
                det_owner.setText(item.getOwner());
                det_location.setText(item.getLocation());
                det_loan_name.setText(item.getLoanName());
                det_loan_contact.setText(item.getLoanContact());
                det_loan_until.setText(item.getLoanUntil());
                det_loan_note.setText(item.getLoanNote());

                det_img.setImageBitmap(stringToBitmap(item.getImg()));

                if(item.getBarcode().trim().length() > 0) {
                    det_barcode.setText(item.getBarcode());
                } else {
                    det_barcode.setText(getString(R.string.det_barcode));
                }

                // display only attributes that are not empty
                if (item.getDescription() != null && item.getDescription().trim().length() == 0){
                    det_desc.setVisibility(View.GONE);
                }
                if (item.getBarcode() != null && item.getBarcode().trim().length() == 0){
                    det_barcode.setVisibility(View.GONE);
                    det_barcode_result.setVisibility(View.GONE);
                }
                if (item.getOwner() != null && item.getOwner().trim().length() == 0){
                    det_owner.setVisibility(View.GONE);
                }
                if (item.getLocation() != null && item.getLocation().trim().length() == 0){
                    det_location.setVisibility(View.GONE);
                }

                // display loan subtitle only if any of the loan attributes contains a value
                // if (loanName.trim().length() == 0 && loanContact.trim().length() == 0 && loanUntil.trim().length() == 0 && loanNote.trim().length() == 0 )
                String loanDetails = item.getLoanName() + item.getLoanContact() + item.getLoanUntil() + item.getLoanNote();
                if (loanDetails.trim().length() == 0) {
                    det_loan.setVisibility(View.GONE);
                }
                // hide empty fields
                if (item.getLoanName() != null && item.getLoanName().trim().length() > 0) {
                    det_loan_name.setVisibility(View.VISIBLE);
                } else {
                    det_loan_name.setVisibility(View.GONE);
                }
                if (item.getLoanContact() != null && item.getLoanContact().trim().length() > 0) {
                    det_loan_contact.setVisibility(View.VISIBLE);
                } else {
                    det_loan_contact.setVisibility(View.GONE);
                }
                if (item.getLoanUntil() != null && item.getLoanUntil().trim().length() > 0) {
                    det_loan_until.setVisibility(View.VISIBLE);
                } else {
                    det_loan_until.setVisibility(View.GONE);
                }
                if (item.getLoanNote() != null && item.getLoanNote().trim().length() > 0) {
                    det_loan_note.setVisibility(View.VISIBLE);
                } else {
                    det_loan_note.setVisibility(View.GONE);
                }

                // status dependent bindings & visibility settings
                // det_status: transform status value into text variable
                // buttons: show/hide depending on status
                if (item.getStatus() == Material.STATUS_AVAILABLE) {
                    det_status.setText(R.string.det_status_available);
                    btn_loan.setVisibility(View.VISIBLE);
                    btn_return.setVisibility(View.GONE);
                }

                else if (item.getStatus() == Material.STATUS_LENT) {
                    det_status.setText(R.string.det_status_lent);
                    btn_loan.setVisibility(View.GONE);
                    btn_return.setVisibility(View.VISIBLE);
                }

                // material nicht verfügbar
                else {
                    det_status.setText(R.string.det_status_unavailable);
                    btn_loan.setVisibility(View.GONE);
                    btn_return.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // edit Item FAB
        // on click all attributes of current item are passed on to the edit material activity
        fabEditItem = (FloatingActionButton) findViewById(R.id.fab_edit_item);
        fabEditItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                // put extras into intent to pass them on to the Material Detail
                Intent intent = new Intent(context, MatEditActivity.class);
                intent.putExtra("ITEM_KEY", itemKey);
                /*intent.putExtra("ID_KEY", itemId);
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
                intent.putExtra("LOAN_NOTE_KEY", loanNote);*/

                // start Material Edit Activity
                context.startActivity(intent);
            }
        });

        // attach AlertDialog to Delete Button
        // and pass the item's uniqueId and title on to the AlertDialog
        btn_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MatDeleteAlertDialogFragment alertDialog = new MatDeleteAlertDialogFragment();
                Bundle args = new Bundle();
                args.putString("ITEMKEY", itemKey);
                args.putString("TITLE", item.getTitle());
                alertDialog.setArguments(args);
                alertDialog.show(fm_delete, "MatDeleteAlertDialogFragment");
            }
        });

        // attach AlertDialog to Loan Button
        btn_loan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO implement borrow Item Popup

                MatLoanAlertDialogFragment alertDialog = new MatLoanAlertDialogFragment();
                Bundle args = new Bundle();
                args.putString("ID", itemKey);
                args.putString("TITLE", item.getTitle());
                alertDialog.setArguments(args);
                alertDialog.show(fm_loan, "MatLoanAlertDialogFragment");

                Toast.makeText(getApplicationContext(), "Artikel ausleihen", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Return Button
    // change status of item and reset loan attributes
    public void onReturnItem(View view) {
        item.setStatus(Material.STATUS_AVAILABLE);
        item.setLoanName("");
        item.setLoanContact("");
        item.setLoanUntil("");
        item.setLoanNote("");

        // TODO save changes into DB

        // reload this Activity
        Intent intent = this.getIntent();
        intent.putExtra("STATUS_KEY", item.getStatus());
        intent.putExtra("LOAN_NAME_KEY", item.getLoanName());
        intent.putExtra("LOAN_CONTACT_KEY", item.getLoanContact());
        intent.putExtra("LOAN_UNTIL_KEY", item.getLoanUntil());
        intent.putExtra("LOAN_NOTE_KEY", item.getLoanNote());
        startActivity(intent);
    }

    // TODO check this function
    // https://stackoverflow.com/questions/23005948/convert-string-to-bitmap
    public Bitmap stringToBitmap(String imgString){
        try{
            byte[] encode = Base64.decode(imgString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(Base64.decode(imgString,Base64.DEFAULT), 0, encode.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    /* Implementation of MatDeleteDialogListener */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, int id) {

        // TODO delete Material with the id transmitted though this function
        Toast.makeText(getApplicationContext(), "Artikel löschen", Toast.LENGTH_SHORT).show();

        // Load Main Activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        dialog.dismiss();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    /* Implementation of MatDeleteDialogListener */
    @Override
    public void onFinishLoanDialog(DialogFragment dialog, String loanName, String loanContact, String loanUntil, String loanNote) {
        
        // refresh this view to display new/changed values
        item.setStatus(Material.STATUS_UNAVAILABLE);
        item.setLoanName(loanName);
        item.setLoanContact(loanContact);
        item.setLoanUntil(loanUntil);
        item.setLoanNote(loanNote);

        // TODO save the changes into database

        // reload this Activity
        Intent intent = this.getIntent();
        intent.putExtra("STATUS_KEY", item.getStatus());
        intent.putExtra("LOAN_NAME_KEY", item.getLoanName());
        intent.putExtra("LOAN_CONTACT_KEY", item.getLoanContact());
        intent.putExtra("LOAN_UNTIL_KEY", item.getLoanUntil());
        intent.putExtra("LOAN_NOTE_KEY", item.getLoanNote());
        startActivity(intent);
    }
}
