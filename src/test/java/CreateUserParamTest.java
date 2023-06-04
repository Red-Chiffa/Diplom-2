import io.restassured.response.ValidatableResponse;
import org.example.User;
import org.example.UserClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.example.UserGenerator;
@RunWith(Parameterized.class)
public class CreateUserParamTest {


        private UserClient userClient;
        private User user;
        private int statusCode;
        private String message;

        public CreateUserParamTest(User user, int statusCode, String message) {
            this.user = user;
            this.statusCode = statusCode;
            this.message = message;
        }
        //test data
        @Parameterized.Parameters
        public static Object[][] getTestData(){
            return new Object[][]{
                    {UserGenerator.loginWithNoPassword(),SC_FORBIDDEN,"Email, password and name are required fields"},
                    {UserGenerator.loginWithNoEmail(),SC_FORBIDDEN,"Email, password and name are required fields"}
            };
        }

        @Before
        public void setUp() {
            userClient = new UserClient();
        }

        @Test
        public void createUserWithOutOneParameter(){
            ValidatableResponse responseCreate = userClient.createUser(user);
            int actualSC = responseCreate.extract().statusCode();
            boolean isUserCreated = responseCreate.extract().path("success");
            String actualMessage = responseCreate.extract().path("message" );
            assertEquals("Status Code incorrect",statusCode, actualSC);
            assertFalse("Expected false",isUserCreated);
            assertEquals("Message incorrect",message,actualMessage);
        }
    }