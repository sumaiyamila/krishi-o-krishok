package foshol.company.com.foshol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Wheat extends AppCompatActivity {

    Button majraPoka, wheatBlast, pataJholshano, moricha,jabPoka;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheat);

        majraPoka= (Button)findViewById(R.id.blotchButton);
        majraPoka.setOnClickListener(new View.OnClickListener(){
                                    public void onClick(View v){
                                        Intent intentBoro=new Intent(getApplicationContext(),PataiPhushkuri.class);
                                        startActivity(intentBoro);
                                    }
                                }
        );
        wheatBlast= (Button)findViewById(R.id.fofuButton);
        wheatBlast.setOnClickListener(new View.OnClickListener(){
                                            public void onClick(View v){
                                                Intent intentBoro=new Intent(getApplicationContext(),FosholPhuskuri.class);
                                                startActivity(intentBoro);
                                            }
                                        }
        );

        pataJholshano= (Button)findViewById(R.id.pataHoludButton);
        pataJholshano.setOnClickListener(new View.OnClickListener(){
                                            public void onClick(View v){
                                                Intent intentBoro=new Intent(getApplicationContext(),PataHoludWheat.class);
                                                startActivity(intentBoro);
                                            }
                                        }
        );

        moricha= (Button)findViewById(R.id.pataRootButton);
        moricha.setOnClickListener(new View.OnClickListener(){
                                         public void onClick(View v){
                                             Intent intentBoro=new Intent(getApplicationContext(),PataRoot.class);
                                             startActivity(intentBoro);
                                         }
                                     }
        );

        jabPoka= (Button)findViewById(R.id.scabButton);
        jabPoka.setOnClickListener(new View.OnClickListener(){
                                            public void onClick(View v){
                                                Intent intentBoro=new Intent(getApplicationContext(),Scab.class);
                                                startActivity(intentBoro);
                                            }
                                        }
        );
    }
}
