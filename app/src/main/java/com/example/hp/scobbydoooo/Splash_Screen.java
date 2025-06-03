package com.example.hp.scobbydoooo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.splash_theme);
        try{
                Thread.sleep(200);
        }
        catch (Exception e){
            Log.e("Error","opening",e);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        Intent myIntent = new Intent(Splash_Screen.this,DeviceList.class);
        startActivity(myIntent);
        finish();
    }
}
