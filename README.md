# E-Commerce Store Backend

This is the backend code for an e-commerce store, providing the necessary APIs and functionalities to support the
frontend of the store. It is built using Java and Spring Boot.

## Features

- User authentication and authorization
- Product management (CRUD operations)
- Order management
- Secure payment processing (IN PROGRESS)

## Technologies Used

The backend of the e-commerce store is built using the following technologies and frameworks:

- Java
- Spring Boot
- Spring Data JPA
- JWT (JSON Web Tokens) for authentication

## Installation Instructions

1. Clone the repository:

`git clone https://github.com/JaiveerS/ecommerce-store-backend.git`

2. Build the project using Maven:

`cd ecommerce-store-backend`

`mvn clean install`

3. Start the server:

`mvn spring-boot:run`

The backend server will start running on the specified port.
(add secret key for jwt token signature in applications.yml)

4. Dummy Data Setup:

The backend API can be initialized with dummy data using the following steps:

- Make a `POST` request to the `/api/products` endpoint with the following request body:
  ```json
  [
      {
        "productName": "Product 1",
        "price": 19.99,
        "category": "Category 1",
        "description": "This is product 1",
        "image": "https://example.com/product1.jpg"
      },
      {
        "productName": "Product 2",
        "price": 29.99,
        "category": "Category 2",
        "description": "This is product 2",
        "image": "https://example.com/product2.jpg"
      }
      // Add more dummy product objects as needed
  ]
  ```
  This will initialize the backend with the specified dummy products.

5. Deploy the frontend:

To utilize the backend in a more user-friendly way, deploy the corresponding React frontend. Follow the deployment
instructions provided in the frontend repository.

- Clone the frontend repository:

  ```
  git clone https://github.com/JaiveerS/ecommerce-store-frontend.git
  ```

- Install the dependencies:

  ```
  cd ecommerce-store-frontend
  npm install
  ```

- Start the development server:

  ```
  npm start
  ```

The frontend will be accessible at [http://localhost:3000](http://localhost:3000) by default.

## API Endpoints

The following are the main API endpoints provided by the backend:

Check all users: GET method to "/api/users"

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - User login
- `GET /api/auth/login` - Retrieve User information from JWT token
- `GET /api/products` - Retrieve all products
- `GET /api/products/{id}` - Retrieve a specific product by ID
- `POST /api/product` - Add a product
- `POST /api/products` - Add a list of products
- `GET /api/categories` - Retrieve all categories
- `GET /api/categories/{category}` - Retrieve all products with a given category

Secured Endpoints (require JWT in header)

- `GET /api/orders/all` - Retrieves all orders
- `POST /api/orders/order` - Creates a new order
- `GET /api/orders/{id}` - Retrieves order with specified id

For detailed information on each endpoint and their request/response structures, refer to the API documentation in the
Postman collection or code comments.

## Deployment

The backend can be deployed to a hosting platform of your choice. Ensure that you configure the necessary environment
variables (if applicable) on your deployment environment.

## Frontend Repository

To access the frontend repository and deploy the frontend along with the backend, visit
the [E-Commerce Store Frontend Repository](https://github.com/JaiveerS/ecommerce-store-frontend).

## Live Demo

Check out the live demo of the e-commerce store backend
at [http://129.153.49.68:8080/api/products](http://129.153.49.68:8080/api/products).

The frontend which uses this backend is accessible at [http://129.153.49.68/](http://140.238.147.51/).

## Troubleshooting and FAQ

**Q: How do I report an issue or contribute to the project?**

A: Please open an issue on the project's GitHub repository or submit a pull request with your proposed changes.

## License

This project is licensed under the [MIT License](LICENSE).

## Contact Information

For any questions or feedback, please contact Jaiveer Singh at jaiveer_@hotmail.com.
