package foshol.company.com.foshol;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Page2 extends AppCompatActivity {

    Button rice, wheat,pat, vutta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        rice= (Button)findViewById(R.id.riceButton);
        wheat= (Button)findViewById(R.id.wheatButton);
        pat= (Button)findViewById(R.id.patButton);
        vutta= (Button)findViewById(R.id.vuttaButton);

        rice.setOnClickListener(new View.OnClickListener(){
                                    public void onClick(View v){
                                        Intent intentRice=new Intent(getApplicationContext(),Rice.class);
                                        startActivity(intentRice);
                                    }
                                }
        );

        wheat.setOnClickListener(new View.OnClickListener(){
                                    public void onClick(View v){
                                        Intent intentwheat=new Intent(getApplicationContext(),Wheat.class);
                                        startActivity(intentwheat);

                                    }
                                }
        );

        pat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentwheat=new Intent(getApplicationContext(),Pat.class);
                startActivity(intentwheat);
            }
        });

        vutta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  Toast toast = Toast.makeText(getApplicationContext(), "শীঘ্রই আসবে" , Toast.LENGTH_LONG);
                toast.show(); */

                Intent intentVutta=new Intent(getApplicationContext(),Vutta.class);
                startActivity(intentVutta);

            }
        });



    }
    public boolean onCreateOptionsMenu(Menu manu){
        getMenuInflater().inflate(R.menu.main,manu);
        return  true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.idlogout){
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(Page2.this,Index.class);
            startActivity(intent);
            finish();
            return true;
        }
        else if (id==R.id.idhome){
            Intent intent=new Intent(Page2.this,Index.class);
            startActivity(intent);
            return true;
        }
        return true;
    }
}
