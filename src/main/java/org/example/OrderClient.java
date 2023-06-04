package org.example;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.example.URLS.ORDER;
import static org.example.URLS.USER;

public class OrderClient {

       public ValidatableResponse createOrderAuthorizedUser (String accessToken, Order order){
        return given()
                .spec(Specification.getSpec())
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDER)
                .then();
    }


    public ValidatableResponse createOrderUnauthorizedUser (Order order){
        return given()
                .spec(Specification.getSpec())
                .body(order)
                .when()
                .post(ORDER)
                .then();
    }


    public ValidatableResponse createOrderWithoutIngredients (String accessToken){
        return given()
                .spec(Specification.getSpec())
                .header("Authorization", accessToken)
                .when()
                .post(ORDER)
                .then();
    }


    public ValidatableResponse getOrdersAuthorizedUser(String accessToken, User vuser){
        return given()
                .spec(Specification.getSpec())
                .header("Authorization", accessToken)
                .when()
                .get(ORDER)
                .then();
    }


    public ValidatableResponse getOrdersUnauthorizedUser (User user){
        return given()
                .spec(Specification.getSpec())
                .when()
                .get(ORDER)
                .then();
    }
}
