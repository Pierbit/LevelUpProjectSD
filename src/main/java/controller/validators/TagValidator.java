package controller.validators;

import controller.RequestValidator;
import model.storage.ConPool;
import model.tag.Tag;
import model.tag.TagManager;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TagValidator {
    static public RequestValidator validateForm(HttpServletRequest request) {
        RequestValidator validator = new RequestValidator(request);
        validator.assertMatch("nome", Pattern.compile("^[a-zA-Z]{3,30}$"), "Il nome del tag deve essere 3-10 caratteri e non può contenere" +
                "caratteri speciali o spazi.");
        validator.assertPresence(!(TagValidator.isPresent(request)), "Esiste già un tag con quel nome");
        return validator;
    }

    static private boolean isPresent(HttpServletRequest request) {
        TagManager service = new TagManager(ConPool.getDataSource());
        List<Tag> tags = new ArrayList<>();
        String tagname = request.getParameter("nome");
        try {
            tags = service.fetchTags(0, service.countTags());
            for (Tag t: tags) {
                if (t.getNome().equals(tagname)){
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
