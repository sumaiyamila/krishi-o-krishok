package foshol.company.com.foshol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class KrishokProfile extends AppCompatActivity  {
    TextView name,district,city;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String useremail;


    DatabaseReference db,db2,db1;
    String keyy,sname,sdist,scity, smail,sPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_krishok_profile);

        name = (TextView) findViewById(R.id.textViewName);
        district = (TextView) findViewById(R.id.textViewDistrict);
        city = (TextView) findViewById(R.id.textViewCity);




        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        db= FirebaseDatabase.getInstance().getReference("krishok");

     //   Toast.makeText(getApplicationContext(),"krishok profile e",Toast.LENGTH_LONG).show();
        Intent intent = getIntent();

        sname=intent.getStringExtra("kname");
        sdist=intent.getStringExtra("kdistrict");
        scity=intent.getStringExtra("kcity");
        smail=intent.getStringExtra("kMail");
        sPass=intent.getStringExtra("kPass");

        keyy=intent.getStringExtra("keyy");

        db1=db.child(keyy);
       // db2=db1.child("Goods");

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



    }



    public boolean onCreateOptionsMenu(Menu manu){
        getMenuInflater().inflate(R.menu.main,manu);
        getMenuInflater().inflate(R.menu.navigation_menu,manu);
        return  true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        finish();
        if(id==R.id.idlogout){
            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(this, SignIn.class);
            i.setFlags(i.FLAG_ACTIVITY_NEW_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i); // Launch the HomescreenActivity
            finish();

            return true;
        }
        else if (id==R.id.idhome){
            Intent intent=new Intent(KrishokProfile.this,Index.class);
            startActivity(intent);
            return true;
        }


        else if (id==R.id.nav_goods){
            Intent in=new Intent(KrishokProfile.this,KrishokGoods.class);
            in.putExtra("kname",sname);
            in.putExtra("kdistrict",sdist);
            in.putExtra("kcity",scity);
          in.putExtra("keyy",keyy);
            startActivity(in);
            return true;
        }



        else if (id==R.id.nav_delete){

            FirebaseAuth.getInstance().signOut();
            db1.removeValue();
            user.delete();
            Intent intent=new Intent(KrishokProfile.this,Index.class);
            startActivity(intent);
            finish();
            return true;
        }
         else if(id==R.id.nav_update){

            Intent in=new Intent(KrishokProfile.this,KrishokProfileUpdate.class);
            in.putExtra("kname",sname);
            in.putExtra("kdistrict",sdist);
            in.putExtra("kcity",scity);
            in.putExtra("kMail",smail);
            in.putExtra("kPass",sPass);

            in.putExtra("keyy",keyy);
            startActivity(in);

            return true;

        }

        return true;
    }


}
