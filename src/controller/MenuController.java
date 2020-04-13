package controller;

import entity.Menu;
import entity.MenuItem;
import tool.SerializeDB;

import java.io.*;
import java.util.LinkedList;

public class MenuController {
    private static final String dir = "src/data/menu.dat";
    private Menu menu;
    private static MenuController MenuController = null;

    public static MenuController getInstance() {
        if (MenuController == null) {
            MenuController = new MenuController();
        }
        return MenuController;
    }

    public MenuController() {
        File file = new File(dir);
        // check if file exists; exist then read, otherwise initialize a new one
        if (file.exists()) {
            menu = (Menu) SerializeDB.readSerializedObject(dir);
        } else {
            file.getParentFile().mkdir();
            menu = new Menu(new LinkedList<>());
            SerializeDB.writeSerializedObject(dir, menu);
        }
    }

    public MenuItem getMenuItem(String name) {return menu.getItem(name, true);}

    public MenuItem searchMenuItem(String name) {
        return menu.getItem(name, false);
    }

    public void createMenuItem(String name, String description, double price) throws Exception {
        if (searchMenuItem(name) == null) {
            MenuItem item = new MenuItem(name, description, price);
            menu.addItem(item);
            SerializeDB.writeSerializedObject(dir, menu);
        } else {
            throw new Exception("Duplicate menu item names!");
        }
    }

    public void removeMenuItem(String name) throws Exception {
        MenuItem target;
        if ((target = searchMenuItem(name)) != null) {
            if (menu.removeItem(target)) {
                SerializeDB.writeSerializedObject(dir, menu);
            }
        } else {
            throw new Exception("Item not exists!");
        }
    }

    public void updateMenuItem(String oldName, String newName, String newDescription, double newPrice) throws Exception {
        MenuItem target;
        if ((target = searchMenuItem(oldName)) != null) {
            // itemName check
            if (searchMenuItem(newName) != null) {
                throw new Exception("ITEM_NAME_EXISTS");
            }
            target.setName(newName);
            target.setDescription(newDescription);
            target.setPrice(newPrice);
            SerializeDB.writeSerializedObject(dir, menu);
        }
    }

    public void displayMenu() {
        System.out.println(menu);
    }
}
