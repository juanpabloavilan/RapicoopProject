package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class EditProfileActivity extends AppCompatActivity {
    TextInputEditText nombreCompleto;
    TextInputEditText correo;
    TextInputEditText telefono;
    TextInputEditText password;
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent intent = getIntent();

        //Obteniendo info del usuario loggeado a tráves del intent
        User userLogged = (User) intent.getSerializableExtra(LoginForm.USER_PAYLOAD_KEY);

        //Asignar los componenentes del layout a las variables;
        nombreCompleto = findViewById(R.id.input_nombre_completo);
        correo = findViewById(R.id.input_correo);
        telefono = findViewById(R.id.input_telefono);
        password = findViewById(R.id.input_password);
        sendButton = findViewById(R.id.send_btn);

        //Asignar valores del usuario loggeado.
        nombreCompleto.setText(userLogged.getFullName());
        correo.setText(userLogged.getEmail());
        telefono.setText(userLogged.getPhone());
        password.setText(userLogged.getPassword());

        //Añadiendo click event al boton de enviar.
        sendButton.setOnClickListener(this::onClickSendEditProfileForm);


    }

    public void onClickSendEditProfileForm (View view) {
        Toast.makeText(this, "Info editada correctamente", Toast.LENGTH_SHORT).show();
    }
}