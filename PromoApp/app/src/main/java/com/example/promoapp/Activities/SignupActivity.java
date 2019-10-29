package com.example.promoapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.promoapp.Models.Usuario;
import com.example.promoapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("usuarios");

    private Button btnSignReg;
    private EditText etxtEmailSign, etxtPassSign, etxtPassSign2, etxtNomSign, etxtApeSign, etxtPhoneSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignReg = findViewById(R.id.btnSignReg);
        etxtPassSign = findViewById(R.id.etxtPassSign);
        etxtPassSign2 = findViewById(R.id.etxtPassSign2);
        etxtEmailSign = findViewById(R.id.etxtEmailSign);
        etxtNomSign = findViewById(R.id.etxtNomSign);
        etxtApeSign = findViewById(R.id.etxtApeSign);
        etxtPhoneSign = findViewById(R.id.etxtPhoneSign);

        btnSignReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etxtEmailSign.getText().toString();
                String pass = etxtPassSign.getText().toString();
                String pass2 = etxtPassSign2.getText().toString();
                String nom = etxtNomSign.getText().toString();
                String ape = etxtApeSign.getText().toString();
                String phone = etxtPhoneSign.getText().toString();

                if (email.isEmpty() || pass.isEmpty() || pass2.isEmpty() || nom.isEmpty() || ape.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Complete los campos", Toast.LENGTH_SHORT).show();
                } else {
                    if (!pass.equals(pass2)) {
                        Toast.makeText(getApplicationContext(), "Contraseña no coincide", Toast.LENGTH_SHORT).show();
                    } else {
                        createUser(email, pass);
                    }
                }
            }
        });
    }


    private void createUser(final String email, final String pass) {

        final String email_2 = etxtEmailSign.getText().toString();
        final String pass_2 = etxtPassSign.getText().toString();
        final String nom = etxtNomSign.getText().toString();
        final String ape = etxtApeSign.getText().toString();
        final String phone = etxtPhoneSign.getText().toString();

        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Cuenta registrada", Toast.LENGTH_SHORT).show();
                            createUserDB(nom, ape, email_2, pass_2, phone);
                            finish();
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(SignupActivity.this, "Este correo ya esta registrado", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignupActivity.this, "Ups! Algo falló", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void createUserDB(String nom, String ape, String email, String pass, String phone) {
        Usuario user = new Usuario(nom, ape, email, pass, phone);
        myRef.push().setValue(user);
    }
}
