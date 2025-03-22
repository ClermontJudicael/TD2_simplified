package com.model;

import java.util.List;

import com.dao.DishAvailabibilityDAO;

public class DishAvailability {
    private String name;
    private Double availability;

    
    public DishAvailability(String name, Double availability) {
        this.name = name;
        this.availability = availability;
    }

    public DishAvailability() {}
    
    public List<DishAvailability> getAllDishAvailability() {
        DishAvailabibilityDAO dao = new DishAvailabibilityDAO();
        return dao.findAll();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAvailability() {
        return availability;
    }

    public void setAvailability(Double availability) {
        this.availability = availability;
    }

    
}
