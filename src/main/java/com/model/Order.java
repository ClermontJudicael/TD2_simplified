package com.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.dao.DishAvailabibilityDAO;

public class Order {
    private Long id;
    private TableNumber tableNumber;
    private Double amountPaid;
    private Double amountDue;
    private Instant customerArrivalDatetime;
    private List<DishOrder> dishOrderList;
    private OrderPaymentStatus paymentStatus;

    public Order(Long id, TableNumber tableNumber, Double amountPaid, Double amountDue,
            Instant customerArrivalDatetime, List<DishOrder> dishOrderList) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.amountPaid = amountPaid;
        this.amountDue = amountDue;
        this.customerArrivalDatetime = customerArrivalDatetime;
        this.dishOrderList = (dishOrderList != null) ? dishOrderList : new ArrayList<>();
        this.paymentStatus = OrderPaymentStatus.UNPAID;
    }


    public List<DishOrder> addDishOrders(List<DishOrder> dishOrderList) {
        DishAvailabibilityDAO dishAvailabilityDao = new DishAvailabibilityDAO();

        for (DishOrder dishOrder : dishOrderList) {
            DishAvailability dishAvailability = dishAvailabilityDao.findByName(dishOrder.getName());

            if (dishAvailability == null) {
                throw new IllegalArgumentException("Dish not found: " + dishOrder.getName());
            }

            if (dishOrder.getQuantityToOrder() > dishAvailability.getAvailability()) {
                throw new IllegalArgumentException("Stock not enough for dish : " + dishOrder.getName() + 
                        ". Quantity Available: " + dishAvailability.getAvailability() + 
                        ", Quantity required: " + dishOrder.getQuantityToOrder());
            }

            // update stock
            double newStock = dishAvailability.getAvailability() - dishOrder.getQuantityToOrder();
            dishAvailability.setAvailability(newStock);
            dishAvailabilityDao.updateStock(dishAvailability);
            
            // add dish to order
            this.dishOrderList.add(dishOrder);
        }

        return this.dishOrderList;
    }

    public Double getTotalPrice() {
        return dishOrderList.stream()
            .mapToDouble(dishOrder -> dishOrder.getPrice() * dishOrder.getQuantityToOrder())
            .sum();
    }

    public double payOrder(double amountPaid) {
        double totalAmount = getTotalPrice();
        this.amountPaid = amountPaid;
    
        if (this.paymentStatus == OrderPaymentStatus.PAID) {
            throw new IllegalStateException("Order has already been paid");
        }

        if (amountPaid < totalAmount) {
            double missingAmount = totalAmount - amountPaid;
            throw new IllegalArgumentException("Given amount not enough. Missing " + missingAmount + " Ariary.");
        }
    
        
        double change = amountPaid - totalAmount;
        // is has been paid
        this.amountDue = 0.0;
        this.paymentStatus = OrderPaymentStatus.PAID;

        return change;
    }

    public OrderPaymentStatus getOrderPaymentStatus() {
        return this.paymentStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TableNumber getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(TableNumber tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(Double amountDue) {
        this.amountDue = amountDue;
    }

    public Instant getCustomerArrivalDatetime() {
        return customerArrivalDatetime;
    }

    public void setCustomerArrivalDatetime(Instant customerArrivalDatetime) {
        this.customerArrivalDatetime = customerArrivalDatetime;
    }

    public List<DishOrder> getDishOrderList() {
        return dishOrderList;
    }

    public void setDishOrderList(List<DishOrder> dishOrderList) {
        this.dishOrderList = dishOrderList;
    }
}
