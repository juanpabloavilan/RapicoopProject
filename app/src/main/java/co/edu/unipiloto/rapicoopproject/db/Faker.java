package co.edu.unipiloto.rapicoopproject.db;

import java.util.HashMap;
import java.util.Map;

import co.edu.unipiloto.rapicoopproject.enums.OrderStatus;

public class Faker {
    private static final String idVendedor = "1";
    private static final String idDomiciliario = "2";
    private static final String idCliente = "3";

    private static final String[] kitchenValues = {
            "1,Plaza Industrial,Calle 132-45C,Castilla",
            "2,Villa Mendoza,Carrera 30,Castilla",
            "3,Plazoleta Verde,Carrera 71A-10B,Chapinero",
            "4,Centro Insular,CL 32 # 6B - 43,Usaquen",
            "5,C.C. Abelidez,AK 7 # 74 - 26,Usaquen"
    };

    private static final String[] usersValues = {
            idVendedor + ",Juan,vendedor@gmail.com,123434343,Cra 1 #18a-12,16-03-1999,venta,Hombre,Vendedor",
            idDomiciliario +",Sofia,domiciliario@gmail.com,123434343,Cra 72A #11B-72 Bogotá Colombia,16-03-1999,domicilio,Hombre,Domiciliario",
            idCliente +",Mateo,cliente@gmail.com,123434343,Cra 1 #18a-12 Bogotá Colombia,16-03-1999,compra,Hombre,Cliente",
    };

    private static final String[] menuDishesValues = {
            "1,Sopa de tomate,Tiene tomates y viene acompañada de nachos y quesadillas,Sopa,1,1000",
            "2,Perros calientes,Tiene salchicha de cerdo y viene acompañada de nachos y salsas,Comida rapida,1,40000",
            "3,Ajiaco Santafereño,Tiene pollo y viene acompañada de arroz y aguacate,Sopa,1,69900",
            "4,La Hamburguesota,Hamburguesa con todos los juguetes,Comida rapida,1,123432"
    };

    private static final String[] restaurantValues = {
            "1,La hamburgueseria,Comida Rapida,1",
    };

    private static final String[] itemOrderValues = {
            "1,1,1",  //Sopa de tomate en orden 1
            "2,3,1",  //Ajiaco en orden 1
            "3,4,1"  //Hamburguesa en orden 1
    };

    private static final Map<String,String> orderValues;

    private static final Map<String,String> deliveryValues;

    static {
        //ORDER TEST VALUES
        orderValues = new HashMap<>();
        orderValues.put("ID","1");
        orderValues.put("RESTAURANT_ID","1");
        orderValues.put("CLIENT_ID",idCliente);
        orderValues.put("TOTAL","234500");
        orderValues.put("DATE","0");
        orderValues.put("STATUS", OrderStatus.INICIADA.toString());
        //DELIVERY TEST VALUES
        deliveryValues = new HashMap<>();
        deliveryValues.put("ID","1");
        deliveryValues.put("ORDER_ID","1");
        deliveryValues.put("DELIVERY_ID", idDomiciliario);
        deliveryValues.put("SOURCE", "4.732963, -74.067114");
        deliveryValues.put("DESTINATION","4.746336, -74.043067");
        deliveryValues.put("ENDED","0");
    }

    public static String[] getKitchenValues() {
        return kitchenValues;
    }

    public static String[] getUsersValues() {
        return usersValues;
    }

    public static String[] getMenuDishesValues() {
        return menuDishesValues;
    }

    public static String[] getRestaurantValues() { return restaurantValues; }

    public static Map<String, String> getOrderValues() { return orderValues; }

    public static String[] getItemOrderValues() { return itemOrderValues; }

    public static Map<String,String> getDeliveryValues() { return deliveryValues; }

}

