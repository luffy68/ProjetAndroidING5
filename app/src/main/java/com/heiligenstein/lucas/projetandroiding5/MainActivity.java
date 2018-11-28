package com.heiligenstein.lucas.projetandroiding5;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.heiligenstein.lucas.projetandroiding5.Activity.LifiActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recuperation D'un token
        recuperationTokenFirebase();



        // Mettre une bar en haut avec titre
        Toolbar toolbar = findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        ((TextView) findViewById(R.id.id_toolbar_titre)).setText("Projet MICHEL - HEILIGENSTEIN");

        // Authorisation pour avoir la caméra

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.CAMERA},
                1);


        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                /*
// This registration token comes from the client FCM SDKs.
                String registrationToken = "YOUR_REGISTRATION_TOKEN";

// See documentation on defining a message payload.
                Message message = Message.builder()
                        .putData("score", "850")
                        .putData("time", "2:45")
                        .setToken(registrationToken)
                        .build();

// Send a message to the device corresponding to the provided
// registration token.
                String response = FirebaseMessaging.getInstance().send(message);
// Response is a message ID string.
                System.out.println("Successfully sent message: " + response);
*/


                Intent intent = new Intent(MainActivity.this, LifiActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
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
                        if (!task.isSuccessful()) {
                            Log.w("Erreur", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        Log.d("Tokeb", token);
                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_LONG).show();
                    }
                });


    }

}
