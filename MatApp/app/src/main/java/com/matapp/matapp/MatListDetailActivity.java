package com.matapp.matapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.matapp.matapp.other.Material;

public class MatListDetailActivity extends AppCompatActivity {

    TextView det_title, det_desc, det_owner, det_location, det_gps, det_status, det_barcode, det_img,
            det_loan, det_loan_name, det_loan_contact, det_loan_until, det_loan_note, det_position;
    Button btn_loan, btn_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_matdetail);

        det_title = (TextView) findViewById(R.id.det_title);
        det_desc = (TextView) findViewById(R.id.det_desc);
        det_owner = (TextView) findViewById(R.id.det_owner);
        det_location = (TextView) findViewById(R.id.det_location);
        det_status = (TextView) findViewById(R.id.det_status);
        det_position = (TextView) findViewById(R.id.det_position);
        det_loan = (TextView) findViewById(R.id.det_loan);
        det_loan_name = (TextView) findViewById(R.id.det_loan_name);
        det_loan_contact = (TextView) findViewById(R.id.det_loan_contact);
        det_loan_until = (TextView) findViewById(R.id.det_loan_until);
        det_loan_note = (TextView) findViewById(R.id.det_loan_note);

        btn_loan = (Button) findViewById(R.id.btn_loan);
        btn_return = (Button) findViewById(R.id.btn_return);

        // Get the Intent that started this activity and extract the string
        Intent intent = this.getIntent();

        // receive Extras
        //int position = intent.getExtras().getInt("POSITION_KEY");

        String title = intent.getExtras().getString("TITLE_KEY");
        String description = intent.getExtras().getString("DESCRIPTION_KEY");
        String owner = intent.getExtras().getString("OWNER_KEY");
        String location = intent.getExtras().getString("LOCATION_KEY");
        int status = intent.getExtras().getInt("STATUS_KEY");
        String gps = intent.getExtras().getString("GPS_KEY");
        String barcode = intent.getExtras().getString("BARCODE_KEY");
        String img = intent.getExtras().getString("IMAGE_KEY");
        String loanName = intent.getExtras().getString("LOAN_NAME_KEY");
        String loanContact = intent.getExtras().getString("LOAN_CONTACT_KEY");
        String loanUntil = intent.getExtras().getString("LOAN_UNTIL_KEY");
        String loanNote = intent.getExtras().getString("LOAN_NOTE_KEY");

        // Bind
        det_title.setText(title);
        det_desc.setText(description);
        det_owner.setText(owner);
        det_location.setText(location);
        det_loan.setText(R.string.det_loan_sub);
        det_loan_name.setText(loanName);
        det_loan_contact.setText(loanContact);
        det_loan_until.setText(loanUntil);
        det_loan_note.setText(loanNote);

        //det_position.setText(position);
        det_position.setVisibility(View.GONE);




        // status dependent bindings & visibility settings
        // det_status: transform status value into text variable
        // buttons: show/hide depending on status
        // loan: show/hide section

        // 0 = material verfügbar
        if (status == 0) {
            det_status.setText(R.string.det_status_available);
            btn_loan.setVisibility(View.VISIBLE);
            btn_return.setVisibility(View.GONE);

            det_loan.setVisibility(View.GONE);
            det_loan_name.setVisibility(View.GONE);
            det_loan_contact.setVisibility(View.GONE);
            det_loan_until.setVisibility(View.GONE);
            det_loan_note.setVisibility(View.GONE);

        }

        // 1 = material ausgeliehen
        else if (status == 1) {
            det_status.setText(R.string.det_status_lent);
            btn_loan.setVisibility(View.GONE);
            btn_return.setVisibility(View.VISIBLE);

            // display only if not empty
            if(loanName.length() == 0) {
                det_loan_name.setVisibility(View.GONE);
            }
            if(loanContact.length() == 0) {
                det_loan_contact.setVisibility(View.GONE);
            }
            if(loanUntil.length() == 0) {
                det_loan_contact.setVisibility(View.GONE);
            }
            if(loanNote.length() == 0) {
                det_loan_note.setVisibility(View.GONE);
            }
        }

        // 2 = material nicht verfügbar (default)
        else {
            det_status.setText(R.string.det_status_unavailable);
            btn_loan.setVisibility(View.GONE);
            btn_return.setVisibility(View.GONE);
            //btn_loan.setVisibility(View.INVISIBLE);
            //btn_return.setVisibility(View.INVISIBLE);

            det_loan.setVisibility(View.GONE);
            det_loan_name.setVisibility(View.GONE);
            det_loan_contact.setVisibility(View.GONE);
            det_loan_until.setVisibility(View.GONE);
            if(loanNote.length() == 0) {
                det_loan_note.setVisibility(View.GONE);
            }
        }

    }


    public void onEditItem(View view) {
        Context context = getApplicationContext();
        CharSequence text = "edit this Material";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void onLendItem(View view) {
        Context context = getApplicationContext();
        CharSequence text = "Artikel ausleihen";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void onReturnItem(View view) {
        Context context = getApplicationContext();
        CharSequence text = "Artikel zurückgeben";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void onDeleteItemPopUp(View view, Intent intent) {
        // TODO Popup Delete
        // if yes, delete Item and return to overview
        Context context = getApplicationContext();
        CharSequence text = "Artikel löschen PopUp";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void onDeleteItem(View view) {
        // TODO  Delete
        // if yes, delete Item and return to overview
        Context context = getApplicationContext();
        CharSequence text = "Artikel löschen";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
