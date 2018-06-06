package com.s.samsungitschool.recom00.maps;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.s.samsungitschool.recom00.R;
import com.s.samsungitschool.recom00.fragments.NewAppFragmentActivity;

public class MapsActivity extends FragmentActivity implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;
    private boolean mLocationPermissionGranted;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private final String NEW_POINT_LAT = "NEW_POINT_LAT";
    private final String NEW_POINT_LNG = "NEW_POINT_LNG";
    private final String START_ACTIVITY = "START_ACTIVITY";
    private final String GET_ADDRESS_FROM_MAP = "GET_ADDRESS_FROM_MAP";

    AlertDialog.Builder ad;
    Context context;

    private double newPointLat;
    private double newPointLng;

    Marker newPointMarker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        context = MapsActivity.this;



    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(56.012029, 92.871278), 12));



        // Add a marker in Sydney and move the camera
        /*
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        Log.i("onMapReady ", "onMapReady");

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(final LatLng latLng) {
                Log.i("onMapClick ", "onMapClick");
                LatLng latLngMarker = latLng;

                newPointMarker = mMap.addMarker(new MarkerOptions().position(latLngMarker).title("Новая точка"));
                mMap.addMarker(new MarkerOptions().position(latLngMarker).title("Новая точка"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngMarker));


                String title = "Новая точка";
                String message = "Добавить новую проблемную точку?";
                String button1String = "Да";
                String button2String = "Нет";

                ad = new AlertDialog.Builder(context);
                ad.setTitle(title);  // заголовок
                ad.setMessage(message); // сообщение
                ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        Toast.makeText(context, "Перенаправление",
                                Toast.LENGTH_LONG).show();


                        newPointLat = latLng.latitude;
                        newPointLng = latLng.longitude;

                        sharedPreferences = getSharedPreferences("SP", MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        editor.putFloat(NEW_POINT_LAT, (float) newPointLat);
                        editor.putFloat(NEW_POINT_LNG, (float) newPointLng);
                        editor.putBoolean(GET_ADDRESS_FROM_MAP, true);
                        editor.apply();

                        finish();
                    }
                });
                ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        Toast.makeText(context, "Отмена", Toast.LENGTH_LONG)
                                .show();
                        newPointMarker.remove();



                    }
                });
                ad.setCancelable(true);
                ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(context, "Вы ничего не выбрали",
                                Toast.LENGTH_LONG).show();
                    }
                });
                ad.show();


            }
        });

    }

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
        // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] ==

                        PackageManager.PERMISSION_GRANTED) {

                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        }
    }*/


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }
}
