package controller.validators;

import controller.components.RequestValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class UpdateCorsoValidator {
    static public RequestValidator validateForm(HttpServletRequest request) {
        RequestValidator validator = new RequestValidator(request);
        validator.assertMatch("nome", Pattern.compile("^[a-zA-Z\\s]{5,30}$"), "Il nome di un corso deve essere di 5-30 caratteri e non può" +
                "contenere caratteri speciali.");
        validator.assertDouble("prezzo", "Il prezzo deve essere un decimale");
        validator.assertString("content", "Il campo descrizione è vuoto");
        return validator;
    }
}
