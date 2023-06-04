package org.example;

import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.example.URLS.*;

public class UserClient  {

    private final Filter requestFilter = new RequestLoggingFilter();
    private final Filter responseFiler = new ResponseLoggingFilter();


    public ValidatableResponse createUser (User user){
        return
                RestAssured
                        .with()
                        .filters(requestFilter, responseFiler)
                        .given()
                        .spec(Specification.getSpec())
                        .body(user)
                        .when()
                        .post(CREATE_USER)
                        .then();
    }

    public ValidatableResponse loginUser (Credentials loginData){
        return given()
                .spec(Specification.getSpec())
                .body(loginData)
                .when()
                .post(LOGIN_USER)
                .then();
    }

    public ValidatableResponse deleteUser (String accessToken){
        return given()
                .spec(Specification.getSpec())
                .header("Authorization", accessToken)
                .when()
                .delete(USER)
                .then();
    }

    public ValidatableResponse updateAuthorizedUserData (String accessToken, Credentials credentials){
        return given()
                .spec(Specification.getSpec())
                .header("Authorization", accessToken)
                .body(credentials)
                .when()
                .patch(USER)
                .then();
    }

    public ValidatableResponse updateUnauthorizedUserData (){
        return given()
                .spec(Specification.getSpec())
                .when()
                .patch(USER)
                .then();
    }
}