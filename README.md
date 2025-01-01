# Retailer Rewards Program API


## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Technologies](#technologies)
- [API Endpoints](#api-endpoints)
- [Running the Application](#running-the-application)

## Overview

This Spring Boot application calculates reward points for customers based on their purchases. Points are awarded as follows:  
- *1 point* for every dollar spent between $50 and $100 (inclusive).  
- *2 points* for every dollar spent over $100.

### Example Calculation  
- A $120 purchase gives 90 points (1x50 + 2x20).  

---

## Architecture

The API is structured in a layered architecture:

- **Controller Layer**: Handles incoming HTTP requests and provides appropriate responses.
- **Service Layer**: Contains the business logic to calculate reward points.
- **Repository Layer**: Manages the storage of transaction records.
- **POJOs (Plain Old Java Objects)**: Data models for customers and their transactions.
- **Global Exception Handler**: Catches and returns consistent error messages.


## Features
- *Reward Points Calculation*: For each customer per month and total points.  
- *Default Period*: Rewards for the last 3 months.  
- *Custom Period*: Rewards for a user-specified date range.  
- *RESTful APIs*: With proper error handling and validation.  
- *Mock Data*: Preloaded for testing purposes.  

---

## Technologies

The following technologies are used to implement the API:

- **Java**: The primary programming language used to implement the business logic.
- **Spring Boot**: Framework used to build the RESTful API.
- **JUnit**: Used for unit testing.
- **Postman**: Tool used for testing the API end points.
- **Maven**: Build automation tool.

## API Endpoints

### 1. Get Rewards for the Last 3 Months  
*URL*: GET http://localhost:8081/api/rewards/lastThreeMonths 
*Response*:  
```json
[
    {
        "customerId": 1,
        "customerName": "John Doe",
        "monthlyPoints": [
            {
                "month": "NOVEMBER",
                "points": 350,
                "amount": 250.0
            }
        ],
        "totalPoints": 350,
        "totalAmounts": 250.0
    },
    {
        "customerId": 2,
        "customerName": "Jane Smith",
        "monthlyPoints": [
            {
                "month": "DECEMBER",
                "points": 200,
                "amount": 250.0
            }
        ],
        "totalPoints": 200,
        "totalAmounts": 250.0
    }
]

###2. Get Rewards for a Custom Date Range

**POST** `/api/rewards/custom?startDate={startDate}&endDate={endDate}
URL: GET http://localhost:8081/api/rewards/custom?startDate=2024-01-01&endDate=2024-03-31
Response: 
[
    {
        "customerId": 1,
        "customerName": "John Doe",
        "monthlyPoints": [
            {
                "month": "JANUARY",
                "points": 90,
                "amount": 120.0
            }
        ],
        "totalPoints": 90,
        "totalAmounts": 120.0
    },
    {
        "customerId": 2,
        "customerName": "Jane Smith",
        "monthlyPoints": [
            {
                "month": "JANUARY",
                "points": 0,
                "amount": 50.0
            }
        ],
        "totalPoints": 0,
        "totalAmounts": 50.0
    }
]


# Running the Application

Follow the steps below to clone, build, and run the project:

##1. Clone the repository:
 
 Clone the project to your local machine using the following command:

git clone https://github.com/your-username/Infosys_Project_Assignment-.git
cd Infosys_Project_Assignment-


##2. Build the project:

mvn clean install


##3.. Run the application:

mvn spring-boot:run


##4. Access APIs via:

3-months: http://localhost:8081/api/rewards/lastThreeMonths
Custom: http://localhost:8081/api/rewards/custom?startDate=2024-01-01&endDate=2024-03-31

#Steps to Run Tests

##1. Run All Tests: Use Maven to run all tests:

mvn test

##2. Run Specific Tests: Run tests for a specific class:

mvn -Dtest=RewardsServiceTest test

