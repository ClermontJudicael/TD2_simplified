import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dao.DishAvailabibilityDAO;
import com.model.DishAvailability;
import com.model.DishOrder;
import com.model.Order;
import com.model.OrderPaymentStatus;
import com.model.TableNumber;

public class OrderTest {
    
    private Order order;
    private DishOrder dishOrder1;
    private DishOrder dishOrder2;

    @BeforeEach
    public void setUp() {
        // Initialize the order
        TableNumber tableNumber = TableNumber.TABLE_1;
        order = new Order(1L, tableNumber, 0.0, 0.0, Instant.now(), new ArrayList<>());
        
        // Initialize the dish orders
        dishOrder1 = new DishOrder(1L, "Dish 1", 2.0, 100.0); // 2 dishes, 100 price each
        dishOrder2 = new DishOrder(2L, "Dish 2", 1.0, 150.0); // 1 dish, 150 price
    }

    @Test
    public void testAddDishOrders() {
        List<DishOrder> dishOrders = new ArrayList<>();
        dishOrders.add(dishOrder1);
        dishOrders.add(dishOrder2);

        // Simulate adding the dish orders to the order
        List<DishOrder> updatedDishOrderList = order.addDishOrders(dishOrders);

        // Ensure that the dish orders were added successfully
        assertEquals(2, updatedDishOrderList.size());
        assertTrue(updatedDishOrderList.contains(dishOrder1));
        assertTrue(updatedDishOrderList.contains(dishOrder2));
    }

    @Test
    public void testGetTotalPrice() {
        // Add dish orders
        order.addDishOrders(List.of(dishOrder1, dishOrder2));

        // Total price should be (2 * 100) + (1 * 150) = 350
        assertEquals(350.0, order.getTotalPrice());
    }

    @Test
    public void testPayOrder_SufficientAmount() {
        // Add dish orders
        order.addDishOrders(List.of(dishOrder1, dishOrder2));

        // Pay for the order
        double change = order.payOrder(400.0); // Pay 400 for 350 order total
        
        // Assert that the order was paid
        assertEquals(0.0, order.getAmountDue());
        assertEquals(OrderPaymentStatus.PAID, order.getOrderPaymentStatus());
        assertEquals(50.0, change); // Change should be 400 - 350 = 50
    }

    @Test
    public void testPayOrder_InsufficientAmount() {
        // Add dish orders
        order.addDishOrders(List.of(dishOrder1, dishOrder2));

        // Try to pay less than the total amount
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            order.payOrder(300.0); // Pay 300 for a 350 total
        });

        assertEquals("Given amount not enough. Missing 50.0 Ariary.", exception.getMessage());
    }

    @Test
    public void testAddDishOrders_InsufficientStock() {
        // Create a DishAvailability mock or use your database for real data
        DishAvailabibilityDAO mockDao = new DishAvailabibilityDAO() {
            @Override
            public DishAvailability findByName(String name) {
                if (name.equals("Dish 1")) {
                    return new DishAvailability("Dish 1", 1.0); // Only 1 dish available
                }
                return null;
            }
        };
        
        // Use the mock DAO
        order.addDishOrders(List.of(dishOrder1)); // This should throw an exception

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            order.addDishOrders(List.of(dishOrder1)); // Try adding 2 dishes when only 1 is available
        });

        assertEquals("Stock insuffisant pour le plat: Dish 1. Quantity Available: 1.0, Quantity required: 2.0", exception.getMessage());
    }
}
