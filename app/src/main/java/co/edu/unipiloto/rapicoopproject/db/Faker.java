package co.edu.unipiloto.rapicoopproject.db;

public class Faker {
    private static final String[] kitchenValues = {
            "Plaza Industrial,Calle 132-45C,Castilla",
            "Villa Mendoza,Carrera 30,Castilla",
            "Plazoleta Verde,Carrera 71A-10B,Chapinero",
            "Centro Insular,CL 32 # 6B - 43,Usaquen","C.C. Abelidez,AK 7 # 74 - 26,Usaquen"
    };

    private static final String[] usersValues = {
            "Buzz Lightyear,buzz@gmail.com,123434343,Cra 1 #18a-12,16-03-1999,hola,Hombre,Vendedor", //1
            "Skywalker,skywalker@gmail.com,123434343,Cra 1 #18a-12,16-03-1999,hola,Hombre,Vendedor", //2
            "Spiderman,spiderman@gmail.com,123434343,Cra 1 #18a-12,16-03-1999,hola,Hombre, Cliente", //3
            "Juan,juan@gmail.com,123434343,Cra 1 #18a-12,16-03-1999,hola,Hombre,Vendedor", //4
            "Mateo,mateo@gmail.com,123434343,Cra 1 #18a-12,16-03-1999,hola,Hombre,Vendedor",//5
            "Pablo,pablo@gmail.com,123434343,Cra 1 #18a-12,16-03-1999,hola,Hombre,Domiciliario" //6
    };

    private static final String[] menuDishesValues = {
            "Sopa de tomate,Tiene tomates y viene acompañada de nachos y quesadillas,Sopa,1,1000",
            "Perros calientes,Tiene salchicha de cerdo y viene acompañada de nachos y salsas,Comida rapida,4,40000",
            "Ajiaco Santafereño,Tiene pollo y viene acompañada de arroz y aguacate,Sopa,4,69900",
            "La Hamburguesota,Hamburguesa con todos los juguetes,Comida rapida,1,123432"
    };

    private static final String[] restaurantValues = {
            "La hamburgueseria,Comida Rapida,-2",
            "Andres Carne de Res,Grill,-3",
            "El Carnal,Mexicana,-4",
            "Ceviche,Comida de mar,-5"
    };

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
}
