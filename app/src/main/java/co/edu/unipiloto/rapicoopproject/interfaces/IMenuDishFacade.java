package co.edu.unipiloto.rapicoopproject.interfaces;


import java.util.List;

import co.edu.unipiloto.rapicoopproject.lib.MenuDish;

public interface IMenuDishFacade {
    long insertMenuDish(MenuDish menuDish);
    MenuDish updateMenuDish(MenuDish menuDish);
    List<MenuDish> getMenuDishesByVendorID(int id);
    MenuDish getMenuDishByID(int id);
}
