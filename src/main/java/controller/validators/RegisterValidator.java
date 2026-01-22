package controller.validators;

import controller.components.RequestValidator;
import model.corso.Corso;
import model.corso.CorsoManager;
import model.storage.ConPool;
import model.utente.Utente;
import model.utente.UtenteManager;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RegisterValidator {

    static public RequestValidator validateForm(HttpServletRequest request) {
        RequestValidator validator = new RequestValidator(request);
        validator.assertMatch("username", Pattern.compile("^[\\w.!-]{5,20}$"), "L'username deve essere lungo 5-20 caratteri e non può" +
                "contenere caratteri speciali oltre (./_/!)");
        validator.assertMatch("password", Pattern.compile("^(?=.*[A-Z])[\\w.!-]{8,}$"),"La password deve essere lunga almeno otto caratteri e" +
                "deve contenere almeno una lettera maiuscola.");
        validator.assertEmail("email","L'email non è in formato corretto");
        validator.assertPresence(!(RegisterValidator.isPresent(request)), "Esiste già un utente con quel nome");
        return validator;
    }

    static private boolean isPresent(HttpServletRequest request) {
        UtenteManager service = new UtenteManager(ConPool.getDataSource());
        List<Utente> utenti = new ArrayList<>();
        String nomeutente = request.getParameter("username");
        try {
            utenti = service.fetchUtenti(0, service.countUtenti());
            for (Utente u: utenti) {
                if (u.getNickname().equals(nomeutente)){
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
