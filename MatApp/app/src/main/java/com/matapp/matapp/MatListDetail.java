package com.matapp.matapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MatListDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_matdetail);


        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String title = intent.getStringExtra(MainActivity.EXTRA_DUMMY);

        // Capture the layout's TextView and set the string as its text
        TextView textView = (TextView) findViewById(R.id.det_hello);
        textView.setText(message);

        TextView textView2 = (TextView) findViewById(R.id.det_title);
        textView2.setText(title);
    }


    public void onAddPhoto(View view) {
        Context context = getApplicationContext();
        CharSequence text = "add Photo";
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
}
