package foshol.company.com.foshol;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Profile extends AppCompatActivity implements View.OnClickListener {
    private ImageButton btn;
    private TextView tv1;
    String sellermail="sellermail";
    public  String sellername="sellername",sellerdistrict="sellerdistrict",sellercity="sellercity",sellershop="sellershop";

    DatabaseReference dbSeller,dbKrishok,dbOfficer, dbKrishokInfo;
    List<MarketNode> sellerList;
    List<Krishoknode> krishoklist;
    List<Officernode> officerlist;
    private ListView listViewSeller,listViewKrishok,listViewOfficer;
    private FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String useremail;
    private ProgressDialog progressDialog;
    String krishokKey;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        dbSeller = FirebaseDatabase.getInstance().getReference("Seller");
        dbKrishok = FirebaseDatabase.getInstance().getReference("krishok");
        dbOfficer = FirebaseDatabase.getInstance().getReference("Officer");

        tv1 = (TextView) findViewById(R.id.krishok);
        progressDialog = new ProgressDialog(this);

        // dbOfficer = FirebaseDatabase.getInstance().getReference("Officer");
      //  Toast.makeText(getApplicationContext(),"on create ",Toast.LENGTH_LONG).show();

        if (firebaseAuth.getCurrentUser() != null){
            user=FirebaseAuth.getInstance().getCurrentUser();
            useremail = user.getEmail();

            tv1.setText(useremail);
        }

        listViewSeller=(ListView)findViewById(R.id.listViewSeller);
        listViewOfficer=(ListView)findViewById(R.id.listViewOfficer);
        listViewKrishok=(ListView)findViewById(R.id.listViewKrishok);

        sellerList=new ArrayList<>();
        krishoklist=new ArrayList<>();
        officerlist=new ArrayList<>();

        //btn=(ImageButton) findViewById(R.id.button2);
       // btn.setOnClickListener(this);
    }

    protected  void onResume(){
        super.onResume();


    }

    protected void onStart() {
        super.onStart();


        dbSeller.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sellerList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    MarketNode seller = snapshot.getValue(MarketNode.class);
                    String SellerName=seller.getMail();
                    String key=snapshot.getKey();
                   if(useremail.equals(SellerName))
                    {
                        sellerList.add(seller);
                        Intent intent = new Intent(Profile.this,SellerProfile.class);
                        intent.putExtra(sellername,seller.getNaam());
                        intent.putExtra(sellerdistrict,seller.getJela());
                        intent.putExtra(sellercity,seller.getShohor());
                        intent.putExtra(sellershop,seller.getShopname());
                        intent.putExtra(sellermail,seller.getMail());
                        intent.putExtra("keyy",key);

                        progressDialog.setMessage("আপনার একাউন্টে লগইন হচ্ছে। অপেক্ষা করুন...");
                        progressDialog.show();
                       // Toast.makeText(getApplicationContext(),"sign in kothay??",Toast.LENGTH_LONG).show();
                        startActivity(intent);
                        finish();

                    }

                }
               // SellerList adapter = new SellerList(Profile.this,sellerList);
              //  listViewSeller.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /////////////////////////////////////////////
        dbKrishok.addValueEventListener(new ValueEventListener() {
            Intent intentK = new Intent(Profile.this,KrishokProfile.class);

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                krishoklist.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    krishokKey=snapshot.getKey();
                    Krishoknode krishok = snapshot.child("Information").getValue(Krishoknode.class);
                    String krishokMail=krishok.getMail();

                    if(useremail.equals(krishokMail)){
                        krishoklist.add(krishok);
                        tv1.setText(krishokMail);

                        intentK.putExtra("kname",krishok.getNaam());
                        intentK.putExtra("kdistrict",krishok.getJela());
                        intentK.putExtra("kcity",krishok.getShohor());
                        intentK.putExtra("kMail",krishok.getMail());
                        intentK.putExtra("kPass",krishok.getPass());

                        intentK.putExtra("keyy",krishokKey);



                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                // Actions to do after 1 seconds

                                startActivity(intentK);
                                finish();

                            }
                        }, 100);
                    }
                }


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /////////////////////////////////////////////////////////////////

        dbOfficer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                officerlist.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Officernode officer = snapshot.getValue(Officernode.class);
                    String officerName=officer.mail;
                   // String stat=officer.;
                    String key3=snapshot.getKey();
                    if(useremail.equals(officerName)){
                        officerlist.add(officer);
                        tv1.setText(officerName);
                        Intent intent = new Intent(Profile.this,OfficerProfile.class);
                        intent.putExtra("sellername",officer.getNaam());
                        intent.putExtra("sellerdistrict",officer.getJela());
                        intent.putExtra("sellercity",officer.getShohor());
                        intent.putExtra("sellermail",officer.getMail());
                        intent.putExtra("keyy",key3);
                        intent.putExtra("sellerpass",officer.getPass());
                        progressDialog.setMessage("Logging into your account.Please wait...");
                        progressDialog.show();
                        startActivity(intent);
                        finish();
                    }

                }
                OfficerList adapter = new OfficerList(Profile.this,officerlist);
                listViewOfficer.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onClick(View view) {

    }
    public boolean onCreateOptionsMenu(Menu manu){
        getMenuInflater().inflate(R.menu.main,manu);
        return  true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();

        if(id==R.id.idlogout){
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(Profile.this,Index.class);
            finish();
            startActivity(intent);
            return true;
        }
        else if (id==R.id.idhome){
            Intent intent=new Intent(Profile.this,Index.class);
            startActivity(intent);
            return true;
        }
        return true;
    }
}
