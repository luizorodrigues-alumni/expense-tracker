# 📟 Expense Tracker API

## 🚀 Overview
This project is a simple **REST API** for managing personal expenses using **Spring Boot**. The API allows users to:
- **Create** new expenses.
- **Update** existing expense details.
- **Delete** expenses from the tracker.
- **View** the list of all recorded expenses.
- **Filter** expenses by date, category, and month.

The API follows **RESTful principles** and uses the **MVC pattern** for structuring the application.

## ♨️ Technologies Used
- **Java**
- **Spring Boot**
- **Spring Data**
- **Spring MVC**
- **Lombok**
- **Swagger**

## 🌐 API Endpoints

### 1. Create a New Expense
**Method:** `POST /expenses`
- Request Body (JSON):
    ```json
    {
      "id": 1,
      "expenseType": 2,
      "date": "2025-03-03",
      "amount": 250.0,
      "category": "Food",
      "account": "Credit Card",
      "note": "Dinner at a restaurant"
    }
    ```
- Response: `201 Created`

### 2. Update an Expense
**Method:** `PUT /expenses/{id}`
- Request Body (JSON):
    ```json
    {
      "id": 1,
      "expenseType": 2,
      "date": "2025-03-03",
      "amount": 300.0,
      "category": "Food",
      "account": "Debit Card",
      "note": "Updated expense details"
    }
    ```
- Response: `204 No Content` (if successful) or `400 Bad Request` / `404 Not Found`

### 3. Delete an Expense
**Method:** `DELETE /expenses/{id}`
- Response: `204 No Content` (if successful) or `404 Not Found`

### 4. Get All Expenses
**Method:** `GET /expenses`
- Response Example:
    ```json
    [
      {
        "id": 1,
        "expenseType": 2,
        "date": "2025-03-03",
        "amount": 250.0,
        "category": "Food",
        "account": "Credit Card",
        "note": "Dinner at a restaurant"
      }
    ]
    ```

### 5. Get Expenses by Day
**Method:** `GET /expenses?day=2025-03-03`
- Response: List of expenses for the given date.

### 6. Get Expenses by Category and Month
**Method:** `GET /expenses?category=Food&month=2025-03`
- Response: List of expenses matching the category and month.

## 📜 REST Principles
1. **Resource-Based**: Each expense is a resource identified by a unique ID.
2. **Statelessness**: Each request contains all the necessary data.
3. **Client-Server Architecture**: The API serves as the backend, and clients (e.g., Postman, frontend apps) interact with it.
4. **Uniform Interface**: Standardized HTTP methods (GET, POST, PUT, DELETE) and response codes are used.

## 🔄 MVC Pattern Explanation
- **Model**: `Expense` class represents the data structure (id, expenseType, date, amount, category, account, note).
- **View**: API responses (JSON) act as the view for the client.
- **Controller**: `ExpenseController` handles HTTP requests.
- **Service**: `ExpenseTrackerService` contains business logic to manage expenses.

## 🏃🏻‍♀️ How to Run the Project
1. Clone the repository:
   ```sh
   git clone https://github.com/luizorodrigues-alumni/expense-tracker.git
   ```
2. Navigate to the project directory:
   ```sh
   cd expense-tracker
   ```
3. Run the application:
   ```sh
   ./gradlew bootRun
   ```
4. Access the API via Postman or your preferred client.

## 📖 API Documentation with Swagger  
This project uses **Swagger UI** to provide interactive API documentation. Swagger helps visualize and test API endpoints directly in the browser.  

### 🛠️ How to Access Swagger UI  
1. **Start the application** following the **How to Run the Project** section.  
2. **Open Swagger UI** in your browser:
   ```sh
   http://localhost:8080/swagger-ui.html
    ```
3. You will see a list of all available API endpoints with descriptions and sample requests.  

### 📝 Testing API via Swagger  
- Click on an endpoint to expand its details.  
- Fill in the required parameters (if any).  
- Click **"Try it out"** and then **"Execute"** to send a request.  
- See the response from the server in real time.  



