package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import co.edu.unipiloto.rapicoopproject.db.RapicoopDataBaseHelper;

public class LoginForm extends AppCompatActivity{
    private Button loginBtn;
    private String email;
    private String password;
    private TextView emailTextView;
    private TextView passwordTextView;
    private final String TAG = "LOGIN_FORM";
    public static final String USER_PAYLOAD_KEY = "user";

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
            Intent intent = new Intent(this, MenuClienteActivity.class);
            startActivity(intent);
        });
    }

    private boolean validateLogin(String email, String password){
        //Validar en la tabla usuarios si existe un usuario con mismo email y password
        RapicoopDataBaseHelper dbHelper = RapicoopDataBaseHelper.getInstance(LoginForm.this);
        User userFound = dbHelper.getUserByEmail(email);
        if (userFound != null){
            Log.i(TAG, userFound.getFullName());
            if(password.equals(userFound.getPassword())){
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();
                return true;
                //Si el login es válido redirigir a vista de menú

                Intent intent;
                switch (userFound.getType()){
                    case "Cliente":
                        intent = new Intent(LoginForm.this, MenuClienteActivity.class);
                        intent.putExtra(USER_PAYLOAD_KEY, (Serializable) userFound);
                        startActivity(intent);
                        break;
                    case "Vendedor":
                        intent = new Intent(LoginForm.this, MenuVendedorActivity.class);
                        intent.putExtra(USER_PAYLOAD_KEY, (Serializable) userFound);
                        startActivity(intent);
                        break;
                    default:
                        Toast.makeText(this, "Bad request", Toast.LENGTH_SHORT).show();
                }
            }
        }
        //Si el login no es válido mostrar error en la interfaz
        Log.i(TAG, "NOT USER FOUND");
        Toast.makeText(this, "Datos Invalidos", Toast.LENGTH_SHORT).show();
        return false;
    }

    public void onClickRegister(View view) {
        Intent registerIntent = new Intent(this,RegisterActivity.class);
        startActivity(registerIntent);
    }

}