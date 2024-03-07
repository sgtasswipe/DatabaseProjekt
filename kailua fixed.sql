
create database if not exists kailua;
use kailua; 

create table if not exists cars_info ( 
model varchar(30) not  null, 
brand varchar (30) not  null, 
fuel_type enum ('gasoline', 'diesel', 'electric'), 
license_plate varchar(10) unique not  null primary key, 
registration datetime, 
odometer mediumint, 
type_of_car enum ('luxury', 'family', 'sport') not null ) ; 

 
   
create table if not exists customers (
first_name varchar(50) not  null,
last_name varchar (50) not  null, 
address varchar(70) not  null, 
zipcode mediumint,
city varchar(50) not  null, 
phone_number bigint, 
email varchar (200 )  not  null, 
drivers_license_number bigint unique not  null primary key,
drivers_license_issue_date  datetime 



) ;

CREATE TABLE IF NOT EXISTS lease_contract (
  customer_id bigINT  not  null,  -- Renamed for clarity and to avoid duplicate column names
  odometer_start MEDIUMINT , -- gets assigned in java by reading from cars_info
  
  license_plate VARCHAR(10) not  null,
  start_date DATETIME,
  end_date DATETIME,
  max_km MEDIUMINT,
  FOREIGN KEY (customer_id) REFERENCES customers(drivers_license_number),  -- Use the unique column
  FOREIGN KEY (license_plate) REFERENCES cars_info(license_plate)
);

 /* ALTERS   
 */ 

ALTER TABLE customers ADD INDEX (drivers_license_number);
Alter table cars_info add index (license_plate) ;


 
  INSERT  INTO  cars_info  (model, brand, fuel_type, license_plate, registration, odometer, type_of_car)
VALUES ('Corolla', 'Toyota', 'gasoline', 'ABC123', '2023-01-01 00:00:00', 10000, 'family'),
       ('Camry', 'Toyota', 'gasoline', 'DEF456', '2022-05-15 00:00:00', 25000, 'family'),
       ('Prius', 'Toyota', 'electric', 'GHI789', '2021-12-31 00:00:00', 50000, 'family'),
       ('Mustang', 'Ford', 'gasoline', 'JKL012', '2020-10-24 00:00:00', 35000, 'sport'),
       ('F-150', 'Ford', 'gasoline', 'MNO345', '2019-06-07 00:00:00', 40000, 'luxury');

INSERT INTO customers (first_name, last_name, address, zipcode, city, phone_number, email, drivers_license_number, drivers_license_issue_date)
VALUES ('John', 'Doe', '123 Main St.', 12345, 'Anytown', 5551234567, 'john.doe@example.com', 1690, '2023-01-01 00:00:00'),
       ('Jane', 'Smith', '456 Elm St.', 54321, 'Anytown', 5559876543, 'jane.smith@example.com', 9210, '2022-05-15 00:00:00'),
       ('Michael', 'Wilson', '789 Oak St.', 90123, 'Anytown', 5557890123, 'michael.wilson@example.com', 23450, '2021-12-31 00:00:00'),
       ('Sarah', 'Jones', '1011 Pine St.', 87654, 'Anytown', 5554567890, 'sarah.jones@example.com', 90123, '2020-10-24 00:00:00'),
       ('David', 'Miller', '1213 Maple St.', 65432, 'Anytown', 5552345678, 'david.miller@example.com', 23401, '2019-06-07 00:00:00');

INSERT INTO lease_contract (customer_id, odometer_start, license_plate, start_date, end_date, max_km)
VALUES (9210, 25000, 'DEF456', '2022-06-01 00:00:00', '2023-05-31 00:00:00', 30000),
		(1690, 10000, 'ABC123', '2023-02-01 00:00:00', '2024-01-31 00:00:00', 20000),
       (23450, 50000, 'GHI789', '2022-01-01 00:00:00', '2023-12-31 00:00:00', 60000);


use kailua;

/* SELECT EXAMPLE SUB QUERY */ 

select customer_id, odometer_start, license_plate, start_date, end_date, max_km,
 (select first_name  from customers where lease_contract.customer_id = customers.drivers_license_number )
 as first_name,
 (select last_name  from customers where lease_contract.customer_id = customers.drivers_license_number ) 
 as last_name from lease_contract ; 


