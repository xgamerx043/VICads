package com.ads.application.vicads;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends Activity {
    Button Button_3;
    EditText textV4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Button_3 = findViewById(R.id.Button_3);
        textV4 = findViewById(R.id.textV4);

        Button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    //Validar correo
    public void validate(){
        String correo = textV4.getText().toString().trim();
        if (correo.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            textV4.setError("Correo invalido");
            return;
        }
        sendEmail(correo);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ForgotPassword.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    public void sendEmail(String correo){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String correoElectronico = correo;
        auth.sendPasswordResetEmail(correoElectronico)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ForgotPassword.this, "Correo enviado",
                                    Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ForgotPassword.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(ForgotPassword.this, "Correo Invalido", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}