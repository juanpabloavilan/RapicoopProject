package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import co.edu.unipiloto.rapicoopproject.entities.UserFacade;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import co.edu.unipiloto.rapicoopproject.lib.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText textFullName, textPhone, textEmail, textPassword, textAddress, textBirthdate;
    private DatePickerDialog birthdatePicker;
    private Spinner userTypeSelector;
    private RadioGroup rGroupGenders, rGroupType;
    private Button registerBtn;
    private UserFacade userFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userFacade = UserFacade.getInstance(RegisterActivity.this);
        textFullName = (EditText) findViewById(R.id.name_et);
        textEmail = (EditText) findViewById(R.id.type_et);
        textPhone = (EditText) findViewById(R.id.phone_number);
        textPassword = (EditText) findViewById(R.id.password);
        textAddress = (EditText) findViewById(R.id.address);
        textBirthdate = (EditText) findViewById(R.id.birthdate);
        userTypeSelector = (Spinner) findViewById(R.id.user_type_spinner);
        rGroupGenders= (RadioGroup) findViewById(R.id.genders);
        registerBtn= (Button) findViewById(R.id.cancelar_btn);
        setBirthdateListener();
        initializeTypeSelector();
        listenForNewUser();
    }

    private void listenForNewUser(){
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText[] registrationEditTexts = new EditText[]{textFullName, textEmail, textPhone, textAddress, textBirthdate, textPassword};
                RadioGroup[] registrationRadioGroups = new RadioGroup[]{rGroupGenders};
                try {
                    if(!validateFields(registrationEditTexts, registrationRadioGroups)) return;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                textAddress.setText(coordsFromAddress(textAddress.getText().toString())); //format to lang,long string
                User inputUser = newUserFromFields(registrationEditTexts, registrationRadioGroups);
                System.out.println(inputUser);
                storeNewUser(inputUser);
                finish();
            }
        });
    }

    public void setBirthdateListener(){
        textBirthdate.setInputType(InputType.TYPE_NULL);
        textBirthdate.setOnClickListener(view -> {
            final Calendar date = Calendar.getInstance();
            int day = date.get(Calendar.DAY_OF_MONTH);
            int month = date.get(Calendar.MONTH);
            int year = date.get(Calendar.YEAR);
            birthdatePicker = new DatePickerDialog(this,
                    (view1, year1, monthOfYear, dayOfMonth) -> textBirthdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1), year, month, day);
            birthdatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
            birthdatePicker.show();
        });
    }

    private void initializeTypeSelector(){
        String[] userTypes = getResources().getStringArray(R.array.user_types);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, userTypes);
        userTypeSelector.setAdapter(typeAdapter);
    }

    private boolean validateFields(EditText[] registerEditTexts, RadioGroup[] registerRadioGroups) throws ParseException {
        String flag = ""; //none
        if(unvalidTextFields(registerEditTexts) || unvalidRadioGroupIds(registerRadioGroups) || userTypeSelector.getSelectedItemPosition() == 0){
            flag = "There are empty data fields";
        } else if(userFacade.getUserByEmail(textEmail.getText().toString()) != null){
            flag = "This email is already taken";
        } else if(unvalidAgeForCommerce()){
            flag = "Minors cannot be vendors or delivers";
        }
        if( !flag.equals("") ){
            Toast.makeText(RegisterActivity.this,flag,Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean unvalidAgeForCommerce() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", new Locale("es","CO"));
        Calendar now = Calendar.getInstance();
        Calendar birthdate  = Calendar.getInstance();
        String birthdateSt = textBirthdate.getText().toString();
        birthdate.setTime(Objects.requireNonNull(format.parse(birthdateSt)));
        String userType = userTypeSelector.getSelectedItem().toString();
        return now.get(Calendar.YEAR) - birthdate.get(Calendar.YEAR) < 18 && (userType.equals("Vendedor") || userType.equals("Repartidor"));
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

    private String coordsFromAddress(String strAddress){
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        String location = textAddress.getText().toString();
        try {
            address = coder.getFromLocationName(strAddress,1);
            if(!address.isEmpty()){
                Address inputLocation = address.get(0);
                location = ""+inputLocation.getLatitude()+","+inputLocation.getLongitude(); //coords
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return location;
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
        userData.add(userTypeSelector.getSelectedItem().toString());
        return new User(userData.get(0),userData.get(1),userData.get(2), userData.get(3), userData.get(4), userData.get(5), userData.get(6), userData.get(7));  //USER DATA IS ORDERED IN LIST AS IN USER PARAMS
    }

    private void storeNewUser(User newUser){
        String insertResultMsg = userFacade.insertUser(newUser) != -1 ? "User created successfully" : "Error when adding user to the database";
        Toast.makeText(RegisterActivity.this,insertResultMsg,Toast.LENGTH_SHORT).show();
    }

    public void onClickGoBack(View view) {
        finish();
    }

}