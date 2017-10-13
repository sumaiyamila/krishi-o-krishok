package foshol.company.com.foshol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Rice extends AppCompatActivity {

    Button foring, boro, bonmasto, boroKholpora, boroPamri, guriPocha, tungro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rice);

        foring= (Button)findViewById(R.id.foringButton);
        foring.setOnClickListener(new View.OnClickListener(){
                                    public void onClick(View v){
                                        Intent intentRice=new Intent(getApplicationContext(),GachForing.class);
                                        startActivity(intentRice);
                                    }
                                }
        );


        boro= (Button)findViewById(R.id.borogButton);
        boro.setOnClickListener(new View.OnClickListener(){
                                      public void onClick(View v){
                                          Intent intentBoro=new Intent(getApplicationContext(),Boro.class);
                                          startActivity(intentBoro);
                                      }
                                  }
        );

        bonmasto= (Button)findViewById(R.id.bonmastoButton);
        bonmasto.setOnClickListener(new View.OnClickListener(){
                                    public void onClick(View v){
                                        Intent intentBoro=new Intent(getApplicationContext(),BoroBonmasto.class);
                                        startActivity(intentBoro);
                                    }
                                }
        );

        boroKholpora= (Button)findViewById(R.id.kholPoraButton);
        boroKholpora.setOnClickListener(new View.OnClickListener(){
                                        public void onClick(View v){
                                            Intent intentBoro=new Intent(getApplicationContext(),BoroKholPora.class);
                                            startActivity(intentBoro);
                                        }
                                    }
        );
        boroPamri= (Button)findViewById(R.id.boroPamriButton);
        boroPamri.setOnClickListener(new View.OnClickListener(){
                                            public void onClick(View v){
                                                Intent intentBoro=new Intent(getApplicationContext(),BoroPamri.class);
                                                startActivity(intentBoro);
                                            }
                                        }
        );

        guriPocha= (Button)findViewById(R.id.rGPButton);
        guriPocha.setOnClickListener(new View.OnClickListener(){
                                         public void onClick(View v){
                                             Intent intentBoro=new Intent(getApplicationContext(),RiceGuriPocha.class);
                                             startActivity(intentBoro);
                                         }
                                     }
        );

        tungro= (Button)findViewById(R.id.rTungutton);
        tungro.setOnClickListener(new View.OnClickListener(){
                                         public void onClick(View v){
                                             Intent intentBoro=new Intent(getApplicationContext(),RiceTungro.class);
                                             startActivity(intentBoro);
                                         }
                                     }
        );


    }
}
