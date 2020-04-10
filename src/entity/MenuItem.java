package entity;

import java.io.Serializable;

public class MenuItem implements Serializable {
    private static final long serialVersionUID = -7688074772896089983L;
    private String name;
    private String description;
    private double price;

    public MenuItem(){}

    public MenuItem(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) return;
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null) return;
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) return;
        this.price = price;
    }

    public MenuItem clone() {
        return new MenuItem(this.name, this.description, this.price);
    }
}
