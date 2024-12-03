package models;

public class ProductModel {
    private int id;
    private String name;
    private String seller;
    private String description;
    private int price;
    private String image;

    public ProductModel(String name, String seller, String description, int price, String image) {
        this.name = name;
        this.seller = seller;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public String getSeller() {
        return seller;
    }

    public String getImage() {
        return image;
    }
}
