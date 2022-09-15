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
        RapicoopDataBaseHelper dbHelper = new RapicoopDataBaseHelper(MainActivity.this);
        SQLiteDatabase DB = dbHelper.getWritableDatabase();
        if(DB != null){
            Toast.makeText(this, "BASE DE DATOS CREADA", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "ERROR AL CREAR BASE DE DATOS", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickRegister(View view) {
        Intent registerIntent = new Intent(this,RegisterActivity.class);
        startActivity(registerIntent);
    }
}