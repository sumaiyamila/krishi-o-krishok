package foshol.company.com.foshol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Seller extends AppCompatActivity {
    TextView sName,bName;


   ListView lv;
    DatabaseReference db,db2;
   List<MarketNode> list;
    String s,s1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);



        sName=(TextView) findViewById(R.id.sName);
        bName=(TextView) findViewById(R.id.bName);
        // tv2=(TextView) findViewById(R.id.tv2);

        Intent getintent=getIntent();
        // s=getintent.getStringExtra(Bazar.ID_KEY);
        s1=getintent.getStringExtra("name");
       String ss=s1.toUpperCase();
        bName.setText(ss);

        db= FirebaseDatabase.getInstance().getReference("Seller");

       lv = (ListView) findViewById(R.id.lv);
        list = new ArrayList<>();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MarketNode marketNode = list.get(i);
                Intent myIntent = new Intent(getApplicationContext(), PriceList.class);

                //Toast.makeText(Bazar.this,marketNode.getId(),Toast.LENGTH_SHORT).show();
                myIntent.putExtra("key",marketNode.getId());
                myIntent.putExtra("name",marketNode.getNaam());
                startActivity(myIntent);

            }
        });
    }

    protected void onStart() {
        super.onStart();


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    //getting artist
                    MarketNode marketNode=snapshot.getValue(MarketNode.class);
                    if(marketNode.getShopname().equals(s1))
                    {
                        //Toast.makeText(Seller.this,"ok",Toast.LENGTH_SHORT).show();
                       //SellerName sellerName=snapshot.getValue(SellerName.class);
                       list.add(marketNode);
                    }

                }
                SellerAdapter goodsAdapter = new SellerAdapter(Seller.this,list);
                //set the adapter to the list view
                lv.setAdapter(goodsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
