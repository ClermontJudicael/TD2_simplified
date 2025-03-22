package com.model;

public class DishOrder {
    private Long id;
    private String name;
    private Double quantityToOrder;
    private Double price;

    public DishOrder() {}
    
    public DishOrder(Long id, String name, Double quantityToOrder, Double price) {
        this.id = id;
        this.name = name;
        this.quantityToOrder = quantityToOrder;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getQuantityToOrder() {
        return quantityToOrder;
    }

    public void setQuantityToOrder(Double quantityToOrder) {
        this.quantityToOrder = quantityToOrder;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
}
