package com.example.locationservice07082019;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.textView);
        final String[] uri = new String[1];
        if((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
                &&
                (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED))
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},0
            );
        }
        locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location)

            {
               double latitude= location.getLatitude();
               double longitude= location.getLongitude();
                uri[0] =String.format(Locale.ENGLISH,"geo:%f,%f",latitude,longitude);

               textView.setText(latitude+"\n"+longitude+"\n\n\n"+uri[0]);
                Geocoder geocoder=new Geocoder(MainActivity.this);
                try
                {

                   List<Address> list= geocoder.getFromLocation(latitude,longitude,1);
                   String country=list.get(0).getCountryName();
                   String locality=list.get(0).getLocality();
                   String address= list.get(0).getAddressLine(0);
                   textView.append("\n"+locality+"\n"+address+"\n"+country);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse(uri[0]));
                startActivity(i);


            }
        });
    }
}
