package foshol.company.com.foshol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Bazar extends AppCompatActivity {

    TextView heading;
    ListView lv;

    DatabaseReference db;

    List<MarketNode> list;
    String shpname,jela,shohor;

    public final static String ID_KEY = "foshol.company.com.Id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bazar);

        db = FirebaseDatabase.getInstance().getReference("Seller");
        heading=(TextView) findViewById(R.id.heading);
        lv = (ListView) findViewById(R.id.lv);
        list = new ArrayList<>();



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
    protected void onStart() {
        super.onStart();

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
                    //  Toast.makeText(getApplicationContext(),"shuru te "+list.size(),Toast.LENGTH_LONG).show();

                    if(list.size()>0) {
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
                            //Toast.makeText(getApplicationContext(),"first else er vitore",Toast.LENGTH_LONG).show();

                            list.add(temp);
                        }
                    }
                    else {
                        list.add(temp);
                       // Toast.makeText(getApplicationContext(),"else er vitore",Toast.LENGTH_LONG).show();
                    }
                    //adding artist to the list

                }
                SellerList bazarAdapter = new SellerList(Bazar.this,list);
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
            Intent intent = new Intent(Bazar.this, SignIn.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.idhome) {
            Intent intent = new Intent(Bazar.this, Index.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.bazar_map) {
            Intent intent = new Intent(Bazar.this, FirstFragment.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.bazar_name) {
           Intent intent = new Intent(Bazar.this, BazarName.class);
           startActivity(intent);
            return true;
        }
        return  true;
    }



}
