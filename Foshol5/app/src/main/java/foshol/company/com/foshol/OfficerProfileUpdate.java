package foshol.company.com.foshol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OfficerProfileUpdate extends AppCompatActivity {
    private String sname,sdist,scity,smail,sPass,keyy;
    private EditText edt_nm,edt_city,edt_zilla, edt_email, edt_pass;
    private Button btn_submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officer_profile_update);
        Intent intent = getIntent();
        sname=intent.getStringExtra("kname");
        sdist=intent.getStringExtra("kdistrict");
        scity=intent.getStringExtra("kcity");
        smail=intent.getStringExtra("kMail");
        sPass=intent.getStringExtra("kPass");

        keyy=intent.getStringExtra("keyy");

        edt_nm=(EditText)findViewById(R.id.edtTxtName);
        edt_city=(EditText)findViewById(R.id.edtTxtCity);
        edt_zilla=(EditText)findViewById(R.id.edtTxtZilla);
       // edt_email=(EditText)findViewById(R.id.edtTxtEmail);
        //edt_pass=(EditText)findViewById(R.id.edtTxtPass);
        btn_submit=(Button)findViewById(R.id.buttonChng);

        edt_nm.setText(sname);
        edt_city.setText(scity);
        edt_zilla.setText(sdist);
        //edt_email.setText(smail);
       // edt_pass.setText(sPass);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_nm.setEnabled(true);
                edt_city.setEnabled(true);
                edt_zilla.setEnabled(true);
               // edt_email.setEnabled(true);
                //edt_pass.setEnabled(true);

                updateKrishok();

               /* Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // Actions to do after 1 seconds

                        startActivity(intentK);
                        finish();

                    }
                }, 100); */

            }
        });


    }

    private void updateKrishok() {

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Officer").child(keyy);

        String nm,city,dist,email,pass, stat;

        nm = edt_nm.getText().toString().trim();
        city = edt_city.getText().toString().trim();
        dist = edt_zilla.getText().toString().trim();
        email = smail;
        pass = sPass;
        stat="Officer";


        Officernode officer = new Officernode(nm,email,pass,dist,city,stat);
        databaseRef.setValue(officer);

        Toast.makeText(this,"YOUR PROFILE HAS BEEN UPDATED!", Toast.LENGTH_SHORT).show();
    }
}
