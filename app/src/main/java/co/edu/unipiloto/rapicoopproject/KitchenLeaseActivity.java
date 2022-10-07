package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import co.edu.unipiloto.rapicoopproject.applicationcontext.UserLoggedContext;
import co.edu.unipiloto.rapicoopproject.lib.Kitchen;
import co.edu.unipiloto.rapicoopproject.lib.LeaseManager;
import co.edu.unipiloto.rapicoopproject.lib.User;

public class KitchenLeaseActivity extends AppCompatActivity {
    public static String EXTRA_KITCHEN = "selected_kitchen";
    private Kitchen selectedKitchen;
    private LeaseManager leaseManager;
    private User usuarioLoggeado;

    //Ui Información de la cocina seleccionada
    private TextView txtViewCurrentKitchenName;
    private TextView txtViewCurrentKitchenAddress;
    private TextView txtViewCurrentKitchenLocality;


    private DatePickerDialog picker;
    private EditText eTextFechaInicio;
    private EditText eTextFechaFinal;
    private Button btnAlquilarCocina;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_lease);
        selectedKitchen = (Kitchen) getIntent().getExtras().get(EXTRA_KITCHEN);
        System.out.println(selectedKitchen);

        leaseManager = new LeaseManager(this);
        usuarioLoggeado = UserLoggedContext.getInstance().getUser();

        //Añadiendo información de la cocina seleccionada a la ui
        txtViewCurrentKitchenName = findViewById(R.id.cocina_actual_name_value);
        txtViewCurrentKitchenName.setText(selectedKitchen.getName());
        txtViewCurrentKitchenAddress = findViewById(R.id.cocina_actual_direccion_value);
        txtViewCurrentKitchenAddress.setText(selectedKitchen.getAddress());
        txtViewCurrentKitchenLocality = findViewById(R.id.cocina_actual_localidad_value);
        txtViewCurrentKitchenLocality.setText(selectedKitchen.getLocality());






        eTextFechaInicio=(EditText) findViewById(R.id.edittext_fecha_inicio);
        eTextFechaInicio.setInputType(InputType.TYPE_NULL);
        eTextFechaInicio.setOnClickListener( v ->{
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(KitchenLeaseActivity.this,
                        (view, ano, mes, dia)-> {
                                eTextFechaInicio.setText(dia + "/" + (mes + 1) + "/" + ano);
                        }, year, month, day);
                String date =  "" + year + " / " + month + " / " + day;

                Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
                picker.show();
        });

        eTextFechaFinal=(EditText) findViewById(R.id.edittext_fecha_final);
        eTextFechaFinal.setInputType(InputType.TYPE_NULL);
        eTextFechaFinal.setOnClickListener( v ->{
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            // date picker dialog
            picker = new DatePickerDialog(KitchenLeaseActivity.this,
                    (view, ano, mes, dia)-> {
                        eTextFechaFinal.setText(dia + "/" + (mes + 1) + "/" + ano);
                    }, year, month, day);
            String date =  "" + year + " / " + month + " / " + day;

            //Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
            picker.show();
        });






    }

    private void leaseKitchen(){
         //leaseManager.newLease(usuarioLoggeado.getId(),selectedKitchen.getId(),  )

    }
}