package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
                RadioGroup[] registrationRadioGroups = new RadioGroup[]{rGroupType, rGroupGenders};
                if(!validateFields(registrationEditTexts, registrationRadioGroups)) return;
                User inputUser = newUserFromFields(registrationEditTexts, registrationRadioGroups);
                storeNewUser(inputUser);
                finish();
            }
        });
    }

    private boolean validateFields(EditText[] registerEditTexts, RadioGroup[] registerRadioGroups){
        String flag = ""; //none
        if(unvalidTextFields(registerEditTexts) || unvalidRadioGroupIds(registerRadioGroups)){
            flag = "There are empty data fields";
        } else if(appDb.getUserByEmail(editEmail.getText().toString()) != null){
            flag = "This email is already taken";
        }
        if( !flag.equals("") ){
            Toast.makeText(RegisterActivity.this,flag,Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean unvalidRadioGroupIds(RadioGroup[] radioGroups){
        for (RadioGroup rGroup:radioGroups) {
            if(rGroup.getCheckedRadioButtonId() == -1) return true;  //-1 = NO CHECKED RADIO BUTTON
        }
        return false;
    }

    private boolean unvalidTextFields(EditText[] dataFields){
        for (EditText field:dataFields) {
            if(field.getText().toString().equals("")) return true;
        }
        return false;
    }

    private User newUserFromFields(EditText[] registerEditTexts, RadioGroup[] registerRadioGroups){
        ArrayList<String> userData = new ArrayList<>();
        for (EditText textField: registerEditTexts) {
            userData.add(textField.getText().toString());
        }
        for (RadioGroup groupField: registerRadioGroups){  //GET TEXT FROM RADIO BUTTON KNOWING RADIO GROUP CHECKED ID
            int checkedButtonInGroup = groupField.getCheckedRadioButtonId();
            userData.add( ( (RadioButton) findViewById(checkedButtonInGroup)).getText().toString() );
        }
        return new User(-1,userData.get(0),userData.get(1),userData.get(2), userData.get(3), userData.get(4), userData.get(5));  //USER DATA IS ORDERED IN LIST AS IN USER PARAMS
    }

    private void storeNewUser(User newUser){
        String insertResultMsg = appDb.insertUser(newUser) ? "User created successfully" : "Error when adding user to the database";
        Toast.makeText(RegisterActivity.this,insertResultMsg,Toast.LENGTH_SHORT).show();
    }

    public void onClickGoBack(View view) {
        finish();
    }

}