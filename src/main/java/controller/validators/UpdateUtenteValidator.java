package controller.validators;

import controller.components.RequestValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class UpdateUtenteValidator {
    static public RequestValidator validateForm(HttpServletRequest request) {
        RequestValidator validator = new RequestValidator(request);
        validator.assertMatch("username", Pattern.compile("^[\\w.!-]{5,20}$"), "L'username deve essere lungo 5-20 caratteri e non può" +
                "contenere caratteri speciali oltre (./_/!)");
        validator.assertMatch("password", Pattern.compile("^(?=.*[A-Z])[\\w.!-]{8,}$"),"La password deve essere lunga almeno otto caratteri e" +
                "deve contenere almeno una lettera maiuscola.");
        validator.assertEmail("email","L'email non è in formato corretto");
        return validator;
    }
}
