import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class OrderTest {
    private OrderClient orderClient;
    private String accessToken;
    private User user;
    private UserClient userClient;

    @Before
    public void setUp() {
        userClient = new UserClient();
        orderClient = new OrderClient();
        user = UserGenerator.createDefaultUser();
        ValidatableResponse responseCreate = userClient.createUser(user);
        accessToken = responseCreate.extract().path("accessToken");
    }

    @After
    public void cleanUp() {
        userClient.deleteUser(accessToken);
    }

    @Test
    public void orderCreatedByAuthorizedUserTest () {
        Order order = new Order();
        List<String> orderList = new ArrayList<>(Arrays.asList("61c0c5a71d1f82001bdaaa6c","61c0c5a71d1f82001bdaaa79"));
        order.setIngredients(orderList);
        ValidatableResponse createOrder = orderClient.createOrderAuthorizedUser(accessToken, order);
        int actual = createOrder.extract().statusCode();
        boolean isOrderCreated = createOrder.extract().path("success");
        assertEquals("Status Code incorrect", SC_OK, actual);
        assertTrue("Expected true", isOrderCreated);
    }

    @Test
    public void orderCreatedByUnauthorizedUserTest () {
        Order order = new Order();
        List<String> orderList = new ArrayList<>(Arrays.asList("61c0c5a71d1f82001bdaaa6c","61c0c5a71d1f82001bdaaa79"));
        order.setIngredients(orderList);
        ValidatableResponse createOrder = orderClient.createOrderUnauthorizedUser(order);
        int actual = createOrder.log().all().extract().statusCode();
        boolean isOrderCreated = createOrder.extract().path("success");
        assertEquals("Status Code incorrect", SC_OK,actual);
  //      assertTrue("Expected True", isOrderCreated);
    }

    @Test
    public void orderCreatedWithoutIngredientsTest () {
        ValidatableResponse createOrder = orderClient.createOrderWithoutIngredients(accessToken);
        int actualSC = createOrder.extract().statusCode();
        boolean isOrderCreated = createOrder.extract().path("success");
        String orderMessage = createOrder.extract().path("message");
        assertEquals("Expected 400", SC_BAD_REQUEST, actualSC);
        assertFalse("Expected false", isOrderCreated);
        assertEquals("Ingredient ids must be provided", orderMessage);
    }

    @Test
    public void orderCreatedWithIncorrectIngredientsTest() {
        Order order = new Order();
        List<String> orderList = new ArrayList<>(Arrays.asList("61c0c5a71d1f82001bdaaa", "61c0c5a71d1f82001bdaaa6f"));
        order.setIngredients(orderList);
        ValidatableResponse createOrder = orderClient.createOrderAuthorizedUser(accessToken, order);
        int actual = createOrder.extract().statusCode();
        assertEquals("Status Code incorrect", SC_INTERNAL_SERVER_ERROR, actual);
    }

    @Test
    public void getOrdersAuthorizedUser() {
        Order order = new Order();
        List<String> orderList = new ArrayList<>(Arrays.asList("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"));
        order.setIngredients(orderList);
        ValidatableResponse createOrder = orderClient.createOrderAuthorizedUser(accessToken, order);
        ValidatableResponse getOrder = orderClient.getOrdersAuthorizedUser(accessToken, user);
        int actual = getOrder.extract().statusCode();
        boolean isOrdersDisplayed = getOrder.extract().path("success");
        assertEquals("Status Code incorrect", SC_OK, actual);
        assertTrue("Expected True", isOrdersDisplayed);
    }

    @Test
    public void getOrdersUnauthorizedUser() {
        ValidatableResponse getOrder = orderClient.getOrdersUnauthorizedUser(user);
        int actual = getOrder.extract().statusCode();
        boolean isOrdersDisplayed = getOrder.extract().path("success");
        String actualMessage = getOrder.extract().path("message");
        assertEquals("Status Code incorrect", SC_UNAUTHORIZED, actual);
        assertFalse("Expected False", isOrdersDisplayed);
        assertEquals("You should be authorised", actualMessage);
    }
}
