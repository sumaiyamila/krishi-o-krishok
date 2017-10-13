package foshol.company.com.foshol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class Kathal extends AppCompatActivity implements View.OnClickListener{
    private ImageButton btn1,btn2,btn3,btn4,btn5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kathal);
        btn2 = (ImageButton) findViewById(R.id.btn2);
        btn1 = (ImageButton) findViewById(R.id.btn1);
        btn4 = (ImageButton) findViewById(R.id.btn4);

        btn2.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn4.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view==btn1){
            Intent intent = new Intent(Kathal.this,KathalMajra.class);
            startActivity(intent);
        }
        if(view==btn2){
            Intent intent = new Intent(Kathal.this,KathalDaag.class);
            startActivity(intent);
        }

        if(view==btn4){
            Intent intent = new Intent(Kathal.this,KathalJaab.class);
            startActivity(intent);
        }
    }

    public boolean onCreateOptionsMenu(Menu manu){
        getMenuInflater().inflate(R.menu.main,manu);
        return  true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.idlogout){
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(Kathal.this,Index.class);
            startActivity(intent);
            finish();

            return true;
        }
        else if (id==R.id.idhome){
            Intent intent=new Intent(Kathal.this,Index.class);
            startActivity(intent);
            return true;
        }
        return true;
    }
}
