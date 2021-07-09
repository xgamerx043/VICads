package com.ads.application.vicads;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginActivity extends Activity {
    TextView textV2, textV3, textV6, textV7;
    ImageView LogoImageView;
    EditText textV4, textV5;
    Button Boton1;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LogoImageView = findViewById(R.id.LogoImageView);
        textV4 = findViewById(R.id.textV4);
        textV2 = findViewById(R.id.textV2);
        textV3 = findViewById(R.id.textV3);
        textV6 = findViewById(R.id.textV6);
        textV5 = findViewById(R.id.textV5);
        textV7 = findViewById(R.id.textV7);
        Boton1 = findViewById(R.id.Boton1);


        textV7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        textV6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });

        Boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        mAuth = FirebaseAuth.getInstance();

    }

    public void validate() {
        String email = textV4.getText().toString().trim();
        String contraseña = textV5.getText().toString().trim();

        //Que el correo sea valido
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textV4.setError("Correo invalido");
            return;
        } else {
            textV4.setError(null);
        }

        //Que la contraseña se valide
        if (contraseña.isEmpty() || contraseña.length() < 8) {
            textV5.setError("Se necesitan mas de 8 caracteres");
            return;
        } else if (!Pattern.compile("[0-9]").matcher(contraseña).find()) {
            textV5.setError("Al menos un número");
            return;
        } else {
            textV5.setError(null);
        }
        iniciarSesion(email, contraseña);
    }

    public void iniciarSesion(String correo, String contraseña) {
        mAuth.signInWithEmailAndPassword(correo, contraseña)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "No coinciden", Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    //Pulsacion de boton atras
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            //Dialogo para confirmar elm abandono de la aplicacion
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea salir de VICads?")
                    //Boton para salir de la aplicacion
                    .setPositiveButton("si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    })
                    //Boton para cancelar la salida
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void siguiente(View view) {
        Intent intent = new Intent(this, PrincipalActivity.class);
        startActivity(intent);
        finish();
    }

}