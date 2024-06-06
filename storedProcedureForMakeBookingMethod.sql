-- Creates a Stored Procedure to insert a reservation
CREATE OR REPLACE PROCEDURE insertReservation(
    IN customer_id BIGINT,
    IN bicycle_id BIGINT,
    IN shop_id BIGINT,
    IN start_date TIMESTAMP,
    IN end_date TIMESTAMP,
    IN amount DECIMAL
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO Reservations (shop_id, customer_id, bicycle_id, start_date, end_date, amount)
    VALUES (shop_id, customer_id, bicycle_id, start_date, end_date, amount);
END;
$$;