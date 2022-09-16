package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import co.edu.unipiloto.rapicoopproject.db.RapicoopDataBaseHelper;

public class LoginForm extends AppCompatActivity{
    private Button loginBtn;
    private String email;
    private String password;
    private TextView emailTextView;
    private TextView passwordTextView;
    private final String TAG = "LOGIN_FORM";

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
        User userFound = dbHelper.getUserByEmail(email);
        if (userFound != null){
            Log.i(TAG, userFound.getFullName());
            if(password.equals(userFound.getPassword())){
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();
                return;
                //Si el login es válido redirigir a vista de menú
            }
        }else {
            //Si el login no es válido mostrar error en la interfaz
            Log.i(TAG, "NOT USER FOUND");
        }
        Toast.makeText(this, "Datos Invalidos", Toast.LENGTH_SHORT).show();
    }

    public void onClickRegister(View view) {
        Intent registerIntent = new Intent(this,RegisterActivity.class);
        startActivity(registerIntent);
    }

}