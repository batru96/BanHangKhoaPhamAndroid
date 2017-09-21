package models;

import org.json.JSONArray;

import java.io.Serializable;

public class Product implements Serializable{
    private int id;
    private String name;
    private int idType;
    private String nameType;
    private int price;
    private String color;
    private String material;
    private String description;
    private String[] images;

    public Product() {

    }

    public Product(int id, String name, int price, String color, String material, String[] images) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.color = color;
        this.material = material;
        this.images = images;
    }

    public Product(int id, String name, int price, String[] images) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.images = images;
    }

    public Product(int id, String name, int idType, String nameType, int price, String color, String material, String description, String[] images) {
        this.id = id;
        this.name = name;
        this.idType = idType;
        this.nameType = nameType;
        this.price = price;
        this.color = color;
        this.material = material;
        this.description = description;
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }
}
