package models;

public class TransactionModel {
    private int id;
    private String productName;
    private String productDescription;
    private int productPrice;
    private int productQuantity;
    private String productImage;
    private String productBuyer;
    private String productSeller;

    public TransactionModel(String productName, String productDescription, int productPrice, int productQuantity,
                            String productImage, String productBuyer, String productSeller) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productImage = productImage;
        this.productBuyer = productBuyer;
        this.productSeller = productSeller;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getProductBuyer() {
        return productBuyer;
    }

    public String getProductSeller() {
        return productSeller;
    }
}
