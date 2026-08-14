package controller.validators;

import controller.RequestValidator;

import javax.servlet.http.HttpServletRequest;

public class LoginValidator {
    static public RequestValidator validateForm(HttpServletRequest request) {
        RequestValidator validator = new RequestValidator(request);
        validator.assertString("username","Il campo username è vuoto");
        validator.assertString("password","Il campo password è vuoto");
        return validator;
    }
}
