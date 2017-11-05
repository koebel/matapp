package com.matapp.matapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.matapp.matapp.fragments.HomeFragment;
import com.matapp.matapp.fragments.LogoutFragment;
import com.matapp.matapp.fragments.MatListFragment;
import com.matapp.matapp.fragments.ScannerFragment;
import com.matapp.matapp.fragments.SettingsFragment;
import com.matapp.matapp.fragments.UsersFragment;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import static android.app.PendingIntent.getActivity;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.matapp.matapp.MESSAGE";
    public static final String EXTRA_DUMMY = "com.matapp.matapp.DUMMY";

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    private String codeFormat,codeContent;
    private TextView formatTxt, contentTxt;

    // Make sure to be using android.support.v7.app.ActionBarDrawerToggle version.
    // The android.support.v4.app.ActionBarDrawerToggle has been deprecated.
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_home:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.nav_scanner:
                fragmentClass = ScannerFragment.class;
                //onScannerAction(menuItem);
                break;
            case R.id.nav_matlist:
                fragmentClass = MatListFragment.class;
                break;
            case R.id.nav_users:
                fragmentClass = UsersFragment.class;
                break;
            case R.id.nav_settings:
                fragmentClass = SettingsFragment.class;
                break;
            case R.id.nav_logout:
                fragmentClass = LogoutFragment.class;
                break;
            default:
                fragmentClass = ScannerFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        //return super.onOptionsItemSelected(item);

        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.miScanner:
                // do something
                Toast.makeText(MainActivity.this, "My toast 2",
                        Toast.LENGTH_LONG).show();
               // onScannerAction();
                return true;
            case R.id.miMatList:
                // Do something
                // showProfileView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

        // The action bar home/up action should open or close the drawer.
        /*
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        */
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE 1: Make sure to override the method with only a single `Bundle` argument
    // Note 2: Make sure you implement the correct `onPostCreate(Bundle savedInstanceState)` method.
    // There are 2 signatures and only `onPostCreate(Bundle state)` shows the hamburger icon.
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    /** Called when the user clicks on the button */
    public void clickButton(View view) {
        Intent intent = new Intent(this, MatListDetail.class);
        String message = "Hello world";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }


    /* Implement Functions for Click on MenuItems */
    public void onScannerAction(MenuItem mi) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt(this.getString(R.string.scan_bar_code));
        integrator.setCaptureActivity(ScannerFragment.class);
        integrator.setOrientationLocked(false);
        //integrator.setResultDisplayDuration(0);
        //integrator.setWide();  // Wide scanning rectangle, may work better for 1D barcodes
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBarcodeImageEnabled(true);
        // set turn the camera flash on by default
        // integrator.addExtra(appConstants.CAMERA_FLASH_ON,true);
        integrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        /*Toast.makeText(MainActivity.this, "scan done",
                Toast.LENGTH_LONG).show();*/
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            if(scanningResult.getContents() != null){
                //we have a result
                codeContent = scanningResult.getContents();
                codeFormat = scanningResult.getFormatName();

                Toast.makeText(this, "Scan: " + codeContent, Toast.LENGTH_LONG).show();
                // display it on screen
                //formatTxt.setText("FORMAT: " + codeFormat);
                //contentTxt.setText("CONTENT: " + codeContent);

            }else{
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onMatListAction(MenuItem mi) {
        // handle click here
    }

}
