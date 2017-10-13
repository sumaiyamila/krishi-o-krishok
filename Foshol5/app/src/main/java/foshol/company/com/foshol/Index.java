package foshol.company.com.foshol;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Index extends AppCompatActivity {

    ImageButton btn1;
    ImageButton btn2;
    ImageButton btn3;
    ImageButton btn4;
    Button btn_LogIn;
    public  int loginCheck=0;
    final String Tag=this.getClass().getName();
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
       // btn1=(ImageButton) findViewById(R.id.imageButton);
        btn2=(ImageButton) findViewById(R.id.imageButton2);
        btn3=(ImageButton) findViewById(R.id.imageButton3);
        btn4=(ImageButton) findViewById(R.id.imageButton4);
        btn_LogIn = (Button) findViewById(R.id.buttonLogIn);
       // Toast.makeText(Index.this, "index e ", Toast.LENGTH_SHORT).show();

        firebaseAuth = FirebaseAuth.getInstance();

       /* btn1.setOnClickListener(new View.OnClickListener(){
                                    public void onClick(View v){
                                        Intent intent1=new Intent(getApplicationContext(),Abhawa2.class);
                                        startActivity(intent1);
                                    }
                                }
        ); */

        btn2.setOnClickListener(new View.OnClickListener(){
                                    public void onClick(View v){
                                        Intent intent2=new Intent(getApplicationContext(),Poka2.class);
                                        startActivity(intent2);
                                    }
                                }
        );
       btn3.setOnClickListener(new View.OnClickListener(){
                                    public void onClick(View v){
                                        Intent intent3=new Intent(getApplicationContext(),Bazar.class);
                                        startActivity(intent3);
                                    }
                                }
        );
        btn4.setOnClickListener(new View.OnClickListener(){
                                    public void onClick(View v){
                                        Intent intent4=new Intent(getApplicationContext(),Beboharbidhi.class);
                                        startActivity(intent4);
                                    }
                                }
        );
        btn_LogIn.setOnClickListener(new View.OnClickListener(){
                                    public void onClick(View v){
                                        Intent intent5=new Intent(getApplicationContext(),SignIn.class);
                                        if (firebaseAuth.getCurrentUser() != null){
                                            //FirebaseAuth.getInstance().signOut();
                                            Intent intent6=new Intent (getApplicationContext(),Profile.class);

                                          // Toast.makeText(getApplicationContext(),"button click",Toast.LENGTH_LONG).show();
                                            startActivity(intent6);
                                        }
                                        else {
                                            //Toast.makeText(getApplicationContext(),"signin option er vitr",Toast.LENGTH_LONG).show();
                                            startActivity(intent5);

                                        }
                                    }
                                }
        );
    }
    boolean twice;

    public void onBackPressed() {
        Log.d(Tag,"click");
       // Toast.makeText(this,"first time click"+twice,Toast.LENGTH_SHORT).show();
        if(twice==true){
           // Toast.makeText(this,"if er vitor",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            this.finish();
            System.exit(0);
        }
        twice=true;
        Log.d(Tag,"twice : "+twice);
       // Toast.makeText(this,"if er baire "+twice,Toast.LENGTH_SHORT).show();


        Toast.makeText(this,"এ্যাপটি বন্ধ করতে পুনরায় ব্যাক বাটন চাপুন।",Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice=false;
                Log.d(Tag,"twice : "+twice);
            }
        },3000);
        //Toast.makeText(this,"runable er baire",Toast.LENGTH_SHORT).show();

        twice=true;
    }

}
