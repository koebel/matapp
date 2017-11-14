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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.matapp.matapp.other.Material;

import java.io.ByteArrayOutputStream;
import java.io.File;

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
    EditText det_title;
    EditText det_desc;
    EditText det_owner;
    EditText det_location;
    EditText det_loan_name;
    EditText det_loan_contact;
    EditText det_loan_until;
    EditText det_loan_note;

    Spinner det_status;
    //int itemId, status;
    //String title, description, owner, location, gps, img, thumb, loanName, loanContact, loanUntil, loanNote, barcode;
    FloatingActionButton fabAddImg;
    Button btnAddBarcode;
    ImageView det_img;
    TextView det_barcode;
    TextView det_loan;
    TextView det_loan_name_label;
    TextView det_loan_contact_label;
    TextView det_loan_until_label;
    TextView det_loan_note_label;
    private Uri uriImagePath;

    Bitmap smallPicture;
    Bitmap bigPicture;

    String thumb;
    String img;
    String barcode;

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

        det_loan = (TextView) findViewById(R.id.det_loan);
        det_loan_name_label = (TextView) findViewById(R.id.det_loan_name_label);
        det_loan_contact_label = (TextView) findViewById(R.id.det_loan_contact_label);
        det_loan_until_label = (TextView) findViewById(R.id.det_loan_until_label);
        det_loan_note_label = (TextView) findViewById(R.id.det_loan_note_label);
        det_loan_name = (EditText) findViewById(R.id.det_loan_name_edit);
        det_loan_contact = (EditText) findViewById(R.id.det_loan_contact_edit);
        det_loan_until = (EditText) findViewById(R.id.det_loan_until_edit);
        det_loan_note = (EditText) findViewById(R.id.det_loan_note_edit);

        det_img = (ImageView) findViewById(R.id.img_mat_edit);
        det_barcode = (TextView)findViewById(R.id.barcode_result);

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
                }

                if (item.getOwner() != null && item.getOwner().trim().length() > 0) {
                    det_owner.setText(item.getOwner());
                }

                if (item.getLocation() != null && item.getLocation().trim().length() > 0) {
                    det_location.setText(item.getLocation());
                }
                if (item.getBarcode() != null && item.getBarcode().trim().length() > 0) {
                    det_barcode.setText(item.getBarcode());
                }
                if (item.getImg() != null && item.getImg().trim().length() > 0) {
                    det_img.setImageBitmap(stringToBitmap(item.getImg()));
                }

                det_status.setSelection(item.getStatus());

                if (item.getLoanName() != null && item.getLoanName().trim().length() > 0) {
                    det_loan_name.setText(item.getLoanName());
                }
                if (item.getLoanContact() != null && item.getLoanContact().trim().length() > 0) {
                    det_loan_contact.setText(item.getLoanContact());
                }
                if (item.getLoanUntil() != null && item.getLoanUntil().trim().length() > 0) {
                    det_loan_until.setText(item.getLoanUntil());
                }
                if (item.getLoanNote() != null && item.getLoanNote().trim().length() > 0) {
                    det_loan_note.setText(item.getLoanNote());
                }

                if (item.getStatus() != Material.STATUS_LENT) {
                    det_loan.setVisibility(View.GONE);
                    det_loan_name_label.setVisibility(View.GONE);
                    det_loan_contact_label.setVisibility(View.GONE);
                    det_loan_until_label.setVisibility(View.GONE);
                    det_loan_note_label.setVisibility(View.GONE);
                    det_loan_name.setVisibility(View.GONE);
                    det_loan_contact.setVisibility(View.GONE);
                    det_loan_until.setVisibility(View.GONE);
                    det_loan_note.setVisibility(View.GONE);
                }

                thumb = item.getThumb();
                img = item.getImg();
                barcode = item.getBarcode();
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

                try {
                    // use standard intent to capture an image
                    Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File direct = new File(Environment.getExternalStorageDirectory() + "/MatAppImage");
                    if (!direct.exists()) {
                        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + "/MatAppImage");
                        wallpaperDirectory.mkdirs();
                    }
                    uriImagePath = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/MatAppImage", "temp.png"));
                    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriImagePath);
                    startActivityForResult(captureIntent, 1);
                } catch (ActivityNotFoundException anfe) {
                    //Do Something...
                }
            }

        });

        btnAddBarcode = (Button) findViewById(R.id.btn_add_barcode);
        btnAddBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Barcode hinzufügen", Toast.LENGTH_SHORT).show();
                addBarcode();
            }
        });

        det_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (det_status.getSelectedItemPosition() == Material.STATUS_LENT) {
                    det_loan.setVisibility(View.VISIBLE);
                    det_loan_name_label.setVisibility(View.VISIBLE);
                    det_loan_contact_label.setVisibility(View.VISIBLE);
                    det_loan_until_label.setVisibility(View.VISIBLE);
                    det_loan_note_label.setVisibility(View.VISIBLE);
                    det_loan_name.setVisibility(View.VISIBLE);
                    det_loan_contact.setVisibility(View.VISIBLE);
                    det_loan_until.setVisibility(View.VISIBLE);
                    det_loan_note.setVisibility(View.VISIBLE);
                } else {
                    det_loan.setVisibility(View.GONE);
                    det_loan_name_label.setVisibility(View.GONE);
                    det_loan_contact_label.setVisibility(View.GONE);
                    det_loan_until_label.setVisibility(View.GONE);
                    det_loan_note_label.setVisibility(View.GONE);
                    det_loan_name.setVisibility(View.GONE);
                    det_loan_contact.setVisibility(View.GONE);
                    det_loan_until.setVisibility(View.GONE);
                    det_loan_note.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
        item.setThumb(thumb);
        item.setImg(img);
        item.setBarcode(barcode);

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

    // TODO save Image into DB!
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // super.onActivityResult(requestCode, resultCode, intent);

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult != null) {
            if(scanningResult.getContents() != null){
                //we have a result
                barcode = scanningResult.getContents();
                det_barcode.setText(barcode);
            }else{
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bitmap fullSizeImage = BitmapFactory.decodeFile(uriImagePath.getPath());
            int imageWidth = fullSizeImage.getHeight();
            int imageHeight = fullSizeImage.getWidth();

            //Scale for BigPicture
            //int maxWidth = Math.round(Width/4);
            //int maxHeight = Math.round(Height/4);
            int maxWidth = 500;
            int maxHeight = 500;

            int newImageWidth = 0;
            int newImageHeight = 0;

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
}
