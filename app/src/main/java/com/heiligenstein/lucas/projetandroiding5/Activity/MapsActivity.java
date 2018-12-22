package com.heiligenstein.lucas.projetandroiding5.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.heiligenstein.lucas.projetandroiding5.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Dialog dialogAfficherNouvellePosition;
    private Dialog dialogEnvoyerNouvellePositionDepuisMap;

    private double latitude;
    private  double longitude;

   /* private TextView tLatitude;
    private TextView tLongitude; */
    private TextView friendLocation;

    private TextView tAfficherLatLongPourEnvoyer;
    private EditText tNumero;

    private String localisationToSend = "";

    // Permet de récupérer les valeurs dans le fichier res/values/strings.xml
    Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    protected void onStart() {
        super.onStart();

        res = this.getResources();
        dialogAfficherNouvellePosition = new Dialog(MapsActivity.this);
        dialogAfficherNouvellePosition.setContentView(R.layout.dialog_put_lat_long);
        /*tLatitude = dialogAfficherNouvellePosition.findViewById(R.id.editTextLatitude);
        tLongitude = dialogAfficherNouvellePosition.findViewById(R.id.editTextLongitude); */
        friendLocation = dialogAfficherNouvellePosition.findViewById(R.id.editTextLocation);

        dialogEnvoyerNouvellePositionDepuisMap = new Dialog(MapsActivity.this);
        dialogEnvoyerNouvellePositionDepuisMap.setContentView(R.layout.dialog_send_position_from_map);
        tAfficherLatLongPourEnvoyer = dialogEnvoyerNouvellePositionDepuisMap.findViewById(R.id.textViewLatLongPourEnvoyer);
        tNumero = dialogEnvoyerNouvellePositionDepuisMap.findViewById(R.id.editTextNumeroDialogBox);

        // Action quand on click sur le floating button Lifi
        FloatingActionButton afficherNouvellePositionSurMap = findViewById(R.id.fab1);
        afficherNouvellePositionSurMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialogAfficherNouvellePosition.show();
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
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
        mMap.setMyLocationEnabled(true);

        // Quand on clique sur la map, on a la latitude et longitude de la position pou envoyer un sms de la position
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latln) {
                // TODO Auto-generated method stub

                try {
                    Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(latln.latitude, latln.longitude, 1);
                    Address address = addresses.get(0);
                    tAfficherLatLongPourEnvoyer.setText(address.getCountryName()+ " : " +address.getLocality() + " \n "+latln.latitude + "," + latln.longitude);
                    localisationToSend = latln.latitude + ";" + latln.longitude;
                    dialogEnvoyerNouvellePositionDepuisMap.show();


                    latitude = latln.latitude;
                    longitude = latln.longitude;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    // Aficher nouvelle position sur
    public void butonShowOnMapPosition(View v){
        try {
            String friendLocationText = friendLocation.getText().toString();
            String[] fLTable = friendLocationText.split(";");

            if(fLTable.length < 2 ||
                    android.text.TextUtils.isDigitsOnly(fLTable[0]) || android.text.TextUtils.isDigitsOnly(fLTable[1])){
                Toast.makeText(MapsActivity.this, res.getString(R.string.mauvaisNombre), Toast.LENGTH_LONG).show();
            }
            else {
                LatLng me = new LatLng(Double.parseDouble(fLTable[0]), Double.parseDouble(fLTable[1]));
                mMap.addMarker(new MarkerOptions().position(me).title("Marker me"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(me));
            }
        }catch (NumberFormatException e){
            Toast.makeText(this, res.getString(R.string.mauvaisNombre), Toast.LENGTH_LONG).show();
        }
        dialogAfficherNouvellePosition.cancel();
    }

    public void butonEnvoyerSmsNouvellePosition(View v){

        String num = tNumero.getText().toString();

        if (num.length() <= 0 || ! android.text.TextUtils.isDigitsOnly(num)){
            Toast.makeText(MapsActivity.this, res.getString(R.string.incorrect_phone_number), Toast.LENGTH_LONG).show();
        }
        else {
            SmsManager.getDefault().sendTextMessage(num, null, localisationToSend, null, null);
            dialogEnvoyerNouvellePositionDepuisMap.cancel();
            Toast.makeText(this, res.getString(R.string.messageEnvoye), Toast.LENGTH_LONG).show();
        }

    }
}
