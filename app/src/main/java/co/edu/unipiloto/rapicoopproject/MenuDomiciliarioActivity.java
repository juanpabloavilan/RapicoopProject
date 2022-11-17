package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import co.edu.unipiloto.rapicoopproject.applicationcontext.UserLoggedContext;
import co.edu.unipiloto.rapicoopproject.entities.DeliveryFacade;
import co.edu.unipiloto.rapicoopproject.entities.OrderFacade;
import co.edu.unipiloto.rapicoopproject.lib.User;

public class MenuDomiciliarioActivity extends AppCompatActivity {
    TextView welcomeMessageTextView;
    Button misPedidosPendientesButton;
    Button misPedidosRealizadosButton;
    Button editarMiPerfilButton;
    Button miRutaActivaBtn;
    User userLogged;
    OrderFacade orderFacade;
    DeliveryFacade deliveryFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_domiciliario);

        userLogged = UserLoggedContext.getInstance().getUser();
        orderFacade = OrderFacade.getInstance(this);
        deliveryFacade = DeliveryFacade.getInstance(this);
        welcomeMessageTextView = findViewById(R.id.user_logged_welcome_message);

        //Actualizar View con los datos del usuario loggeado
        welcomeMessageTextView.setText(welcomeMessageTextView.getText().toString() + ", " + userLogged.getFullName());

        misPedidosPendientesButton = findViewById(R.id.mis_pedidos_pendientes_btn);
        misPedidosRealizadosButton = findViewById(R.id.mis_pedidos_entregados_btn);
        editarMiPerfilButton = findViewById(R.id.editar_perfil_btn);
        miRutaActivaBtn = findViewById(R.id.ver_ruta_btn);

        //Añadiendo Click event Handlers
        misPedidosPendientesButton.setOnClickListener(this::onClickGoToMisPedidosPendientes);
        misPedidosRealizadosButton.setOnClickListener(this::onClickGoToMisPedidosRealizados);
        editarMiPerfilButton.setOnClickListener(this::onClickGoToEditarPerfil);
        miRutaActivaBtn.setOnClickListener(this::onClickViewActiveRoute);
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

    public void onClickViewActiveRoute(View view) {
        double[] targetCoords = getOrderRoute();
        Uri.Builder builder= new Uri.Builder();
        builder.scheme("https").authority("www.google.com").
                appendPath("maps").
                appendPath("dir").
                appendPath("").
                appendQueryParameter("api", "1").
                appendQueryParameter("destination", targetCoords[0] + "," + targetCoords[1]).
                appendQueryParameter("travelmode","driving");
        String url= builder.build().toString();
        Intent intent= new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private double[] getOrderRoute(){
        int orderId = deliveryFacade.getOrderIdByDeliver(userLogged.getId());
        return orderFacade.getOrderTarget(orderId);
    }
}