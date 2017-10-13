package foshol.company.com.foshol;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static foshol.company.com.foshol.FirstFragment.MY_PERMISSIONS_REQUEST_LOCATION;
import static foshol.company.com.foshol.R.id.map;

public class BazarName extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    TextView heading,tvcheck,tvcheck2;
    ListView lv;

    DatabaseReference db;
    DatabaseReference dbSeller,dbKrishok,dbOfficer;
    String useremail;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    List<MarketNode> list;
    String shpname,jela,shohor,area,address,country,area2;
    String Userjela;
    private ProgressDialog progressDialog;

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    private GoogleMap mMap;
    Marker mCurrLocationMarker;
    Geocoder geocoder;
    List<Address> addresses;
    double longitude,latitude;
    int count=0;

    public final static String ID_KEY = "foshol.company.com.Id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bazar_name);
        db = FirebaseDatabase.getInstance().getReference("Seller");
        dbSeller = FirebaseDatabase.getInstance().getReference("Seller");
        dbKrishok = FirebaseDatabase.getInstance().getReference("krishok");
        dbOfficer = FirebaseDatabase.getInstance().getReference("Officer");
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
        geocoder = new Geocoder(this, Locale.getDefault());


        heading=(TextView) findViewById(R.id.heading);
        tvcheck=(TextView) findViewById(R.id.tvcheck);
        tvcheck2=(TextView) findViewById(R.id.tvcheck2);

        lv = (ListView) findViewById(R.id.lv);
        list = new ArrayList<>();



        if (firebaseAuth.getCurrentUser() != null){
            firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
            useremail = firebaseUser.getEmail();
           // Toast.makeText(getApplicationContext(),"user er mail "+useremail,Toast.LENGTH_LONG).show();

            // tv1.setText(useremail);
        }
        else{
           // Toast.makeText(getApplicationContext(),"user er mail "+useremail,Toast.LENGTH_LONG).show();
            progressDialog.setMessage("নিকটবর্তি বাজার তালিকা পেতে লগইন করুন");
            progressDialog.show();
            return;
        }




        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MarketNode marketNode = list.get(i);
                Intent myIntent = new Intent(getApplicationContext(), Seller.class);

                //Toast.makeText(Bazar.this,marketNode.getId(),Toast.LENGTH_SHORT).show();
                // myIntent.putExtra(ID_KEY,marketNode.getId());
                myIntent.putExtra("name",marketNode.getShopname());
                startActivity(myIntent);

            }
        });
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
    protected void onStart() {
        super.onStart();
       // Toast.makeText(getApplicationContext(),"onstart e ",Toast.LENGTH_LONG).show();

        if (firebaseAuth.getCurrentUser() != null){
            progressDialog.setMessage("অনুগ্রহ করে কিছুক্ষন অপেক্ষা করুন...");
            progressDialog.show();
            dbSeller.addValueEventListener(new ValueEventListener() {
                @Override

                public void onDataChange(DataSnapshot dataSnapshot) {
                 //   Toast.makeText(getApplicationContext(),"seller er jela"+Userjela,Toast.LENGTH_LONG).show();

                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        MarketNode seller = snapshot.getValue(MarketNode.class);
                        String SellerName=seller.getMail();
                        String key=snapshot.getKey();
                        if(useremail.equals(SellerName))
                        {
                             Userjela= seller.getJela();
                           // tvcheck.setText(jela+" etai jela ");
                        }

                    }
                    // SellerList adapter = new SellerList(Profile.this,sellerList);
                    //  listViewSeller.setAdapter(adapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            dbKrishok.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Krishoknode krishok = snapshot.child("Information").getValue(Krishoknode.class);
                        String krishokName=krishok.getMail();
                        //String stat=krishok.stat;
                        String key2=snapshot.getKey();
                      //  Toast.makeText(getApplicationContext(),"if er baire krishok er jela"+Userjela,Toast.LENGTH_LONG).show();
                      //  Toast.makeText(getApplicationContext(),"if er baire krishok er mail"+useremail+"  "+krishokName,Toast.LENGTH_LONG).show();

                        if(useremail.equals(krishokName)){
                            Userjela=krishok.getJela();
                          //  Toast.makeText(getApplicationContext(),"if er bhitore krishok er jela"+Userjela,Toast.LENGTH_LONG).show();

                           // Toast.makeText(getApplicationContext(),"krishok er jela"+Userjela,Toast.LENGTH_LONG).show();
                        }

                    }
                    // KrishokList adapter = new KrishokList(Profile.this,krishoklist);
                    //listViewKrishok.setAdapter(adapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            dbOfficer.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Officernode officer = snapshot.getValue(Officernode.class);
                        String officerName=officer.mail;
                        // String stat=officer.;
                        String key3=snapshot.getKey();
                        if(useremail.equals(officerName)){
                             Userjela= officer.getJela();
                            //tvcheck.setText(jela+" etai jela ");
                        }

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            // tv1.setText(useremail);
        }

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    //getting artist
                    MarketNode temp = snapshot.getValue(MarketNode.class);
                    shpname = temp.getShopname();
                    jela=temp.getJela();
                    shohor=temp.getShohor();
                    int tmp2=1;

                     //  Toast.makeText(getApplicationContext(),"shuru te "+jela,Toast.LENGTH_LONG).show();
                      //  Toast.makeText(getApplicationContext(),"user er jela "+Userjela,Toast.LENGTH_LONG).show();
                    for (int i = 0; i < list.size(); i++) {
                        // System.out.println(list.get(i));
                        // Toast.makeText(getApplicationContext(), "loop er vitore " + shpname, Toast.LENGTH_LONG).show();
                        if(shpname.equals(list.get(i).getShopname())){
                            // Toast.makeText(getApplicationContext(),"milse "+temp.getShopname()+" "+list.get(i).getShopname(),Toast.LENGTH_LONG).show();
                            tmp2=0;
                            break;
                        }
                    }
                    if(tmp2==1){
                       // area2=" ";

                        tvcheck.setText("আপনার বর্তমান অবস্থান "+area2);
                       // onLocationChanged(mLastLocation);
                        if(  jela.equals(Userjela)  ||(count!=0 && area2.equals("Dhaka") && jela.equals("ঢাকা") )   ){
                            progressDialog.hide();
                            //       Toast.makeText(getApplicationContext(),"add kortesi "+Userjela+"   "+jela+"  "+temp.getNaam(),Toast.LENGTH_LONG).show();

                            // Toast.makeText(getApplicationContext(),"milse "+temp.getShopname()+" "+list.get(i).getShopname(),Toast.LENGTH_LONG).show();
                            list.add(temp);
                            //break;
                        }
                        //Toast.makeText(getApplicationContext(),"first else er vitore",Toast.LENGTH_LONG).show();

                    }

                    //adding artist to the list

                }
                SellerList bazarAdapter = new SellerList(BazarName.this,list);
                //set the adapter to the list view
                lv.setAdapter(bazarAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu manu){
        getMenuInflater().inflate(R.menu.main,manu);
        //  getMenuInflater().inflate(R.menu.navigation_menu,manu);
        getMenuInflater().inflate(R.menu.nearest,manu);
        return  true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        finish();
        if (id == R.id.idlogout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(BazarName.this, SignIn.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.idhome) {
            Intent intent = new Intent(BazarName.this, Index.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.bazar_map) {
            Intent intent = new Intent(BazarName.this, FirstFragment.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.bazar_name) {
            // Intent intent = new Intent(Bazar.this, BazarName.class);
            //  startActivity(intent);
            
            return true;
        }
        return  true;
    }


    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(100000);
        mLocationRequest.setFastestInterval(100000);
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

         latitude = location.getLatitude();
         longitude = location.getLongitude();
        //Toast.makeText(getApplicationContext(),"ekhane ashlam",Toast.LENGTH_LONG).show();
        try {
            addresses = geocoder.getFromLocation(latitude,longitude,1);
             address = addresses.get(0).getAddressLine(0);
             area = addresses.get(0).getLocality();
             country = addresses.get(0).getCountryName();
           // tvcheck.setText(area+" country "+country+"+   address  "+address);
            if(count==0){
                count++;
                area2=area;
                tvcheck.setText(area2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        lv = (ListView) findViewById(R.id.lv);
        list = new ArrayList<>();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"item click korsi",Toast.LENGTH_LONG).show();
                MarketNode marketNode = list.get(i);
                Intent myIntent = new Intent(getApplicationContext(), Seller.class);

                //Toast.makeText(Bazar.this,marketNode.getId(),Toast.LENGTH_SHORT).show();
                // myIntent.putExtra(ID_KEY,marketNode.getId());
                myIntent.putExtra("name",marketNode.getShopname());
                startActivity(myIntent);
                //finish();

            }
        });

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    //getting artist
                    MarketNode temp = snapshot.getValue(MarketNode.class);
                    shpname = temp.getShopname();
                    jela=temp.getJela();
                    shohor=temp.getShohor();
                    int tmp2=1;

                    //  Toast.makeText(getApplicationContext(),"shuru te "+jela,Toast.LENGTH_LONG).show();
                    //  Toast.makeText(getApplicationContext(),"user er jela "+Userjela,Toast.LENGTH_LONG).show();
                    for (int i = 0; i < list.size(); i++) {
                        // System.out.println(list.get(i));
                        // Toast.makeText(getApplicationContext(), "loop er vitore " + shpname, Toast.LENGTH_LONG).show();
                        if(shpname.equals(list.get(i).getShopname())){
                            // Toast.makeText(getApplicationContext(),"milse "+temp.getShopname()+" "+list.get(i).getShopname(),Toast.LENGTH_LONG).show();
                            tmp2=0;
                            break;
                        }
                    }
                    if(tmp2==1){
                       // area2="mila";

                        tvcheck.setText("আপনার বর্তমান অবস্থান "+area2);
                        tvcheck2.setText("আপনার স্থায়ী এলাকা  "+Userjela);
                        // onLocationChanged(mLastLocation);
                        if(  jela.equals(Userjela)||(jela.equals("ঢাকা") && area2.equals("Dhaka")    )   ){
                            progressDialog.hide();
                            //       Toast.makeText(getApplicationContext(),"add kortesi "+Userjela+"   "+jela+"  "+temp.getNaam(),Toast.LENGTH_LONG).show();

                            // Toast.makeText(getApplicationContext(),"milse "+temp.getShopname()+" "+list.get(i).getShopname(),Toast.LENGTH_LONG).show();
                            list.add(temp);
                            //break;
                        }
                        //Toast.makeText(getApplicationContext(),"first else er vitore",Toast.LENGTH_LONG).show();

                    }

                    //adding artist to the list

                }
                SellerList bazarAdapter = new SellerList(BazarName.this,list);
                //set the adapter to the list view
                lv.setAdapter(bazarAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            //Toast.makeText(getApplicationContext(),"amar location on",Toast.LENGTH_LONG).show();
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

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
                                ActivityCompat.requestPermissions(BazarName.this,
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
