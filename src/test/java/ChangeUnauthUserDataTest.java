import io.restassured.response.ValidatableResponse;
import org.example.User;
import org.example.UserClient;
import org.example.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ChangeUnauthUserDataTest {

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
    public void unauthorizedUserCantChangeEmail() {
        user.setEmail("newEmail@gmail.com");
        ValidatableResponse responseUpdateData = userClient.updateUnauthorizedUserData();
        int actualStatusCodeChange = responseUpdateData.extract().statusCode();
        boolean isUserChangeData = responseUpdateData.extract().path("success");
        String changeDataMessage = responseUpdateData.extract().path("message");
        assertEquals("Expected 401", SC_UNAUTHORIZED, actualStatusCodeChange);
        assertFalse("Expected false", isUserChangeData);
        assertEquals("You should be authorised", changeDataMessage);
        userClient.deleteUser(accessToken);
    }

    @Test
    public void unauthorizedUserChangePasswordTest () {
        user.setPassword("54641fdght");
        ValidatableResponse responseUpdateData = userClient.updateUnauthorizedUserData();
        int actualStatusCodeChange = responseUpdateData.extract().statusCode();
        boolean isUserChangeData = responseUpdateData.extract().path("success");
        String changeDataMessage = responseUpdateData.extract().path("message");
        assertEquals("Expected 401", SC_UNAUTHORIZED, actualStatusCodeChange);
        assertFalse("Expected false", isUserChangeData);
        assertEquals("You should be authorised", changeDataMessage);
        userClient.deleteUser(accessToken);
    }

    @Test
    public void unauthorizedUserChangeNameTest () {
        user.setName("NewName");
        ValidatableResponse responseUpdateData = userClient.updateUnauthorizedUserData();
        int actualStatusCodeChange = responseUpdateData.extract().statusCode();
        boolean isUserChangeData = responseUpdateData.extract().path("success");
        String changeDataMessage = responseUpdateData.extract().path("message");
        assertEquals("Expected 401", SC_UNAUTHORIZED, actualStatusCodeChange);
        assertFalse("Expected false", isUserChangeData);
        assertEquals("You should be authorised", changeDataMessage);
    }


    @After
    public void cleanUp() {
        userClient.deleteUser(accessToken);
    }
}

