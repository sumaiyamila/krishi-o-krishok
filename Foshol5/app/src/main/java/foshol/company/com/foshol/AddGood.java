package foshol.company.com.foshol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddGood extends AppCompatActivity implements View.OnClickListener{
    EditText roll;
    Spinner name;
    Button button;
    List<Goods> goodsList;


String keyPaisi;
    private ListView listViewGoods;

    DatabaseReference databaseGood,db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_good);
        Intent intent=getIntent();
        keyPaisi=intent.getStringExtra("keyPathai");


       databaseGood = FirebaseDatabase.getInstance().getReference("Seller");
        db=databaseGood.child(keyPaisi).child("Goods");
       // db= FirebaseDatabase.getInstance().getReference("Seller").child(keyPaisi);

        name=(Spinner) findViewById(R.id.spinnerName);
        roll=(EditText)findViewById(R.id.textViewRoll);
        listViewGoods=(ListView)findViewById(R.id.ListViewGoods);
        goodsList = new ArrayList<>();

        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                goodsList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Goods goods = snapshot.getValue(Goods.class);

                    goodsList.add(goods);
                    // goodsList.add(seller);
                }
                GoodsList adapter = new GoodsList(AddGood.this,goodsList);
                listViewGoods.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        addGood();
    }
    public void addGood(){
        String naam = name.getSelectedItem().toString().trim();
        String rollno = roll.getText().toString().trim();

        String Id=db.push().getKey();
        Goods good = new Goods(Id,naam,rollno);

        db.child(Id).setValue(good);
        Toast.makeText(this,"পণ্য যোগ হয়েছে",Toast.LENGTH_LONG).show();
    }
}
