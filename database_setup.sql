
CREATE DATABASE IF NOT EXISTS shopping_mall_db;


USE shopping_mall_db;


CREATE TABLE IF NOT EXISTS shops (
    shop_id INT PRIMARY KEY AUTO_INCREMENT,
    shop_name VARCHAR(100) NOT NULL,
    owner_name VARCHAR(100) NOT NULL,
    contact_number VARCHAR(15) NOT NULL,
    shop_category VARCHAR(50) NOT NULL,
    rent_amount DECIMAL(10, 2) NOT NULL,
    lease_start_date DATE NOT NULL,
    lease_end_date DATE NOT NULL,
    occupancy_status VARCHAR(20) NOT NULL DEFAULT 'Occupied',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS rent_payments (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    shop_id INT NOT NULL,
    payment_date DATE NOT NULL,
    amount_paid DECIMAL(10, 2) NOT NULL,
    payment_status VARCHAR(20) NOT NULL DEFAULT 'Pending',
    due_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (shop_id) REFERENCES shops(shop_id) ON DELETE CASCADE
);


INSERT INTO shops (shop_name, owner_name, contact_number, shop_category, rent_amount, lease_start_date, lease_end_date, occupancy_status) VALUES
('Fashion Hub', 'John Doe', '9876543210', 'Clothing', 25000.00, '2023-01-01', '2024-01-01', 'Occupied'),
('Tech World', 'Jane Smith', '9876543211', 'Electronics', 35000.00, '2023-01-01', '2024-01-01', 'Occupied'),
('Food Court Express', 'Mike Johnson', '9876543212', 'Food', 20000.00, '2023-01-01', '2024-01-01', 'Occupied');


INSERT INTO rent_payments (shop_id, payment_date, amount_paid, payment_status, due_date) VALUES
(1, '2023-10-01', 25000.00, 'Paid', '2023-10-01'),
(2, '2023-10-01', 35000.00, 'Pending', '2023-10-01'),
(3, '2023-10-01', 20000.00, 'Overdue', '2023-09-01');


SELECT * FROM shops;


SELECT * FROM rent_payments;
