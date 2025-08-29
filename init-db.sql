CREATE DATABASE flight_db;
CREATE DATABASE hotel_db;
CREATE DATABASE currency_db;
CREATE DATABASE trip_db;
CREATE DATABASE user_db;

\c flight_db
CREATE SCHEMA trip_planner;

\c currency_db
CREATE SCHEMA trip_planner;

\c user_db
CREATE SCHEMA trip_planner;

\c trip_db
CREATE SCHEMA IF NOT EXISTS trip_planner;

\c hotel_db
CREATE SCHEMA IF NOT EXISTS trip_planner;