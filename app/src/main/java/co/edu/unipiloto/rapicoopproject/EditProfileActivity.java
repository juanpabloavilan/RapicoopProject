package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import co.edu.unipiloto.rapicoopproject.applicationcontext.UserLoggedContext;

import co.edu.unipiloto.rapicoopproject.entities.UserFacade;
import co.edu.unipiloto.rapicoopproject.lib.User;

public class EditProfileActivity extends AppCompatActivity {
    User userLogged;
    TextInputEditText editTxtNombreCompleto;
    TextInputEditText editTxtCorreo;
    TextInputEditText editTxtTelefono;
    TextInputEditText editTxtPassword;
    Button sendButton;
    UserFacade userFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);



        //Obteniendo info del usuario loggeado a tráves del Context
        userLogged = UserLoggedContext.getInstance().getUser();

        //Asignar los componenentes del layout a las variables;
        editTxtNombreCompleto = findViewById(R.id.input_nombre_completo);
        editTxtCorreo = findViewById(R.id.input_correo);
        editTxtTelefono = findViewById(R.id.input_telefono);
        editTxtPassword = findViewById(R.id.input_password);
        sendButton = findViewById(R.id.send_btn);

        //Asignar valores del usuario loggeado.
        editTxtNombreCompleto.setText(userLogged.getFullName());
        editTxtCorreo.setText(userLogged.getEmail());
        editTxtTelefono.setText(userLogged.getPhone());
        editTxtPassword.setText(userLogged.getPassword());

        //Añadiendo click event al boton de enviar.
        sendButton.setOnClickListener(this::onClickSendEditProfileForm);

        //Asignar el userFacade
        userFacade = UserFacade.getInstance(EditProfileActivity.this);


    }

    public void onClickSendEditProfileForm (View view) {
        //Obteniendo información de los campos.
        String nombreCompleto = editTxtNombreCompleto.getText().toString();
        String telefono = editTxtTelefono.getText().toString();
        String email = editTxtCorreo.getText().toString();
        String password = editTxtPassword.getText().toString();

        //Crear nuevo usuario a partir de la nueva información.
        User newInfo = new User(userLogged.getId(),nombreCompleto, email, telefono, userLogged.getAddress(), userLogged.getBirthdate(), password, userLogged.getGender(), userLogged.getType());

        User updatedUser = userFacade.updateUser(newInfo);
        if(updatedUser != null){
            UserLoggedContext.getInstance().setUser(updatedUser);
        }

        Toast.makeText(this, "Info editada correctamente", Toast.LENGTH_SHORT).show();
    }
}