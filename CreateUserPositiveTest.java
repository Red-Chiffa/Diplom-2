import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.example.URLS;
import org.example.User;
import org.example.UserClient;
import org.example.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.example.URLS.USER;
import static org.junit.Assert.*;

public class CreateUserPositiveTest {

    private UserClient userClient;
    private User user;
    private String accessToken;


    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.createDefaultUser();
    }



    @Test
    public void createUserTest() {
        ValidatableResponse responseCreate = userClient.createUser(user);
        accessToken = responseCreate.extract().path("accessToken");
        int actualSC = responseCreate.extract().statusCode();
        boolean isUserCreated = responseCreate.extract().path("success");
        System.out.println(accessToken);
        assertEquals("Status Code incorrect", SC_OK,actualSC);
        assertTrue("Expected true", isUserCreated);
    }

    @After
    public void cleanUp() {
        userClient.deleteUser(accessToken);
    }

}





