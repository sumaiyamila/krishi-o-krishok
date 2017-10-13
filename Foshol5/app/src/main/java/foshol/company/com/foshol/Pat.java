package foshol.company.com.foshol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Pat extends AppCompatActivity {

    Button btn_ghora, btn_kando, btn_bicha_poka, btn_gora_pocha, mojaik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pat);

        btn_ghora= (Button)findViewById(R.id.ghoraButton);
        btn_ghora.setOnClickListener(new View.OnClickListener(){
                                            public void onClick(View v){
                                                Intent intentBoro=new Intent(getApplicationContext(),PatGhora.class);
                                                startActivity(intentBoro);
                                            }
                                        }
        );


        btn_bicha_poka= (Button)findViewById(R.id.bButton);
        btn_bicha_poka.setOnClickListener(new View.OnClickListener(){
                                         public void onClick(View v){
                                             Intent intentBoro=new Intent(getApplicationContext(),PatBichaPoka.class);
                                             startActivity(intentBoro);
                                         }
                                     }
        );

        mojaik= (Button)findViewById(R.id.mButton);
        mojaik.setOnClickListener(new View.OnClickListener(){
                                         public void onClick(View v){
                                             Intent intentBoro=new Intent(getApplicationContext(),PatMojaik.class);
                                             startActivity(intentBoro);
                                         }
                                     }
        );

        btn_gora_pocha= (Button)findViewById(R.id.goraPochatButton);
        btn_gora_pocha.setOnClickListener(new View.OnClickListener(){
                                         public void onClick(View v){
                                             Intent intentBoro=new Intent(getApplicationContext(),PatGoraPocha.class);
                                             startActivity(intentBoro);
                                         }
                                     }
        );


        btn_kando= (Button)findViewById(R.id.kandoPochaButton);
        btn_kando.setOnClickListener(new View.OnClickListener(){
                                         public void onClick(View v){
                                             Intent intentBoro=new Intent(getApplicationContext(),PatKandoPocha.class);
                                             startActivity(intentBoro);
                                         }
                                     }
        );





    }
}
