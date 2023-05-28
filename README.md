# E-Commerce Store Backend

This is the backend code for an e-commerce store, providing the necessary APIs and functionalities to support the frontend of the store. It is built using Java and Spring Boot.

## Features

- User authentication and authorization
- Product management (CRUD operations)
- Order management
- Secure payment processing
- Integration with external services (e.g., shipping, email notifications)

## Technologies Used

The backend of the e-commerce store is built using the following technologies and frameworks:

- Java
- Spring Boot
- Spring Data JPA
- JWT (JSON Web Tokens) for authentication
- Stripe for payment processing

## Installation Instructions

1. Clone the repository:

`git clone https://github.com/JaiveerS/ecommerce-store-backend.git`


2. Build the project using Maven:

`cd ecommerce-store-backend`

`mvn clean install`


3. Start the server:

`mvn spring-boot:run`



The backend server will start running on the specified port.

4. Import the Postman collection:

- Download the [Postman Collection JSON file](https://drive.google.com/file/d/14_L0adQOtW6dT4gqw4Du2iwez4XIyRiF/view?usp=drive_link).
- Import the collection into Postman by clicking on "Import" and selecting the downloaded JSON file.
- The collection includes the predefined CRUD operations for the e-commerce store API.

5. Dummy Data Setup:

The backend API can be initialized with dummy data using the following steps:

- Make a `POST` request to the `/api/products` endpoint with the following request body:
  ```json
  {
    "products": [
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
  }
  ```
  This will initialize the backend with the specified dummy products.

6. Deploy the frontend:

To utilize the backend in a more user-friendly way, deploy the corresponding React frontend. Follow the deployment instructions provided in the frontend repository.

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

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - User login
- `GET /api/products` - Retrieve all products
- `GET /api/products/{id}` - Retrieve a specific product by ID
- `POST /api/products` - Create a new product
- `PUT /api/products/{id}` - Update a specific product by ID
- `DELETE /api/products/{id}` - Delete a specific product by ID
- `GET /api/orders` - Retrieve all orders
- `GET /api/orders/{id}` - Retrieve a specific order by ID
- `POST /api/orders` - Create a new order
- `PUT /api/orders/{id}` - Update a specific order by ID
- `DELETE /api/orders/{id}` - Delete a specific order by ID
- `POST /api/payments/process` - Process a payment

For detailed information on each endpoint and their request/response structures, refer to the API documentation in the Postman collection or code comments.

## Deployment

The backend can be deployed to a hosting platform of your choice. Ensure that you configure the necessary environment variables (if applicable) on your deployment environment.

## Frontend Repository

To access the frontend repository and deploy the frontend along with the backend, visit the [E-Commerce Store Frontend Repository](https://github.com/JaiveerS/ecommerce-store-frontend).

## Live Demo

Check out the live demo of the e-commerce store backend at [http://140.238.147.51:8080/api/products](http://140.238.147.51:8080/api/products).

## Troubleshooting and FAQ

**Q: How do I report an issue or contribute to the project?**

A: Please open an issue on the project's GitHub repository or submit a pull request with your proposed changes.

## License

This project is licensed under the [MIT License](LICENSE).

## Contact Information

For any questions or feedback, please contact Jaiveer Singh at jaiveer_@hotmail.com.
