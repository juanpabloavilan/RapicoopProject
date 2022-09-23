package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MenuVendedorActivity extends AppCompatActivity {
    TextView welcomeMessageTextView;
    Button miCocinaButton;
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
        userLogged = (User) intent.getSerializableExtra(LoginForm.USER_PAYLOAD_KEY);
        welcomeMessageTextView = findViewById(R.id.user_logged_welcome_message);

        //Actualizar View con los datos del usuario loggeado
        welcomeMessageTextView.setText("Hola " + userLogged.getFullName());


        miCocinaButton = findViewById(R.id.mi_cocina_btn);
        miMenuButton = findViewById(R.id.mi_menu_btn);
        misPedidosButton = findViewById(R.id.mis_pedidos_btn);
        editarMiPerfilButton = findViewById(R.id.editar_perfil_btn);

        //Añadiendo Click event Handlers
        miCocinaButton.setOnClickListener(this::onClickGoToMiCocina);
        miMenuButton.setOnClickListener(this::onClickGoToMiMenu);
        misPedidosButton.setOnClickListener(this::onClickGoToMisPedidos);
        editarMiPerfilButton.setOnClickListener(this::onClickGoToEditarPerfil);
    }

    public void onClickGoToEditarPerfil(View view) {
        Intent intent = new Intent(MenuVendedorActivity.this, EditProfileActivity.class);
        intent.putExtra(LoginForm.USER_PAYLOAD_KEY, userLogged);
        startActivity(intent);
    }

    public void onClickGoToMisPedidos(View view) {
        Toast.makeText(this, "IR a mis pedidos", Toast.LENGTH_SHORT).show();
    }

    public void onClickGoToMiMenu(View view) {
        Toast.makeText(this, "IR a mi menu", Toast.LENGTH_SHORT).show();
    }

    public void onClickGoToMiCocina(View view) {
        Toast.makeText(this, "IR a mi cocina", Toast.LENGTH_SHORT).show();
    }

}