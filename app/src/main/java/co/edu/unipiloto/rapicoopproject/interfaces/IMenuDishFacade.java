package co.edu.unipiloto.rapicoopproject.interfaces;


import java.util.List;
import java.util.Set;

import co.edu.unipiloto.rapicoopproject.lib.MenuDish;

public interface IMenuDishFacade {
    long insertMenuDish(MenuDish menuDish);
    MenuDish updateMenuDish(MenuDish menuDish);
    List<MenuDish> getMenuDishesByVendorID(int id);
    MenuDish getMenuDishByID(int id);
    List<MenuDish> getMenuDishesByListOfIDs(Set<Integer> listaIds);
}
