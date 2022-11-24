package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.edu.unipiloto.rapicoopproject.applicationcontext.UserLoggedContext;
import co.edu.unipiloto.rapicoopproject.entities.MenuDishFacade;
import co.edu.unipiloto.rapicoopproject.entities.RestaurantFacade;
import co.edu.unipiloto.rapicoopproject.lib.MenuDish;
import co.edu.unipiloto.rapicoopproject.lib.User;

public class AddMenuDishActivity extends AppCompatActivity {
    private Button btnAddMenuDish;
    private EditText eTxtNombre;
    private EditText eTxtDescripcion;
    private EditText eTxtPrecio;
    private EditText eTxtFoodCategory;

    private User userLogged;
    private MenuDishFacade menuDishFacade;
    private RestaurantFacade restaurantFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu_dish);

        eTxtNombre = findViewById(R.id.etxt_nombre_plato_value);
        eTxtDescripcion = findViewById(R.id.etxt_descripcion_value);
        eTxtPrecio = findViewById(R.id.etxt_precio_value);
        eTxtFoodCategory = findViewById(R.id.etxt_food_category_value);
        btnAddMenuDish = findViewById(R.id.btn_add_menu_dish);

        userLogged = UserLoggedContext.getInstance().getUser();
        menuDishFacade = MenuDishFacade.getInstance(this);
        restaurantFacade = RestaurantFacade.getInstance(this);

        btnAddMenuDish.setOnClickListener((v)->{
            addMenuDish();
        });



    }

    private void addMenuDish() {
        String nombreDelPlato = eTxtNombre.getText().toString();
        String descripcion = eTxtDescripcion.getText().toString();
        Integer precio = Integer.parseInt(eTxtPrecio.getText().toString());
        String foodCategory = eTxtFoodCategory.getText().toString();
        Integer vendorID = restaurantFacade.getRestaurantIdByUserId(userLogged.getId());

        boolean areFieldsValid = !nombreDelPlato.equals("") &&
                !descripcion.equals("") &&
                precio > 0 &&
                !foodCategory.equals("");

        if(areFieldsValid){
            MenuDish menuDish = new MenuDish(descripcion, nombreDelPlato,precio, R.drawable.add_menu_icon);
            menuDish.setFoodCategory(foodCategory);
            menuDish.setRestaurantId(vendorID);
            long id = menuDishFacade.insertMenuDish(menuDish);
            menuDish.setId((int) id);
            Toast.makeText(this, "Plato agregado al menú correctamente con id "+ id, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Datos invalidos ingresados, por favor intente de nuevo", Toast.LENGTH_SHORT).show();
        }

    }
}