package foshol.company.com.foshol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Vutta extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vutta);

        Button btn_seed_pocha, btn_kando_pocha, btn_pata_jholshano,btn_mocha_pocha;

        btn_seed_pocha= (Button)findViewById(R.id.vuttaSeedButton);
        btn_seed_pocha.setOnClickListener(new View.OnClickListener(){
                                    public void onClick(View v){
                                        Intent intentBoro=new Intent(getApplicationContext(),VuttaSeedPocha.class);
                                        startActivity(intentBoro);
                                    }
                                }
        );

        btn_pata_jholshano= (Button)findViewById(R.id.vPJButton);
        btn_pata_jholshano.setOnClickListener(new View.OnClickListener(){
                                              public void onClick(View v){
                                                  Intent intentBoro=new Intent(getApplicationContext(),VuttaPataJholshano.class);
                                                  startActivity(intentBoro);
                                              }
                                          }
        );

        btn_mocha_pocha= (Button)findViewById(R.id.vMButton);
        btn_mocha_pocha.setOnClickListener(new View.OnClickListener(){
                                                  public void onClick(View v){
                                                      Intent intentBoro=new Intent(getApplicationContext(),VuttaMocha.class);
                                                      startActivity(intentBoro);
                                                  }
                                              }
        );

        btn_kando_pocha= (Button)findViewById(R.id.vKButton);
        btn_kando_pocha.setOnClickListener(new View.OnClickListener(){
                                               public void onClick(View v){
                                                   Intent intentBoro=new Intent(getApplicationContext(),VuttaKando.class);
                                                   startActivity(intentBoro);
                                               }
                                           }
        );

    }
}
