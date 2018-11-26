package com.heiligenstein.lucas.projetandroiding5.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.heiligenstein.lucas.projetandroiding5.R;
import com.lize.oledcomm.camera_lifisdk_android.ILiFiPosition;
import com.lize.oledcomm.camera_lifisdk_android.LiFiSdkManager;
import com.lize.oledcomm.camera_lifisdk_android.V1.LiFiLocationManagerV1;

public class LifiActivity extends AppCompatActivity {


    private LiFiSdkManager liFiSdkManager;
    private TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifi_main);

        // Mettre une bar en haut avec titre
        Toolbar toolbar = findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        ((TextView) findViewById(R.id.id_toolbar_titre)).setText("LIFI");

        /*
        //Pour la fleche de gauche
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_fleche_retour);
        actionBar.setDisplayHomeAsUpEnabled(true);
        */

        t = findViewById(R.id.text);

        ActivityCompat.requestPermissions(LifiActivity.this,
                new String[]{Manifest.permission.CAMERA},
                1);
    }

    protected void onStart() {
        super.onStart();

    }


    protected void onStop() {
        super.onStop();

        if (liFiSdkManager != null && liFiSdkManager.isStarted()) {
            liFiSdkManager.stop();
            liFiSdkManager.release();
            liFiSdkManager = null;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();

                    liFiSdkManager = new LiFiSdkManager(this, LiFiSdkManager.CAMERA_LIB_VERSION_0_1,
                            "token", "user", new ILiFiPosition() {
                        @Override
                        public void onLiFiPositionUpdate(String lamp) {

         /* Add your actions here.
         *  lamp will contain the tag (eg. 10101010) if decoding was successful.
         *  If there was an error, lamp could contain the text "No lamp detected" or "Weak signal".
         */
                            t.setText(lamp);

                            Log.e("EEEE","AAAAAA");
                        }
                    });

                    liFiSdkManager.setLocationRequestMode(LiFiSdkManager.LOCATION_REQUEST_OFFLINE_MODE);
                    liFiSdkManager.init(R.id.id_lifi_content, LiFiLocationManagerV1.FRONT_CAMERA);
                    liFiSdkManager.start();

                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }



}
