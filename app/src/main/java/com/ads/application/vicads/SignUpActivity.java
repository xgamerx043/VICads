package com.ads.application.vicads;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.util.Calendar;
import java.util.regex.Pattern;

public class SignUpActivity extends Activity {

    //Objetos declarados
    ImageView imageV1;
    TextView textV1_1;
    EditText textV8_2, text9_2, text11_2, text12_2, text10_2, text13_2;
    Button Button_2;
   TextInputLayout textF1, textF2, textF3, textF4, textF5, textF7, textE8;
   TextInputEditText textE1, textE2, textE3, textE4, textE5, textE7, textF8;
   AutoCompleteTextView Etapas_list;
   DatePickerDialog.OnDateSetListener setListener;


    //Metodo de Firebase para la autenticacion de usuarios
    private FirebaseAuth mAuth;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        imageV1 = findViewById(R.id.imageV1);
        textV1_1 = findViewById(R.id.textV1_1);
        Button_2 = findViewById(R.id.Button_2);
        textV8_2 = findViewById(R.id.textV8_2);
        text9_2 = findViewById(R.id.text9_2);
        text11_2 = findViewById(R.id.text11_2);
        text12_2 = findViewById(R.id.text12_2);
        text10_2 = findViewById(R.id.text10_2);
        text13_2 = findViewById(R.id.text13_2);
        textF1 = findViewById(R.id.textF1);
        textF2 = findViewById(R.id.textF2);
        textF3 = findViewById(R.id.textF3);
        textF4 = findViewById(R.id.textF4);
        textF5 = findViewById(R.id.textF5);
        textF7 = findViewById(R.id.textF7);
        textF8 = findViewById(R.id.textF8);
        textE1 = findViewById(R.id.textE1);
        textE2 = findViewById(R.id.textE2);
        textE3 = findViewById(R.id.textE3);
        textE4 = findViewById(R.id.textE4);
        textE5 = findViewById(R.id.textE5);
        textE7 = findViewById(R.id.textE7);
        Etapas_list = findViewById(R.id.Etapas_list);


        String nombre = textE5.getText().toString().trim();
        String numero = textE4.getText().toString().trim();
        String apellido = textE7.getText().toString().trim();
        //String etapa = text9_2.getText().toString().trim();
        //String nomPapa = text11_2.getText().toString().trim();
        //String numPapa = text12_2.getText().toString().trim();
        //String nomMama = text10_2.getText().toString().trim();
        //String numMama = text13_2.getText().toString().trim();


        String [] etapas = new String[]{
                "Amigo",
                "Servidor",
                "Solidario",
                "Animador"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (SignUpActivity.this,
                        R.layout.dropdown_item,
                        etapas
                );

        Etapas_list.setAdapter(adapter);

        Button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        }


    public void Abrircalendario(View view){
            //Calendario de nacimiento
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
        String fecha = day+ "/"+ month+ "/"+year;
        textF8.setText(fecha);
            }
        }, year, month, day);
        datePickerDialog.show();
    }
    //Declaracion para la funcion de los espacios en blanco de correo, contraseña, etc...
    public void validate() {
        String email = textE1.getText().toString().trim();
        String contraseña = textE2.getText().toString().trim();
        String confirmarContraseña = textE3.getText().toString().trim();

        //Que el correo sea valido
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textE1.setError("Correo invalido");
            return;
        } else {
            textE1.setError(null);
        }

        //Que la contraseña se valide
        if (contraseña.isEmpty() || contraseña.length() < 8) {
            textE2.setError("Se necesitan mas de 8 caracteres");
            return;
        } else if (!Pattern.compile("[0-9]").matcher(contraseña).find()) {
            textE2.setError("Al menos un número");
            return;
        } else {
            textE2.setError(null);
        }

        //Que las dos contraseñas sean iguales
        if (!confirmarContraseña.equals(contraseña)) {
            textE3.setError("No coinciden");
            return;
        } else {
            registrar(email, contraseña);
        }
    }

    //Metodo para registrar usuarios
    public void registrar(String correo, String contraseña) {
        mAuth.createUserWithEmailAndPassword(correo, contraseña)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(SignUpActivity.this, PrincipalActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Fallo el registro, intente de nuevo",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    //Validar boton de regreso de la barra del telefono
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
