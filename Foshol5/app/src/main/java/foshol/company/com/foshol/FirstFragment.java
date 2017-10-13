package foshol.company.com.foshol;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import static foshol.company.com.foshol.R.id.map;

public class FirstFragment extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private double longitude;
    private double latitude;
    private GoogleApiClient googleApiClient;
    LatLng Current, next;


    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_fragment);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
        Current = new LatLng(longitude, latitude);
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng newmarket = new LatLng(23.733032, 90.384641);
        LatLng chadnichowk = new LatLng(23.734266, 90.385354);
        LatLng mhmdpur = new LatLng(23.765651, 90.358840);
        LatLng mirpur6 = new LatLng(23.810594, 90.363526);
        LatLng mirpur = new LatLng(23.797948, 90.350906);
        LatLng banani = new LatLng(23.793105, 90.407995);
        LatLng Karwanbazar  = new LatLng(23.752375, 90.394366);
        LatLng hatirpul  = new LatLng(23.740994, 90.391847);
        LatLng SegunBagicha   = new LatLng(23.733609, 90.409351);
        LatLng agora   = new LatLng(23.824173, 90.364970);
        LatLng meena   = new LatLng(23.818778, 90.365505);
        LatLng swopno   = new LatLng(23.822478, 90.364490);
        LatLng PrinceBazar   = new LatLng(23.827252, 90.363985);
        LatLng CSD   = new LatLng(23.827252, 90.363985);

        mMap.addMarker(new MarkerOptions().position(newmarket).title("নিউ মার্কেট, ঢাকা"));
        mMap.addMarker(new MarkerOptions().position(PrinceBazar).title("প্রিন্স বাজার, ঢাকা"));
        mMap.addMarker(new MarkerOptions().position(CSD).title("সিএসডি সরোবর, ঢাকা"));
        mMap.addMarker(new MarkerOptions().position(meena).title("মীনাবাজার,মিরপুর, ঢাকা"));
        mMap.addMarker(new MarkerOptions().position(agora).title("আগোরা সুপার শপ,মিরপুর, ঢাকা"));
        mMap.addMarker(new MarkerOptions().position(swopno).title("স্বপ্ন,মিরপুর, ঢাকা"));
        mMap.addMarker(new MarkerOptions().position(Karwanbazar).title("কারওয়ানবাজার, ঢাকা"));
        mMap.addMarker(new MarkerOptions().position(hatirpul).title("হাতিরপুল কাচা বাজার, ঢাকা"));


        mMap.addMarker(new MarkerOptions().position(banani).title("বনানী কাচা বাজার, ঢাকা"));
        mMap.addMarker(new MarkerOptions().position(SegunBagicha).title("সেগুনবাগিচা কাচা বাজার, ঢাকা"));


        mMap.addMarker(new MarkerOptions().position(mhmdpur).title("মোহাম্মদপুর কৃষি কাচা বাজার, ঢাকা"));
        mMap.addMarker(new MarkerOptions().position(mirpur6).title("মিরপুর-৬ কাচা বাজার, ঢাকা"));
        mMap.addMarker(new MarkerOptions().position(mirpur).title("মিরপুর, ঢাকা"));

        mMap.addMarker(new MarkerOptions().position(chadnichowk).title("চাদনি চক,ঢাকা"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(chadnichowk));

        mMap.animateCamera(CameraUpdateFactory.newLatLng(chadnichowk));
      //  mMap.animateCamera(CameraUpdateFactory.zoomBy((float) 2.0));
     //   goToLocationZoom(23.819585, 90.414753, 20);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
                goToLocationZoom(23.819585, 90.414753, 500);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            //Toast.makeText(getApplicationContext(),"amar location on",Toast.LENGTH_LONG).show();
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            goToLocationZoom(23.819585, 90.414753, 500);
        }


       /* if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(getApplicationContext(),"amar location off",Toast.LENGTH_LONG).show();
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            //Getting longitude and latitude
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            //moving the map to location
            moveMap();
        }*/
    }
        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, (float) 20.0));

        //  mMap.setMaxZoomPreference(50);

    private void goToLocationZoom(double lat, double lng, float zoom) { //this function will take us to desired location with specified zoom
        LatLng ll = new LatLng(lat, lng);
        Toast.makeText(getApplicationContext(),"zoom korte ashsi",Toast.LENGTH_LONG).show();
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(update);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(10000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public void onLocationChanged(Location location)
    {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("আমার এলাকা");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);
        //Toast.makeText(getApplicationContext(),"amar elaka dekhai"+latLng,Toast.LENGTH_LONG).show();

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11));

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(FirstFragment.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }


}
