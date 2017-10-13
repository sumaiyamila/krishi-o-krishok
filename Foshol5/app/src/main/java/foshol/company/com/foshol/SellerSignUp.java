package foshol.company.com.foshol;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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

public class SellerSignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail,confirmpw,et1,et5,et6;
    private EditText editTextPassword;
    private Button btn1;
    private ImageButton buttonSignup;
    private ListView listViewSeller;
    //ImageButton btn4;
   // private TextView textViewSignin;
    private Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    private ProgressDialog progressDialog;
    DatabaseReference dbSeller;
    List<MarketNode> sellerList;

    // defining FirebaseAuth
    private FirebaseAuth firebaseAuth;
    private LinearLayout parentLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_sign_up);

        dbSeller = FirebaseDatabase.getInstance().getReference("Seller");

        spinner = (Spinner) findViewById(R.id.spinner);

        parentLinearLayout = (LinearLayout) findViewById(R.id.linearlayout);
        listViewSeller=(ListView)findViewById(R.id.listViewSeller);


        //getting the firebase object
        firebaseAuth = FirebaseAuth.getInstance();

        //initializing all the views
        editTextEmail = (EditText) findViewById(R.id.et2);
        et1 = (EditText) findViewById(R.id.et1);
        et5 = (EditText) findViewById(R.id.editText5);
        et6 = (EditText) findViewById(R.id.et6);
        editTextPassword = (EditText) findViewById(R.id.et3);
        confirmpw = (EditText) findViewById(R.id.et4);

        adapter = ArrayAdapter.createFromResource(this, R.array.District, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        buttonSignup = (ImageButton) findViewById(R.id.btn3);

        btn1 = (Button) findViewById(R.id.btn1);


        //underlining tv8


        progressDialog = new ProgressDialog(this);
        sellerList=new ArrayList<>();

        //attaching listener to button sign up
        buttonSignup.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignup){
            registerUser();
        }
    }



    private void registerUser() {

        //getting all the inputs from the user
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmp = confirmpw.getText().toString().trim();
        String naam = et1.getText().toString().trim();
        String dist = spinner.getSelectedItem().toString();

        String city = et5.getText().toString().trim();
        String shopname = et6.getText().toString().trim();


        //validation

        if (TextUtils.isEmpty(shopname)){
            Toast.makeText(this, "দোকানের নাম প্রদান করুন", Toast.LENGTH_SHORT).show();
            return;
        }

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

        //validation
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "ইমেইল প্রদান করুন!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "পাসওয়ার্ড প্রদান করুন!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() <= 5){
            Toast.makeText(this, "পাসওয়ার্ড কমপক্ষে ৫ অক্ষর বিশিষ্ট হতে হবে!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(editTextPassword.getText().toString().trim().equals( confirmpw.getText().toString().trim())){
            progressDialog.setMessage("রেজিস্ট্রেশন হচ্ছে,অনুগ্রহ করে অপেক্ষা করুন...");
            progressDialog.show();

            //now we can create the user
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SellerSignUp.this, "রেজিস্ট্রেশন সম্পন্ন হয়েছে", Toast.LENGTH_SHORT).show();

                                addSeller();
                                finish();

                                progressDialog.hide();
                                return;

                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(SellerSignUp.this, "ইমেইল ইতিমধ্যে আছে।অন্য ইমেইল দিন!", Toast.LENGTH_SHORT).show();
                                }
                                Toast.makeText(SellerSignUp.this, "কোথাও সমস্যা হয়েছে। পুনরায় চেষ্টা করুন!", Toast.LENGTH_SHORT).show();
                                progressDialog.hide();
                                return;
                            }
                        }
                    });}
        else{
            Toast.makeText(this, "পাসওয়ার্ড মিলে নি। পুনরায় প্রদান করুন।", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void addSeller(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmp = confirmpw.getText().toString().trim();
        String naam = et1.getText().toString().trim();
        String dist = spinner.getSelectedItem().toString();
        String city = et5.getText().toString().trim();
        String shopname = et6.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        if (password.length() <= 5){
            Toast.makeText(this, "পাসওয়ার্ডের দৈর্ঘ্য ৫ থেকে বেশি হতে হবে!!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(confirmp)){
            Toast.makeText(getApplicationContext(),"পাসোয়ার্ড নিশ্চিত করুন",Toast.LENGTH_LONG).show();
            return;
        }


        if(TextUtils.isEmpty(dist)){
            Toast.makeText(getApplicationContext(),"জেলা লিখুন ",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(city)){
            Toast.makeText(getApplicationContext(),"শহর লিখুন",Toast.LENGTH_LONG).show();
            return;
        }
        if(!TextUtils.isEmpty(naam)){
            //action
            String Id = dbSeller.push().getKey();
            String stat="Seller";

            MarketNode seller = new MarketNode(naam,email,password,dist,city,shopname,stat,Id);

            dbSeller.child(Id).setValue(seller);

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
            Intent intent=new Intent(SellerSignUp.this,Index.class);
            startActivity(intent);
            return true;
        }
        else if (id==R.id.idhome){
            Intent intent=new Intent(SellerSignUp.this,Index.class);
            startActivity(intent);
            return true;
        }
        return true;
    }


}
