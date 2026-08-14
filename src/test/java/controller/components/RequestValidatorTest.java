package controller.components;

import controller.RequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RequestValidatorTest {

    private HttpServletRequest request;

    @BeforeEach
    void setup() {
        request = mock(HttpServletRequest.class);
    }

    //Testing nonblank values for assertMatch username
    @Test
    void assertStringPassesForNonBlankValue() {
        when(request.getParameter("username")).thenReturn("xyz123");
        RequestValidator validator = new RequestValidator(request);

        boolean result = validator.assertString("username", "Username is required");

        assertTrue(result);
        assertFalse(validator.hasErrors());
    }

    //Testing null values for assertMatch username
    @Test
    void assertStringFailsForNullValue() {
        when(request.getParameter("username")).thenReturn(null);
        RequestValidator validator = new RequestValidator(request);

        boolean result = validator.assertString("username", "Username is required");

        assertFalse(result);
        assertTrue(validator.hasErrors());
        assertEquals(1, validator.getErrors().size());
    }

    //Testing valid pattern values for assertMart username
    @Test
    void assertMatchPassesForValidPattern() {
        when(request.getParameter("username")).thenReturn("xyz123");
        RequestValidator validator = new RequestValidator(request);

        boolean result = validator.assertMatch("username", Pattern.compile("^[\\w.!-]{5,20}$"), "Invalid username");

        assertTrue(result);
    }

    //Testing invalid pattern values for assertMatch username
    @Test
    void assertMatchFailsForInvalidPattern() {
        when(request.getParameter("username")).thenReturn("ab"); // too short
        RequestValidator validator = new RequestValidator(request);

        boolean result = validator.assertMatch("username", Pattern.compile("^[\\w.!-]{5,20}$"), "Invalid username");

        assertFalse(result);
        assertTrue(validator.hasErrors());
    }

    //Testing invalid email values for assertEmail
    @Test
    void assertEmailFailsForInvalidFormat() {
        when(request.getParameter("email")).thenReturn("not-an-email");
        RequestValidator validator = new RequestValidator(request);

        boolean result = validator.assertEmail("email", "Invalid email");

        assertFalse(result);
    }

    //Testing valid password values for assertMatch password
    @Test
    void assertMatchPassesForValidPassword() {
        when(request.getParameter("password")).thenReturn("Password123");
        RequestValidator validator = new RequestValidator(request);

        boolean result = validator.assertMatch("password", Pattern.compile("^(?=.*[A-Z])[\\w.!-]{8,}$"), "Invalid password");

        assertTrue(result);
    }

    //Testing invalid password values for assertMatch password
    @Test
    void assertMatchFailsForPasswordWithoutUppercase() {
        when(request.getParameter("password")).thenReturn("password123"); // no capital letter
        RequestValidator validator = new RequestValidator(request);

        boolean result = validator.assertMatch("password", Pattern.compile("^(?=.*[A-Z])[\\w.!-]{8,}$"), "Invalid password");

        assertFalse(result);
        assertTrue(validator.hasErrors());
    }

    //Testing invalid password values for assertMatch password
    @Test
    void assertMatchFailsForPasswordTooShort() {
        when(request.getParameter("password")).thenReturn("Pass1"); // under 8 chars
        RequestValidator validator = new RequestValidator(request);

        boolean result = validator.assertMatch("password", Pattern.compile("^(?=.*[A-Z])[\\w.!-]{8,}$"), "Invalid password");

        assertFalse(result);
    }
}
