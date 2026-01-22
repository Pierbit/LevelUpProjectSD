package controller.search;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class CorsoSearch implements SearchBuilder {

    @Override
    public List<Condition> buildSearch(HttpServletRequest request) {
        List<Condition> conditions = new ArrayList<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String param = parameterNames.nextElement();

            switch (param) {

                case "creatorName": {
                    String value = request.getParameter(param);
                    if(value!=null && !value.isBlank()) {
                        conditions.add(new Condition("nicknameUtente", Operator.MATCH, value, "utenteCreaCorso"));
                    }
                    break;
                }

                case "corsoName": {
                    String value = request.getParameter(param);
                    if(value!=null && !value.isBlank()) {
                        conditions.add(new Condition("nome", Operator.MATCH, value, "corso"));
                    }
                    break;
                }

                case "minPrice": {
                    String value = request.getParameter(param);
                    if(value!=null && !value.isBlank()) {
                        conditions.add(new Condition("prezzoBase", Operator.GE, value, "corso"));
                    }
                    break;
                }

                case "maxPrice": {
                    String value = request.getParameter(param);
                    if(value!=null && !value.isBlank()) {
                        conditions.add(new Condition("prezzoBase", Operator.LE, value, "corso"));
                    }
                    break;
                }

                case "categoriaName": {
                    String value = request.getParameter(param);
                    if(value!=null && !value.isBlank()) {
                        conditions.add(new Condition("nomeCategoria",Operator.MATCH,value,"corsoCategoria"));
                    }
                    break;
                }

                case "tagNames": {
                    String[] value = request.getParameterValues(param);
                    System.out.println(value.length);
                    for(int i = 0; i < value.length; i++){
                        System.out.println("diocane");
                        if(value[i]!=null && !value[i].isBlank()) {
                            conditions.add(new Condition("nomeTag", Operator.EQ,value[i],"corsoTag"));
                        }
                    }
                    break;
                }
            }
        }
        return conditions;
    }
}
