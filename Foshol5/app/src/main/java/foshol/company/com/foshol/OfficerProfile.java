package foshol.company.com.foshol;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OfficerProfile extends AppCompatActivity implements View.OnClickListener{
    private TextView name,district,city;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String useremail,keyy;
    DatabaseReference db,db2;
    String sname,sdist,scity,smail,spass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officer_profile);
        name = (TextView) findViewById(R.id.textViewName);
        district = (TextView) findViewById(R.id.textViewDistrict);
        city = (TextView) findViewById(R.id.textViewCity);

        Intent intent = getIntent();
         sname=intent.getStringExtra("sellername");
         sdist=intent.getStringExtra("sellerdistrict");
         scity=intent.getStringExtra("sellercity");
         smail=intent.getStringExtra("sellermail");
        spass = intent.getStringExtra("sellerpass");
        keyy=intent.getStringExtra("keyy");

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference("Officer");
        db2=db.child(keyy);

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

    @Override
    public void onClick(View view) {

    }

    public boolean onCreateOptionsMenu(Menu manu){
        getMenuInflater().inflate(R.menu.main,manu);
      //  getMenuInflater().inflate(R.menu.navigation_menu,manu);
        getMenuInflater().inflate(R.menu.menu_officer,manu);
        return  true;
    }


    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        finish();
        if(id==R.id.idlogout){
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(OfficerProfile.this,SignIn.class);
            startActivity(intent);
            finish();
            return true;
        }
        else if (id==R.id.idhome){
            Intent intent=new Intent(OfficerProfile.this,Index.class);
            startActivity(intent);
            return true;
        }
        else if(id==R.id.nav_update){
            Intent in=new Intent(OfficerProfile.this,OfficerProfileUpdate.class);
            in.putExtra("kname",sname);
            in.putExtra("kdistrict",sdist);
            in.putExtra("kcity",scity);
            in.putExtra("kMail",smail);
            in.putExtra("kPass",spass);

            in.putExtra("keyy",keyy);
            startActivity(in);
        }



        else if (id==R.id.nav_delete){
            db2.removeValue();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),"Your account is deleted.",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            Intent intent=new Intent(OfficerProfile.this,Index.class);
            startActivity(intent);
            finish();
            return true;
        }

        return true;
    }
}
