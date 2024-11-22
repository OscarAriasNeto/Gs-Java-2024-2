# Endpoints

## User

| METHOD  | URL                                 | Expects & Returns |
|:-------:|-------------------------------------|----------------|
|POST | [http://localhost:8080/users] | Expects { email, whats } - Inserts a new user |
|POST | [http://localhost:8080/users/login] | Expects { email, whats } - Returns user logged |
|GET | [http://localhost:8080/users] | Returns user list |
|GET | [http://localhost:8080/users/{id}] | Returns user |
|PUT | [http://localhost:8080/users/{id}] | Expects { email, whats } - Updates user |
|DELETE | [http://localhost:8080/users/{id}] | Deletes user |

### User Type

- id: number;
- email: string;
- whats: string;

## Product

| METHOD  | URL                                 | Expects & Returns |
|:-------:|-------------------------------------|----------------|
|POST | [http://localhost:8080/products] | Expects { name, price, voltage, availability } - Inserts a new product |
|GET | [http://localhost:8080/products] | Returns product list |
|GET | [http://localhost:8080/products/{id}] | Returns product |
|PUT | [http://localhost:8080/products/{id}] | Expects { name, price, voltage, availability } - Updates product |
|DELETE | [http://localhost:8080/products/{id}] | Deletes product |

### Product Type

- id: number;
- name: string;
- price: number;
- voltage: number;
- availabilty: "IN_STOCK" | "OUT_OF_STOCK"
