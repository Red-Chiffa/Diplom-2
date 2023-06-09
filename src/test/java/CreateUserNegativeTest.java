import io.restassured.response.ValidatableResponse;
import org.example.User;
import org.example.UserClient;
import org.example.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CreateUserNegativeTest {
    private UserClient userClient;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.createDefaultUser();
        ValidatableResponse responseCreate = userClient.createUser(user);
        accessToken = responseCreate.extract().path("accessToken");
    }



    @Test
     public void createTwinUserTest(){
        ValidatableResponse responseCreate = userClient.createUser(user);
        int actual = responseCreate.extract().statusCode();
        boolean isUserCreated = responseCreate.extract().path("success");
        String actualMessage = responseCreate.extract().path("message" );
        assertEquals("Expected 403",SC_FORBIDDEN, actual);
        assertEquals("User already exists",actualMessage);
        assertFalse(isUserCreated);
    }


    @After
    public void cleanUp(){
        userClient.deleteUser(accessToken);
    }
}