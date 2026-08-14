package controller.validators;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegisterValidatorTest {

    //Testing valid username passes
    @Test
    void validUsernameAccepted() {
        assertTrue(RegisterValidator.isValidUsername("pier123"));
    }

    //Testing username too short is rejected
    @Test
    void usernameTooShortRejected() {
        assertFalse(RegisterValidator.isValidUsername("ab"));
    }

    //Testing username with disallowed special characters is rejected
    @Test
    void usernameWithInvalidCharactersRejected() {
        assertFalse(RegisterValidator.isValidUsername("pier@123"));
    }

    //Testing null username is rejected
    @Test
    void nullUsernameRejectedSafely() {
        assertFalse(RegisterValidator.isValidUsername(null));
    }

    //Testing valid password passes
    @Test
    void validPasswordAccepted() {
        assertTrue(RegisterValidator.isValidPassword("Password123"));
    }

    //Testing no uppercase password is rejected
    @Test
    void passwordWithoutUppercaseRejected() {
        assertFalse(RegisterValidator.isValidPassword("password123"));
    }

    //Testing password shorter than 8 characters is rejected
    @Test
    void passwordTooShortRejected() {
        assertFalse(RegisterValidator.isValidPassword("Pass1"));
    }

    //Testing null password is rejected
    @Test
    void nullPasswordRejectedSafely() {
        assertFalse(RegisterValidator.isValidPassword(null));
    }
}
