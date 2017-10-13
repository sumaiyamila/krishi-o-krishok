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

public class BazarName2 extends AppCompatActivity implements View.OnClickListener {

    TextView heading, tvcheck, tvcheck2;
    ListView lv;

    DatabaseReference db;
    DatabaseReference dbSeller, dbKrishok, dbOfficer;
    String useremail;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    List<MarketNode> list;
    String shpname, jela, shohor, area, address, country, area2;
    String Userjela;
    private ProgressDialog progressDialog;


    public final static String ID_KEY = "foshol.company.com.Id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bazar_name2);
        db = FirebaseDatabase.getInstance().getReference("Seller");
        dbSeller = FirebaseDatabase.getInstance().getReference("Seller");
        dbKrishok = FirebaseDatabase.getInstance().getReference("krishok");
        dbOfficer = FirebaseDatabase.getInstance().getReference("Officer");
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


        heading = (TextView) findViewById(R.id.heading);
        tvcheck = (TextView) findViewById(R.id.tvcheck);
        tvcheck2 = (TextView) findViewById(R.id.tvcheck2);
        Intent intent = getIntent();
        area2=intent.getStringExtra("area");
        lv = (ListView) findViewById(R.id.lv);
        list = new ArrayList<>();

        if (firebaseAuth.getCurrentUser() != null){
            firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
            useremail = firebaseUser.getEmail();
            // Toast.makeText(getApplicationContext(),"user er mail "+useremail,Toast.LENGTH_LONG).show();

            // tv1.setText(useremail);
        }
        if (firebaseAuth.getCurrentUser() != null){
            firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
            useremail = firebaseUser.getEmail();
            // Toast.makeText(getApplicationContext(),"user er mail "+useremail,Toast.LENGTH_LONG).show();

            // tv1.setText(useremail);
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

    protected void onStart() {
        super.onStart();
       // Toast.makeText(getApplicationContext(),"onstart e ",Toast.LENGTH_LONG).show();

        if (firebaseAuth.getCurrentUser() != null){

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
                // Toast.makeText(getApplicationContext(),"vitore",Toast.LENGTH_LONG).show();


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
                        if(  jela.equals(Userjela) || (area2.equals("Dhaka") && jela.equals("ঢাকা") )   ){

                            //progressDialog.hide();
                            //       Toast.makeText(getApplicationContext(),"add kortesi "+Userjela+"   "+jela+"  "+temp.getNaam(),Toast.LENGTH_LONG).show();

                            // Toast.makeText(getApplicationContext(),"milse "+temp.getShopname()+" "+list.get(i).getShopname(),Toast.LENGTH_LONG).show();

                                list.add(temp);
                            //break;
                        }
                        //Toast.makeText(getApplicationContext(),"first else er vitore",Toast.LENGTH_LONG).show();

                    }

                    //adding artist to the list

                }
                SellerList bazarAdapter = new SellerList(BazarName2.this,list);
                //set the adapter to the list view
                lv.setAdapter(bazarAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public boolean onCreateOptionsMenu(Menu manu) {
        getMenuInflater().inflate(R.menu.main, manu);
        //  getMenuInflater().inflate(R.menu.navigation_menu,manu);
        getMenuInflater().inflate(R.menu.nearest, manu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        finish();
        if (id == R.id.idlogout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(BazarName2.this, SignIn.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.idhome) {
            Intent intent = new Intent(BazarName2.this, Index.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.bazar_map) {
            Intent intent = new Intent(BazarName2.this, FirstFragment.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.bazar_name) {
            // Intent intent = new Intent(Bazar.this, BazarName.class);
            //  startActivity(intent);

            return true;
        }
        return true;
    }


    @Override
    public void onClick(View view) {

    }
}