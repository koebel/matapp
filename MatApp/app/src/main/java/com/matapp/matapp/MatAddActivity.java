package com.matapp.matapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.matapp.matapp.fragments.MatListFragment;
import com.matapp.matapp.other.Material;

import java.io.ByteArrayOutputStream;


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
    String title, description, owner, location, gps, barcode, img, codeFormat,codeContent;
    Button btn_create, btn_delete;
    FloatingActionButton fabAddImg, scanning;
    ImageView imageView;
    TextView formatTxt, contentTxt;


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
        imageView = (ImageView) findViewById(R.id.showPic);

        //formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.barcode_result);

        // change color of all "optional" attributes to Placeholder color
        // det_title.setTextColor(getResources().getColor(R.color.colorPlaceholder));
        // det_desc.setTextColor(getResources().getColor(R.color.colorPlaceholder));
        // det_owner.setTextColor(getResources().getColor(R.color.colorPlaceholder));
        // det_location.setTextColor(getResources().getColor(R.color.colorPlaceholder));

        btn_create = (Button) findViewById(R.id.btn_create);
        btn_delete = (Button) findViewById(R.id.btn_delete_material);

        // Get the Intent that started this activity (and extract values)
        // there are no values passed into this activity
        final Intent intent = this.getIntent();

        scanning = (FloatingActionButton) findViewById(R.id.fab_add_barcode);
        scanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Barcode hinzufügen", Toast.LENGTH_SHORT).show();
                addBarcode(scanning);
            }
        });

        // add Image FAB
        fabAddImg = (FloatingActionButton) findViewById(R.id.fab_add_img);
        fabAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO add image
                // same as update image on Edit Material?!?
                Intent picintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(picintent,0);

                Toast.makeText(getApplicationContext(), "Bild hinzufügen", Toast.LENGTH_SHORT).show();
            }

        });


    }

    /* Implement Functions for Click on MenuItems */

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

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        Toast.makeText(getApplicationContext(), "Bild anzeigen", Toast.LENGTH_SHORT).show();

        super.onActivityResult(requestCode, resultCode, intent);

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

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
