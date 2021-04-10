# comparison-service

A simple service is used to compare product price from different resources.

# Description
This demo uses Frameworks/Libraries to create some Rest APIs, such as:
- Register user.
- Get JWT token.
- List all products.
- Get details of a product.

# Frameworks/Libraries:
- Spring Boot
- Spring Security + JWT
- Hibernate
- Spring Data JPA
- H2 Database
- JUnit
- Lombok

# Project structure
![Project structure](https://github.com/vongoccongminh/comparison-service/blob/master/project-structure.png)

# Tables
- users
    ```sql
    CREATE TABLE `comparison-service`.`users` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(45) NOT NULL,
    `password` VARCHAR(45) NULL,
    PRIMARY KEY (`id`));
    ```
- products
    ```sql
    CREATE TABLE `comparison-service`.`products` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `imageUrl` VARCHAR(45) NULL,
    PRIMARY KEY (`id`));
    ```
- product_details
    ```sql
    CREATE TABLE `comparison-service`.`product_details` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `price` FLOAT NOT NULL,
    `discountRate` FLOAT NULL,
    `provider` VARCHAR(45) NOT NULL,
    `product_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_product_details_products`
    FOREIGN KEY (`id`)
    REFERENCES `comparison-service`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    ```
- ER – Diagram

    ![ER – Diagram](https://github.com/vongoccongminh/comparison-service/blob/master/ERD.png)


# Solution for some requirements
- Data of products are stored in the database. An API will get all products from the database, include a list of product details.
- When the customer wants to see product detail, if the product information doesn't exist in the database, the app will get it from 3rd parties ([tiki-service](https://github.com/vongoccongminh/comparison-service/tree/master/tiki-service)) via [APIs](https://github.com/vongoccongminh/comparison-service/blob/master/comparison-service/src/main/java/com/comparison/service/datasource/ProductDataSource.java#L10). Then the information also stored in the database.
- The app can be used cookies to store the activity of customers (searching history,...) and get them when they want.
- In order to limit sending requests to 3rd to decrease the cost, the app will store data of the product in the database after receiving the response. The app will use data in the database to respond to the customer instead of resending requests to 3rd. Finally, the app will update the information of all products by sending requests via running a scheduled batch (maybe at the midnight).

# Steps to run
- Clone source code [comparison-service](https://github.com/vongoccongminh/comparison-service.git)
- Run tiki-service:
    ```sh
    cd tiki-service
    ./mvnw spring-boot:run
    ```
- Run comparison-service:
<br /> Open new terminal
    ```sh
    cd comparison-service
    ./mvnw spring-boot:run
    ```

# CURL commands
- [API register user](https://github.com/vongoccongminh/comparison-service/blob/master/comparison-service/src/main/java/com/comparison/service/controller/LoginController.java#L37), the example `username` is ***admin*** and `password` is ***123456***
    ```sh
    curl --header "Content-Type: application/json" --request POST --data '{"username":"admin","password":"123456"}' http://localhost:8081/api/sign-up | json_pp
    ```
- [API get JWT token](https://github.com/vongoccongminh/comparison-service/blob/master/comparison-service/src/main/java/com/comparison/service/controller/LoginController.java#L46)
    ```sh
    curl --header "Content-Type: application/json" --request POST --data '{"username":"admin","password":"123456"}' http://localhost:8081/api/login | json_pp
    ```
    Response example:
    ```json
    {
        "accessToken" : "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE4MDI2NTY3LCJleHAiOjE2MTgwMjcxNjd9.jMKnBiavOsBQKWiZUr03PoAWeyt3Sy8RV0KXa7hTcMT5CeJTpJ121BGz8Dz3xSxfsHR1ETjKVkL6BATNaYQlfA",
        "tokenType" : "Bearer"
    }
    ```
- [API get all products](https://github.com/vongoccongminh/comparison-service/blob/master/comparison-service/src/main/java/com/comparison/service/controller/ProductController.java#L35). Notice that replace `{accessToken}` by `accessToken` get from above
    ```sh
    curl -H "Content-Type: application/json" -H "Authorization: Bearer {accessToken}" http://localhost:8081/api/product | json_pp
    ```
    Response example:
    ```json
    [
        {
            "product_detail_list" : [],
            "image_url" : "https://fptshop.com.vn/dien-thoai/iphone-12.jpg",
            "name" : "Iphone 12",
            "id" : 1
        },
        {
            "id" : 2,
            "product_detail_list" : [
                {
                    "id" : 1,
                    "discount_rate" : 0.8,
                    "price" : 10.4,
                    "image_url" : "https://fptshop.com.vn/dien-thoai/iphone-12-pro-shopee.jpg",
                    "provider" : "SHOPEE"
                },
                {
                    "discount_rate" : 15,
                    "price" : 20.4,
                    "provider" : "LAZADA",
                    "image_url" : "https://fptshop.com.vn/dien-thoai/iphone-12-pro-lazada.jpg",
                    "id" : 2
                }
            ],
            "name" : "Iphone 12 Pro",
            "image_url" : "https://fptshop.com.vn/dien-thoai/iphone-12-pro.jpg"
        },
        {
            "id" : 3,
            "image_url" : "https://fptshop.com.vn/dien-thoai/iphone-12-pro-max.jpg",
            "name" : "Iphone 12 Pro Max",
            "product_detail_list" : []
        }
    ]
    ```
- [API get product detail](https://github.com/vongoccongminh/comparison-service/blob/master/comparison-service/src/main/java/com/comparison/service/controller/ProductController.java#L42)
    ```sh
    curl -H "Content-Type: application/json" -H "Authorization: Bearer {accessToken}" http://localhost:8081/api/product/2 | json_pp
    ```
    This product has 2 product details from SHOPEE and LAZADA
    ```json
    {
        "id" : 2,
        "product_detail_list" : [
            {
                "provider" : "SHOPEE",
                "id" : 1,
                "price" : 10.4,
                "discount_rate" : 0.8,
                "image_url" : "https://fptshop.com.vn/dien-thoai/iphone-12-pro-shopee.jpg"
            },
            {
                "price" : 20.4,
                "id" : 2,
                "provider" : "LAZADA",
                "image_url" : "https://fptshop.com.vn/dien-thoai/iphone-12-pro-lazada.jpg",
                "discount_rate" : 15
            }
        ],
        "name" : "Iphone 12 Pro",
        "image_url" : "https://fptshop.com.vn/dien-thoai/iphone-12-pro.jpg"
    }
    ```
- [API get product detail from a provider](https://github.com/vongoccongminh/comparison-service/blob/master/comparison-service/src/main/java/com/comparison/service/controller/ProductController.java#L48). Suppose that get from TIKI and this product detail doesn't exist in the database.
    ```sh
    curl -H "Content-Type: application/json" -H "Authorization: Bearer {accessToken}" http://localhost:8081/api/product/product-detail/2/TIKI | json_pp
    ```
    Response example:
    ```json
    {
        "name" : "Iphone 12 Pro",
        "image_url" : "https://fptshop.com.vn/dien-thoai/iphone-12-pro.jpg",
        "id" : 2,
        "product_detail_list" : [
            {
                "provider" : "TIKI",
                "discount_rate" : 1.3,
                "id" : 3,
                "image_url" : "https://fptshop.com.vn/dien-thoai/iphone-12-pro-tiki.jpg",
                "price" : 1998.4
            }
        ]
    }
    ```
- Get product which has `id = 2` again, product detail from TIKI appears
    ```sh
    curl -H "Content-Type: application/json" -H "Authorization: Bearer {accessToken}" http://localhost:8081/api/product/2 | json_pp
    ```
    This product has 3 product details from SHOPEE and LAZADA and TIKI
    ```json
    {
        "id" : 2,
        "product_detail_list" : [
            {
                "price" : 10.4,
                "image_url" : "https://fptshop.com.vn/dien-thoai/iphone-12-pro-shopee.jpg",
                "id" : 1,
                "provider" : "SHOPEE",
                "discount_rate" : 0.8
            },
            {
                "provider" : "LAZADA",
                "id" : 2,
                "price" : 20.4,
                "image_url" : "https://fptshop.com.vn/dien-thoai/iphone-12-pro-lazada.jpg",
                "discount_rate" : 15
            },
            {
                "image_url" : "https://fptshop.com.vn/dien-thoai/iphone-12-pro-tiki.jpg",
                "price" : 1998.4,
                "provider" : "TIKI",
                "id" : 3,
                "discount_rate" : 1.3
            }
        ],
        "name" : "Iphone 12 Pro",
        "image_url" : "https://fptshop.com.vn/dien-thoai/iphone-12-pro.jpg"
    }
    ```