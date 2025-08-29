[![CircleCI](https://circleci.com/gh/bpmcircuits/trip-planner.svg?style=svg)](https://app.circleci.com/pipelines/github/bpmcircuits/trip-planner?branch=main)
 [![License: MIT](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

# Trip Planner

A comprehensive travel planning application built with a microservices architecture that helps users plan their trips by searching for flights, hotels, and managing their travel itineraries. The application integrates with multiple external APIs to provide real-time data on flights, accommodations, and currency exchange rates.

## Architecture Overview

Trip Planner is built using a microservices architecture with the following components:

### Infrastructure Services

- **Config Service**: Centralized configuration server using Spring Cloud Config
- **Discovery Service**: Service registry and discovery using Netflix Eureka
- **Gateway Service**: API Gateway using Spring Cloud Gateway for routing and load balancing

### Domain Services

- **Flight API Service**: Manages flight search and offers using the Amadeus API
- **Hotel API Service**: Manages hotel search and offers using the Booking.com API via RapidAPI
- **Trip API Service**: Manages trip entities that combine flights and hotels
- **User API Service**: Handles user registration, authentication, and profile management
- **NBP API Service**: Provides currency exchange rates from the National Bank of Poland API

### Frontend

- **Frontend Service**: Web interface for users to interact with the application

### Database

- **PostgreSQL**: Relational database with separate schemas for each service

## Technologies Used

- **Java 21**
- **Spring Boot 3.5.4**
- **Spring Cloud**
  - Spring Cloud Config
  - Spring Cloud Netflix Eureka
  - Spring Cloud Gateway
- **Spring Data JPA**
- **Spring Security**
- **PostgreSQL**
- **Docker & Docker Compose**
- **Gradle**
- **Lombok**

## External API Integrations

The application integrates with the following external APIs:

### Amadeus API
Used for flight search and airport information. Provides:
- City/Airport search functionality
- Flight offers with pricing and availability

Endpoint: https://test.api.amadeus.com

### Booking.com API (via RapidAPI)
Used for hotel search and booking. Provides:
- Location autocomplete
- Hotel search with various filters (dates, number of guests, etc.)

Endpoint: https://booking-com18.p.rapidapi.com/stays

### National Bank of Poland (NBP) API
Used for currency exchange rates. Provides:
- Current exchange rates for various currencies

Endpoint: https://api.nbp.pl/api

## Features

- **Flight Search**: Search for flights between destinations with filters for dates, passengers, and more
- **Hotel Search**: Find accommodations at your destination with various filtering options
- **Trip Management**: Create and manage complete trip itineraries
- **Currency Exchange**: Get real-time currency exchange rates for travel budgeting
- **User Management**: User registration and authentication system
- **Traveler Management**: Add and manage travelers for your trips

## Setup Instructions

### Prerequisites
- Docker and Docker Compose
- Java 21 or higher (for development)
- Gradle (for development)

### Environment Variables

Create a `.env` file in the project root with the following variables:

```
AMADEUS_API_KEY=your_amadeus_api_key
AMADEUS_CLIENT_ID=your_amadeus_client_id
AMADEUS_CLIENT_SECRET=your_amadeus_client_secret
BOOKING_API_KEY=your_booking_api_key
MAILTRAP_USERNAME=your_mailtrap_username
MAILTRAP_PASSWORD=your_mailtrap_password
```

### Running with Docker Compose

1. Clone the repository
```
git clone https://github.com/yourusername/trip-planner.git
cd trip-planner
```

2. Start the application using Docker Compose
```
docker-compose up -d
```

3. The application will be available at:
   - Frontend: http://localhost:3001
   - API Gateway: http://localhost:8080
   - Eureka Dashboard: http://localhost:8761

### Development Setup

1. Clone the repository
```
git clone https://github.com/yourusername/trip-planner.git
cd trip-planner
```

2. Build the project
```
./gradlew build
```

3. Run individual services (example for flight-api-service)
```
cd flight-api-service
../gradlew bootRun
```

## API Endpoints

The application exposes the following REST API endpoints through the API Gateway:

### Flights

- `POST /api/v1/flights/flight-offer` - Search for flights
  - Request body: origin, destination, departureDate, returnDate, adults, children, infants, currencyCode
- `GET /api/v1/flights` - Get all flights
- `GET /api/v1/flights/{id}` - Get flight by ID
- `DELETE /api/v1/flights/{id}` - Delete a flight

### Hotels

- `POST /api/v1/hotels/hotel-offer` - Search for hotels
  - Request body: query, checkinDate, checkoutDate, adults
- `GET /api/v1/hotels` - Get all hotels
- `GET /api/v1/hotels/{id}` - Get hotel by ID
- `DELETE /api/v1/hotels/{id}` - Delete a hotel

### Trips

- `GET /api/v1/trips` - Get all trips
- `GET /api/v1/trips/{id}` - Get trip by ID
- `POST /api/v1/trips` - Create a new trip
- `PUT /api/v1/trips/{id}/flight` - Update trip flight details
- `PUT /api/v1/trips/{id}/hotel` - Update trip hotel details
- `DELETE /api/v1/trips/{id}` - Delete a trip

### Currency

- `GET /api/v1/currency/rates` - Get current exchange rates

## Usage Examples

### Searching for Flights

```
POST /api/v1/flights/flight-offer
{
  "origin": "WAW",
  "destination": "LHR",
  "departureDate": "2025-09-01",
  "returnDate": "2025-09-10",
  "adults": 2,
  "children": 0,
  "infants": 0,
  "currencyCode": "PLN"
}
```

### Searching for Hotels

```
POST /api/v1/hotels/hotel-offer
{
  "query": "London",
  "checkinDate": "2025-09-01",
  "checkoutDate": "2025-09-10",
  "adults": 2
}
```

### Creating a Trip

```
POST /api/v1/trips
{
  "name": "London Trip",
  "startDate": "2025-09-01",
  "endDate": "2025-09-10",
  "destination": "London"
}
```

## Service Communication

Services communicate with each other through:
1. **Service Discovery**: Using Netflix Eureka for service registration and discovery
2. **REST API Calls**: Services call each other's REST APIs when needed
3. **API Gateway**: External clients access the services through the API Gateway

## Monitoring and Health Checks

Each service exposes health endpoints through Spring Boot Actuator:
- `/actuator/health` - Health status of the service
- `/actuator/info` - Information about the service

## License

This project is licensed under the MIT License - see the LICENSE file for details.
