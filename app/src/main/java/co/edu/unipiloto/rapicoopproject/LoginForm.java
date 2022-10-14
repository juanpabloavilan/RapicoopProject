package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import co.edu.unipiloto.rapicoopproject.applicationcontext.UserLoggedContext;
import co.edu.unipiloto.rapicoopproject.db.RapicoopDataBaseHelper;
import co.edu.unipiloto.rapicoopproject.lib.User;

public class LoginForm extends AppCompatActivity{
    private Button loginBtn;
    private TextView registerLink;
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
        registerLink = findViewById(R.id.register_link);
        loginBtn.setOnClickListener((view)->{
            emailTextView = findViewById(R.id.emaiInput);
            passwordTextView = findViewById(R.id.passwordInput);
            email = emailTextView.getText().toString();
            password = passwordTextView.getText().toString();
            User loginUser = validateUser(email, password);
            if(loginUser != null) {
                launchValidIntent(loginUser);
                clearTextEntries();
            }else{
                Log.i(TAG, "NOT USER FOUND");
                Toast.makeText(this, "Datos Invalidos", Toast.LENGTH_SHORT).show();
            }
        });
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginForm.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    private User validateUser(String email, String password){
        //Validar en la tabla usuarios si existe un usuario con mismo email y password
        RapicoopDataBaseHelper dbHelper = RapicoopDataBaseHelper.getInstance(LoginForm.this);
        User userFound = dbHelper.getUserByEmail(email);
        if (userFound == null || !password.equals(userFound.getPassword())){
            return null; //Si el login no es válido retornar null
        }
        Log.i(TAG, userFound.getFullName());
        return userFound;
    }

    private void launchValidIntent(User validUser){
        switch (validUser.getType()){
            case "Cliente":
                Intent clientIntent = new Intent(LoginForm.this, MenuClienteActivity.class);
                //clientIntent.putExtra(MenuClienteActivity.LOGGED_USER, (Serializable) validUser);
                UserLoggedContext.getInstance().setUser(validUser);
                startActivity(clientIntent);
                break;
            case "Vendedor":
                Intent vendorIntent = new Intent(LoginForm.this, MenuVendedorActivity.class);
                //vendorIntent.putExtra(USER_PAYLOAD_KEY, (Serializable) validUser);
                UserLoggedContext.getInstance().setUser(validUser);
                startActivity(vendorIntent);
                break;
            default:
                Toast.makeText(this, "Bad request", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearTextEntries(){
        emailTextView.setText("");
        passwordTextView.setText("");
    }
}