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


create or replace function is_bike_available(id BIGINT, p_time TIMESTAMP) returns INT as
$$
DECLARE
    reservation_count INT;
begin
	SELECT COUNT(*)
    INTO reservation_count
    FROM reservations
    WHERE bicycle_id = id
      AND (
            (start_date < p_time AND end_date > p_time)
          );
    return reservation_count;
end;
$$
language plpgsql;


-- Insert data into Shops Table
INSERT INTO Shops (manager, address, city, phone, email) VALUES
('Alice Johnson', '123 Maple St', 'Springfield', '123-456-7890', 'alice.johnson@shop.com'),
('Bob Smith', '456 Oak Ave', 'Rivertown', '234-567-8901', 'bob.smith@shop.com'),
('Charlie Brown', '789 Pine Dr', 'Mountainview', '345-678-9012', 'charlie.brown@shop.com');

-- Insert data into Bicycles Table
INSERT INTO Bicycles (shop_id, type, weight, model, brand, gear_system, status, range, max_speed) VALUES
(1, 'classic', 15, 'Roadster', 'BikeCo', 6, 'free', NULL, NULL),
(1, 'electric', 20, 'E-Rider', 'ElectroBike', 1, 'occupied', 50, 25),
(2, 'classic', 12, 'MountainMaster', 'BikePro', 18, 'in_reserve', NULL, NULL),
(2, 'electric', 22, 'Speedy', 'ElectroBike', 1, 'free', 60, 30),
(3, 'classic', 14, 'CityCruiser', 'UrbanBikes', 24, 'under_maintenance', NULL, NULL);

-- Insert data into GPS_Devices Table
INSERT INTO GPS_Devices (bicycle_id, serial_number, latitude, longitude, battery_percentage) VALUES
(1, 'GPS12345', 40.712776, -74.005974, 85.5),
(2, 'GPS67890', 34.052235, -118.243683, 75.0),
(3, 'GPS54321', 41.878113, -87.629799, 90.0),
(4, 'GPS09876', 37.774929, -122.419416, 60.0);

-- Insert data into Customers Table
INSERT INTO Customers (name, address, email, phone, identification_number, nationality) VALUES
('John Doe', '100 Elm St', 'john.doe@gmail.com', '456-789-0123', 'ID100', 'American'),
('Jane Smith', '200 Cedar St', 'jane.smith@gmail.com', '567-890-1234', 'ID101', 'Canadian'),
('Bill Gates', '300 Birch St', 'bill.gates@gmail.com', '678-901-2345', 'ID102', 'British');

-- Insert data into Reservations Table
INSERT INTO Reservations (shop_id, customer_id, bicycle_id, start_date, end_date, amount, version) VALUES
(1, 1, 1, '2024-06-01 10:00:00', '2024-06-01 18:00:00', 25.00,1),
(2, 2, 3, '2024-06-02 09:00:00', '2024-06-02 17:00:00', 30.00,2),
(3, 3, 5, '2024-06-03 11:00:00', '2024-06-03 19:00:00', 20.00,3);

CREATE OR REPLACE FUNCTION makenewreservation(
    in p_shop_id integer,
    in p_customer_id integer,
    in p_bicycle_id integer,
    IN p_start_date TIMESTAMP,
    IN p_end_date TIMESTAMP,
    in p_amount REAL
) returns void
AS $$
DECLARE
    reservation_count INT;
BEGIN
    SELECT COUNT(*)
    INTO reservation_count
    FROM Reservations
    WHERE bicycle_id = p_bicycle_id
      AND (
            (start_date < p_end_date AND end_date > p_start_date)
          );

    IF reservation_count > 0 THEN
        RAISE EXCEPTION 'The bicycle is not available for the specified time period.';
    END IF;


    INSERT INTO Reservations (shop_id, customer_id, bicycle_id, start_date, end_date, amount)
    VALUES (p_shop_id, p_customer_id, p_bicycle_id, p_start_date, p_end_date, p_amount);

    RAISE NOTICE 'Reservation made successfully for bicycle_id %', p_bicycle_id;
    return;
END;
$$
LANGUAGE plpgsql;