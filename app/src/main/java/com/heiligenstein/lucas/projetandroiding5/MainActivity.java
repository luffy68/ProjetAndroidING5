package com.heiligenstein.lucas.projetandroiding5;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.heiligenstein.lucas.projetandroiding5.Activity.LifiActivity;
import com.heiligenstein.lucas.projetandroiding5.Activity.MapsActivity;


import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textviewLatLong;

    private double longitude;
    private double latitude;

    // Permet de récupérer les valeurs dans le fichier res/values/strings.xml
    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Cette variable permet d'intancier le FireBase Analytic
        FirebaseAnalytics.getInstance(this);

        // Recuperation D'un token pour les messages FireBase
        recuperationTokenFirebase();

        // Les authorisations
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.SEND_SMS,
                        Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION},
                1);
    }


    @Override
    protected void onStart() {
        super.onStart();

        res = this.getResources();
        // Mettre une bar en haut avec titre
        Toolbar toolbar = findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        ((TextView) findViewById(R.id.id_toolbar_titre)).setText(res.getString(R.string.nomProjet));

        // Action quand on click sur le floating button Lifi
        FloatingActionButton myFabLifi = findViewById(R.id.fab);
        myFabLifi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, LifiActivity.class);
                startActivity(intent);
                onPause();
            }
        });

        // Action quand on click sur le floating button Map
        FloatingActionButton myFabMap = findViewById(R.id.fabMap);
        myFabMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
                onPause();
            }
        });


        textviewLatLong = findViewById(R.id.textView2);
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        longitude = location.getLongitude();
        latitude = location.getLatitude();

        textviewLatLong.setText(latitude + " / "+ longitude);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // Si la permission de la localisation est activé, alors on peut l'afficher les coordonnées
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, res.getString(R.string.permission_localisation_authorisee), Toast.LENGTH_LONG).show();
                    textviewLatLong.setText(latitude + " / "+ longitude);
                } else {
                    Toast.makeText(this, res.getString(R.string.permission_localisation_non_authorisee), Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private void recuperationTokenFirebase(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        // Recuperation du token
                        task.getResult().getToken();
                    }
                });
    }

    // Partager position avec SMS
    public void share(View v) {

        EditText phone = findViewById(R.id.etPhone);
        String num = phone.getText().toString();

        if (num.length() <= 0 || ! android.text.TextUtils.isDigitsOnly(num)){
            Toast.makeText(MainActivity.this, res.getString(R.string.incorrect_phone_number), Toast.LENGTH_LONG).show();
        }
        else {
            String message = latitude + ";" + longitude;
            SmsManager.getDefault().sendTextMessage(num, null, message, null, null);
            Toast.makeText(MainActivity.this, res.getString(R.string.messageEnvoye), Toast.LENGTH_LONG).show();
        }

    }
}
