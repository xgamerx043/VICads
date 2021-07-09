package com.ads.application.vicads;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity {

    //Declaracion de Elementos
    ProgressBar progressB1;
    ProgressBar progressB2;
    private TimerTask Tarea;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Relacion de los elementos
        progressB1 = findViewById(R.id.progressB1);
        progressB2 = findViewById(R.id.progressB2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(SplashActivity.this, PrincipalActivity.class);
                startActivity(intent);
                finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
            },
                5000);
    }
}