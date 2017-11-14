package com.matapp.matapp;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    EditText detTitle;
    EditText detDesc;
    EditText detOwner;
    EditText detLocation;
    EditText detLoanName;
    EditText detLoanContact;
    EditText detLoanUntil;
    EditText detLoanNote;
    Spinner detStatus;
    TextView detLoan;
    TextView detLoanNameLabel;
    TextView detLoanContactLabel;
    TextView detLoanUntilLabel;
    TextView detLoanNoteLabel;
    int status;

    String title;
    String description;
    String owner;
    String location;
    String img = "";
    String thumb = "";
    String codeContent = "";

    Button btnCreate;
    Button btnDelete;
    Button btnAddBarcode;

    FloatingActionButton fabAddImg;
    TextView textViewBarcode;
    ImageView detImg;
    Bitmap smallPicture;
    Bitmap bigPicture;

    private DatabaseReference itemReference;
    private Uri uriImagePath;

    /* static Variables */
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String LOG_MAT_ADD_ACTIVITY = "MatAddActivity";


    /* Lifecycle Methods */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mat_add);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        //Get Firebase database instance
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Get reference to material
        itemReference = database.getReference("material/" + MatAppSession.getInstance().getListKey() + "/item");
        Log.i(LOG_MAT_ADD_ACTIVITY, "Reference: " + "material/" + MatAppSession.getInstance().getListKey() + "/item");

        // binding of UI elements
        detTitle = (EditText) findViewById(R.id.det_title_add);
        detDesc = (EditText) findViewById(R.id.det_desc_add);
        detOwner = (EditText) findViewById(R.id.det_owner_add);
        detLocation = (EditText) findViewById(R.id.det_location_add);
        detStatus = (Spinner) findViewById(R.id.det_status_add);

        detLoan = (TextView) findViewById(R.id.det_loan);
        detLoanNameLabel = (TextView) findViewById(R.id.det_loan_name_label);
        detLoanContactLabel = (TextView) findViewById(R.id.det_loan_contact_label);
        detLoanUntilLabel = (TextView) findViewById(R.id.det_loan_until_label);
        detLoanNoteLabel = (TextView) findViewById(R.id.det_loan_note_label);
        detLoanName = (EditText) findViewById(R.id.det_loan_name_edit);
        detLoanContact = (EditText) findViewById(R.id.det_loan_contact_edit);
        detLoanUntil = (EditText) findViewById(R.id.det_loan_until_edit);
        detLoanNote = (EditText) findViewById(R.id.det_loan_note_edit);

        textViewBarcode = (TextView)findViewById(R.id.barcode_result);
        if(getIntent().hasExtra("barcode")){
            String scannercontext = getIntent().getStringExtra("barcode");
            textViewBarcode.setText(scannercontext);
        }

        detImg = (ImageView) findViewById(R.id.img_mat_add);

        btnCreate = (Button) findViewById(R.id.btn_create);
        btnDelete = (Button) findViewById(R.id.btn_delete_material);

        btnAddBarcode = (Button) findViewById(R.id.btn_add_barcode);
        btnAddBarcode.setOnClickListener(new View.OnClickListener() {
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
                    uriImagePath = Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/MatAppImage", "temp.png"));
                    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriImagePath);
                    startActivityForResult(captureIntent, 1);
                } catch (ActivityNotFoundException anfe) {
                    //Do Something...
                }
            }
        });

        //Hide Loan section
        detLoan.setVisibility(View.GONE);
        detLoanNameLabel.setVisibility(View.GONE);
        detLoanContactLabel.setVisibility(View.GONE);
        detLoanUntilLabel.setVisibility(View.GONE);
        detLoanNoteLabel.setVisibility(View.GONE);
        detLoanName.setVisibility(View.GONE);
        detLoanContact.setVisibility(View.GONE);
        detLoanUntil.setVisibility(View.GONE);
        detLoanNote.setVisibility(View.GONE);

        detStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (detStatus.getSelectedItemPosition() == Material.STATUS_LENT) {
                    detLoan.setVisibility(View.VISIBLE);
                    detLoanNameLabel.setVisibility(View.VISIBLE);
                    detLoanContactLabel.setVisibility(View.VISIBLE);
                    detLoanUntilLabel.setVisibility(View.VISIBLE);
                    detLoanNoteLabel.setVisibility(View.VISIBLE);
                    detLoanName.setVisibility(View.VISIBLE);
                    detLoanContact.setVisibility(View.VISIBLE);
                    detLoanUntil.setVisibility(View.VISIBLE);
                    detLoanNote.setVisibility(View.VISIBLE);
                } else {
                    detLoan.setVisibility(View.GONE);
                    detLoanNameLabel.setVisibility(View.GONE);
                    detLoanContactLabel.setVisibility(View.GONE);
                    detLoanUntilLabel.setVisibility(View.GONE);
                    detLoanNoteLabel.setVisibility(View.GONE);
                    detLoanName.setVisibility(View.GONE);
                    detLoanContact.setVisibility(View.GONE);
                    detLoanUntil.setVisibility(View.GONE);
                    detLoanNote.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Always selected one
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult != null) {
            if (scanningResult.getContents() != null) {
                //we have a result
                codeContent = scanningResult.getContents();
                textViewBarcode.setText(codeContent);
            } else {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }else if ((requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)){
            Bitmap fullSizeImage = BitmapFactory.decodeFile(uriImagePath.getPath());
            int imageWidth = fullSizeImage.getHeight();
            int imageHeight = fullSizeImage.getWidth();

            //Scale for BigPicture
            int maxWidth = 500;
            int maxHeight = 500;

            int newImageWidth;
            int newImageHeight;

            if (imageWidth > imageHeight) {
                // landscape
                float ratio = (float) imageHeight / imageWidth;
                newImageWidth = maxWidth;
                newImageHeight = (int)(maxWidth * ratio);
            } else if (imageHeight > imageWidth) {
                // portrait
                float ratio = (float) imageWidth / imageHeight;
                newImageHeight = maxHeight;
                newImageWidth = (int)(maxHeight * ratio);
            } else {
                // square
                newImageHeight = maxHeight;
                newImageWidth = maxWidth;
            }
            bigPicture = Bitmap.createScaledBitmap(fullSizeImage,newImageWidth,newImageHeight,false);
            //Convert to Base 64
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bigPicture.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            String imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
            img = imageString;
            detImg.setImageBitmap(bigPicture);

            //Scale for small picture
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
    public void addBarcode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt(this.getString(R.string.scan_bar_code));
        integrator.setCaptureActivity(ScannerActivity.class);
        integrator.setOrientationLocked(false);
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    // create new Material
    public void onCreateItem (View view){
        // get Content from Input fields
        title = detTitle.getText().toString();
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(getApplicationContext(), R.string.det_title_error, Toast.LENGTH_SHORT).show();
            return;
        }
        description = detDesc.getText().toString();
        owner = detOwner.getText().toString();
        location = detLocation.getText().toString();
        status = detStatus.getSelectedItemPosition();

        // create new Material Object
        Material newMat = new Material(title, description, owner, location, status);
        newMat.setImg(img);
        newMat.setThumb(thumb);
        newMat.setBarcode(codeContent);
        if (status == Material.STATUS_LENT) {
            newMat.setLoanName(detLoanName.getText().toString());
            newMat.setLoanContact(detLoanContact.getText().toString());
            newMat.setLoanUntil(detLoanUntil.getText().toString());
            newMat.setLoanNote(detLoanNote.getText().toString());
        }

        //Save newMat into DB
        String itemKey = itemReference.push().getKey();
        itemReference.child(itemKey).setValue(newMat);
        Log.i(LOG_MAT_ADD_ACTIVITY, "new item created with itemKey=" + itemKey);

        // load Mat Detail activity
        Intent intent = new Intent(this, MatDetailActivity.class);
        intent.putExtra("ITEM_KEY", itemKey);

        // start Material Detail Activity
        startActivity(intent);
        finish();
    }
}
