package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;

import co.edu.unipiloto.rapicoopproject.db.RapicoopDataBaseHelper;
import co.edu.unipiloto.rapicoopproject.lib.Kitchen;

public class MiCocinaActivity extends AppCompatActivity {
    private Spinner localityShowDown;
    private ListView kitchensView;
    private Button lookKitchens;
    private RapicoopDataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_cocina);
        db = RapicoopDataBaseHelper.getInstance(MiCocinaActivity.this);
        localityShowDown = (Spinner) findViewById(R.id.localities_list);
        populateSpinner();
        lookKitchens = (Button) findViewById(R.id.buscar_cocina_btn);
        kitchensView = (ListView) findViewById(R.id.kitchen_list);
        lookKitchens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                populateKitchensView();
            }
        });

        kitchensView.setOnItemClickListener((lista, viw, pos, id)->{
            Intent intent = new Intent(MiCocinaActivity.this, KitchenLeaseActivity.class);
            Kitchen selectedKitchen = (Kitchen) lista.getAdapter().getItem(pos);
            intent.putExtra(KitchenLeaseActivity.EXTRA_KITCHEN, selectedKitchen);
            startActivity(intent);
        });
    }

    private void populateSpinner(){
        final String[] KITCHEN_LOCALITIES = db.getAllKitchenLocalities();
        ArrayAdapter<String> localityAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_dropdown_item, KITCHEN_LOCALITIES);
        localityShowDown.setAdapter(localityAdapter);


    }

    private void populateKitchensView(){
        String desiredLocality = String.valueOf(localityShowDown.getSelectedItem());
        final Kitchen[] KITCHENS = db.getAllKitchensByLocality(desiredLocality);
        ArrayAdapter<Kitchen> kitchenAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,KITCHENS);
        kitchensView.setAdapter(kitchenAdapter);
    }
}