package controller.validators;

import controller.RequestValidator;
import model.categoria.Categoria;
import model.categoria.CategoriaManager;
import model.storage.ConPool;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CategoriaValidator {
    static public RequestValidator validateForm(HttpServletRequest request) {
        RequestValidator validator = new RequestValidator(request);
        validator.assertMatch("nome", Pattern.compile("^[a-zA-Z]{3,30}$"), "Il nome della categoria deve essere 3-10 caratteri e non può contenere" +
                "caratteri speciali o spazi.");
        validator.assertPresence(!(CategoriaValidator.isPresent(request)), "Esiste già una categoria con quel nome");
        return validator;
    }

    static private boolean isPresent(HttpServletRequest request) {
        CategoriaManager service = new CategoriaManager(ConPool.getDataSource());
        List<Categoria> categorie = new ArrayList<>();
        String categoria = request.getParameter("nome");
        try {
            categorie = service.fetchCategorie(0, service.countCategorie());
            for (Categoria c: categorie) {
                if (c.getNome().equals(categoria)){
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
