package com.mycompany.library;

public class Book {
    private final int id;
    private final String title;
    private final String author;
    private final int year;
    private final double price;
    private final String category;
    private int amount; 
    private int available;
    
    public Book(int id, String title, String author, int year, double price,
                String category, int amount, int available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.price = price;
        this.category = category;
        this.amount = amount;
        this.available = available;
    }
    
    public int getId() {return id;}
    public String getTitle() {return title;}
    public String getAuthor() {return author;}
    public int getYear() {return year;}
    public double getPrice() {return price;}
    public String getCategory() {return category;}
    public int getAmount() {return amount;}
    public int getAvailable() {return available;}
    
    public void setAmount (int amount) {this.amount = amount;}
    public void setAvailable(int available) {this.available = available;}  
}