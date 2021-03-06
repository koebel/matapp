package com.matapp.matapp;

import android.os.Build;
import android.view.View;
import android.widget.Button;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;


/**
 *
 * local unit tests for Scanner Activity,
 * implemented with Robolectrics,
 * will be execute on the development machine (host)
 *
 **/

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class ScannerActivityTest {

    @Test
    public void scannerActivityShouldNotBeNull() {
        ScannerActivity activity = Robolectric.setupActivity(ScannerActivity.class);
        assertNotNull(activity);
    }

    @Test
    public void checkSwitchFlashLightOnButtonIsVisible() {
        ScannerActivity activity = Robolectric.setupActivity(ScannerActivity.class);
        Button flashLightOn = (Button) activity.findViewById(R.id.switch_flashlight_on);
        assertNotNull(flashLightOn);
        assertEquals(View.VISIBLE, flashLightOn.getVisibility());
        assertEquals(activity.getString(R.string.turn_on_flashlight), flashLightOn.getText());
    }

    @Test
    public void checkSwitchFlashLightOffButtonIsNotVisible() {
        ScannerActivity activity = Robolectric.setupActivity(ScannerActivity.class);
        Button flashLightOff = (Button) activity.findViewById(R.id.switch_flashlight_off);
        assertNotNull(flashLightOff);
        assertEquals(View.GONE, flashLightOff.getVisibility());
    }

    @Test
    public void checkSwitchFlashLightOnClick() {
        ScannerActivity activity = Robolectric.setupActivity(ScannerActivity.class);
        Button flashLightOn = (Button) activity.findViewById(R.id.switch_flashlight_on);
        Button flashLightOff = (Button) activity.findViewById(R.id.switch_flashlight_off);

        flashLightOn.performClick();

        assertEquals(View.GONE, flashLightOn.getVisibility());
        assertEquals(View.VISIBLE, flashLightOff.getVisibility());
        assertEquals(activity.getString(R.string.turn_off_flashlight), flashLightOff.getText());
    }
}











