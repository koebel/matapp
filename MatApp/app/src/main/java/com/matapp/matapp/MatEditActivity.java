package com.matapp.matapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.matapp.matapp.other.Material;

import static com.matapp.matapp.MatAddActivity.REQUEST_IMAGE_CAPTURE;


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
    EditText det_title, det_desc, det_owner, det_location, det_barcode,
            det_loan_name, det_loan_contact, det_loan_until, det_loan_note;
    Spinner det_status;
    //int itemId, status;
    //String title, description, owner, location, gps, img, thumb, loanName, loanContact, loanUntil, loanNote, barcode;
    FloatingActionButton fabAddImg;
    Button btn_add_barcode;
    ImageView det_img;
    TextView formatTxt, textViewBarcode;

    Intent intent;

    private String itemKey;
    private Material item;

    private FirebaseDatabase database;
    private DatabaseReference itemReference;

    /* Lifecycle Methods */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mat_edit);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

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

        det_img = (ImageView) findViewById(R.id.img_mat_edit);
        textViewBarcode = (TextView)findViewById(R.id.barcode_result);

        // Get the Intent that started this activity and extract values
        intent = this.getIntent();

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
                Log.i("MatEditActivity", "item: " + item);

                // binding of extracted values to the UI elements
                // if no value is stored for attributes show default value for that field
                det_title.setText(item.getTitle());

                if (item.getDescription() != null && item.getDescription().trim().length() > 0) {
                    det_desc.setText(item.getDescription());
                } else {
                    det_desc.setHint(R.string.det_desc_hint);
                    //det_desc.setTextColor(getResources().getColor(R.color.colorPlaceholder));
                }

                if (item.getOwner() != null && item.getOwner().trim().length() > 0) {
                    det_owner.setText(item.getOwner());
                } else {
                    det_owner.setHint(R.string.det_owner_hint);
                    //det_owner.setTextColor(getResources().getColor(R.color.colorPlaceholder));
                }

                if (item.getLocation() != null && item.getLocation().trim().length() > 0) {
                    det_location.setText(item.getLocation());
                } else {
                    det_location.setHint(R.string.det_location_hint);
                    //det_location.setTextColor(getResources().getColor(R.color.colorPlaceholder));
                }

                det_status.setSelection(item.getStatus());

                if (item.getLoanName() != null && item.getLoanName().trim().length() > 0) {
                    det_loan_name.setText(item.getLoanName());
                } else {
                    det_loan_name.setHint(R.string.det_loan_name_hint);
                    //det_loan_name.setTextColor(getResources().getColor(R.color.colorPlaceholder));
                }
                if (item.getLoanContact() != null && item.getLoanContact().trim().length() > 0) {
                    det_loan_contact.setText(item.getLoanContact());
                } else {
                    det_loan_contact.setHint(R.string.det_loan_contact_hint);
                    //det_loan_contact.setTextColor(getResources().getColor(R.color.colorPlaceholder));
                }
                if (item.getLoanUntil() != null && item.getLoanUntil().trim().length() > 0) {
                    det_loan_until.setText(item.getLoanUntil());
                } else {
                    det_loan_until.setHint(R.string.det_loan_until_hint);
                    //det_loan_until.setTextColor(getResources().getColor(R.color.colorPlaceholder));
                }
                if (item.getLoanNote() != null && item.getLoanNote().trim().length() > 0) {
                    det_loan_note.setText(item.getLoanNote());
                } else {
                    det_loan_note.setHint(R.string.det_loan_note_hint);
                    //det_loan_note.setTextColor(getResources().getColor(R.color.colorPlaceholder));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // add Image FAB
        fabAddImg = (FloatingActionButton) findViewById(R.id.fab_add_img);
        fabAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO add image
                // same as update image on Edit Material?!?
                Toast.makeText(getApplicationContext(), "Bild hinzufügen", Toast.LENGTH_SHORT).show();
                // take Picture
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }

        });

        btn_add_barcode = (Button) findViewById(R.id.btn_add_barcode);
        btn_add_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Barcode hinzufügen", Toast.LENGTH_SHORT).show();
                addBarcode();
            }
        });
    }

    // Update Button:
    // store changes into DB by updating the modified Material item attributes
    public void onEditItem(View v) {
        // get Content from Input fields
        // if fields contain Helpertext reset to empty String
        String title = det_title.getText().toString();
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(getApplicationContext(), R.string.det_title_error, Toast.LENGTH_SHORT).show();
            return;
        }

        item.setTitle(title);
        item.setDescription(det_desc.getText().toString());
        item.setOwner(det_owner.getText().toString());
        item.setLocation(det_location.getText().toString());
        item.setStatus(det_status.getSelectedItemPosition());
        item.setLoanName(det_loan_name.getText().toString());
        item.setLoanContact(det_loan_contact.getText().toString());
        item.setLoanUntil(det_loan_until.getText().toString());
        item.setLoanNote(det_loan_note.getText().toString());

        //Save changes in database
        itemReference.child(itemKey).setValue(item);
        Log.i("MatAddActivity", "saved modified item with itemKey=" + itemKey);
        Toast.makeText(getApplicationContext(), getString(R.string.edit_saved), Toast.LENGTH_SHORT).show();

        // put extras into intent to pass them on to the Material Detail
        //Intent intent = new Intent(this, MatDetailActivity.class);
        //intent.putExtra("ITEM_KEY", itemKey);

        // start Material Detail Activity
        //startActivity(intent);
        finish();
    }

    // Cancel Button:
    // return to Material Detail view without making any changes
    public void onCancel(View view) {

        // create intent with original values passed on to this view
        // and load Mat Detail activity
        //Intent intent = new Intent(this, MatDetailActivity.class);
        //intent.putExtra("ITEM_KEY", itemKey);

        // start Material Detail Activity
        //startActivity(intent);
        finish();
    }

    public void addBarcode() {

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt(this.getString(R.string.scan_bar_code));
        integrator.setCaptureActivity(ScannerActivity.class);
        integrator.setOrientationLocked(false);
        //integrator.setResultDisplayDuration(0);
        //integrator.setWide();  // Wide scanning rectangle, may work better for 1D barcodes
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBarcodeImageEnabled(true);
        // set turn the camera flash on by default
        // integrator.addExtra(appConstants.CAMERA_FLASH_ON,true);
        integrator.initiateScan();

    }

    // TODO save Image into DB!
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // super.onActivityResult(requestCode, resultCode, intent);

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult != null) {
            if(scanningResult.getContents() != null){
                //we have a result
                String barcode = scanningResult.getContents();
                item.setBarcode(barcode);
                textViewBarcode.setText(barcode);
            }else{
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            // TODO resize image, make it fit the full header area...
            // more Info here: https://developer.android.com/training/camera/photobasics.html
            det_img.setImageBitmap(imageBitmap);

            // TODO convert Bitmap into String with Base64 and downsize it
            // this not sure if this basic conversion is working...
            //item.setImg(imageBitmap.toString());
            /*
            maybe use this code for Base64 conversion:
            https://stackoverflow.com/questions/4837110/how-to-convert-a-base64-string-into-a-bitmap-image-to-show-it-in-a-imageview

            //encode image to base64 string
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            //decode base64 string to image
            imageBytes = Base64.decode(imageString, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            image.setImageBitmap(decodedImage);
             */
        }else {
            Toast toast = Toast.makeText(getApplicationContext(),"No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
