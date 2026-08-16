package controller;

import controller.InvalidRequestException;
import controller.Paginator;
import controller.RequestValidator;
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
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ManagerServlet", value = "/manager/*") @MultipartConfig
public class ManagerServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ManagerServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        Utente admin = (Utente) request.getSession().getAttribute("utente");
        if (!admin.getManager()) {
            sendForbidden(response);
            return;
        }
        String path = (request.getPathInfo() != null) ? request.getPathInfo() : "/";
        try {
            switch (path) {
                case "/":
                    break;
                case "/goindex":
                    request.getRequestDispatcher("/WEB-INF/views/admin/adminIndex.jsp").forward(request, response);
                    break;
                case "/executeSearchBar":
                    handleExecuteSearchBar(request, response);
                    break;
                case "/showAllUtente":
                    handleShowAllUtente(request, response);
                    break;
                case "/createUtente":
                    request.getRequestDispatcher("/WEB-INF/views/admin/createResults/createUtente.jsp").forward(request, response);
                    break;
                case "/showAllOrdine":
                    handleShowAllOrdine(request, response);
                    break;
                case "/showAllTag":
                    handleShowAllTag(request, response);
                    break;
                case "/showAllCorso":
                    handleShowAllCorso(request, response);
                    break;
                case "/createCorso":
                    request.getRequestDispatcher("/WEB-INF/views/admin/createResults/createCorso.jsp").forward(request, response);
                    break;
                case "/showAllCategoria":
                    handleShowAllCategoria(request, response);
                    break;
                case "/createCategoria":
                    request.getRequestDispatcher("/WEB-INF/views/admin/createResults/createCategoria.jsp").forward(request, response);
                    break;
                case "/createTag":
                    request.getRequestDispatcher("/WEB-INF/views/admin/createResults/createTag.jsp").forward(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Risorsa non trovata");
            }
        } catch (ServletException | IOException e) {
            handleUnexpectedError(response, "GET " + path, e);
        }
    }

    private void handleExecuteSearchBar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("adminsearchbar");
        String dest;
        switch (command) {
            case "Mostra tutti gli utenti": dest = "/manager/showAllUtente"; break;
            case "Mostra tutti i corsi": dest = "/manager/showAllCorso"; break;
            case "Mostra tutti i tag": dest = "/manager/showAllTag"; break;
            case "Mostra tutte le categorie": dest = "/manager/showAllCategoria"; break;
            case "Mostra tutti gli ordini": dest = "/manager/showAllOrdine"; break;
            case "Crea una categoria": dest = "/manager/createCategoria"; break;
            case "Crea un corso": dest = "/manager/createCorso"; break;
            case "Crea un tag": dest = "/manager/createTag"; break;
            case "Crea un utente": dest = "/manager/createUtente"; break;
            default: dest = "/manager/goindex"; break;
        }
        response.sendRedirect(request.getContextPath() + dest);
    }

    private void handleShowAllUtente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UtenteDao utenteDao = new UtenteManager(ConPool.getDataSource());
        int intPage = parsePageParam(request);
        Paginator paginator = new Paginator(intPage, 5);
        int size = 0;
        List<Utente> utenti = null;
        try {
            utenti = utenteDao.fetchUtenti(paginator.getOffset(), paginator.getLimit());
            size = utenteDao.countUtenti();
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Failed to fetch utenti list", throwables);
        }
        request.setAttribute("pages", paginator.getPages(size));
        request.setAttribute("elencoUtenti", utenti);
        request.getRequestDispatcher("/WEB-INF/views/admin/showAllResults/showAllUtente.jsp").forward(request, response);
    }

    private void handleShowAllOrdine(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OrdineDao ordineDao = new OrdineManager(ConPool.getDataSource());
        int intPage = parsePageParam(request);
        Paginator paginator = new Paginator(intPage, 5);
        int size = 0;
        List<Ordine> ordini = null;
        try {
            ordini = ordineDao.fetchOrdini(paginator.getOffset(), paginator.getLimit());
            size = ordineDao.countOrdini();
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Failed to fetch ordini list", throwables);
        }
        request.setAttribute("pages", paginator.getPages(size));
        request.setAttribute("elencoOrdini", ordini);
        request.getRequestDispatcher("/WEB-INF/views/admin/showAllResults/showAllOrdine.jsp").forward(request, response);
    }

    private void handleShowAllTag(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TagDao tagDao = new TagManager(ConPool.getDataSource());
        int intPage = parsePageParam(request);
        Paginator paginator = new Paginator(intPage, 5);
        int size = 0;
        List<Tag> tags = null;
        try {
            tags = tagDao.fetchTags(paginator.getOffset(), paginator.getLimit());
            size = tagDao.countTags();
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Failed to fetch tags list", throwables);
        }
        request.setAttribute("pages", paginator.getPages(size));
        request.setAttribute("elencoTags", tags);
        request.getRequestDispatcher("/WEB-INF/views/admin/showAllResults/showAllTag.jsp").forward(request, response);
    }

    private void handleShowAllCorso(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CorsoDao corsoDao = new CorsoManager(ConPool.getDataSource());
        int intPage = parsePageParam(request);
        Paginator paginator = new Paginator(intPage, 5);
        int size = 0;
        List<Corso> corsi = null;
        try {
            corsi = corsoDao.fetchCorsi(paginator.getOffset(), paginator.getLimit());
            size = corsoDao.countCorsi();
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Failed to fetch corsi list", throwables);
        }
        request.setAttribute("pages", paginator.getPages(size));
        request.setAttribute("elencoCorsi", corsi);
        request.getRequestDispatcher("/WEB-INF/views/admin/showAllResults/showAllCorso.jsp").forward(request, response);
    }

    private void handleShowAllCategoria(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CategoriaDao categoriaDao = new CategoriaManager(ConPool.getDataSource());
        int intPage = parsePageParam(request);
        Paginator paginator = new Paginator(intPage, 5);
        int size = 0;
        List<Categoria> categorie = null;
        try {
            categorie = categoriaDao.fetchCategorie(paginator.getOffset(), paginator.getLimit());
            size = categoriaDao.countCategorie();
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Failed to fetch categorie list", throwables);
        }
        request.setAttribute("pages", paginator.getPages(size));
        request.setAttribute("elencoCategorie", categorie);
        request.getRequestDispatcher("/WEB-INF/views/admin/showAllResults/showAllCategoria.jsp").forward(request, response);
    }

    private int parsePageParam(HttpServletRequest request) {
        String temp = request.getParameter("page");
        if (temp == null) {
            temp = "1";
        }
        return Integer.parseInt(temp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        Utente admin = (Utente) request.getSession().getAttribute("utente");
        if (!admin.getManager()) {
            sendForbidden(response);
            return;
        }
        String path = (request.getPathInfo() != null) ? request.getPathInfo() : "/";
        try {
            switch (path) {
                case "/":
                    break;
                case "/inspectUser":
                    handleInspectUser(request, response);
                    break;
                case "/createUtente":
                    handleCreateUtente(request, response);
                    break;
                case "/updateUtente":
                    handleUpdateUtente(request, response);
                    break;
                case "/executeUpdateUtente":
                    handleExecuteUpdateUtente(request, response);
                    break;
                case "/deleteUtente":
                    handleDeleteUtente(request, response);
                    break;
                case "/inspectOrdine":
                    handleInspectOrdine(request, response);
                    break;
                case "/inspectTag":
                    handleInspectTag(request, response);
                    break;
                case "/inspectCorso":
                    handleInspectCorso(request, response);
                    break;
                case "/executeCreateTag":
                    handleExecuteCreateTag(request, response);
                    break;
                case "/createCorso":
                    handleCreateCorso(request, response);
                    break;
                case "/associaCorso":
                    handleAssociaCorso(request, response);
                    break;
                case "/executeAssociaCorso":
                    handleExecuteAssociaCorso(request, response);
                    break;
                case "/deleteOrdine":
                    handleDeleteOrdine(request, response);
                    break;
                case "/updateCorso":
                    handleUpdateCorso(request, response);
                    break;
                case "/executeUpdateCorso":
                    handleExecuteUpdateCorso(request, response);
                    break;
                case "/deleteCorso":
                    handleDeleteCorso(request, response);
                    break;
                case "/deleteTag":
                    handleDeleteTag(request, response);
                    break;
                case "/deleteCategoria":
                    handleDeleteCategoria(request, response);
                    break;
                case "/inspectCategoria":
                    handleInspectCategoria(request, response);
                    break;
                case "/executeCreateCategoria":
                    handleExecuteCreateCategoria(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Operazione non consentita");
            }
        } catch (ServletException | IOException e) {
            handleUnexpectedError(response, "POST " + path, e);
        }
    }

    private void handleInspectUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UtenteManager service = new UtenteManager(ConPool.getDataSource());
        Utente utente = null;
        try {
            utente = service.fetchUtente(request.getParameter("username"));
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Failed to fetch utente for inspection", throwables);
            response.sendError(500, "Errore interno");
            return;
        }
        try {
            utente.setCorsiPartecipati((ArrayList<Corso>) service.fetchCorsiPartecipati(utente.getNickname()));
            utente.setCorsiCreati((ArrayList<Corso>) service.fetchCorsiPubblicati(utente.getNickname()));
            utente.setOrdini((ArrayList<Ordine>) service.fetchOrdini(utente.getNickname()));
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Failed to fetch related data for utente " + utente.getNickname(), throwables);
        }
        request.setAttribute("utente", utente);
        request.getRequestDispatcher("/WEB-INF/views/admin/inspectResults/inspectUser.jsp").forward(request, response);
    }

    private void handleCreateUtente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("back", "/WEB-INF/views/admin/createResults/createUtente.jsp");
        try {
            validate(RegisterValidator.validateForm(request));
        } catch (InvalidRequestException e) {
            e.handle(request, response);
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
                service.setCarrello(utente.getNickname(), carrello.getId());
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
            LOGGER.log(Level.SEVERE, "Failed to create utente " + utente.getNickname(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel caricamento dei dati.");
        }
    }

    private void handleUpdateUtente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UtenteManager service = new UtenteManager(ConPool.getDataSource());
        Utente user = null;
        try {
            user = service.fetchUtente(request.getParameter("username"));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to fetch utente for update form", e);
            response.sendError(500, "Errore interno");
            return;
        }
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/views/admin/updateResults/updateUtente.jsp").forward(request, response);
    }

    private void handleExecuteUpdateUtente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UtenteManager utenteManager = new UtenteManager(ConPool.getDataSource());
        Utente user = null;
        try {
            user = utenteManager.fetchUtente(request.getParameter("nickname"));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to fetch utente before update", e);
            response.sendError(500, "Errore interno");
            return;
        }
        request.setAttribute("user", user);
        request.setAttribute("back", "/WEB-INF/views/admin/updateResults/updateUtente.jsp");
        try {
            validate(RegisterValidator.validateForm(request));
        } catch (InvalidRequestException e) {
            e.handle(request, response);
            return;
        }
        Utente utente = null;
        try {
            utente = utenteManager.fetchUtente(request.getParameter("username"));
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Failed to fetch utente to apply update", throwables);
            response.sendError(500, "Errore interno");
            return;
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
            LOGGER.log(Level.SEVERE, "Failed to update utente " + utente.getNickname(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel caricamento dei dati.");
        }
    }

    private void handleDeleteUtente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UtenteManager service = new UtenteManager(ConPool.getDataSource());
        try {
            service.deleteUtente(request.getParameter("username"));
            response.sendRedirect(request.getContextPath() + "/manager/showAllUtente");
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Failed to delete utente", throwables);
            response.sendError(500, "Errore interno");
        }
    }

    private void handleInspectOrdine(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OrdineManager service = new OrdineManager(ConPool.getDataSource());
        CarrelloManager service2 = new CarrelloManager(ConPool.getDataSource());
        OggettoManager service3 = new OggettoManager(ConPool.getDataSource());
        int ordineid = Integer.parseInt(request.getParameter("ordineid"));
        Carrello carrelloassociato = new Carrello();
        ArrayList<Oggetto> oggettiassociati = new ArrayList<>();
        ArrayList<Corso> corsiassociati = new ArrayList<>();
        Utente utente = null;
        try {
            utente = service.fetchUtente(ordineid);
            carrelloassociato = service.fetchCarrello(ordineid);
            oggettiassociati = (ArrayList<Oggetto>) service2.fetchOggetti(carrelloassociato.getId());
            for (Oggetto oggetto : oggettiassociati) {
                corsiassociati.add(service3.fetchCorso(oggetto.getId()));
            }
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Failed to inspect ordine " + ordineid, throwables);
        }
        request.setAttribute("corsiassociati", corsiassociati);
        request.setAttribute("utente", utente);
        request.getRequestDispatcher("/WEB-INF/views/admin/inspectResults/inspectOrdine.jsp").forward(request, response);
    }

    private void handleInspectTag(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TagManager service = new TagManager(ConPool.getDataSource());
        String tagname = request.getParameter("nome");
        ArrayList<Corso> corsiassociati = null;
        try {
            corsiassociati = (ArrayList<Corso>) service.fetchCorsiAssociati(tagname);
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Failed to fetch corsi associated with tag " + tagname, throwables);
        }
        request.setAttribute("corsiassociati", corsiassociati);
        request.setAttribute("tagname", tagname);
        request.getRequestDispatcher("/WEB-INF/views/admin/inspectResults/inspectTag.jsp").forward(request, response);
    }

    private void handleInspectCorso(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CorsoManager service = new CorsoManager(ConPool.getDataSource());
        Corso corso = new Corso();
        corso.setId(Integer.parseInt(request.getParameter("corsoid")));
        try {
            corso = service.fetchCorso(corso.getId());
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Failed to fetch corso " + corso.getId(), throwables);
            response.sendError(500, "Errore interno");
            return;
        }
        try {
            corso.setUtentiPartecipanti(service.fetchUtentiPartecipanti(corso.getId()));
            corso.setUtenteCreatore(service.fetchUtenteCreatore(corso.getId()));
            corso.setTags(service.fetchTagAssociati(corso.getId()));
            corso.setCategoria(service.fetchCategoria(corso.getId()));
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Failed to fetch related data for corso " + corso.getId(), throwables);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore interno");
            return;
        }
        request.setAttribute("corso", corso);
        request.getRequestDispatcher("/WEB-INF/views/admin/inspectResults/inspectCorso.jsp").forward(request, response);
    }

    private void handleExecuteCreateTag(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("back", "/WEB-INF/views/admin/createResults/createTag.jsp");
        try {
            validate(TagValidator.validateForm(request));
        } catch (InvalidRequestException e) {
            e.handle(request, response);
            return;
        }
        TagManager service = new TagManager(ConPool.getDataSource());
        Tag tag = new Tag();
        tag.setNome(request.getParameter("nome"));
        try {
            if (service.createTag(tag)) {
                response.sendRedirect(request.getContextPath() + "/manager/showAllTag");
            }
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Failed to create tag " + tag.getNome(), throwables);
            response.sendError(500, "Errore interno");
        }
    }

    private void handleCreateCorso(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("back", "/WEB-INF/views/admin/createResults/createCorso.jsp");
        try {
            validate(CorsoValidator.validateForm(request));
        } catch (InvalidRequestException e) {
            e.handle(request, response);
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
            if (corsoManager.createCorso(corso) && corsoManager.insertCreatore(utente.getNickname(), corsoManager.fetchCorsoId(corso.getNome()))) {
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
            LOGGER.log(Level.SEVERE, "Failed to create corso " + corso.getNome(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel caricamento dei dati.");
        }
    }

    private void handleAssociaCorso(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CorsoManager service = new CorsoManager(ConPool.getDataSource());
        CategoriaManager service2 = new CategoriaManager(ConPool.getDataSource());
        TagManager service3 = new TagManager(ConPool.getDataSource());
        ArrayList<Categoria> categorie;
        ArrayList<Tag> tags;
        Corso corso;
        int corsoid = Integer.parseInt(request.getParameter("corsoid"));
        try {
            corso = service.fetchCorso(corsoid);
            categorie = (ArrayList<Categoria>) service2.fetchCategorie(0, service2.countCategorie());
            tags = (ArrayList<Tag>) service3.fetchTags(0, service3.countTags());
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Failed to load associaCorso form data for corso " + corsoid, throwables);
            response.sendError(500, "Errore interno");
            return;
        }
        request.setAttribute("corso", corso);
        request.setAttribute("categorie", categorie);
        request.setAttribute("tags", tags);
        request.getRequestDispatcher("/WEB-INF/views/admin/associateResults/associaCorso.jsp").forward(request, response);
    }

    private void handleExecuteAssociaCorso(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CorsoManager service = new CorsoManager(ConPool.getDataSource());
        int corsoid = Integer.parseInt(request.getParameter("corsoid"));
        String categoria = request.getParameter("categoria");
        String[] tags = request.getParameterValues("tags");
        Corso corso;
        try {
            corso = service.fetchCorso(corsoid);
            service.insertCorsoCategoria(corso, categoria);
            for (String s : tags) {
                service.insertCorsoTag(corso, s);
            }
            response.sendRedirect(request.getContextPath() + "/manager/showAllCorso");
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Failed to associate category/tags to corso " + corsoid, throwables);
            response.sendError(500, "Errore interno");
        }
    }

    private void handleDeleteOrdine(HttpServletRequest request, HttpServletResponse response) throws IOException {
        OrdineManager service = new OrdineManager(ConPool.getDataSource());
        int id = Integer.parseInt(request.getParameter("ordineid"));
        try {
            service.deleteOrdine(id);
            response.sendRedirect(request.getContextPath() + "/manager/showAllOrdine");
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Failed to delete ordine " + id, throwables);
            response.sendError(500, "Errore interno");
        }
    }

    private void handleUpdateCorso(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CorsoManager service = new CorsoManager(ConPool.getDataSource());
        int corsoid = Integer.parseInt(request.getParameter("corsoid"));
        Corso corso = null;
        try {
            corso = service.fetchCorso(corsoid);
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Failed to fetch corso " + corsoid + " for update form", throwables);
            response.sendError(500, "Errore interno");
            return;
        }
        request.setAttribute("corso", corso);
        request.getRequestDispatcher("/WEB-INF/views/admin/updateResults/updateCorso.jsp").forward(request, response);
    }

    private void handleExecuteUpdateCorso(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        CorsoManager corsoManager = new CorsoManager(ConPool.getDataSource());
        int corsoid = Integer.parseInt(request.getParameter("corsoid"));
        Corso corso = null;
        try {
            corso = corsoManager.fetchCorso(corsoid);
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Failed to fetch corso " + corsoid + " before update", throwables);
            response.sendError(500, "Errore interno");
            return;
        }
        request.setAttribute("corso", corso);
        request.setAttribute("back", "/WEB-INF/views/admin/updateResults/updateCorso.jsp");
        try {
            validate(UpdateCorsoValidator.validateForm(request));
        } catch (InvalidRequestException e) {
            e.handle(request, response);
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
            LOGGER.log(Level.SEVERE, "Failed to update corso " + corsoid, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel caricamento dei dati.");
        }
    }

    private void handleDeleteCorso(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CorsoManager service = new CorsoManager(ConPool.getDataSource());
        try {
            service.deleteCorso(Integer.parseInt(request.getParameter("corsoid")));
            response.sendRedirect(request.getContextPath() + "/manager/showAllCorso");
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Failed to delete corso", throwables);
            response.sendError(500, "Errore interno");
        }
    }

    private void handleDeleteTag(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TagManager service = new TagManager(ConPool.getDataSource());
        try {
            service.deleteTag(request.getParameter("nome"));
            response.sendRedirect(request.getContextPath() + "/manager/showAllTag");
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Failed to delete tag", throwables);
            response.sendError(500, "Errore interno");
        }
    }

    private void handleDeleteCategoria(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CategoriaManager service = new CategoriaManager(ConPool.getDataSource());
        try {
            service.deleteCategoria(request.getParameter("nome"));
            response.sendRedirect(request.getContextPath() + "/manager/showAllCategoria");
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Failed to delete categoria", throwables);
            response.sendError(500, "Errore interno");
        }
    }

    private void handleInspectCategoria(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CategoriaManager service = new CategoriaManager(ConPool.getDataSource());
        String nomecategoria = request.getParameter("nome");
        ArrayList<Corso> corsiassociati = null;
        try {
            corsiassociati = (ArrayList<Corso>) service.fetchCorsiAssociati(nomecategoria);
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Failed to fetch corsi associated with categoria " + nomecategoria, throwables);
        }
        request.setAttribute("corsiassociati", corsiassociati);
        request.setAttribute("nomecategoria", nomecategoria);
        request.getRequestDispatcher("/WEB-INF/views/admin/inspectResults/inspectCategoria.jsp").forward(request, response);
    }

    private void handleExecuteCreateCategoria(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("back", "/WEB-INF/views/admin/createResults/createCategoria.jsp");
        try {
            validate(CategoriaValidator.validateForm(request));
        } catch (InvalidRequestException e) {
            e.handle(request, response);
            return;
        }
        CategoriaManager service = new CategoriaManager(ConPool.getDataSource());
        try {
            if (service.createCategoria(request.getParameter("nome"))) {
                response.sendRedirect(request.getContextPath() + "/manager/showAllCategoria");
            }
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Failed to create categoria", throwables);
            response.sendError(500, "Errore interno");
        }
    }

    private void sendForbidden(HttpServletResponse response) {
        try {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Operazione non autorizzata.");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to send forbidden response", e);
        }
    }

    private void handleUnexpectedError(HttpServletResponse response, String context, Exception e) {
        LOGGER.log(Level.SEVERE, "Unhandled error during " + context, e);
        try {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore interno");
        } catch (IOException ioException) {
            LOGGER.log(Level.SEVERE, "Failed to send error response for " + context, ioException);
        }
    }

    protected void validate(RequestValidator validator) throws InvalidRequestException {
        if (validator.hasErrors()) {
            throw new InvalidRequestException("Validation error", validator.getErrors(), HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
