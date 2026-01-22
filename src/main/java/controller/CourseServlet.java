package controller;

import model.carrello.Carrello;
import model.carrello.CarrelloManager;
import model.corso.Corso;
import model.corso.CorsoManager;
import model.oggetto.Oggetto;
import model.oggetto.OggettoManager;
import model.storage.ConPool;
import model.utente.Utente;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CourseServlet", value = "/course/*")
public class CourseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = (request.getPathInfo() != null) ? request.getPathInfo() : "/";
        switch (path) {
            case "/":
                break;
            case "/view": {
                int idcorso = Integer.parseInt(request.getParameter("id"));
                HttpSession session = request.getSession();
                CorsoManager corsoManager = new CorsoManager(ConPool.getDataSource());
                CarrelloManager carrelloManager = new CarrelloManager(ConPool.getDataSource());
                Carrello carrello = (Carrello) session.getAttribute("carrello");
                Corso corso = null;
                try {
                    corso = corsoManager.fetchCorso(idcorso);
                    if (corso == null)
                        response.sendError(404, "Non abbiamo trovato nulla...");
                    else {
                        corso.setUtenteCreatore(corsoManager.fetchUtenteCreatore(corso.getId()));
                        boolean token = false;
                        boolean contains = false; //Variabile che indica se il corso è presente nel carrello
                        Utente utente = (Utente) request.getSession().getAttribute("utente");
                        if (utente != null) {
                            if (corso.getUtenteCreatore().getNickname().equals(utente.getNickname()))
                                token = true;
                            else {
                                for (Utente u: corsoManager.fetchUtentiPartecipanti(corso.getId())) {
                                    if (u.getNickname().equals(utente.getNickname())) {
                                        token = true;
                                        break;
                                    }
                                }
                            }
                        }

                        if (!token) {

                            for (Oggetto o: carrello.getOggetti()) {
                                if (o.getCorso().getId() == corso.getId()) {
                                    contains = true;
                                    break;
                                }
                            }
                        }

                        request.setAttribute("corso", corso);
                        request.setAttribute("token", token);
                        request.setAttribute("contains", contains); //Viene impostata la variabile nella request
                        request.getRequestDispatcher("/WEB-INF/views/course/course.jsp").forward(request, response);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    response.sendError(500, "Errore interno");
                }
                break;
            }
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Risorsa non trovata");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
