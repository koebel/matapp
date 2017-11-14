package com.matapp.matapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 *
 * This activity is used to build the basic layout including
 * navigation drawer & tolbar of this app.
 *
 * Created by kathrinkoebel on 25.10.17.
 *
 **/

public class MainActivity extends AppCompatActivity {

    /* Variables */
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    String codeFormat;
    String codeContent;

    private FirebaseAuth auth;

    private FirebaseDatabase database;
    private DatabaseReference itemReference;

    // Make sure to be using android.support.v7.app.ActionBarDrawerToggle version.
    // The android.support.v4.app.ActionBarDrawerToggle has been deprecated.
    private ActionBarDrawerToggle drawerToggle;

    /* static Variables */
    public static final String LOG_MAIN_ACTIVITY = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        // Tie DrawerLayout events to the ActionBarToggle
        drawerLayout.addDrawerListener(drawerToggle);

        NavigationView navigationDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(navigationDrawer);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        //Check if logged in
        // TODO: maybe in onStart or onResume?
        if (auth.getCurrentUser() == null || MatAppSession.getInstance().getListKey() == null) {
            //Call login screen
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Login: " + auth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        Log.i(LOG_MAIN_ACTIVITY, "listKey: " + MatAppSession.getInstance().getListKey());
        if(MatAppSession.getInstance().getListKey() != null) {
            // load MatListFragment on Start Screen
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            MatListFragment fragment = MatListFragment.newInstance(0, "");
            ft.replace(R.id.flContent, fragment);
            ft.commit();

            //Set Title to Listname
            String title = MatAppSession.getInstance().getListName() + " " + getResources().getText(R.string.tabbar_matlist);
            setTitle(title);
        }
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,  R.string.drawer_close);
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
        if(menuItem.getItemId() == R.id.nav_scanner) {
            //Start Scanner
            onScannerAction(menuItem);
        } else if(menuItem.getItemId() == R.id.nav_logout){
            // Start activity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        // Close the navigation drawer
        drawerLayout.closeDrawers();
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
        int x=3;
        int y = x;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /* Implement Functions for Click on MenuItems in Toolbar */
    public void onScannerAction(MenuItem mi) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt(this.getString(R.string.scan_bar_code));
        integrator.setCaptureActivity(ScannerActivity.class);
        integrator.setOrientationLocked(false);
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            if(scanningResult.getContents() != null){
                //we have a result
                codeContent = scanningResult.getContents();
                codeFormat = scanningResult.getFormatName();
                Toast.makeText(this, "Scan: " + codeContent, Toast.LENGTH_LONG).show();

                //TODO find codeContent in database or start add otherwise

                //Get Firebase database instance
                database = FirebaseDatabase.getInstance();
                //Get reference to material
                itemReference = database.getReference("material/" + MatAppSession.getInstance().getListKey() + "/item");
                //Select child where barcode=codeContent
                Query query = itemReference.orderByChild("barcode").equalTo(codeContent);
                //Add Listener for one read
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()) {
                            //If list is found get itemKey
                            String itemKey = "";
                            for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                                itemKey = childDataSnapshot.getKey();
                                break;
                            }
                            //Intent for Detail
                            Intent newMatIntent = new Intent(MainActivity.this, MatDetailActivity.class);
                            newMatIntent.putExtra("ITEM_KEY", itemKey);
                            startActivity(newMatIntent);
                        } else {
                            if(MatAppSession.getInstance().isListWriteable()) {
                                //Intent for Add
                                Intent newMatIntent = new Intent(MainActivity.this, MatAddActivity.class);
                                newMatIntent.putExtra("barcode", codeContent);
                                startActivity(newMatIntent);
                            } else {
                                Toast.makeText(MainActivity.this, getString(R.string.item_notFound), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }else{
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /*
    this Function is currently not in use because in response to Usability test feedback
    we have decided to show only the barcode scannere in the toolbar as long as
    additional features such as usermanagement are implemented this is not needed

    public void onMatListAction(MenuItem mi) {

        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass = MatListFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();

        // Set action bar title
        setTitle(mi.getTitle());

        // Set Title to Listname
        // String title = MatAppSession.getInstance().getListName() + " " + getResources().getText(R.string.tabbar_matlist);
        // setTitle(title);
    }
    */
}



