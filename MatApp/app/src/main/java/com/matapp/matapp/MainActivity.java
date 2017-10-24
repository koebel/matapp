package com.matapp.matapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.matapp.matapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        // setSupportActionBar(myToolbar);
    }

    /** Called when the user clicks on the button */
    public void clickButton(View view) {
        Intent intent = new Intent(this, ListDetail.class);
        String message = "Hello world";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

        Button btn = (Button) findViewById(R.id.btn_next);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });
    }
}
