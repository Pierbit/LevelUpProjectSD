package controller;

import controller.search.Condition;
import controller.search.CorsoSearch;
import model.carrello.Carrello;
import model.carrello.CarrelloManager;
import model.oggetto.Oggetto;
import model.oggetto.OggettoManager;
import model.ordine.Ordine;
import model.ordine.OrdineManager;
import model.storage.ConPool;
import model.tag.Tag;
import model.tag.TagManager;
import model.utente.Utente;
import model.utente.UtenteManager;

import model.categoria.Categoria;
import model.categoria.CategoriaManager;
import model.corso.Corso;
import model.corso.CorsoManager;
import model.storage.ConPool;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "HomeServlet", value = "/home/*")
public class HomeServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = (request.getPathInfo() != null) ? request.getPathInfo() : "/";
        switch (path) {
            case "/":
                break;

            case "/browseCorsi": {

                String categoria = request.getParameter("categoriaName");
                if (categoria == null || categoria.isBlank()) {
                    response.sendRedirect(request.getContextPath());
                    return;
                }

                CorsoManager service = new CorsoManager(ConPool.getDataSource());
                TagManager service2 = new TagManager(ConPool.getDataSource());

                List<Condition> conditions = new CorsoSearch().buildSearch(request);
                ArrayList<Corso> corsi = new ArrayList<>();
                ArrayList<Tag> tags = new ArrayList<>();

                try {
                    corsi = (ArrayList<Corso>) service.search(conditions);
                    tags = (ArrayList<Tag>) service2.fetchTags(0, service2.countTags());
                    for (Corso corso : corsi) {
                        corso.setUtenteCreatore(service.fetchUtenteCreatore(corso.getId()));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    response.sendError(500, "Errore interno");
                    return;
                }

                request.setAttribute("tags", tags);
                request.setAttribute("categoriacercata", categoria);
                request.setAttribute("corsicercati", corsi);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/home/browseCorsi.jsp");
                dispatcher.forward(request, response);
                break;

            }

            case "/loadCategorie": {

                CategoriaManager service = new CategoriaManager(ConPool.getDataSource());
                ArrayList<Categoria> categorie = new ArrayList<>();
                JSONObject root = new JSONObject();
                JSONArray arr = new JSONArray();
                try {
                    categorie = (ArrayList<Categoria>) service.fetchCategorie(0, service.countCategorie());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    response.sendError(500, "Errore interno");
                }
                root.put("categorie", arr);
                for (Categoria c : categorie) {
                    arr.put(c.toJson());
                }
                sendJson(response, root);
                break;

            }

            case "/visualizzaCarrello": {


                HttpSession session = request.getSession();
                Carrello carrello = (Carrello) session.getAttribute("carrello");
                CorsoManager service = new CorsoManager(ConPool.getDataSource());
                List<Oggetto> oggetti = new ArrayList<>();
                List<Corso> corsi = new ArrayList<>();

                oggetti = carrello.getOggetti();

                try {
                    if (oggetti != null) {
                        for (Oggetto o : oggetti) {
                            Corso corso = o.getCorso();
                            corso.setUtenteCreatore(service.fetchUtenteCreatore(corso.getId()));
                            corsi.add(corso);
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    response.sendError(500, "Errore interno");
                    return;
                }
                request.setAttribute("oggettinelcarrello", corsi);
                request.getRequestDispatcher("/WEB-INF/views/user/results/carrello.jsp").forward(request, response);
                break;
            }

            case "/publish": {
                request.getRequestDispatcher("/WEB-INF/views/home/createCorso.jsp").forward(request, response);
                break;
            }

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Risorsa non trovata");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = (request.getPathInfo() != null) ? request.getPathInfo() : "/";
        switch (path) {
            case "/":
                break;

            case "/aggiungiAlCarrello": {

                int corsoselezionatoid = Integer.parseInt(request.getParameter("corsoid"));
                CorsoManager service1 = new CorsoManager(ConPool.getDataSource());
                HttpSession session = request.getSession();
                Carrello carrello = (Carrello) session.getAttribute("carrello");
                try {

                    Corso corso = service1.fetchCorso(corsoselezionatoid);
                    Oggetto oggetto = new Oggetto();
                    oggetto.setId((int) (new Date().getTime() / 1000));
                    oggetto.setPrezzo(corso.getPrezzoBase());
                    oggetto.setCorso(corso);
                    carrello.addOggetto(oggetto);
                    session.setAttribute("carrello", carrello);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    response.sendError(500, "Errore interno");
                    return;
                }

                response.sendRedirect(request.getContextPath() + "/home/visualizzaCarrello");
                break;
            }

            case "/rimuoviDalCarrello": {

                HttpSession session = request.getSession();
                Carrello carrello = (Carrello) session.getAttribute("carrello");
                int corsoselezionato = Integer.parseInt(request.getParameter("corsoid"));
                ArrayList<Oggetto> oggetti = (ArrayList<Oggetto>) carrello.getOggetti();
                ArrayList<Oggetto> toRemove = new ArrayList<>();


                for (Oggetto o : oggetti) {
                    if (o.getCorso().getId() == corsoselezionato) {
                        toRemove.add(o);
                    }
                }

                oggetti.removeAll(toRemove);
                carrello.setOggetti(oggetti);
                session.setAttribute("carrello", carrello);
                response.sendRedirect(request.getContextPath() + "/home/visualizzaCarrello");
                break;
            }

            case "/acquistaCarrello": {

                OrdineManager service = new OrdineManager(ConPool.getDataSource());
                UtenteManager service1 = new UtenteManager(ConPool.getDataSource());
                CarrelloManager service2 = new CarrelloManager(ConPool.getDataSource());
                OggettoManager service3 = new OggettoManager(ConPool.getDataSource());
                CorsoManager service4 = new CorsoManager(ConPool.getDataSource());
                HttpSession session = request.getSession();
                Carrello carrello = (Carrello) session.getAttribute("carrello");
                Utente utente = (Utente) session.getAttribute("utente");
                Ordine ordine = new Ordine();
                ordine.setId(((int) (new Date().getTime() / 1000)));
                ordine.setData(LocalDate.now());
                try {

                    for (Oggetto o : carrello.getOggetti()) {
                        service3.deleteOggetto(o.getId());
                        service3.createOggetto(o);
                        service3.insertCorsoOggetto(o.getCorso().getId(), o.getId());
                        service4.insertPartecipante(utente.getNickname(), o.getCorso().getId());
                        service3.insertOggettoCarrello(o.getId(), carrello.getId());
                    }

                    service.createOrdine(ordine);
                    service.insertUtenteOrdine(utente.getNickname(), ordine.getId());
                    service.insertCarrelloOrdine(carrello.getId(), ordine.getId());
                    service2.deleteUtenteCarrello(utente.getNickname());

                    Carrello nuovocarrello = new Carrello();
                    nuovocarrello.setId((int) (new Date().getTime() / 1000));
                    nuovocarrello.setOggetti(new ArrayList<>());
                    service2.createCarrello(nuovocarrello);
                    service1.setCarrello(utente.getNickname(), nuovocarrello.getId());

                    session.removeAttribute("carrello");
                    session.setAttribute("carrello", nuovocarrello);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    response.sendError(500, "Errore interno");
                    return;
                }
                response.sendRedirect(request.getContextPath());
                break;
            }
        }
    }

    protected void sendJson(HttpServletResponse response, JSONObject object) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.print(object.toString());
        writer.flush();
    }
}
