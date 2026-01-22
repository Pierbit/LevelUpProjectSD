package controller.validators;

import controller.components.RequestValidator;
import model.corso.Corso;
import model.corso.CorsoManager;
import model.storage.ConPool;
import model.utente.Utente;
import model.utente.UtenteManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CorsoValidator {

    static public RequestValidator validateForm(HttpServletRequest request) {
        RequestValidator validator = new RequestValidator(request);
        validator.assertMatch("nome", Pattern.compile("^[a-zA-Z\\s]{5,30}$"), "Il nome di un corso deve essere di 5-30 caratteri e non può" +
                "contenere caratteri speciali.");
        validator.assertDouble("prezzo", "Il prezzo deve essere un decimale");
        validator.assertString("content", "Il campo descrizione è vuoto");
        validator.assertPresence(!(CorsoValidator.isPresent(request)), "Esiste già un corso con quel nome");
        return validator;
    }

    static private boolean isPresent(HttpServletRequest request) {
        CorsoManager service = new CorsoManager(ConPool.getDataSource());
        List<Corso> corsi = new ArrayList<>();
        String nomecorso = request.getParameter("nome");
        try {
            corsi = service.fetchCorsi(0, service.countCorsi());
            for (Corso c : corsi) {
                if (c.getNome().equals(nomecorso)){
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

}
