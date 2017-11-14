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
    TextView detTitle;
    TextView detDesc;
    TextView detOwner;
    TextView detLocation;
    TextView detStatus;
    TextView detBarcode;
    TextView detBarcodeResult;
    TextView detLoan;
    TextView detLoanName;
    TextView detLoanContact;
    TextView detLoanUntil;
    TextView detLoanNote;

    Button btnLoan;
    Button btnReturn;
    Button btnDelete;
    FloatingActionButton fabEditItem;
    ImageView detImg;

    private String itemKey;
    private Material item;

    private FirebaseDatabase database;
    private DatabaseReference itemReference;

    FragmentManager fragmentManagerDelete = getSupportFragmentManager();
    FragmentManager fragmentManagerLoan = getSupportFragmentManager();

    /* Lifecycle Methods */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matdetail);

        // binding of UI elements
        detTitle = (TextView) findViewById(R.id.det_title);
        detDesc = (TextView) findViewById(R.id.det_desc);
        detOwner = (TextView) findViewById(R.id.det_owner);
        detLocation = (TextView) findViewById(R.id.det_location);
        detStatus = (TextView) findViewById(R.id.det_status);
        detLoan = (TextView) findViewById(R.id.det_loan);
        detLoanName = (TextView) findViewById(R.id.det_loan_name);
        detLoanContact = (TextView) findViewById(R.id.det_loan_contact);
        detLoanUntil = (TextView) findViewById(R.id.det_loan_until);
        detLoanNote = (TextView) findViewById(R.id.det_loan_note);
        detImg = (ImageView) findViewById(R.id.img_mat);
        detBarcode = (TextView) findViewById(R.id.det_barcode);
        detBarcodeResult = (TextView) findViewById(R.id.barcode_result);

        btnLoan = (Button) findViewById(R.id.btn_loan);
        btnReturn = (Button) findViewById(R.id.btn_return);
        btnDelete = (Button) findViewById(R.id.btn_delete_material);

        // Get the Intent that started this activity and extract values
        Intent intent = this.getIntent();

        // receive Extras
        itemKey = intent.getExtras().getString("ITEM_KEY");

        //Get Firebase database instance
        database = FirebaseDatabase.getInstance();
        //Get reference to material
        itemReference = database.getReference("material/" + MatAppSession.getInstance().getListKey() + "/item");
        //Read from database
        itemReference.child(itemKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                item = dataSnapshot.getValue(Material.class);
                Log.i("MatDetailActivity", "item: " + item);

                if(item != null) {

                    // binding of extracted values to the UI elements
                    detTitle.setText(item.getTitle());
                    detDesc.setText(item.getDescription());
                    detBarcode.setText(item.getBarcode());
                    detOwner.setText(item.getOwner());
                    detLocation.setText(item.getLocation());
                    detLoanName.setText(item.getLoanName());
                    detLoanContact.setText(item.getLoanContact());
                    detLoanUntil.setText(item.getLoanUntil());
                    detLoanNote.setText(item.getLoanNote());

                    //TODO get Full Image from storage.
                    detImg.setImageBitmap(stringToBitmap(item.getImg()));

                    if (item.getBarcode().trim().length() > 0) {
                        detBarcode.setText(item.getBarcode());
                    } else {
                        detBarcode.setText(getString(R.string.det_barcode));
                    }

                    // display only attributes that are not empty
                    if (item.getDescription() != null && item.getDescription().trim().length() == 0) {
                        detDesc.setVisibility(View.GONE);
                    }
                    if (item.getBarcode() != null && item.getBarcode().trim().length() == 0) {
                        detBarcode.setVisibility(View.GONE);
                        detBarcodeResult.setVisibility(View.GONE);
                    }
                    if (item.getOwner() != null && item.getOwner().trim().length() == 0) {
                        detOwner.setVisibility(View.GONE);
                    }
                    if (item.getLocation() != null && item.getLocation().trim().length() == 0) {
                        detLocation.setVisibility(View.GONE);
                    }

                    // display loan subtitle only if any of the loan attributes contains a value
                    String loanDetails = item.getLoanName() + item.getLoanContact() + item.getLoanUntil() + item.getLoanNote();
                    if (loanDetails.trim().length() == 0) {
                        detLoan.setVisibility(View.GONE);
                    } else {
                        detLoan.setVisibility(View.VISIBLE);
                    }
                    // hide empty fields
                    if (item.getLoanName() != null && item.getLoanName().trim().length() > 0) {
                        detLoanName.setVisibility(View.VISIBLE);
                    } else {
                        detLoanName.setVisibility(View.GONE);
                    }
                    if (item.getLoanContact() != null && item.getLoanContact().trim().length() > 0) {
                        detLoanContact.setVisibility(View.VISIBLE);
                    } else {
                        detLoanContact.setVisibility(View.GONE);
                    }
                    if (item.getLoanUntil() != null && item.getLoanUntil().trim().length() > 0) {
                        detLoanUntil.setVisibility(View.VISIBLE);
                    } else {
                        detLoanUntil.setVisibility(View.GONE);
                    }
                    if (item.getLoanNote() != null && item.getLoanNote().trim().length() > 0) {
                        detLoanNote.setVisibility(View.VISIBLE);
                    } else {
                        detLoanNote.setVisibility(View.GONE);
                    }

                    // status dependent bindings & visibility settings
                    // det_status: transform status value into text variable
                    // buttons: show/hide depending on status
                    if (item.getStatus() == Material.STATUS_AVAILABLE) {
                        detStatus.setText(R.string.det_status_available);
                        btnLoan.setVisibility(View.VISIBLE);
                        btnReturn.setVisibility(View.GONE);
                    } else if (item.getStatus() == Material.STATUS_LENT) {
                        detStatus.setText(R.string.det_status_lent);
                        btnLoan.setVisibility(View.GONE);
                        btnReturn.setVisibility(View.VISIBLE);
                    }

                    // material nicht verfügbar
                    else {
                        detStatus.setText(R.string.det_status_unavailable);
                        btnLoan.setVisibility(View.GONE);
                        btnReturn.setVisibility(View.GONE);
                    }

                    if (!MatAppSession.getInstance().isListWriteable()) {
                        btnLoan.setVisibility(View.GONE);
                        btnReturn.setVisibility(View.GONE);
                    }

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
                // start Material Edit Activity
                context.startActivity(intent);
            }
        });

        if(!MatAppSession.getInstance().isListWriteable()) {
            fabEditItem.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }

        // attach AlertDialog to Delete Button
        // and pass the item's uniqueId and title on to the AlertDialog
        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MatDeleteAlertDialogFragment alertDialog = new MatDeleteAlertDialogFragment();
                Bundle args = new Bundle();
                args.putString("ITEMKEY", itemKey);
                args.putString("TITLE", item.getTitle());
                alertDialog.setArguments(args);
                alertDialog.show(fragmentManagerDelete, "MatDeleteAlertDialogFragment");
            }
        });

        // attach AlertDialog to Loan Button
        btnLoan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showLoanAlertDialog();
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

        //Save changes in database
        itemReference.child(itemKey).setValue(item);
        Log.i("MatAddActivity", "saved modified item with itemKey=" + itemKey);
        Toast.makeText(getApplicationContext(), getString(R.string.edit_saved), Toast.LENGTH_SHORT).show();
    }

    public void showLoanAlertDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = MatLoanAlertDialogFragment.newInstance(item.getTitle());
        dialog.show(fragmentManagerLoan, "MatLoanAlertDialogFragment");
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
    public void onDeleteDialogPositiveClick(DialogFragment dialog) {

        //Delete item from DB
        itemReference.child(itemKey).setValue(null);
        Toast.makeText(getApplicationContext(), getString(R.string.delete_item_deleted), Toast.LENGTH_SHORT).show();

        dialog.dismiss();
        finish();
    }

    @Override
    public void onDeleteDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    /* Implementation of MatLoanAlertDialogListener */

    @Override
    public void onLoanDialogPositiveClick(DialogFragment dialog, String loanName, String loanContact, String loanUntil, String loanNote) {

        // update item
        item.setStatus(Material.STATUS_LENT);
        item.setLoanName(loanName);
        item.setLoanContact(loanContact);
        item.setLoanUntil(loanUntil);
        item.setLoanNote(loanNote);

        //Save changes in database
        itemReference.child(itemKey).setValue(item);
        Log.i("MatAddActivity", "saved modified item with itemKey=" + itemKey);
        Toast.makeText(getApplicationContext(), getString(R.string.edit_saved), Toast.LENGTH_SHORT).show();

        dialog.dismiss();
    }

    @Override
    public void onLoanDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

}
