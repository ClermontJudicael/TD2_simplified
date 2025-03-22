CREATE TYPE tablenumber AS ENUM ('TABLE_1', 'TABLE_2', 'TABLE_3', 'TABLE_4', 'TABLE_5');

CREATE TYPE orderpaymentstatus AS ENUM ('PAID', 'UNPAID');

CREATE TABLE dish_availability (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    available_stock FLOAT NOT NULL CHECK (available_stock >= 0)
);

CREATE TABLE "order" (
    id SERIAL PRIMARY KEY,
    table_number tablenumber NOT NULL,
    amount_paid NUMERIC(10,2) DEFAULT 0.00,
    amount_due NUMERIC(10,2) DEFAULT 0.00,
    order_payment_status orderpaymentstatus NOT NULL DEFAULT 'UNPAID',
    customer_arrival_date_time TIMESTAMP WITH TIME ZONE DEFAULT now()
);

CREATE TABLE dish_order (
    id SERIAL PRIMARY KEY,
    id_order INT NOT NULL,
    id_dish_availability INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    price NUMERIC(10,2) NOT NULL CHECK (price >= 0),
    quantity_to_order FLOAT NOT NULL CHECK (quantity_to_order > 0),
    FOREIGN KEY (id_order) REFERENCES "order"(id) ON DELETE CASCADE,
    FOREIGN KEY (id_dish_availability) REFERENCES dish_availability(id) ON DELETE CASCADE
);
