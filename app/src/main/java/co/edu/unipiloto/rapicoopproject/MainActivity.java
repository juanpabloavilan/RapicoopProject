package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import co.edu.unipiloto.rapicoopproject.db.RapicoopDataBaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        RapicoopDataBaseHelper dbHelper = RapicoopDataBaseHelper.getInstance(MainActivity.this);
        if(dbHelper.getWritableDatabase() != null){
            Toast.makeText(this, "DATABASE CREADA", Toast.LENGTH_SHORT).show();
        }

    }


}