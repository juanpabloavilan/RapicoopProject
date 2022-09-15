package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import co.edu.unipiloto.rapicoopproject.db.RapicoopDataBaseHelper;

public class LoginForm extends AppCompatActivity{
    private Button loginBtn;
    private String email;
    private String password;
    private TextView emailTextView;
    private TextView passwordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener((view)->{
            emailTextView = findViewById(R.id.emaiInput);
            passwordTextView = findViewById(R.id.passwordInput);
            email = emailTextView.getText().toString();
            password = passwordTextView.getText().toString();
            validateLogin(email, password);
        });
    }

    private void validateLogin(String email, String password){
        //Validar en la tabla usuarios si existe un usuario con mismo email y password
        RapicoopDataBaseHelper dbHelper = RapicoopDataBaseHelper.getInstance(LoginForm.this);
        dbHelper.getUserByEmail(email);
        //Si el login es válido redirigir a vista de menú
        //Si el login no es válido mostrar error en la interfaz
    }

}