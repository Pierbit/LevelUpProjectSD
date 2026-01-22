package controller;

import controller.components.InvalidRequestException;
import controller.components.Paginator;
import controller.components.RequestValidator;
import controller.validators.CorsoValidator;
import controller.validators.LoginValidator;
import controller.validators.RegisterValidator;
import controller.validators.UpdateUtenteValidator;
import model.carrello.Carrello;
import model.carrello.CarrelloManager;
import model.categoria.Categoria;
import model.categoria.CategoriaManager;
import model.corso.Corso;
import model.corso.CorsoManager;
import model.oggetto.Oggetto;
import model.oggetto.OggettoManager;
import model.ordine.Ordine;
import model.ordine.OrdineManager;
import model.storage.ConPool;
import model.storage.Upload;
import model.utente.Utente;
import model.utente.UtenteManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

@WebServlet(name = "UserServlet", value = "/user/*") @MultipartConfig
public class UserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = (request.getPathInfo() != null) ? request.getPathInfo() : "/";
        switch (path) {

            case "/":
                break;

            case "/goindex": {
                response.sendRedirect(request.getContextPath());
                break;
            }

            case "/profile": {
                UtenteManager service = new UtenteManager(ConPool.getDataSource());
                CorsoManager service1 = new CorsoManager(ConPool.getDataSource());
                Utente user = null;
                try {
                    user = service.fetchUtente(request.getParameter("nickname"));
                    if (user != null) {
                        user.setCorsiCreati(service.fetchCorsiPubblicati(user.getNickname()));
                        user.setCorsiPartecipati(service.fetchCorsiPartecipati(user.getNickname()));
                        for(Corso c: user.getCorsiPartecipati()){
                            c.setUtenteCreatore(service1.fetchUtenteCreatore(c.getId()));
                        }
                        request.setAttribute("utente", user);
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/user/userprofile.jsp");
                        dispatcher.forward(request,response);
                    } else {
                        response.sendError(404, "Risorsa non trovata");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    response.sendError(500, "Errore interno");
                }
                break;
            }

            case "/showAllSubscribed" : {
                if (request.getSession().getAttribute("utente") != null) {
                    UtenteManager service = new UtenteManager(ConPool.getDataSource());
                    HttpSession session = request.getSession();
                    Utente user = (Utente) session.getAttribute("utente");
                    String temp = request.getParameter("page");
                    if(temp == null){
                        temp = "1";
                    }
                    int intPage = Integer.parseInt(temp);
                    Paginator paginator = new Paginator(intPage,5);
                    int size = 0;
                    ArrayList<Corso> corsipartecipati = new ArrayList<>();
                    try{
                        corsipartecipati = (ArrayList<Corso>) service.fetchCorsiPartecipatiLimit(user.getNickname(), paginator.getOffset(), paginator.getLimit());
                        size = service.countCorsiPartecipati(user.getNickname());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    request.setAttribute("pages",paginator.getPages(size));
                    request.setAttribute("corsipartecipati",corsipartecipati);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/user/results/showAllSubscribed.jsp");
                    dispatcher.forward(request,response);
                    break;
                } else {
                    response.sendError(403, "Operazione non autorizzata");
                }
            }

            case "/showAllPublished" : {
                if (request.getSession().getAttribute("utente") != null) {
                    UtenteManager service = new UtenteManager(ConPool.getDataSource());
                    HttpSession session = request.getSession();
                    Utente user = (Utente) session.getAttribute("utente");
                    String temp = request.getParameter("page");
                    if(temp == null){
                        temp = "1";
                    }
                    int intPage = Integer.parseInt(temp);
                    Paginator paginator = new Paginator(intPage,5);
                    int size = 0;
                    ArrayList<Corso> corsipubblicati = new ArrayList<>();
                    try{
                        corsipubblicati = (ArrayList<Corso>) service.fetchCorsiPubblicatiLimit(user.getNickname(),paginator.getOffset(),paginator.getLimit());
                        size = service.countCorsiPubblicati(user.getNickname());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    request.setAttribute("pages",paginator.getPages(size));
                    request.setAttribute("corsipubblicati",corsipubblicati);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/user/results/showAllPublished.jsp");
                    dispatcher.forward(request,response);
                } else {
                    response.sendError(403, "Operazione non autorizzata");
                }
                break;
            }

            case "/showAllOrders": {
                if (request.getSession().getAttribute("utente") != null) {
                    UtenteManager service = new UtenteManager(ConPool.getDataSource());
                    HttpSession session = request.getSession();
                    Utente user = (Utente) session.getAttribute("utente");
                    String temp = request.getParameter("page");
                    if(temp == null){
                        temp = "1";
                    }
                    int intPage = Integer.parseInt(temp);
                    Paginator paginator = new Paginator(intPage,5);
                    int size = 0;
                    ArrayList<Ordine> ordinieffettuati = new ArrayList<>();
                    try{
                        ordinieffettuati = (ArrayList<Ordine>) service.fetchOrdiniLimit(user.getNickname(),paginator.getOffset(),paginator.getLimit());
                        size = service.countOrdiniEffettuati(user.getNickname());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        response.sendError(500,"Errore interno");
                    }

                    request.setAttribute("pages",paginator.getPages(size));
                    request.setAttribute("ordinieffettuati",ordinieffettuati);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/user/results/showAllOrders.jsp");
                    dispatcher.forward(request,response);
                } else {
                    response.sendError(403, "Operazione non autorizzata");
                }
                break;

            }

            case "/updateUtente" :{
                if (request.getSession().getAttribute("utente") != null) {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/user/results/updateUtente.jsp");
                    dispatcher.forward(request,response);
                } else {
                    response.sendError(403, "Operazione non autorizzata");
                }
                break;

            }

            case "/createCorso":{
                if (request.getSession().getAttribute("utente") != null) {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/user/results/createCorso.jsp");
                    dispatcher.forward(request,response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/accounts/login");
                }
                break;
            }

        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = (request.getPathInfo() != null) ? request.getPathInfo() : "/";
        switch (path) {

            case "/":
                break;

            case "/inspectCorsoPubblicato" : {

                HttpSession session = request.getSession();
                if(session.getAttribute("utente")==null){
                    response.sendError(403,"Operazione non autorizzata");
                    return;
                }
                CorsoManager service = new CorsoManager(ConPool.getDataSource());
                String temp = request.getParameter("corsoid");
                int corsoid = Integer.parseInt(temp);
                Corso corso = new Corso();
                ArrayList<Utente> utentipartecipanti = new ArrayList<>();

                try{
                    corso = service.fetchCorso(corsoid);
                    utentipartecipanti = (ArrayList<Utente>) service.fetchUtentiPartecipanti(corsoid);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    response.sendError(500, "Errore interno");
                }

                int numutenti = utentipartecipanti.size();
                double guadagno = numutenti*corso.getPrezzoBase();
                request.setAttribute("corso",corso);
                request.setAttribute("guadagno",guadagno);
                request.setAttribute("numiscritti",numutenti);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/user/results/inspectCorsoPubblicato.jsp");
                dispatcher.forward(request,response);
                break;

            }

            case "/inspectOrdineEffettuato":{

                HttpSession session = request.getSession();
                if(session.getAttribute("utente")==null){
                    response.sendError(403,"Operazione non autorizzata");
                    return;
                }
                OrdineManager service = new OrdineManager(ConPool.getDataSource());
                CarrelloManager service2 = new CarrelloManager(ConPool.getDataSource());
                OggettoManager service3 = new OggettoManager(ConPool.getDataSource());
                String temp = request.getParameter("ordineid");
                int ordineid = Integer.parseInt(temp);
                Carrello carrelloassociato = new Carrello();
                ArrayList<Oggetto> oggettiassociati = new ArrayList<>();
                ArrayList<Corso> corsiassociati = new ArrayList<>();
                Double spesatotale = 0.0;

                try{
                    carrelloassociato = service.fetchCarrello(ordineid);
                    oggettiassociati = (ArrayList<Oggetto>) service2.fetchOggetti(carrelloassociato.getId());

                    for(Oggetto oggetto: oggettiassociati){
                        corsiassociati.add(service3.fetchCorso(oggetto.getId()));
                        spesatotale += oggetto.getPrezzo();
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    response.sendError(500,"Errore interno");
                    return;
                }

                request.setAttribute("spesatotale",spesatotale);
                request.setAttribute("corsiassociati",corsiassociati);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/user/results/inspectOrdine.jsp");
                dispatcher.forward(request,response);
                break;

            }

            case "/unsub": {
                HttpSession session = request.getSession();
                if(session.getAttribute("utente")==null){
                    response.sendError(403,"Operazione non autorizzata");
                    return;
                }
                CorsoManager service = new CorsoManager(ConPool.getDataSource());
                String temp = request.getParameter("corsoid");
                int corsoid = Integer.parseInt(temp);
                Utente user = (Utente) session.getAttribute("utente");
                String nickname = user.getNickname();
                try {
                   service.dropUtentePartecipante(corsoid,nickname);
                   response.sendRedirect(request.getContextPath() + "/user/showAllSubscribed");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    response.sendError(500,"Errore interno");
                }
                break;
            }

            case "/executeUpdateUtente": {
                HttpSession session = request.getSession();
                if(session.getAttribute("utente")==null){
                    response.sendError(403,"Operazione non autorizzata");
                    return;
                }
                request.setAttribute("back","/WEB-INF/views/user/results/updateUtente.jsp");
                try {
                    validate(UpdateUtenteValidator.validateForm(request));
                } catch (InvalidRequestException e) {
                    e.handle(request,response);
                    return;
                }
                request.setCharacterEncoding("UTF-8");
                UtenteManager service = new UtenteManager(ConPool.getDataSource());
                Utente utente = (Utente) session.getAttribute("utente");
                utente.setNickname(request.getParameter("username"));
                utente.setEmail(request.getParameter("email"));
                utente.setPasswordHashed(request.getParameter("password"));
                utente.setBiografia(request.getParameter("bio"));
                Part filePart = request.getPart("cover");
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                if (!fileName.equals("")) {
                    fileName = (new Timestamp(System.currentTimeMillis())).toString().replace(":", ".") + " " + fileName;
                    utente.setFotoProfilo(fileName);
                }
                try {
                    if (service.updateUtente(utente)) {
                        response.sendRedirect(request.getContextPath() + "/user/profile?nickname="+utente.getNickname());
                        if (!fileName.equals("")) {
                            String uploadRoot = Upload.getUploadPath();
                            try (InputStream fileStream = filePart.getInputStream()) {
                                File file = new File(uploadRoot + fileName);
                                Files.copy(fileStream, file.toPath());
                            }
                        }
                    } else {
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel caricamento dei dati.");
                    }
                } catch (SQLException e) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel caricamento dei dati.");
                    e.printStackTrace();
                }
                break;
            }

            case "/updateCorso" :{
                HttpSession session = request.getSession();
                if(session.getAttribute("utente")==null){
                    response.sendError(403,"Operazione non autorizzata");
                    return;
                }
                CorsoManager service = new CorsoManager(ConPool.getDataSource());
                String temp = request.getParameter("corsoid");
                int corsoid = Integer.parseInt(temp);
                Corso corso = null;
                try {
                    corso = service.fetchCorso(corsoid);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    response.sendError(500, "Errore interno");
                }
                request.setAttribute("corso", corso);
                RequestDispatcher dispatcher =
                        request.getRequestDispatcher("/WEB-INF/views/user/results/updateCorso.jsp");
                dispatcher.forward(request,response);
                break;

            }

            case "/executeUpdateCorso":{
                HttpSession session = request.getSession();
                if(session.getAttribute("utente")==null){
                    response.sendError(403,"Operazione non autorizzata");
                    return;
                }
                request.setAttribute("back","/WEB-INF/views/user/results/updateCorso.jsp");
                try {
                    validate(CorsoValidator.validateForm(request));
                } catch (InvalidRequestException e) {
                    e.handle(request,response);
                    return;
                }
                request.setCharacterEncoding("UTF-8");
                CorsoManager corsoManager = new CorsoManager(ConPool.getDataSource());
                String temp = request.getParameter("corsoid");
                int corsoid = Integer.parseInt(temp);
                Corso corso = null;

                try {
                    corso = corsoManager.fetchCorso(corsoid);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    response.sendError(500, "Errore interno");
                }
                corso.setNome(request.getParameter("nome"));
                corso.setPrezzoBase(Double.parseDouble(request.getParameter("prezzo")));
                corso.setTesto(request.getParameter("content"));
                Part filePart = request.getPart("cover");
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                if (!fileName.equals("")) {
                    fileName = (new Timestamp(System.currentTimeMillis())).toString().replace(":", ".") + " " + fileName;
                    corso.setCopertina(fileName);
                }
                try {
                    if (corsoManager.updateCorso(corso)) {
                        response.sendRedirect(request.getContextPath() + "/user/profile");
                        if (!fileName.equals("")) {
                            String uploadRoot = Upload.getUploadPath();
                            try (InputStream fileStream = filePart.getInputStream()) {
                                File file = new File(uploadRoot + fileName);
                                Files.copy(fileStream, file.toPath());
                            }
                        }
                    } else {
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel caricamento dei dati.");
                    }
                } catch (SQLException e) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel caricamento dei dati.");
                    e.printStackTrace();
                }
                break;
            }

            case "/executeCreateCorso": {
                HttpSession session = request.getSession();
                if(session.getAttribute("utente")==null){
                    response.sendError(403,"Operazione non autorizzata");
                    return;
                }
                request.setAttribute("back","/WEB-INF/views/home/createCorso.jsp");
                try {
                    validate(CorsoValidator.validateForm(request));
                } catch (InvalidRequestException e) {
                    e.handle(request,response);
                    return;
                }
                request.setCharacterEncoding("UTF-8");
                CorsoManager corsoManager = new CorsoManager(ConPool.getDataSource());
                Utente utente = (Utente) session.getAttribute("utente");
                Corso corso = new Corso();
                corso.setNome(request.getParameter("nome"));
                corso.setPrezzoBase(Double.parseDouble(request.getParameter("prezzo")));
                corso.setTesto(request.getParameter("content"));
                Part filePart = request.getPart("cover");
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                if (fileName.equals("")) {
                    corso.setCopertina("placeholder.jpg");
                } else {
                    fileName = (new Timestamp(System.currentTimeMillis())).toString().replace(":", ".") + " " + fileName;
                    corso.setCopertina(fileName);
                }
                try {
                    if (corsoManager.createCorso(corso) && corsoManager.insertCreatore(utente.getNickname(),corsoManager.fetchCorsoId(corso.getNome()))) {

                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/home/createCorsoSuccess.jsp");
                        dispatcher.forward(request,response);
                        if (!fileName.equals("")) {
                            String uploadRoot = Upload.getUploadPath();
                            try (InputStream fileStream = filePart.getInputStream()) {
                                File file = new File(uploadRoot + fileName);
                                Files.copy(fileStream, file.toPath());
                            }
                        }
                    }
                } catch (SQLException e) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel caricamento dei dati.");
                    e.printStackTrace();
                }
                break;
            }

            case "/deleteCorso": {
                HttpSession session = request.getSession();
                if(session.getAttribute("utente")==null){
                    response.sendError(403,"Operazione non autorizzata");
                    return;
                }
                CorsoManager service = new CorsoManager(ConPool.getDataSource());
                try {
                    service.deleteCorso(Integer.parseInt(request.getParameter("corsoid")));
                    response.sendRedirect(request.getContextPath() + "/user/profile");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    response.sendError(500, "Errore interno");
                }
                break;
            }

            case "/deleteOrdine": {
                HttpSession session = request.getSession();
                if(session.getAttribute("utente")==null){
                    response.sendError(403,"Operazione non autorizzata");
                    return;
                }
                OrdineManager service = new OrdineManager(ConPool.getDataSource());
                int id = Integer.parseInt(request.getParameter("ordineid"));
                try {
                    service.deleteOrdine(id);
                    response.sendRedirect(request.getContextPath() + "/user/showAllOrders");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    response.sendError(500,"Errore interno");
                    return;
                }
                break;

            }

        }
    }
    protected void validate(RequestValidator validator) throws InvalidRequestException {
        if(validator.hasErrors()){
            throw new InvalidRequestException("Validation error",validator.getErrors(),HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
