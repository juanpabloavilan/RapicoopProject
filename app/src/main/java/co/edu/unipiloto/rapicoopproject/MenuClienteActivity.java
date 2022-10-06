package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import co.edu.unipiloto.rapicoopproject.applicationcontext.UserLoggedContext;
import co.edu.unipiloto.rapicoopproject.lib.User;

public class MenuClienteActivity extends AppCompatActivity {

    public static final String LOGGED_USER = "USER_PAYLOAD_KEY";
    private TextView greeting;
    private User userLogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cliente);
        greeting = (TextView) findViewById(R.id.greetingMessage);
        userLogged = UserLoggedContext.getInstance().getUser();
        greeting.setText(greeting.getText() + userLogged.getFullName());
    }
}