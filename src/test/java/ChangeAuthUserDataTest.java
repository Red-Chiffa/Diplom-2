import io.restassured.response.ValidatableResponse;
import org.example.Credentials;
import org.example.User;
import org.example.UserClient;
import org.example.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;

public class ChangeAuthUserDataTest {
    private UserClient userClient;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.createDefaultUser();
    }


    @Test
    public void authorizedUserChangeNameTest(){
        userClient.createUser(user);
        ValidatableResponse responseLogin = userClient.loginUser(Credentials.from(user));
        accessToken = responseLogin.extract().path("accessToken");
        String  userOldName = user.getName();
        user.setName("user2");
        boolean isUserChangeData = responseLogin.extract().path("success");
        String newName = user.getName();
        int actualSC = responseLogin.extract().statusCode();
        assertEquals("Status Code incorrect", actualSC, SC_OK);
        assertTrue("Expected true", isUserChangeData);
        assertNotEquals("Expected different name",userOldName, newName);
    }
    @Test
    public void authorizedUserChangeEmailTest (){
        userClient.createUser(user);
        ValidatableResponse responseLogin = userClient.loginUser(Credentials.from(user));
        accessToken = responseLogin.extract().path("accessToken");
        String OldEmail =  user.getEmail();
        user.setEmail("user@gmail.com");
        boolean isUserChangeData = responseLogin.extract().path("success");
        String newEmail = user.getEmail();
        int actualSC = responseLogin.extract().statusCode();
        assertEquals("Status Code incorrect", actualSC, SC_OK);
        assertTrue("Expected true", isUserChangeData);
        assertNotEquals("Expected different Email", OldEmail, newEmail);
    }
    @Test
    public void authorizedUserChangePasswordTest(){
        ValidatableResponse responseCreate = userClient.createUser(user);
        ValidatableResponse responseLogin = userClient.loginUser(Credentials.from(user));
        accessToken = responseCreate.extract().path("accessToken");
        String oldPassword = user.getPassword();
        user.setPassword("password2");
        boolean isUserChangeData = responseLogin.extract().path("success");
        String newPassword = user.getPassword();
        int actual = responseLogin.extract().statusCode();
        assertEquals("Status Code incorrect", actual, SC_OK);
        assertTrue("Expected true", isUserChangeData);
        assertNotEquals("Expected different Email",oldPassword, newPassword);
    }

    @After
    public void cleanUp() {
        userClient.deleteUser(accessToken);
    }



}
