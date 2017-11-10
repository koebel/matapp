package com.matapp.matapp;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.matapp.matapp.other.Material;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Calendar;


/**
 *
 * This activity is used to add new Materials to the app.
 *
 * Created by kathrinkoebel on 07.11.17.
 *
 **/

public class MatAddActivity extends AppCompatActivity {

    /* Variables for Mat Detail Add */
    EditText det_title, det_desc, det_owner, det_location, det_gps, det_barcode;
    Spinner det_status;
    int status;
    String title, description, owner, location, gps, barcode, img = "", thumb, codeFormat,codeContent = "", mCurrentPhotoPath;
    Button btn_create, btn_delete, btn_add_barcode;
    FloatingActionButton fabAddImg, scanning;
    ImageView imageView;
    TextView formatTxt, contentTxt;
    ImageView det_img;
    Bitmap mBitmapPhoto;

    private FirebaseDatabase database;
    private DatabaseReference itemReference;
    private StorageReference mStorage;
    private Uri uriFilePath;
    private ProgressDialog mProgress;

    /* static Variables */
    static final int REQUEST_IMAGE_CAPTURE = 1;


    /* Lifecycle Methods */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mat_add);


        //Get Firebase database instance
        database = FirebaseDatabase.getInstance();
        mStorage = FirebaseStorage .getInstance().getReference();
        mProgress = new ProgressDialog(this);
        //Get reference to material
        itemReference = database.getReference("material/" + MatAppSession.getInstance().listKey + "/item");
        Log.i("MatAddActivity", "Reference: " + "material/" + MatAppSession.getInstance().listKey + "/item");

        // binding of UI elements
        det_title = (EditText) findViewById(R.id.det_title_add);
        det_desc = (EditText) findViewById(R.id.det_desc_add);
        det_owner = (EditText) findViewById(R.id.det_owner_add);
        det_location = (EditText) findViewById(R.id.det_location_add);
        det_status = (Spinner) findViewById(R.id.det_status_add);
        //imageView = (ImageView) findViewById(R.id.showPic);

        //formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.barcode_result);
        //getIntent().hasExtra("barcode");
        if(getIntent().hasExtra("barcode")){
            String scannercontext = getIntent().getStringExtra("barcode");
            contentTxt.setText(scannercontext);
        }

        det_img = (ImageView) findViewById(R.id.img_mat_add);

        btn_create = (Button) findViewById(R.id.btn_create);
        btn_delete = (Button) findViewById(R.id.btn_delete_material);

        // Get the Intent that started this activity (and extract values)
        // there are no values passed into this activity
        final Intent intent = this.getIntent();


        btn_add_barcode = (Button) findViewById(R.id.btn_add_barcode);
        btn_add_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Barcode hinzufügen", Toast.LENGTH_SHORT).show();
                addBarcode();
            }
        });

        // add Image FAB
        fabAddImg = (FloatingActionButton) findViewById(R.id.fab_add_img);
        fabAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uriFilePath =getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriFilePath);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
    }


    // TODO save Image into DB!
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // super.onActivityResult(requestCode, resultCode, intent);

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult != null) {
            if (scanningResult.getContents() != null) {
                //we have a result
                codeContent = scanningResult.getContents();
                //codeFormat = scanningResult.getFormatName();

                // formatTxt.setText("FORMAT: " + codeFormat);
                contentTxt.setText(codeContent);
            } else {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }else if ((requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)){
            /*
            //add Image to firebase
            mProgress.setMessage("Uploading Image ...");
            mProgress.show();
            StorageReference filepath = mStorage.child("Foto").child("222");
            filepath.putFile(uriFilePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgress.dismiss();
                    Toast.makeText(MatAddActivity.this, "Uploading finshed...", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MatAddActivity.this, "Uploading failed...", Toast.LENGTH_LONG).show();
                    //do something
                }
            });*/

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            // TODO resize image, make it fit the full header area...
            // more Info here: https://developer.android.com/training/camera/photobasics.html
            det_img.setImageBitmap(imageBitmap);

            // TODO convert Bitmap into String with Base64 and downsize it
            // this not sure if this basic conversion is working...
            img = imageBitmap.toString();

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

    /* Implement Functions for Click on add Barcode Button */
    // TODO add this function to the new Button (not FAB),
    // looks like there are some dependencies... doesn't work for regular Button
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

    // create new Material
    public void onCreateItem (View view){
        // get Content from Input fields
        // if fields contain Helpertext reset to empty String
        title = det_title.getText().toString();
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(getApplicationContext(), R.string.det_title_error, Toast.LENGTH_SHORT).show();
            return;
        }
        description = det_desc.getText().toString();
        owner = det_owner.getText().toString();
        location = det_location.getText().toString();
        status = det_status.getSelectedItemPosition();

        // create new Material Object
        Material newMat = new Material(title, description, owner, location, status);
        newMat.setImg(img);
        newMat.setBarcode(codeContent);

        //Save newMat into DB
        String itemKey = itemReference.push().getKey();
        itemReference.child(itemKey).setValue(newMat);
        Log.i("MatAddActivity", "new item created with itemKey=" + itemKey);

        /*
        //add Image to firebase
        mProgress.setMessage("Uploading Image ...");
        mProgress.show();
        StorageReference filepath = mStorage.child("Foto").child(itemKey);
        filepath.putFile(uriFilePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mProgress.dismiss();
                Toast.makeText(MatAddActivity.this, "Uploading finshed...", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MatAddActivity.this, "Uploading failed...", Toast.LENGTH_LONG).show();
                //do something
            }
        });*/

        // load Mat Detail activity
        Intent intent = new Intent(this, MatDetailActivity.class);
        intent.putExtra("ITEM_KEY", itemKey);

        // start Material Detail Activity
        startActivity(intent);
        finish();
    }
}
