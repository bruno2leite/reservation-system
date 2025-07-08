# 🍽️ Reservation System - Java Spring Boot

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/springboot-%236DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)

This project is a restaurant table reservation system built with Java Spring Boot and PostgreSQL. Users can create reservations for available tables, and the system automatically checks for conflicts based on the reservation time.

---

## 🛠️ Technologies Used

- Java 17  
- Spring Boot  
- Spring Web  
- Spring Data JPA  
- Spring Security  
- PostgreSQL  
- Maven

---

## ⚙️ How It Works

1. **User authentication** is used to link reservations to the logged-in user.
2. **Time validation** ensures reservations are only made during working hours.
3. **Conflict detection** prevents double booking of tables.
4. **Automatic best table selection** is based on the number of people in the reservation.

---

## 🧪 Endpoints Overview

### ➕ Create Reservation

```
POST /reserve
```

**Request Body:**
```json
{
  "quantity": 4,
  "date": "19:00"
}
```

**Response:**
```json
{
  "id": "uuid",
  "reservationTime": "19:00",
  "endTime": "20:00",
  "tableId": "table-uuid",
  "userId": "user-uuid"
}
```

---

### 📋 List My Reservations

```
GET /reserve
```

---

### ❌ Cancel Reservation

```
DELETE /reserve/remove/{id}
```

Only the user who created the reservation can cancel it.

---

## 🏁 Running the Project Locally

### 📚 Requirements

- Java 17
- PostgreSQL
- Maven

### 🧱 PostgreSQL Setup

Create a database:

```sql
CREATE DATABASE reservation_db;
```

Update `src/main/resources/application.properties`:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/reservation_db
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.jpa.hibernate.ddl-auto=update
```

### ▶️ Run

```
mvn spring-boot:run
```

---

## 📌 Notes

- All reservations last 1 hour.
- The restaurant only operates between **18:00 (6pm)** and **23:00 (11pm)**.
- Table conflict resolution is based on reservation time overlap.

---

## 👨‍💻 Author

Developed by Bruno Leite  
🔗 [linkedin.com/in/brunoprestesleite](https://linkedin.com/in/brunoprestesleite)
