# Trip Planner

A comprehensive travel planning application that helps users plan their trips by searching for flights, hotels, and managing their travel itineraries. The application integrates with multiple external APIs to provide real-time data on flights, accommodations, and currency exchange rates.

Frontend repository: https://github.com/bpmcircuits/trip-planner-front

## Technologies Used

- Java 21
- Spring Boot 3.5.4
- Spring Data JPA
- Spring Security
- MySQL (production)
- H2 Database (development/testing)
- Lombok
- dotenv-java (for environment variable management)

## Features

- **Flight Search**: Search for flights between destinations with filters for dates, passengers, and more
- **Hotel Search**: Find accommodations at your destination with various filtering options
- **Trip Management**: Create and manage complete trip itineraries
- **Currency Exchange**: Get real-time currency exchange rates for travel budgeting
- **User Management**: User registration and authentication system
- **Traveler Management**: Add and manage travelers for your trips

## API Integrations

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

## Setup Instructions

### Prerequisites
- Java 21 or higher
- MySQL (for production)
- Gradle

### Installation

1. Clone the repository
```
git clone https://github.com/yourusername/trip-planner.git
cd trip-planner
```

2. Create a `.env` file in the project root with the following variables:
```
KIWI_API_KEY=your_kiwi_api_key
BOOKING_API_KEY=your_booking_api_key
AMADEUS_API_KEY=your_amadeus_api_key
AMADEUS_CLIENT_ID=your_amadeus_client_id
AMADEUS_CLIENT_SECRET=your_amadeus_client_secret
```

3. Configure the database connection in `application.properties`

By default, the application is configured to use MySQL with the following settings:
```
spring.datasource.url=jdbc:mysql://localhost:3306/trip_planner?serverTimezone=Europe/Warsaw&useSSL=False&allowPublicKeyRetrieval=true
spring.datasource.username=trip_planner_user
spring.datasource.password=trip_planner_pass
```

You may need to create the database and user with appropriate permissions before running the application.

4. Build the project
```
./gradlew build
```

5. Run the application
```
./gradlew bootRun
```

The application will start on port 8083 by default. You can access it at http://localhost:8083

## API Endpoints

The application exposes the following REST API endpoints:

### Flights

- `GET /api/v1/flights/search` - Search for flights
  - Parameters: origin, destination, departureDate, returnDate, adults, children, infants, currencyCode

### Hotels

- `GET /api/v1/hotels/search` - Search for hotels
  - Parameters: location, checkinDate, checkoutDate, adults

### Trips

- `GET /api/v1/trips` - Get all trips
- `GET /api/v1/trips/{id}` - Get trip by ID
- `POST /api/v1/trips` - Create a new trip
- `PUT /api/v1/trips/{id}/flight` - Update trip flight details
- `PUT /api/v1/trips/{id}/hotel` - Update trip hotel details
- `POST /api/v1/trips/{id}/book` - Book a trip
- `DELETE /api/v1/trips/{id}` - Delete a trip

### Travelers

- `GET /api/v1/trips/travelers` - Get all travelers
- `POST /api/v1/trips/travelers/add` - Add a new traveler
- `DELETE /api/v1/trips/travelers/{travelerId}` - Remove a traveler

### Currency

- `GET /api/v1/currency/rates` - Get current exchange rates

## Usage Examples

### Searching for Flights

```
GET /api/v1/flights/search?origin=WAW&destination=LHR&departureDate=2025-09-01&returnDate=2025-09-10&adults=2
```

### Searching for Hotels

```
GET /api/v1/hotels/search?location=London&checkinDate=2025-09-01&checkoutDate=2025-09-10&adults=2
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

### Adding a Traveler

```
POST /api/v1/trips/travelers/add
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "+1234567890"
}
```

## Configuration

The application uses Spring profiles for different environments:

- `dev`: Development environment with H2 database
- `prod`: Production environment with MySQL database

You can specify the active profile by setting the `spring.profiles.active` property in `application.properties` or as a command-line argument:

```
./gradlew bootRun --args='--spring.profiles.active=dev'
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.