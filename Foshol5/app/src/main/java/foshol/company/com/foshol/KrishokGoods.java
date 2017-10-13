package foshol.company.com.foshol;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class KrishokGoods extends AppCompatActivity implements View.OnClickListener {

    TextView textAdd;
    Spinner spinAdd;
    Button buttonAdd, btn_delete;
    List<kGoodNode> goodsList;
    private ListView listViewGoods;
    DatabaseReference db,db2,db1;
    FirebaseAuth firebaseAuth;
    static String keyy,sname,sdist,scity, naam, date,Id;

    private EditText birthday;
    int year_x,month_x,day_x;
    static final int DIALOG_ID=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_krishok_goods);

        firebaseAuth = FirebaseAuth.getInstance();
        db= FirebaseDatabase.getInstance().getReference("krishok");
        Intent intent = getIntent();
        sname=intent.getStringExtra("kname");
        sdist=intent.getStringExtra("kdistrict");
        scity=intent.getStringExtra("kcity");
        keyy=intent.getStringExtra("keyy");
        db1=db.child(keyy);
        db2=db1.child("Goods");

        textAdd = (TextView) findViewById(R.id.textAdd);
        spinAdd=(Spinner) findViewById(R.id.spinAdd);
        buttonAdd=(Button) findViewById(R.id.buttonAdd);
        btn_delete=(Button) findViewById(R.id.buttonDel);
        listViewGoods=(ListView)findViewById(R.id.lv);
        goodsList = new ArrayList<>();


        final Calendar cal=Calendar.getInstance();
        year_x=cal.get(Calendar.YEAR);
        month_x=cal.get(Calendar.MONTH);
        day_x=cal.get(Calendar.DAY_OF_MONTH);

        birthday = (EditText) findViewById(R.id.birthday);
        showDialogOnClick();
        buttonAdd.setOnClickListener(this);

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db2.removeValue();
            }
        });

    }

    protected void onStart() {
        super.onStart();
        db2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                goodsList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    kGoodNode goods = snapshot.getValue(kGoodNode.class);

                    goodsList.add(goods);
                    //goodsList.add(seller);
                }
                krishokGoodsList adapter = new krishokGoodsList(KrishokGoods.this,goodsList);
                listViewGoods.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void showDialogOnClick(){
        birthday.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                showDialog(DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id){
        if(id==DIALOG_ID)
            return new DatePickerDialog(this,dpickerListener,year_x,month_x,day_x);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListener =
            new DatePickerDialog.OnDateSetListener(){


                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    year_x=year;
                    month_x=month+1;
                    day_x=day;
                    birthday.setText(day_x+"/"+month_x+"/"+year_x);
                }
            };

    @Override
    public void onClick(View view) {
        if(view==buttonAdd)
        {
            naam = spinAdd.getSelectedItem().toString();
            date=birthday.getText().toString().trim();
            Id=db2.push().getKey();

            kGoodNode good = new kGoodNode(Id,naam,date);

            db2.child(Id).setValue(good);
            Toast.makeText(this,"পণ্য যোগ হয়েছে",Toast.LENGTH_LONG).show();
            birthday.setText("");

        }
    }

    public static String getKeyy(){
       return keyy;
    }


    public String getGoodName(){
        return naam;
    }

    public String getGoodDate(){
        return date;
    }
    public  String getGoodId(){
        return Id;
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
            Intent i = new Intent(this, SignIn.class);
            i.setFlags(i.FLAG_ACTIVITY_NEW_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i); // Launch the HomescreenActivity
            finish();

            return true;
        }
        else if (id==R.id.idhome){
            Intent intent=new Intent(KrishokGoods.this,Index.class);
            startActivity(intent);
            return true;
        }


        else if (id==R.id.nav_goods){

            return true;
        }

        else if (id==R.id.nav_delete){
            db1.removeValue();
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(KrishokGoods.this,Index.class);
            startActivity(intent);
            finish();
            return true;
        }

        return true;
    }

}
