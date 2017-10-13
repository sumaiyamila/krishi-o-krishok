package foshol.company.com.foshol;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SellerProfile extends AppCompatActivity implements View.OnClickListener {
    private TextView name,district,city,shop,keychild;
    String keyy;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String useremail;

    List <Goods> goodsList;

    private ListView listViewGoods;
    DatabaseReference databaseGoods,db,db2;
    Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_profile);

        name = (TextView) findViewById(R.id.textViewName);
        district = (TextView) findViewById(R.id.textViewDistrict);
        city = (TextView) findViewById(R.id.textViewCity);
        shop = (TextView) findViewById(R.id.textViewShop);
        keychild = (TextView) findViewById(R.id.textViewKey);
        firebaseAuth = FirebaseAuth.getInstance();


       listViewGoods=(ListView)findViewById(R.id.ListViewGoods);
        buttonAdd = (Button) findViewById(R.id.button1);

        Intent intent = getIntent();
       String sname=intent.getStringExtra("sellername");
        String sdist=intent.getStringExtra("sellerdistrict");
        String scity=intent.getStringExtra("sellercity");
        String sshop=intent.getStringExtra("sellershop");
        String smail=intent.getStringExtra("sellermail");
        keyy=intent.getStringExtra("keyy");
        databaseGoods = FirebaseDatabase.getInstance().getReference("Seller");
        db2=databaseGoods.child(keyy);
        db=db2.child("Goods");

        if (firebaseAuth.getCurrentUser() != null){
            user=FirebaseAuth.getInstance().getCurrentUser();
            useremail = user.getEmail();
            if (useremail.equals(smail)){
                // Toast.makeText(getApplicationContext(),"equal",Toast.LENGTH_LONG).show();
            }
            else{
                // Toast.makeText(getApplicationContext(),"not equal",Toast.LENGTH_LONG).show();
                finish();
                return;
            }


        }


        name.setText(sname);
        district.setText(sdist);
        city.setText(scity);
        shop.setText(sshop);
        keychild.setText("");




       goodsList = new ArrayList<>();

        buttonAdd.setOnClickListener(this);

        listViewGoods.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Goods goods = goodsList.get(i);
                showUpdate(goods.getId(), goods.getName(),goods.getPrice());
                return true;
            }
        });


    }


    @Override
    public void onClick(View view) {
        if(view == buttonAdd){
           // Toast.makeText(this,"click korsi",Toast.LENGTH_LONG).show();
          Intent intent = new Intent(SellerProfile.this,AddGood.class);
            //String keyPathai="keyPathai";
           intent.putExtra("keyPathai",keyy);
            startActivity(intent);

        }

    }

    protected void onStart() {
        super.onStart();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                goodsList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Goods goods = snapshot.getValue(Goods.class);

                        goodsList.add(goods);
                       //goodsList.add(seller);
                }
                GoodsList adapter = new GoodsList(SellerProfile.this,goodsList);
                listViewGoods.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public boolean onCreateOptionsMenu(Menu manu){
        getMenuInflater().inflate(R.menu.main,manu);
        getMenuInflater().inflate(R.menu.navigation_menu,manu);
        return  true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.idlogout){
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(SellerProfile.this,SignIn.class);
            startActivity(intent);
            finish();
            return true;
        }
        else if (id==R.id.idhome){
            Intent intent=new Intent(SellerProfile.this,Index.class);
            startActivity(intent);
            return true;
        }


        else if (id==R.id.nav_delete){
            db2.removeValue();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),"একাউন্ট মুছে ফেলা হয়েছে",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            Intent intent=new Intent(SellerProfile.this,Index.class);
            startActivity(intent);
            finish();
            return true;
        }
        return true;
    }

    private boolean update(String id, String name, String price) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Seller");
        DatabaseReference db=dR.child(keyy).child("Goods").child(id);

        //updating artist
        Goods goods = new Goods(id, name, price);
        db.setValue(goods);
        Toast.makeText(getApplicationContext(), "পরিবর্তন সম্পন্ন হয়েছে", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean delete(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Seller");
        DatabaseReference db=dR.child(keyy).child("Goods").child(id);

        //removing artist
        db.removeValue();

        Toast.makeText(getApplicationContext(), "মুছে ফেলা হয়েছে", Toast.LENGTH_LONG).show();

        return true;
    }

    private void showUpdate(final String id, String name, String price){
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        final View dialogView=inflater.inflate(R.layout.update_dialog,null);
        dialog.setView(dialogView);
        final Spinner et1 = (Spinner) dialogView.findViewById(R.id.et1);
        //et1.setText(name);
        final EditText et2 = (EditText) dialogView.findViewById(R.id.et2);
        et2.setText(price);
        final Button b1 = (Button) dialogView.findViewById(R.id.b1);
        final Button b2 = (Button) dialogView.findViewById(R.id.b2);



        dialog.setTitle(name);
        final AlertDialog b = dialog.create();
        b.show();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et1.getSelectedItem().toString().trim();
                String price = et2.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {
                    update(id, name, price);
                    b.dismiss();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(id);
                b.dismiss();
            }
        });
    }




}
