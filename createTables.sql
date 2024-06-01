-- Creating Shops Table
CREATE TABLE Shops (
    shop_id SERIAL PRIMARY KEY,
    manager VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(255) NOT NULL
);

-- Creating Bicycles Table
CREATE TABLE Bicycles (
    bicycle_id SERIAL PRIMARY KEY,
    shop_id INT NOT NULL,
    type VARCHAR(10) CHECK (type IN ('classic', 'electric')),
    weight INT NOT NULL,
    model VARCHAR(255) NOT NULL,
    brand VARCHAR(255) NOT NULL,
    gear_system INT CHECK (gear_system IN (1, 6, 18, 24)),
    status VARCHAR(20) CHECK (status IN ('free', 'occupied', 'in_reserve', 'under_maintenance')),
    range INT,  -- only for electric bicycles
    max_speed INT,  -- only for electric bicycles
    FOREIGN KEY (shop_id) REFERENCES Shops (shop_id)
);

-- Creating GPS Devices Table
CREATE TABLE GPS_Devices (
    gps_id SERIAL PRIMARY KEY,
    bicycle_id INT NOT NULL,
    serial_number VARCHAR(255) NOT NULL UNIQUE,
    latitude DECIMAL(9, 6) NOT NULL,
    longitude DECIMAL(9, 6) NOT NULL,
    battery_percentage DECIMAL(5, 2) NOT NULL,
    FOREIGN KEY (bicycle_id) REFERENCES Bicycles (bicycle_id)
);

-- Creating Customers Table
CREATE TABLE Customers (
    customer_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    identification_number VARCHAR(20) NOT NULL,
    nationality VARCHAR(100) NOT NULL
);

-- Creating Reservations Table
CREATE TABLE Reservations (
    reservation_id SERIAL PRIMARY KEY,
    shop_id INT NOT NULL,
    customer_id INT NOT NULL,
    bicycle_id INT NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (shop_id) REFERENCES Shops (shop_id),
    FOREIGN KEY (customer_id) REFERENCES Customers (customer_id),
    FOREIGN KEY (bicycle_id) REFERENCES Bicycles (bicycle_id),
    UNIQUE (customer_id, start_date)  -- Ensure no double bookings for the same start date
);