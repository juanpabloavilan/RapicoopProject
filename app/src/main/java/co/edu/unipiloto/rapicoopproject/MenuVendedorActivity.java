package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import co.edu.unipiloto.rapicoopproject.applicationcontext.UserLoggedContext;

import co.edu.unipiloto.rapicoopproject.lib.User;

public class MenuVendedorActivity extends AppCompatActivity {
    TextView welcomeMessageTextView;
    Button miRestauranteButton;
    Button miMenuButton;
    Button misPedidosButton;
    Button editarMiPerfilButton;
    Intent intent;
    User userLogged;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_vendedor);

        intent = getIntent();
        userLogged = UserLoggedContext.getInstance().getUser();
        welcomeMessageTextView = findViewById(R.id.user_logged_welcome_message);

        //Actualizar View con los datos del usuario loggeado
        welcomeMessageTextView.setText(welcomeMessageTextView.getText().toString() + ", " + userLogged.getFullName());


        miRestauranteButton = findViewById(R.id.mi_restaurante_btn);
        miMenuButton = findViewById(R.id.mi_menu_btn);
        misPedidosButton = findViewById(R.id.mis_pedidos_btn);
        editarMiPerfilButton = findViewById(R.id.editar_perfil_btn);

        //Añadiendo Click event Handlers
        miRestauranteButton.setOnClickListener(this::onClickGoToMiRestaurante);
        miMenuButton.setOnClickListener(this::onClickGoToMiMenu);
        misPedidosButton.setOnClickListener(this::onClickGoToMisPedidos);
        editarMiPerfilButton.setOnClickListener(this::onClickGoToEditarPerfil);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
    }

    public void onClickGoToEditarPerfil(View view) {
        Intent intent = new Intent(MenuVendedorActivity.this, EditProfileActivity.class);
        startActivity(intent);
    }

    public void onClickGoToMisPedidos(View view) {
        Toast.makeText(this, "IR a mis pedidos", Toast.LENGTH_SHORT).show();
    }

    public void onClickGoToMiMenu(View view) {
        Toast.makeText(this, "IR a mi menu", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MenuVendedorActivity.this, MiMenuActivity.class);
        startActivity(intent);
    }

    public void onClickGoToMiRestaurante(View view) {
        Intent intent = new Intent(MenuVendedorActivity.this, MiRestauranteActivity.class);
        startActivity(intent);
        //Toast.makeText(this, "IR a mi cocina", Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_vendedor, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_create_promotion:
                //Boton crear orden:
                Intent intent = new Intent(this, PromoActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}