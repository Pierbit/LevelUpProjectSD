package controller;

import controller.validators.*;
import model.carrello.Carrello;
import model.carrello.CarrelloManager;
import model.categoria.Categoria;
import model.categoria.CategoriaDao;
import model.categoria.CategoriaManager;
import model.corso.Corso;
import model.corso.CorsoDao;
import model.corso.CorsoManager;
import model.oggetto.Oggetto;
import model.oggetto.OggettoManager;
import model.ordine.Ordine;
import model.ordine.OrdineDao;
import model.ordine.OrdineManager;
import model.storage.ConPool;
import model.storage.Upload;
import model.tag.Tag;
import model.tag.TagDao;
import model.tag.TagManager;
import model.utente.Utente;
import model.utente.UtenteDao;
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
import java.util.Date;
import java.util.List;

@WebServlet(name = "ManagerServlet", value = "/manager/*") @MultipartConfig
public class ManagerServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Utente admin = (Utente) request.getSession().getAttribute("utente");
        if (admin.getManager()) {
            String path = (request.getPathInfo() != null) ? request.getPathInfo() : "/";
            switch (path) {
                case "/":
                    break;

                case "/goindex": {
                    RequestDispatcher dispatcher =
                            request.getRequestDispatcher("/WEB-INF/views/admin/adminIndex.jsp");
                    dispatcher.forward(request,response);
                    break;
                }

                case "/executeSearchBar": {
                    String command = request.getParameter("adminsearchbar");
                    String dest;
                    switch(command) {

                        case "Mostra tutti gli utenti": {
                            dest = "/manager/showAllUtente";
                            break;
                        }
                        case "Mostra tutti i corsi": {
                            dest = "/manager/showAllCorso";
                            break;
                        }
                        case "Mostra tutti i tag": {
                            dest = "/manager/showAllTag";
                            break;
                        }
                        case "Mostra tutte le categorie": {
                            dest = "/manager/showAllCategoria";
                            break;
                        }
                        case "Mostra tutti gli ordini": {
                            dest = "/manager/showAllOrdine";
                            break;
                        }
                        case "Crea una categoria": {
                            dest = "/manager/createCategoria";
                            break;
                        }
                        case "Crea un corso": {
                            dest = "/manager/createCorso";
                            break;
                        }
                        case "Crea un tag": {
                            dest = "/manager/createTag";
                            break;
                        }
                        case "Crea un utente": {
                            dest = "/manager/createUtente";
                            break;
                        }
                        default: {
                            dest = "/manager/goindex";
                            break;
                        }
                    }
                    response.sendRedirect(request.getContextPath() + dest);
                    break;
                }

                case "/showAllUtente": {
                    UtenteDao utenteDao = new UtenteManager(ConPool.getDataSource());
                    String temp = request.getParameter("page");
                    if(temp == null){
                        temp = "1";
                    }
                    int intPage = Integer.parseInt(temp);
                    Paginator paginator = new Paginator(intPage,5);
                    int size = 0;
                    List<Utente> utenti = null;
                    try {
                        utenti = utenteDao.fetchUtenti(paginator.getOffset(),paginator.getLimit());
                        size = utenteDao.countUtenti();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    request.setAttribute("pages",paginator.getPages(size));
                    request.setAttribute("elencoUtenti", utenti);
                    String address = "/WEB-INF/views/admin/showAllResults/showAllUtente.jsp";
                    RequestDispatcher dispatcher = request.getRequestDispatcher(address);
                    dispatcher.forward(request, response);
                    break;
                }

                case "/createUtente": {
                    String address = "/WEB-INF/views/admin/createResults/createUtente.jsp";
                    request.getRequestDispatcher(address).forward(request, response);
                    break;
                }

                case "/showAllOrdine": {
                    OrdineDao ordineDao = new OrdineManager(ConPool.getDataSource());
                    String temp = request.getParameter("page");
                    if(temp == null){
                        temp = "1";
                    }
                    int intPage = Integer.parseInt(temp);
                    Paginator paginator = new Paginator(intPage,5);
                    int size = 0;
                    List<Ordine> ordini = null;
                    try {
                        ordini = ordineDao.fetchOrdini(paginator.getOffset(),paginator.getLimit());
                        size = ordineDao.countOrdini();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    request.setAttribute("pages",paginator.getPages(size));
                    request.setAttribute("elencoOrdini", ordini);
                    String address = "/WEB-INF/views/admin/showAllResults/showAllOrdine.jsp";
                    RequestDispatcher dispatcher = request.getRequestDispatcher(address);
                    dispatcher.forward(request, response);
                    break;
                }

                case "/showAllTag": {
                    TagDao tagDao = new TagManager(ConPool.getDataSource());
                    String temp = request.getParameter("page");
                    if(temp == null){
                        temp = "1";
                    }
                    int intPage = Integer.parseInt(temp);
                    Paginator paginator = new Paginator(intPage,5);
                    int size = 0;
                    List<Tag> tags = null;
                    try {
                        tags = tagDao.fetchTags(paginator.getOffset(),paginator.getLimit());
                        size = tagDao.countTags();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    request.setAttribute("pages",paginator.getPages(size));
                    request.setAttribute("elencoTags", tags);
                    String address = "/WEB-INF/views/admin/showAllResults/showAllTag.jsp";
                    RequestDispatcher dispatcher = request.getRequestDispatcher(address);
                    dispatcher.forward(request, response);
                    break;
                }

                case "/showAllCorso": {
                    CorsoDao corsoDao = new CorsoManager(ConPool.getDataSource());
                    String temp = request.getParameter("page");
                    if(temp == null){
                        temp = "1";
                    }
                    int intPage = Integer.parseInt(temp);
                    Paginator paginator = new Paginator(intPage,5);
                    int size = 0;
                    List<Corso> corsi = null;
                    try {
                        corsi = corsoDao.fetchCorsi(paginator.getOffset(),paginator.getLimit());
                        size = corsoDao.countCorsi();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    request.setAttribute("pages",paginator.getPages(size));
                    request.setAttribute("elencoCorsi", corsi);
                    String address = "/WEB-INF/views/admin/showAllResults/showAllCorso.jsp";
                    RequestDispatcher dispatcher = request.getRequestDispatcher(address);
                    dispatcher.forward(request, response);
                    break;
                }

                case "/createCorso": {
                    String address = "/WEB-INF/views/admin/createResults/createCorso.jsp";
                    request.getRequestDispatcher(address).forward(request, response);
                    break;
                }

                case "/showAllCategoria": {
                    CategoriaDao categoriaDao = new CategoriaManager(ConPool.getDataSource());
                    String temp = request.getParameter("page");
                    if(temp == null){
                        temp = "1";
                    }
                    int intPage = Integer.parseInt(temp);
                    Paginator paginator = new Paginator(intPage,5);
                    int size = 0;
                    List<Categoria> categorie = null;
                    try {
                        categorie = categoriaDao.fetchCategorie(paginator.getOffset(),paginator.getLimit());
                        size = categoriaDao.countCategorie();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    request.setAttribute("pages",paginator.getPages(size));
                    request.setAttribute("elencoCategorie", categorie);
                    String address = "/WEB-INF/views/admin/showAllResults/showAllCategoria.jsp";
                    RequestDispatcher dispatcher = request.getRequestDispatcher(address);
                    dispatcher.forward(request, response);
                    break;
                }

                case "/createCategoria": {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/createResults/createCategoria.jsp");
                    dispatcher.forward(request,response);
                    break;
                }

                case "/createTag": {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/createResults/createTag.jsp");
                    dispatcher.forward(request,response);
                    break;
                }

                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Risorsa non trovata");
            }
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Operazione non autorizzata.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Utente admin = (Utente) request.getSession().getAttribute("utente");
        if (admin.getManager()) {
            String path = (request.getPathInfo() != null) ? request.getPathInfo() : "/";
            switch (path) {
                case "/":
                    break;

                case "/inspectUser": {
                    UtenteManager service = new UtenteManager(ConPool.getDataSource());
                    Utente utente = null;
                    try {
                        utente = service.fetchUtente(request.getParameter("username"));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        response.sendError(500, "Errore interno");
                    }
                    try {
                        utente.setCorsiPartecipati((ArrayList<Corso>) service.fetchCorsiPartecipati(utente.getNickname()));
                        utente.setCorsiCreati((ArrayList<Corso>) service.fetchCorsiPubblicati(utente.getNickname()));
                        utente.setOrdini((ArrayList<Ordine>) service.fetchOrdini(utente.getNickname()));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    request.setAttribute("utente", utente);
                    RequestDispatcher dispatcher =
                            request.getRequestDispatcher("/WEB-INF/views/admin/inspectResults/inspectUser.jsp");
                    dispatcher.forward(request,response);
                    break;
                }

                case "/createUtente": {
                    request.setAttribute("back","/WEB-INF/views/admin/createResults/createUtente.jsp");
                    try {
                        validate(RegisterValidator.validateForm(request));
                    } catch (InvalidRequestException e) {
                        e.handle(request,response);
                        return;
                    }
                    request.setCharacterEncoding("UTF-8");
                    UtenteManager service = new UtenteManager(ConPool.getDataSource());
                    Utente utente = new Utente();
                    utente.setNickname(request.getParameter("username"));
                    utente.setEmail(request.getParameter("email"));
                    utente.setPasswordHashed(request.getParameter("password"));
                    utente.setManager(Boolean.parseBoolean(request.getParameter("admin")));
                    CarrelloManager service1 = new CarrelloManager(ConPool.getDataSource());

                    Part filePart = request.getPart("cover");
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    if (fileName.equals("")) {
                        utente.setFotoProfilo("no_avatar.png");
                    } else {
                        fileName = (new Timestamp(System.currentTimeMillis())).toString().replace(":", ".") + " " + fileName;
                        utente.setFotoProfilo(fileName);
                    }
                    try {
                        if (service.createUtente(utente)) {
                            Carrello carrello = new Carrello();
                            carrello.setId((int) (new Date().getTime() / 1000));
                            carrello.setOggetti(new ArrayList<>());
                            service1.createCarrello(carrello);
                            service.setCarrello(utente.getNickname(),carrello.getId());
                            response.sendRedirect(request.getContextPath() + "/manager/showAllUtente");
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

                case "/updateUtente": {
                    UtenteManager service = new UtenteManager(ConPool.getDataSource());
                    Utente user = null;
                    try {
                        user = service.fetchUtente(request.getParameter("username"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                        response.sendError(500, "Errore interno");
                    }
                    System.out.println(user.getNickname());
                    request.setAttribute("user", user);
                    RequestDispatcher dispatcher =
                            request.getRequestDispatcher("/WEB-INF/views/admin/updateResults/updateUtente.jsp");
                    dispatcher.forward(request,response);
                    break;
                }

                case "/executeUpdateUtente": {
                    UtenteManager utenteManager = new UtenteManager(ConPool.getDataSource());
                    Utente user = null;
                    try {
                        user = utenteManager.fetchUtente(request.getParameter("nickname"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                        response.sendError(500, "Errore interno");
                    }
                    request.setAttribute("user", user);
                    request.setAttribute("back","/WEB-INF/views/admin/updateResults/updateUtente.jsp");
                    try {
                        validate(RegisterValidator.validateForm(request));
                    } catch (InvalidRequestException e) {
                        e.handle(request,response);
                        return;
                    }
                    Utente utente = null;
                    try {
                        utente = utenteManager.fetchUtente(request.getParameter("username"));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        response.sendError(500, "Errore interno");
                    }
                    utente.setEmail(request.getParameter("email"));
                    utente.setPasswordHashed(request.getParameter("password"));
                    utente.setManager(Boolean.parseBoolean(request.getParameter("admin")));
                    Part filePart = request.getPart("cover");
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    if (!fileName.equals("")) {
                        fileName = (new Timestamp(System.currentTimeMillis())).toString().replace(":", ".") + " " + fileName;
                        utente.setFotoProfilo(fileName);
                    }
                    try {
                        if (utenteManager.updateUtente(utente)) {
                            response.sendRedirect(request.getContextPath() + "/manager/showAllUtente");
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

                case "/deleteUtente": {
                    UtenteManager service = new UtenteManager(ConPool.getDataSource());
                    try {
                        service.deleteUtente(request.getParameter("username"));
                        response.sendRedirect(request.getContextPath() + "/manager/showAllUtente");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        response.sendError(500, "Errore interno");
                    }
                    break;
                }

                case "/inspectOrdine": {
                    OrdineManager service = new OrdineManager(ConPool.getDataSource());
                    CarrelloManager service2 = new CarrelloManager(ConPool.getDataSource());
                    OggettoManager service3 = new OggettoManager(ConPool.getDataSource());
                    String temp = request.getParameter("ordineid");
                    int ordineid = Integer.parseInt(temp);
                    Carrello carrelloassociato = new Carrello();
                    ArrayList<Oggetto> oggettiassociati = new ArrayList<>();
                    ArrayList<Corso> corsiassociati = new ArrayList<>();
                    Utente utente = null;
                    try{
                        utente = service.fetchUtente(ordineid);
                        carrelloassociato = service.fetchCarrello(ordineid);
                        oggettiassociati = (ArrayList<Oggetto>) service2.fetchOggetti(carrelloassociato.getId());

                        for(Oggetto oggetto: oggettiassociati){
                            corsiassociati.add(service3.fetchCorso(oggetto.getId()));
                        }

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    request.setAttribute("corsiassociati",corsiassociati);
                    request.setAttribute("utente",utente);
                    RequestDispatcher dispatcher =
                            request.getRequestDispatcher("/WEB-INF/views/admin/inspectResults/inspectOrdine.jsp");
                    dispatcher.forward(request,response);
                    break;
                }

                case "/inspectTag": {
                    TagManager service = new TagManager(ConPool.getDataSource());
                    String tagname = request.getParameter("nome");
                    ArrayList<Corso> corsiassociati = null;
                    try{
                        corsiassociati = (ArrayList<Corso>) service.fetchCorsiAssociati(tagname);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    request.setAttribute("corsiassociati",corsiassociati);
                    request.setAttribute("tagname",tagname);
                    RequestDispatcher dispatcher =
                            request.getRequestDispatcher("/WEB-INF/views/admin/inspectResults/inspectTag.jsp");
                    dispatcher.forward(request,response);
                    break;
                }

                case "/inspectCorso": {
                    CorsoManager service = new CorsoManager(ConPool.getDataSource());
                    Corso corso = new Corso();
                    corso.setId(Integer.parseInt(request.getParameter("corsoid")));
                    try {
                        corso = service.fetchCorso(corso.getId());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        response.sendError(500, "Errore interno");
                    }
                    try {
                        corso.setUtentiPartecipanti(service.fetchUtentiPartecipanti(corso.getId()));
                        corso.setUtenteCreatore(service.fetchUtenteCreatore(corso.getId()));
                        corso.setTags(service.fetchTagAssociati(corso.getId()));
                        corso.setCategoria(service.fetchCategoria(corso.getId()));
                    } catch (SQLException throwables) {
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore interno");
                        throwables.printStackTrace();
                    }
                    request.setAttribute("corso", corso);
                    RequestDispatcher dispatcher =
                            request.getRequestDispatcher("/WEB-INF/views/admin/inspectResults/inspectCorso.jsp");
                    dispatcher.forward(request,response);
                    break;
                }

                case "/executeCreateTag": {
                    request.setCharacterEncoding("UTF-8");
                    request.setAttribute("back","/WEB-INF/views/admin/createResults/createTag.jsp");
                    try {
                        validate(TagValidator.validateForm(request));
                    } catch (InvalidRequestException e) {
                        e.handle(request,response);
                        return;
                    }
                    TagManager service = new TagManager(ConPool.getDataSource());
                    Tag tag = new Tag();
                    tag.setNome(request.getParameter("nome"));
                    try{
                        if(service.createTag(tag)){
                            response.sendRedirect(request.getContextPath() + "/manager/showAllTag");
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        response.sendError(500,"Errore interno");
                        return;
                    }
                    break;

                }

                case "/createCorso": {
                    request.setCharacterEncoding("UTF-8");
                    request.setAttribute("back","/WEB-INF/views/admin/createResults/createCorso.jsp");
                    try {
                        validate(CorsoValidator.validateForm(request));
                    } catch (InvalidRequestException e) {
                        e.handle(request,response);
                        return;
                    }
                    CorsoManager corsoManager = new CorsoManager(ConPool.getDataSource());
                    HttpSession session = request.getSession();
                    Corso corso = new Corso();
                    Utente utente = (Utente) session.getAttribute("utente");
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
                            response.sendRedirect(request.getContextPath() + "/manager/showAllCorso");
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

                case "/associaCorso": {
                    CorsoManager service = new CorsoManager(ConPool.getDataSource());
                    CategoriaManager service2 = new CategoriaManager(ConPool.getDataSource());
                    TagManager service3 = new TagManager(ConPool.getDataSource());
                    ArrayList<Categoria> categorie;
                    ArrayList<Tag> tags;
                    Corso corso;
                    int corsoid = Integer.parseInt(request.getParameter("corsoid"));
                    try {
                        corso = service.fetchCorso(corsoid);
                        categorie = (ArrayList<Categoria>) service2.fetchCategorie(0,service2.countCategorie());
                        tags = (ArrayList<Tag>) service3.fetchTags(0,service3.countTags());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        response.sendError(500,"Errore interno");
                        return;
                    }

                    request.setAttribute("corso",corso);
                    request.setAttribute("categorie",categorie);
                    request.setAttribute("tags",tags);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/associateResults/associaCorso.jsp");
                    dispatcher.forward(request,response);
                    break;
                }

                case "/executeAssociaCorso": {
                    CorsoManager service = new CorsoManager(ConPool.getDataSource());
                    int corsoid = Integer.parseInt(request.getParameter("corsoid"));
                    String categoria = request.getParameter("categoria");
                    String[] tags = request.getParameterValues("tags");
                    Corso corso = new Corso();
                    try {
                        corso = service.fetchCorso(corsoid);
                        service.insertCorsoCategoria(corso,categoria);

                        for(String s: tags){
                            service.insertCorsoTag(corso,s);
                        }

                        response.sendRedirect(request.getContextPath() + "/manager/showAllCorso");

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        response.sendError(500,"Errore interno");
                    }
                    break;
                }

                case "/deleteOrdine" : {
                    OrdineManager service = new OrdineManager(ConPool.getDataSource());
                    int id = Integer.parseInt(request.getParameter("ordineid"));
                    try {
                        service.deleteOrdine(Integer.parseInt(request.getParameter("ordineid")));
                        response.sendRedirect(request.getContextPath() + "/manager/showAllOrdine");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        response.sendError(500,"Errore interno");
                    }
                    break;
                }

                case "/updateCorso": {
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
                            request.getRequestDispatcher("/WEB-INF/views/admin/updateResults/updateCorso.jsp");
                    dispatcher.forward(request,response);
                    break;
                }

                case "/executeUpdateCorso": {
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
                    request.setAttribute("corso", corso);
                    request.setAttribute("back","/WEB-INF/views/admin/updateResults/updateCorso.jsp");
                    try {
                        validate(UpdateCorsoValidator.validateForm(request));
                    } catch (InvalidRequestException e) {
                        e.handle(request,response);
                        return;
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
                            response.sendRedirect(request.getContextPath() + "/manager/showAllCorso");
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

                case "/deleteCorso": {
                    CorsoManager service = new CorsoManager(ConPool.getDataSource());
                    try {
                        service.deleteCorso(Integer.parseInt(request.getParameter("corsoid")));
                        response.sendRedirect(request.getContextPath() + "/manager/showAllCorso");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        response.sendError(500, "Errore interno");
                    }
                    break;
                }

                case "/deleteTag": {
                    TagManager service = new TagManager(ConPool.getDataSource());
                    try {
                        service.deleteTag(request.getParameter("nome"));
                        response.sendRedirect(request.getContextPath() + "/manager/showAllTag");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        response.sendError(500,"Errore interno");
                    }
                    break;

                }

                case "/deleteCategoria" : {
                    CategoriaManager service = new CategoriaManager(ConPool.getDataSource());
                    try {
                        service.deleteCategoria(request.getParameter("nome"));
                        response.sendRedirect(request.getContextPath() + "/manager/showAllCategoria");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        response.sendError(500,"Errore interno");
                        return;
                    }
                    break;
                }
                case "/inspectCategoria": {
                    CategoriaManager service = new CategoriaManager(ConPool.getDataSource());
                    String nomecategoria = request.getParameter("nome");
                    ArrayList<Corso> corsiassociati = null;
                    try{
                        corsiassociati = (ArrayList<Corso>) service.fetchCorsiAssociati(nomecategoria);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    request.setAttribute("corsiassociati",corsiassociati);
                    request.setAttribute("nomecategoria",nomecategoria);
                    RequestDispatcher dispatcher =
                            request.getRequestDispatcher("/WEB-INF/views/admin/inspectResults/inspectCategoria.jsp");
                    dispatcher.forward(request,response);
                    break;
                }

                case "/executeCreateCategoria": {
                    request.setCharacterEncoding("UTF-8");
                    request.setAttribute("back","/WEB-INF/views/admin/createResults/createCategoria.jsp");
                    try {
                        validate(CategoriaValidator.validateForm(request));
                    } catch (InvalidRequestException e) {
                        e.handle(request,response);
                        return;
                    }
                    CategoriaManager service = new CategoriaManager(ConPool.getDataSource());
                    try{
                        if(service.createCategoria(request.getParameter("nome"))){
                            response.sendRedirect(request.getContextPath() + "/manager/showAllCategoria");
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        response.sendError(500,"Errore interno");
                        return;
                    }
                    break;


                }

                default:
                    response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Operazione non consentita");
            }
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Operazione non autorizzata.");
        }
    }

    protected void validate(RequestValidator validator) throws InvalidRequestException {
        if(validator.hasErrors()){
            throw new InvalidRequestException("Validation error",validator.getErrors(),HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}

