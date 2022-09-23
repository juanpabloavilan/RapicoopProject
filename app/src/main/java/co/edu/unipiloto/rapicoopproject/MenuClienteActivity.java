package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MenuClienteActivity extends AppCompatActivity {

    public static final String LOGGED_USER = "USER_PAYLOAD_KEY";
    private TextView greeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cliente);
        greeting = (TextView) findViewById(R.id.greetingMessage);
        User client = (User) getIntent().getSerializableExtra(LOGGED_USER);
        greeting.setText(greeting.getText() + client.getFullName());
    }
}