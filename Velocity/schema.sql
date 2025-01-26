-- Create `status` table
CREATE TABLE status (
    status_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    label VARCHAR(50) UNIQUE NOT NULL
);

-- Insert initial data into `status` table
INSERT INTO status (label) VALUES ('ACTIVE'), ('INACTIVE'), ('BANNED'), ('DELETED');

-- Create `role` table
CREATE TABLE role (
    role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    label VARCHAR(50) UNIQUE NOT NULL
);

-- Insert initial data into `role` table
INSERT INTO role (label) VALUES ('USER'), ('ADMIN');

-- Create `users` table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    status_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_status FOREIGN KEY (status_id) REFERENCES status(status_id),
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES role(role_id)
);

-- Insert 5 random users into the `users` table
INSERT INTO users (username, email, password_hash, first_name, last_name, status_id, role_id)
VALUES
    ('user1', CONCAT('user1_', FLOOR(RAND() * 1000), '@example.com'), MD5('password1'), 'John', 'Doe', 
        (SELECT status_id FROM status ORDER BY RAND() LIMIT 1), 
        (SELECT role_id FROM role ORDER BY RAND() LIMIT 1)),
    ('user2', CONCAT('user2_', FLOOR(RAND() * 1000), '@example.com'), MD5('password2'), 'Jane', 'Smith', 
        (SELECT status_id FROM status ORDER BY RAND() LIMIT 1), 
        (SELECT role_id FROM role ORDER BY RAND() LIMIT 1)),
    ('user3', CONCAT('user3_', FLOOR(RAND() * 1000), '@example.com'), MD5('password3'), 'Alice', 'Johnson', 
        (SELECT status_id FROM status ORDER BY RAND() LIMIT 1), 
        (SELECT role_id FROM role ORDER BY RAND() LIMIT 1));


-- Create `category` table
CREATE TABLE category (
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    label VARCHAR(100) UNIQUE NOT NULL
);

-- Insert initial data into `category` table
INSERT INTO category (label) VALUES ('Electronics'), ('Clothing'), ('Home & Kitchen');

-- Create `products` table
CREATE TABLE products (
    product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    discount DECIMAL(5, 2) DEFAULT 0.00, -- Discount in percentage, logicaly max 0.99
    quantity INT DEFAULT 0,
    category_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES category(category_id)
);

-- Insert sample products into `products` table
INSERT INTO products (name, description, price, discount, quantity, category_id)
VALUES
    ('Smartphone', 'Latest model with high resolution camera and 128GB storage', 100, 0.60, 50, 
        (SELECT category_id FROM category WHERE label = 'Electronics' LIMIT 1)),
    ('T-shirt', 'Comfortable cotton T-shirt available in multiple sizes', 5, 0.00, 150, 
        (SELECT category_id FROM category WHERE label = 'Clothing' LIMIT 1)),
    ('Blender', 'High-powered blender perfect for smoothies and shakes', 45, 0.10, 30, 
        (SELECT category_id FROM category WHERE label = 'Home & Kitchen' LIMIT 1)),
    ('Laptop', '15-inch laptop with Intel i7 processor and 512GB SSD', 700, 0.00, 25, 
        (SELECT category_id FROM category WHERE label = 'Electronics' LIMIT 1)),
    ('Coffee Maker', 'Automatic coffee maker with programmable settings', 20, 0.20, 40, 
        (SELECT category_id FROM category WHERE label = 'Home & Kitchen' LIMIT 1)),
    ('Wireless Earbuds', 'Bluetooth-enabled wireless earbuds with noise cancellation', 30, 0.00, 75, 
        (SELECT category_id FROM category WHERE label = 'Electronics' LIMIT 1)),
    ('Sweater', 'Warm wool sweater available in various colors', 25, 0.12, 60, 
        (SELECT category_id FROM category WHERE label = 'Clothing' LIMIT 1)),
    ('Microwave Oven', 'Compact microwave oven with defrost and reheat functions', 100, 0.00, 20, 
        (SELECT category_id FROM category WHERE label = 'Home & Kitchen' LIMIT 1)),
    ('Smart Watch', 'Stylish smart watch with fitness tracking and notifications', 120, 0.00, 50, 
        (SELECT category_id FROM category WHERE label = 'Electronics' LIMIT 1)),
    ('Jeans', 'Denim jeans with a slim fit and various washes', 40, 0.00, 100, 
        (SELECT category_id FROM category WHERE label = 'Clothing' LIMIT 1)),
    ('Air Fryer', 'Healthy air fryer with multiple cooking presets', 90, 0.00, 35, 
        (SELECT category_id FROM category WHERE label = 'Home & Kitchen' LIMIT 1)),
    ('Tablet', '10-inch tablet with high-resolution display and 64GB storage', 250, 0.00, 45, 
        (SELECT category_id FROM category WHERE label = 'Electronics' LIMIT 1)),
    ('Jacket', 'Water-resistant jacket suitable for all weather conditions', 60, 0.00, 80, 
        (SELECT category_id FROM category WHERE label = 'Clothing' LIMIT 1)),
    ('Dishwasher', 'Energy-efficient dishwasher with multiple cleaning cycles', 300, 0.40, 15, 
        (SELECT category_id FROM category WHERE label = 'Home & Kitchen' LIMIT 1)),
    ('Gaming Console', 'Latest gaming console with 4K support and exclusive games', 500, 0.00, 40, 
        (SELECT category_id FROM category WHERE label = 'Electronics' LIMIT 1));


