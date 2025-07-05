package model;

public class Book {
    private int id;
    private String title;
    private String author;
    private double price;
    private int quantity;         // ✅ Add this
    private String category;      // ✅ Add this

    // Constructor for INSERT (without id)
    public Book(String title, String author, double price, int quantity, String category) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    // Constructor for UPDATE (with id)
    public Book(int id, String title, String author, double price, int quantity, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    // ✅ Add Getters & Setters for all new fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
