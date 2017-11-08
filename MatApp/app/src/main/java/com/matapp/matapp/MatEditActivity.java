package com.matapp.matapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
    EditText det_title, det_desc, det_owner, det_location, det_gps, det_barcode,
            det_loan_name, det_loan_contact, det_loan_until, det_loan_note;
    Spinner det_status;
    int itemId, status;
    String title, description, owner, location, gps, barcode, img, loanName, loanContact, loanUntil, loanNote, codeContent;
    FloatingActionButton fabAddImg, scanning;
    ImageView det_img;
    TextView formatTxt, contentTxt;

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

        det_img = (ImageView) findViewById(R.id.img_mat_edit);
        contentTxt = (TextView)findViewById(R.id.barcode_result);

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
                // same as update image on Edit Material?!?
                Toast.makeText(getApplicationContext(), "Bild hinzufügen", Toast.LENGTH_SHORT).show();
                // take Picture
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }

        });

        scanning = (FloatingActionButton) findViewById(R.id.fab_add_barcode);
        scanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Barcode hinzufügen", Toast.LENGTH_SHORT).show();
                addBarcode(scanning);
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

    public void addBarcode(FloatingActionButton mi) {
        Toast.makeText(getApplicationContext(), "Barcode hinzufügen", Toast.LENGTH_SHORT).show();

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
                codeContent = scanningResult.getContents();
                //codeFormat = scanningResult.getFormatName();

                // formatTxt.setText("FORMAT: " + codeFormat);
                contentTxt.setText(codeContent);
            }else{
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            // TODO resize image, make it fit the full header area...
            // more Info here: https://developer.android.com/training/camera/photobasics.html
            det_img.setImageBitmap(imageBitmap);

            // TODO convert Bitmap into String with Base64 and downsize it
            // this not sure if this basic conversion is working...
            img = imageBitmap.toString();

            /*
        Bitmap bitmap = (Bitmap)intent.getExtras().get("intent");
        //imageView.setImageBitmap(bitmap);

        //endcode the Bitmap Image to Base 64
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArry = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(byteArry,Base64.DEFAULT); //comprimiertes und konvertiertes Bild. ev.

        Toast.makeText(getApplicationContext(), "Bild: " +encodedImage, Toast.LENGTH_SHORT).show();

        //decode the Image from Base64 to Bitmap
        byte[] decodedString = Base64.decode(encodedImage,Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);*/

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
        }
    }
}
