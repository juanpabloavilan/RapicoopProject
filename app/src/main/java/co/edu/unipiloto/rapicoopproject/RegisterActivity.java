package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

import co.edu.unipiloto.rapicoopproject.db.RapicoopDataBaseHelper;

public class RegisterActivity extends AppCompatActivity {

    private RapicoopDataBaseHelper appDb;
    private EditText editFullName, editPhone, editEmail, editPassword;
    private RadioGroup rGroupGenders, rGroupType;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        appDb = RapicoopDataBaseHelper.getInstance(RegisterActivity.this);
        editFullName= (EditText) findViewById(R.id.fullname);
        editEmail= (EditText) findViewById(R.id.email);
        editPhone= (EditText) findViewById(R.id.phone_number);
        editPassword= (EditText) findViewById(R.id.password);
        rGroupType = (RadioGroup) findViewById(R.id.type);
        rGroupGenders= (RadioGroup) findViewById(R.id.genders);
        registerBtn= (Button) findViewById(R.id.register_btn);

        listenForNewUser();
    }

    private void listenForNewUser(){
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText[] registrationEditTexts = new EditText[]{editFullName, editEmail, editPhone, editPassword};
                ArrayList<String> registrationData = getDataStrings(registrationEditTexts);
                int[] radioGroupIds = new int[]{rGroupGenders.getCheckedRadioButtonId(),rGroupType.getCheckedRadioButtonId()};
                if(unvalidRadioGroupIds(radioGroupIds) || unvalidFields(registrationData)){
                    Toast.makeText(RegisterActivity.this,"Invalid data fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                String gender = ((RadioButton)findViewById(radioGroupIds[0])).getText().toString();
                String type = ((RadioButton)findViewById(radioGroupIds[1])).getText().toString();
                User new_user = new User(editFullName.getText().toString(), editEmail.getText().toString(), editPhone.getText().toString(), editPassword.getText().toString(), gender, type);
                appDb.insertUser(new_user);
                Toast.makeText(RegisterActivity.this,"User created successfully",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private boolean unvalidRadioGroupIds(int[] ids){
        for (int id:ids) {
            if(id == -1)
                return true;
        }
        return false;
    }

    private ArrayList<String> getDataStrings(EditText[] dataFields){
        ArrayList<String> dataStrings = new ArrayList<String>();
        for (EditText field:dataFields) {
            dataStrings.add(field.getText().toString());
        }
        return dataStrings;
    }

    private boolean unvalidFields(ArrayList<String> dataStrings){
        for (String data:dataStrings) {
            if(data.equals("")) {  //non-filled field
                return true;
            }
        }
        return false;
    }

    public void onClickGoBack(View view) {
        finish();
    }

}