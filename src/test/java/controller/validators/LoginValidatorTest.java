package controller.validators;

import controller.RequestValidator;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginValidatorTest {

    //Testing valid username and password
    @Test
    void validCredentialsProduceNoErrors() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("username")).thenReturn("pier123");
        when(request.getParameter("password")).thenReturn("Password123");

        RequestValidator validator = LoginValidator.validateForm(request);

        assertFalse(validator.hasErrors());
    }

    //Testing missing username produces exactly one error
    @Test
    void missingUsernameProducesError() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("username")).thenReturn(null);
        when(request.getParameter("password")).thenReturn("Password123");

        RequestValidator validator = LoginValidator.validateForm(request);

        assertTrue(validator.hasErrors());
        assertEquals(1, validator.getErrors().size());
    }

    //Testing missing password produces exactly one error
    @Test
    void missingPasswordProducesError() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("username")).thenReturn("pier123");
        when(request.getParameter("password")).thenReturn(null);

        RequestValidator validator = LoginValidator.validateForm(request);

        assertTrue(validator.hasErrors());
        assertEquals(1, validator.getErrors().size());
    }

    //Testing both fields missing produces two separate errors
    @Test
    void bothFieldsMissingProducesTwoErrors() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("username")).thenReturn(null);
        when(request.getParameter("password")).thenReturn(null);

        RequestValidator validator = LoginValidator.validateForm(request);

        assertTrue(validator.hasErrors());
        assertEquals(2, validator.getErrors().size());
    }
}