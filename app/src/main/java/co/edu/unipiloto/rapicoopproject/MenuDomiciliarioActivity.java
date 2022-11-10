package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import co.edu.unipiloto.rapicoopproject.applicationcontext.UserLoggedContext;
import co.edu.unipiloto.rapicoopproject.lib.User;

public class MenuDomiciliarioActivity extends AppCompatActivity {
    TextView welcomeMessageTextView;
    Button misPedidosPendientesButton;
    Button misPedidosRealizadosButton;
    Button editarMiPerfilButton;
    User userLogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_domiciliario);

        userLogged = UserLoggedContext.getInstance().getUser();
        welcomeMessageTextView = findViewById(R.id.user_logged_welcome_message);

        //Actualizar View con los datos del usuario loggeado
        welcomeMessageTextView.setText(welcomeMessageTextView.getText().toString() + ", " + userLogged.getFullName());


        misPedidosPendientesButton = findViewById(R.id.mis_pedidos_pendientes_btn);
        misPedidosRealizadosButton = findViewById(R.id.mis_pedidos_entregados_btn);
        editarMiPerfilButton = findViewById(R.id.editar_perfil_btn);

        //Añadiendo Click event Handlers
        misPedidosPendientesButton.setOnClickListener(this::onClickGoToMisPedidosPendientes);
        misPedidosRealizadosButton.setOnClickListener(this::onClickGoToMisPedidosRealizados);
        editarMiPerfilButton.setOnClickListener(this::onClickGoToEditarPerfil);
    }

    private void onClickGoToEditarPerfil(View view) {
        Intent intent = new Intent(MenuDomiciliarioActivity.this, EditProfileActivity.class);
        startActivity(intent);
    }

    private void onClickGoToMisPedidosRealizados(View view) {

    }

    private void onClickGoToMisPedidosPendientes(View view){
        Intent intent = new Intent(MenuDomiciliarioActivity.this, DomiciliosPendientesActivity.class);
        startActivity(intent);
    }
}