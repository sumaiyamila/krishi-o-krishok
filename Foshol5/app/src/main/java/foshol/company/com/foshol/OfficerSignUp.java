package foshol.company.com.foshol;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

import static foshol.company.com.foshol.R.id.et1;

import static foshol.company.com.foshol.R.id.et6;

public class OfficerSignUp extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextEmail,confirmpw,et1,et5;
    private EditText editTextPassword;
    private ImageButton buttonSignup;

    private Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    private ProgressDialog progressDialog;

    // defining FirebaseAuth
    private FirebaseAuth firebaseAuth;
    DatabaseReference dbOfficer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officer_sign_up);
        spinner = (Spinner) findViewById(R.id.spinner);

        //getting the firebase object
        firebaseAuth = FirebaseAuth.getInstance();
        dbOfficer = FirebaseDatabase.getInstance().getReference("Officer");


        //initializing all the views
        et1 = (EditText) findViewById(R.id.et1);
        et5 = (EditText) findViewById(R.id.editText5);
        editTextEmail = (EditText) findViewById(R.id.et2);
        editTextPassword = (EditText) findViewById(R.id.et3);
        confirmpw = (EditText) findViewById(R.id.et4);

        buttonSignup = (ImageButton) findViewById(R.id.btn3);


        adapter = ArrayAdapter.createFromResource(this, R.array.District, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        progressDialog = new ProgressDialog(this);

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
        //validation

        if (TextUtils.isEmpty(naam)){
            Toast.makeText(this, "Enter your name!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(city)){
            Toast.makeText(this, "Enter your city!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Enter your mail id!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() <= 5){
            Toast.makeText(this, "Password should contain more than 5 characters at least!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(editTextPassword.getText().toString().trim().equals( confirmpw.getText().toString().trim())){
            progressDialog.setMessage("Registration on process.Please wait...");
            progressDialog.show();

            //now we can create the user
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(OfficerSignUp.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                addOfficer();
                                finish();
                                progressDialog.hide();
                                return;

                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(OfficerSignUp.this, "Email exists!", Toast.LENGTH_SHORT).show();
                                }
                                Toast.makeText(OfficerSignUp.this, "Registration failed. Please check your internet connection and try again!", Toast.LENGTH_SHORT).show();
                                progressDialog.hide();
                                return;
                            }
                        }
                    });}
        else{
            Toast.makeText(this, "Password doesn't match. Enter password again.", Toast.LENGTH_SHORT).show();
            return;
        }
    }
    public void addOfficer(){

        String email = editTextEmail.getText().toString().trim();
        String pass = editTextPassword.getText().toString().trim();
        String conpass= confirmpw.getText().toString().trim();
        String naam = et1.getText().toString().trim();
        String dist = spinner.getSelectedItem().toString();
        String city = et5.getText().toString().trim();
        //Toast.makeText(this, "inside addkrishok", Toast.LENGTH_LONG).show();
        // dbkrishok = FirebaseDatabase.getInstance().getReference("");

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(pass)){
            Toast.makeText(getApplicationContext(),"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        if (pass.length() <= 5){
            Toast.makeText(this, "Password should contain more than 5 characters at least!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(conpass)){
            Toast.makeText(getApplicationContext(),"Please confirm your password",Toast.LENGTH_LONG).show();
            return;
        }


        if(TextUtils.isEmpty(dist)){
            Toast.makeText(getApplicationContext(),"Please enter district",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(city)){
            Toast.makeText(getApplicationContext(),"Please enter city",Toast.LENGTH_LONG).show();
            return;
        }
        if(!TextUtils.isEmpty(naam)){
            //action
            String Id = dbOfficer.push().getKey();
            String stat="Officer";

            Officernode officer = new Officernode(naam,email,pass,dist,city,stat);

            dbOfficer.child(Id).setValue(officer);

            //et1.setText("");
            //Toast.makeText(getApplicationContext(), "Officer Added!", Toast.LENGTH_SHORT).show();

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
            Intent intent=new Intent(OfficerSignUp.this,Index.class);
            startActivity(intent);
            finish();
            return true;
        }
        else if (id==R.id.idhome){
            Intent intent=new Intent(OfficerSignUp.this,Index.class);
            startActivity(intent);
            return true;
        }
        return true;
    }
}
