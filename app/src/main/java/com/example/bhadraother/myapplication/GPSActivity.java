package com.example.bhadraother.myapplication;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**

Assignment Notes: For this activity, we are embedding the LocationListener
in this Activity, instead of creating a separate GPS class.  The code is basically
the same, but is a bit easier to reference here.  You can find more info on
how to do a LocationListener in the Service example code we did in class:
https://github.com/marksherriff/SensorExample

*/

public class GPSActivity extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    LocationListener locationListener;
    boolean gpsEnabled;
    boolean netEnabled;
    private static final int TAKE_PHOTO_PERMISSION = 1;

    TextView latTextView;
    TextView lonTextView;
    EditText latEditText;
    EditText lonEditText;

    Double currentLat;
    Double currentLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        latTextView = (TextView)findViewById(R.id.latTextView);
        lonTextView = (TextView)findViewById(R.id.lonTextView);

        latEditText = (EditText)findViewById(R.id.latEditText);
        lonEditText = (EditText)findViewById(R.id.lonEditText);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }

    public void startGPS(View view) {

        // Here is the code to handle permissions - you should not need to edit this.
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION }, TAKE_PHOTO_PERMISSION);
        }

        // Add code here to register the listener with the Location Manager to receive location updates

        Criteria c = new Criteria();
        c.setAccuracy(Criteria.ACCURACY_FINE);

        final String PROVIDER = locationManager.getBestProvider(c, true);
        Location tempLocation = locationManager.getLastKnownLocation(PROVIDER);
        if (tempLocation != null) {
            currentLon = tempLocation.getLongitude();
            currentLat = tempLocation.getLatitude();
        }
        else {
            currentLon = -1.0;
            currentLat = -1.0;
        }

        lonEditText.setText(String.format("%.2f", currentLon));
        latEditText.setText(String.format("%.2f", currentLat));


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    currentLon = location.getLongitude();
                    currentLat = location.getLatitude();
                }
                else {
                    currentLon = -1.0;
                    currentLat = -1.0;
                }

                lonEditText.setText(String.format("%.2f", currentLon));
                latEditText.setText(String.format("%.2f", currentLat));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0.0F, this.locationListener);


    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            currentLon = location.getLongitude();
            currentLat = location.getLatitude();
        }
        else {
            currentLon = -1.0;
            currentLat = -1.0;
        }

        lonEditText.setText(String.format("%.2f", currentLon));
        latEditText.setText(String.format("%.2f", currentLat));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
