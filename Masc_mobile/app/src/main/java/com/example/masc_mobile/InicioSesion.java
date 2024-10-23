package com.example.masc_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InicioSesion extends AppCompatActivity {

    EditText Correo, Password;
    Button Acceder;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);


        ActionBar actionBar = getSupportActionBar(); //Creamos el ACTIONBAR
        assert actionBar != null; // Afirmamos que el ACTIONBAR no sea nulo
        actionBar.setTitle("Inicio Sesion"); // Le asignamos un titulo
        actionBar.setDisplayHomeAsUpEnabled(true);// Habilitamos el boton de retroceso
        actionBar.setDisplayShowCustomEnabled(true);

        Correo = findViewById(R.id. Correo);
        Password = findViewById(R.id.Password);
        Acceder=findViewById(R.id.Acceder);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(InicioSesion.this);
        progressDialog.setMessage("ingresando espere por favor");
        progressDialog.setCancelable(false);

        Acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //convertimos a string EDIT TEXT Correo y contraseña
                String correo = Correo.getText().toString();
                String pass = Password.getText().toString();

                
                    //VALIDACION DEL CORREO
                    if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                        Correo.setError("Correo invalido");
                        Correo.setFocusable(true);
                    }
                    else if (pass.length()<6){
                        Password.setError("Cpntraseña debe ser mayo r oigual a 6");
                        Password.setFocusable(true);
                    }else {
                        LogueoAdministradores(correo,pass);
                    }


            }
        });
    }

    private void LogueoAdministradores(String correo, String pass) {
        progressDialog.show();
        progressDialog.setCancelable(false);
        firebaseAuth.signInWithEmailAndPassword(correo, pass)
                .addOnCompleteListener(InicioSesion.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //si el inicio de sesion fue exitoso
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            startActivity(new Intent(InicioSesion.this, MainActivityAdministrador.class));

                            Toast.makeText(InicioSesion.this, "Bienvenido(a)"+ user.getEmail(), Toast.LENGTH_SHORT).show();
                            finish();

                        }else{
                            progressDialog.dismiss();
                            UsuarioInvalido();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        UsuarioInvalido();
                    }
                });

    }

    private void UsuarioInvalido() {
        AlertDialog.Builder builder = new AlertDialog.Builder(InicioSesion.this);
        builder.setCancelable(false);
        builder.setTitle("!HA OCURRIDO UN ERROR¡");
        builder.setMessage("Verifique si el correo o contraseñaa sean los correctos")
                .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
