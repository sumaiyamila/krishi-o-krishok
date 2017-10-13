package foshol.company.com.foshol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class PriceList extends AppCompatActivity {
    TextView tv1,bName;
    TextView tv2;


    ListView lv;
    DatabaseReference db,db2;
    List<Goods> list;
    String s,s1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_list);



        tv1=(TextView) findViewById(R.id.tv1);
        bName=(TextView) findViewById(R.id.bName);
       // tv2=(TextView) findViewById(R.id.tv2);

          Intent getintent=getIntent();
         s=getintent.getStringExtra("key");
        s1=getintent.getStringExtra("name");
        //String ss=s1.toUpperCase();
        bName.setText(s1);

        db= FirebaseDatabase.getInstance().getReference("Seller");
        db2=db.child(s).child("Goods");

       // Toast.makeText(this,s,Toast.LENGTH_LONG).show();

        lv = (ListView) findViewById(R.id.lv);
        list = new ArrayList<>();

    }

       protected void onStart() {
        super.onStart();


        db2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    //getting artist
                    Goods goods=snapshot.getValue(Goods.class);
                   list.add(goods);

                }
               GoodsList goodsAdapter = new GoodsList(PriceList.this,list);
                //set the adapter to the list view
                lv.setAdapter(goodsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
