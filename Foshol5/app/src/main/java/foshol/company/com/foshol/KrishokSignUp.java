package foshol.company.com.foshol;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class KrishokSignUp extends AppCompatActivity implements View.OnClickListener{
    private EditText mail,pw,confirmpw,et1,et5,et6;
    private ImageButton btn;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private Spinner spinner;
    DatabaseReference dbkrishok;
    ArrayAdapter<CharSequence> adapter;
    public  String sellername="sellername",sellerdistrict="sellerdistrict",sellercity="sellercity",sellershop="sellershop",keyChild="key";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_krishok_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();
        dbkrishok = FirebaseDatabase.getInstance().getReference("krishok");

        spinner = (Spinner) findViewById(R.id.spinner);

        mail = (EditText) findViewById(R.id.et2);
        et1 = (EditText) findViewById(R.id.et1);

        et6 = (EditText) findViewById(R.id.et6);
        pw = (EditText) findViewById(R.id.et3);
        confirmpw= (EditText) findViewById(R.id.et4);
        progressDialog = new ProgressDialog(this);
        btn = (ImageButton) findViewById(R.id.btn1);
        adapter = ArrayAdapter.createFromResource(this, R.array.District, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view == btn){
            registerUser();
        }

    }

    private void registerUser() {

        //getting all the inputs from the user
        String email = mail.getText().toString().trim();
        String password = pw.getText().toString().trim();
        String confirmp = confirmpw.getText().toString().trim();
        final String naam = et1.getText().toString().trim();
        final String city = et6.getText().toString().trim();
        String dist = spinner.getSelectedItem().toString();

        //validation

        if (TextUtils.isEmpty(naam)){
            Toast.makeText(this, "নাম প্রদান করুন", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(dist)){
            Toast.makeText(this, "জেলা প্রদান করুন", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(city)){
            Toast.makeText(this, "শহর প্রদান করুন", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "ইমেইল প্রদান করুন", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "পাসওয়ার্ড প্রদান করুন!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() <= 5){
            Toast.makeText(this, "পাসওয়ার্ডে ৫ অক্ষরের বেশি হতে হবে", Toast.LENGTH_SHORT).show();
            return;
        }

        if(pw.getText().toString().trim().equals( confirmpw.getText().toString().trim())){


            progressDialog.setMessage("রেজিস্ট্রেশন হচ্ছে,অনুগ্রহ করে অপেক্ষা করুন...");
            progressDialog.show();

            //now we can create the user
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(KrishokSignUp.this, "রেজিস্ট্রেশন সম্পন্ন হয়েছে", Toast.LENGTH_SHORT).show();
                                addKrishok();
                                progressDialog.hide();
                                finish();
                                return;

                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(KrishokSignUp.this, "ইমেইল ইতিমধ্যে আছে। !", Toast.LENGTH_SHORT).show();
                                }
                                Toast.makeText(KrishokSignUp.this,"রেজিস্ট্রেশন সম্পন্ন হয়নি। পুনরায় চেষ্টা করুন।  " + task.getException().getMessage(), //ADD THIS
                                        Toast.LENGTH_LONG).show();
                                progressDialog.hide();
                                return;
                            }
                        }
                    }); }
        else{
            Toast.makeText(this, "পাসওয়ার্ড মিলে নি। পুনরায় প্রদান করুন।", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void addKrishok(){
        String name = et1.getText().toString().trim();
        String email = mail.getText().toString().trim();
        String pass = pw.getText().toString().trim();
        String conpass= confirmpw.getText().toString().trim();
        String jela =  spinner.getSelectedItem().toString();
        String shohor = et6.getText().toString().trim();
        //Toast.makeText(this, "inside addkrishok", Toast.LENGTH_LONG).show();
        // dbkrishok = FirebaseDatabase.getInstance().getReference("");

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"ইমেইল দিন",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(pass)){
            Toast.makeText(getApplicationContext(),"পাসওয়ার্ড দিন",Toast.LENGTH_LONG).show();
            return;
        }

        if (pass.length() <= 5){
            Toast.makeText(this, "পাসওয়ার্ডের দৈর্ঘ্য ৫ থেকে বেশি হতে হবে!!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(conpass)){
            Toast.makeText(getApplicationContext(),"পুনরায় পাসওয়ার্ড দিন।",Toast.LENGTH_LONG).show();
            return;
        }


        if(TextUtils.isEmpty(jela)){
            Toast.makeText(getApplicationContext(),"Please enter jela",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(shohor)){
            Toast.makeText(getApplicationContext(),"Please enter shohor",Toast.LENGTH_LONG).show();
            return;
        }
        if(!TextUtils.isEmpty(name)){
            //action
            String Id = dbkrishok.push().getKey();
            String stat="Krishok";

            Krishoknode krishok = new Krishoknode(name,email,pass,jela,shohor,stat);

            dbkrishok.child(Id).child("Information").setValue(krishok);

            //et1.setText("");
           // Toast.makeText(getApplicationContext(), "Krishok Added!", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please enter name!", Toast.LENGTH_SHORT).show();
        }
        return;

    }
    public boolean onCreateOptionsMenu(Menu manu){
        getMenuInflater().inflate(R.menu.main,manu);
        return  true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.idlogout){
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(KrishokSignUp.this,Index.class);
            startActivity(intent);
            finish();
            return true;
        }
        else if (id==R.id.idhome){
            Intent intent=new Intent(KrishokSignUp.this,Index.class);
            startActivity(intent);
            return true;
        }
        return true;
    }

}

