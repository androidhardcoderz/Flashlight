package com.flashlight.logify;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity  {


    /**
     * Initialize any classes needed
     * views are not created until onCreateView is run
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean hasFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        /*
            Check if the user's device contains the hardware flashlight
            permissions are stated in manifest but
            bugs containing in play store allow devices to run through without hardware specified
         */

        if (!hasFlash) {
            // no flashlight equipped
            // alert user shutdown application
            showAlertUserNoFlashlight();

        } else {
            setNewFragment(); //set to be night mode first!
        }
    }

    /**
     * displays a AlertDialogBox to user when no flashlight is attached to device
     */
    private void showAlertUserNoFlashlight(){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle("Flashlight Not Found");
        alertDialogBuilder.setCancelable(false);

        // set dialog message
        alertDialogBuilder
                .setMessage(
                        "You Do Not Have A Flashlight... We'll Brighten The Screen For You")
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                //show fragment white background with brightness turned up
                                setWhiteFragment();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    /**
     * loads white fragment a simple fragment whcih contains a white background
     * to user when flashlight hardware is not present on device
     */
    private void setWhiteFragment(){

        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
        trans.replace(R.id.container,new WhiteFragment()).commit();
    }

    private void setNewFragment(){

        MainActivityFragment fragment = new MainActivityFragment();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
        trans.replace(R.id.container,fragment).commit();
    }


}
