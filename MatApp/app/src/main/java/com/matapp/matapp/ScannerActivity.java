package com.matapp.matapp;

import android.view.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

/**
 * Created by kathrinkoebel on 25.10.17.
 */

public class  ScannerActivity extends Activity {
    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    private View turnFlashOn;
    private View turnFlashOff;
    private boolean cameraFlashOn = false;
    public static final String CAMERA_FLASH_ON = "CAMERA_FLASH_ON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        barcodeScannerView = initializeContent();
        TorchEventListener torchEventListener = new TorchEventListener(this);
        barcodeScannerView.setTorchListener(torchEventListener);

        turnFlashOn = findViewById(R.id.switch_flashlight_on);
        turnFlashOff = findViewById(R.id.switch_flashlight_off);

        // turn the flash on if set via intent
        Intent scanIntent = getIntent();
        if(scanIntent.hasExtra(this.CAMERA_FLASH_ON)){
            if(scanIntent.getBooleanExtra(this.CAMERA_FLASH_ON,false)){
                barcodeScannerView.setTorchOn();
                updateView();
            }
        }

        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();
    }

    /**
     * Override to use a different layout.
     *
     * @return the DecoratedBarcodeView
     */
    protected DecoratedBarcodeView initializeContent() {
        setContentView(R.layout.activity_scan);
        return (DecoratedBarcodeView)findViewById(com.google.zxing.client.android.R.id.zxing_barcode_scanner);
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    public void toggleFlash(View view){
        if(cameraFlashOn){
            barcodeScannerView.setTorchOff();
        }else{
            barcodeScannerView.setTorchOn();
        }
    }

    public void updateView(){
        if(cameraFlashOn){
            turnFlashOn.setVisibility(View.GONE);
            turnFlashOff.setVisibility(View.VISIBLE);
        }else{
            turnFlashOn.setVisibility(View.VISIBLE);
            turnFlashOff.setVisibility(View.GONE);
        }
    }

    class TorchEventListener implements DecoratedBarcodeView.TorchListener{
        private ScannerActivity activity;

        TorchEventListener(ScannerActivity activity){
            this.activity = activity;
        }

        @Override
        public void onTorchOn() {
            this.activity.cameraFlashOn = true;
            this.activity.updateView();
        }

        @Override
        public void onTorchOff() {
            this.activity.cameraFlashOn = false;
            this.activity.updateView();
        }
    }
}
