package foshol.company.com.foshol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Shobji extends AppCompatActivity implements View.OnClickListener{
    private Button btn1,btn2,btn3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shobji);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

    }

    public void onClick(View view) {
        if (view == btn1){
            Intent intent = new Intent(Shobji.this,Aalu.class);
            startActivity(intent);
        }
        if (view == btn2){
            Intent intent = new Intent(Shobji.this,Begun.class);
            startActivity(intent);
        }
        if(view == btn3){
            Intent intent = new Intent(Shobji.this,Badhakopi.class);
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
            Intent intent=new Intent(Shobji.this,Index.class);
            startActivity(intent);
            finish();
            return true;
        }
        else if (id==R.id.idhome){
            Intent intent=new Intent(Shobji.this,Index.class);
            startActivity(intent);
            return true;
        }
        return true;
    }
}
