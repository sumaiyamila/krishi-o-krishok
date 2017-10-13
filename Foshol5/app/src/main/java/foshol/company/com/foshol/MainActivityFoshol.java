package foshol.company.com.foshol;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import static java.lang.Thread.sleep;

public class MainActivityFoshol extends AppCompatActivity {

    //TextView tv;
   // Animation rotation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_foshol);

     //   tv=(TextView)findViewById(R.id.textViewmain);

        Thread myThread = new Thread(){
            @Override
            public void run(){

                //tv.setMovementMethod(new ScrollingMovementMethod());
                try {
                    sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(getApplicationContext(),Index.class);
                startActivity(intent);
                finish();
            }
        };
        myThread.start();
    }
}
