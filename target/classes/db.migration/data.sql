INSERT INTO dish_availability (name, available_stock) VALUES
('Pizza Margherita', 100),
('Burger Classique', 50),
('Salade César', 30),
('Pâtes Bolognaise', 75),
('Riz Sauté', 120);

-- Table 1, montant dû de 5000 Ariary
INSERT INTO "order" (table_number, amount_paid, amount_due, order_payment_status, customer_arrival_date_time) 
VALUES
('TABLE_1', 0.00, 5000.00, 'UNPAID', now()),
-- Table 2, montant dû de 15000 Ariary
('TABLE_2', 0.00, 15000.00, 'UNPAID', now());

-- Pour la commande de Table 1
INSERT INTO dish_order (id_order, id_dish_availability, name, price, quantity_to_order)
VALUES
(1, 1, 'Pizza Margherita', 2500.00, 2),  -- 2 pizzas Margherita
(1, 2, 'Burger Classique', 1500.00, 1);  -- 1 burger classique

-- Pour la commande de Table 2
INSERT INTO dish_order (id_order, id_dish_availability, name, price, quantity_to_order)
VALUES
(2, 3, 'Salade César', 2000.00, 3),  -- 3 salades César
(2, 4, 'Pâtes Bolognaise', 3500.00, 2);  -- 2 pâtes Bolognaise
