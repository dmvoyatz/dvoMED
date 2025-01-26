-- Migration Script to Insert 5 Orders with at least 3 Products Each

-- Insert 5 Orders
INSERT INTO orders (user_id, order_status_id, total_price)
VALUES
    ((SELECT id FROM users ORDER BY RAND() LIMIT 1), 
     (SELECT status_id FROM order_status WHERE label = 'COMPLETED' LIMIT 1), 
     0.0), -- Placeholder total price
    ((SELECT id FROM users ORDER BY RAND() LIMIT 1), 
     (SELECT status_id FROM order_status WHERE label = 'COMPLETED' LIMIT 1), 
     0.0),
    ((SELECT id FROM users ORDER BY RAND() LIMIT 1), 
     (SELECT status_id FROM order_status WHERE label = 'COMPLETED' LIMIT 1), 
     0.0),
    ((SELECT id FROM users ORDER BY RAND() LIMIT 1), 
     (SELECT status_id FROM order_status WHERE label = 'PENDING' LIMIT 1), 
     0.0),
    ((SELECT id FROM users ORDER BY RAND() LIMIT 1), 
     (SELECT status_id FROM order_status WHERE label = 'PENDING' LIMIT 1), 
     0.0);

-- Insert Order Details for each Order with random products and quantities
-- Note: Using LAST_INSERT_ID() to get the most recently inserted order_id
INSERT INTO order_details (order_id, product_id, quantity, price)
SELECT LAST_INSERT_ID(), product_id, FLOOR(1 + RAND() * 10), price FROM products ORDER BY RAND() LIMIT 3;

-- Insert for the second order
INSERT INTO order_details (order_id, product_id, quantity, price)
SELECT LAST_INSERT_ID(), product_id, FLOOR(1 + RAND() * 10), price FROM products ORDER BY RAND() LIMIT 3;

-- Insert for the third order
INSERT INTO order_details (order_id, product_id, quantity, price)
SELECT LAST_INSERT_ID(), product_id, FLOOR(1 + RAND() * 10), price FROM products ORDER BY RAND() LIMIT 3;

-- Insert for the fourth order
INSERT INTO order_details (order_id, product_id, quantity, price)
SELECT LAST_INSERT_ID(), product_id, FLOOR(1 + RAND() * 10), price FROM products ORDER BY RAND() LIMIT 3;

-- Insert for the fifth order
INSERT INTO order_details (order_id, product_id, quantity, price)
SELECT LAST_INSERT_ID(), product_id, FLOOR(1 + RAND() * 10), price FROM products ORDER BY RAND() LIMIT 3;

-- Update Price for each set of products in Order
UPDATE order_details od 
JOIN products p ON p.product_id = od.product_id 
SET od.price = (od.quantity * p.price * (1 - p.discount) );

-- Update Total Price for each Order
UPDATE orders o 
JOIN order_details od ON o.order_id = od.order_id 
SET o.total_price = ( 
    SELECT SUM(od.price) 
    FROM order_details od 
    WHERE od.order_id = o.order_id ) 
WHERE o.order_id IN (
    SELECT DISTINCT order_id FROM order_details);