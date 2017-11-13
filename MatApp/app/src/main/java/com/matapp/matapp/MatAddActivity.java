package com.matapp.matapp;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.matapp.matapp.other.Material;

import java.io.ByteArrayOutputStream;
import java.io.File;


/**
 *
 * This activity is used to add new Materials to the app.
 *
 * Created by kathrinkoebel on 07.11.17.
 *
 **/

public class MatAddActivity extends AppCompatActivity {

    /* Variables for Mat Detail Add */
    EditText det_title, det_desc, det_owner, det_location;
    Spinner det_status;
    int status;
    String title, description, owner, location, img = "", thumb, codeContent = "";
    Button btn_create, btn_delete, btn_add_barcode;
    FloatingActionButton fabAddImg;
    TextView textViewBarcode;
    ImageView det_img;
    Bitmap smallPicture, bigPicture;

    private FirebaseDatabase database;
    private DatabaseReference itemReference;
    private Uri UriImagePath;

    /* static Variables */
    static final int REQUEST_IMAGE_CAPTURE = 1;


    /* Lifecycle Methods */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mat_add);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        //Get Firebase database instance
        database = FirebaseDatabase.getInstance();

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
        textViewBarcode = (TextView)findViewById(R.id.barcode_result);
        //getIntent().hasExtra("barcode");
        if(getIntent().hasExtra("barcode")){
            String scannercontext = getIntent().getStringExtra("barcode");
            textViewBarcode.setText(scannercontext);
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

                try {
                    // use standard intent to capture an image
                    Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File direct = new File(Environment.getExternalStorageDirectory() + "/MatAppImage");
                    if (!direct.exists()) {
                        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + "/MatAppImage");
                        wallpaperDirectory.mkdirs();
                    }
                    UriImagePath = Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/MatAppImage", "temp.png"));
                    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, UriImagePath);
                    startActivityForResult(captureIntent, 1);
                } catch (ActivityNotFoundException anfe) {
                    //Do Something...
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
                textViewBarcode.setText(codeContent);
            } else {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }else if ((requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)){
            Bitmap FullSizeImage = BitmapFactory.decodeFile(UriImagePath.getPath());
            int ImageWidth = FullSizeImage.getHeight();
            int ImageHeight = FullSizeImage.getWidth();

            //Scale for BigPicture
            //int maxWidth = Math.round(Width/4);
            //int maxHeight = Math.round(Height/4);
            int maxWidth = 500;
            int maxHeight = 500;

            int newImageWidth = 0;
            int newImageHeight = 0;

            if (ImageWidth > ImageHeight) {
                // landscape
                float ratio = (float) ImageHeight / ImageWidth;
                newImageWidth = maxWidth;
                newImageHeight = (int)(maxWidth * ratio);
            } else if (ImageHeight > ImageWidth) {
                // portrait
                float ratio = (float) ImageWidth / ImageHeight;
                newImageHeight = maxHeight;
                newImageWidth = (int)(maxHeight * ratio);
            } else {
                // square
                newImageHeight = maxHeight;
                newImageWidth = maxWidth;
            }
            bigPicture = Bitmap.createScaledBitmap(FullSizeImage,newImageWidth,newImageHeight,false);
            //Convert to Base 64
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bigPicture.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            String imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
            img = imageString;
            det_img.setImageBitmap(bigPicture);

            //Scale for small Picture;
            //int length = Math.round(64*getResources().getDisplayMetrics().density);
            int length = 25;
            smallPicture = Bitmap.createScaledBitmap(bigPicture,length,length,false);
            //Convert to Base64
            smallPicture.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
            byteArray = byteArrayOutputStream .toByteArray();
            imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
            thumb = imageString;
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
        newMat.setThumb(thumb);
        newMat.setBarcode(codeContent);

        //Save newMat into DB
        String itemKey = itemReference.push().getKey();
        itemReference.child(itemKey).setValue(newMat);
        Log.i("MatAddActivity", "new item created with itemKey=" + itemKey);

        // load Mat Detail activity
        Intent intent = new Intent(this, MatDetailActivity.class);
        intent.putExtra("ITEM_KEY", itemKey);

        // start Material Detail Activity
        startActivity(intent);
        finish();
    }
}
