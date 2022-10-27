package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        restaurantBtn = findViewById(R.id.restaurant_btn);
        restaurantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuClienteActivity.this,RestaurantsActivity.class);
                startActivity(intent);
            }
        });
        //actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_create_order:
                //Boton crear orden:
                Intent intent = new Intent(this, OrderFoodActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}