-- Create `order_status` table
CREATE TABLE order_status (
    status_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    label VARCHAR(50) UNIQUE NOT NULL
);

-- Insert initial data into `order_status` table
INSERT INTO order_status (label) VALUES ('PENDING'), ('COMPLETED'), ('SHIPPED'), ('CANCELLED');

-- Create `orders` table
CREATE TABLE orders (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL, -- Refers to the user making the order
    order_status_id BIGINT NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_order_status FOREIGN KEY (order_status_id) REFERENCES order_status(status_id)
);

-- -- Insert sample order into `orders` table
-- INSERT INTO orders (user_id, order_status_id, total_price)
-- VALUES
--     (1, (SELECT status_id FROM order_status WHERE label = 'PENDING' LIMIT 1), 719.98),  -- Example order for user 1
--     (2, (SELECT status_id FROM order_status WHERE label = 'COMPLETED' LIMIT 1), 129.99); -- Example order for user 2


-- Create `order_details` table
CREATE TABLE order_details (
    order_detail_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL, -- Price of the product at the time of the order 
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders(order_id),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- -- Insert sample order details for the previously inserted orders
-- INSERT INTO order_details (order_id, product_id, quantity, price)
-- VALUES
--     (1, (SELECT product_id FROM products WHERE name = 'Smartphone' LIMIT 1), 4, 699.99),
--     (1, (SELECT product_id FROM products WHERE name = 'T-shirt' LIMIT 1), 3, 19.99),
--     (2, (SELECT product_id FROM products WHERE name = 'Blender' LIMIT 1), 2, 129.99);

-- Insert 5 Orders
INSERT INTO orders (user_id, order_status_id, total_price)
VALUES
    ((SELECT id FROM users ORDER BY RAND() LIMIT 1), 
     (SELECT status_id FROM order_status WHERE label = 'PENDING' LIMIT 1), 
     0.0), -- Placeholder total price
    ((SELECT id FROM users ORDER BY RAND() LIMIT 1), 
     (SELECT status_id FROM order_status WHERE label = 'COMPLETED' LIMIT 1), 
     0.0),
    ((SELECT id FROM users ORDER BY RAND() LIMIT 1), 
     (SELECT status_id FROM order_status WHERE label = 'SHIPPED' LIMIT 1), 
     0.0),
    ((SELECT id FROM users ORDER BY RAND() LIMIT 1), 
     (SELECT status_id FROM order_status WHERE label = 'CANCELLED' LIMIT 1), 
     0.0),
    ((SELECT id FROM users ORDER BY RAND() LIMIT 1), 
     (SELECT status_id FROM order_status WHERE label = 'PENDING' LIMIT 1), 
     0.0);

-- Insert Order Details for each Order with random products and quantities
-- Note: Using LAST_INSERT_ID() to get the most recently inserted order_id
INSERT INTO order_details (order_id, product_id, quantity, price)
SELECT 1, product_id, FLOOR(1 + RAND() * 10), price FROM products ORDER BY RAND() LIMIT 3;

-- Insert for the second order
INSERT INTO order_details (order_id, product_id, quantity, price)
SELECT 2, product_id, FLOOR(1 + RAND() * 10), price FROM products ORDER BY RAND() LIMIT 3;

-- Insert for the third order
INSERT INTO order_details (order_id, product_id, quantity, price)
SELECT 3, product_id, FLOOR(1 + RAND() * 10), price FROM products ORDER BY RAND() LIMIT 3;

-- Insert for the fourth order
INSERT INTO order_details (order_id, product_id, quantity, price)
SELECT 4, product_id, FLOOR(1 + RAND() * 10), price FROM products ORDER BY RAND() LIMIT 3;

-- Insert for the fifth order
INSERT INTO order_details (order_id, product_id, quantity, price)
SELECT 5, product_id, FLOOR(1 + RAND() * 10), price FROM products ORDER BY RAND() LIMIT 3;

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
