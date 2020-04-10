package entity;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable {
    private static final long serialVersionUID = 4485137913238413815L;
    private List<MenuItem> menu;

    public Menu(List<MenuItem> menu) {
        this.menu = menu;
    }

    public void addItem(MenuItem item) {
        menu.add(item);
    }

    public MenuItem getItem(String name, boolean isClone) {
        for (MenuItem item: menu) {
            if (item.getName().equals(name)) {
                if (isClone) return item.clone();
                else return item;
            }
        }
        return null;
    }

    public boolean removeItem(MenuItem item) {
        return menu.remove(item);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\nMenu:\nName -> Description -> Price\n");
        for (MenuItem item: menu) {
            String s = item.getName() + " -> " + item.getDescription() + " -> " + "$" + String.valueOf(item.getPrice()) + "\n";
            sb.append(s);
        }
        return sb.toString();
    }
}
