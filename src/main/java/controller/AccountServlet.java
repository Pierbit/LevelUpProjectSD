package controller;

import controller.validators.LoginValidator;
import controller.validators.RegisterValidator;
import model.carrello.Carrello;
import model.carrello.CarrelloManager;
import model.oggetto.Oggetto;
import model.oggetto.OggettoManager;
import model.storage.ConPool;
import model.utente.Utente;
import model.utente.UtenteManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "AccountServlet", value = "/accounts/*")
public class AccountServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AccountServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = (request.getPathInfo() != null) ? request.getPathInfo() : "/";
        switch (path) {
            case "/":
                break;
            case "/register": {
                RequestDispatcher dispatcher =
                        request.getRequestDispatcher("/WEB-INF/views/logging/register.jsp");
                dispatcher.forward(request, response);
                break;
            }
            case "/login": {
                RequestDispatcher dispatcher =
                        request.getRequestDispatcher("/WEB-INF/views/logging/login.jsp");
                dispatcher.forward(request, response);
                break;
            }
            case "/logout": {
                HttpSession session = request.getSession();
                CarrelloManager service = new CarrelloManager(ConPool.getDataSource());
                UtenteManager service1 = new UtenteManager(ConPool.getDataSource());
                OggettoManager service2 = new OggettoManager(ConPool.getDataSource());
                Carrello carrello = (Carrello) session.getAttribute("carrello");
                Utente utente = (Utente) session.getAttribute("utente");
                try{

                    for(Oggetto o: carrello.getOggetti()) {
                        service2.deleteOggetto(o.getId());
                        service2.createOggetto(o);
                        service2.insertCorsoOggetto(o.getCorso().getId(),o.getId());
                        service2.insertOggettoCarrello(o.getId(),carrello.getId());
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    response.sendError(500,"Errore interno");
                    return;
                }
                session.removeAttribute("carrello");
                session.removeAttribute("utente");
                response.sendRedirect(request.getContextPath());
                break;
            }
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Risorsa non trovata");
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String path = (request.getPathInfo() != null) ? request.getPathInfo() : "/";
        try {
            switch (path) {
                case "/":
                    break;
                case "/register":
                    handleRegister(request, response);
                    break;
                case "/login":
                    handleLogin(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Operazione non consentita");
            }
        } catch (ServletException | IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to handle request for path: " + path, e);
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore interno");
            } catch (IOException ioException) {
                LOGGER.log(Level.SEVERE, "Failed to send error response for path: " + path, ioException);
            }
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("back", "/WEB-INF/views/logging/register.jsp");
        try {
            validate(RegisterValidator.validateForm(request));
        } catch (InvalidRequestException e) {
            e.handle(request, response);
        }

        UtenteManager service = new UtenteManager(ConPool.getDataSource());
        Utente utente = new Utente();
        utente.setNickname(request.getParameter("username"));
        utente.setEmail(request.getParameter("email"));
        utente.setPasswordHashed(request.getParameter("password"));
        utente.setManager(false);
        utente.setFotoProfilo("no_avatar.png");

        HttpSession session = request.getSession();
        Carrello carrello = (Carrello) session.getAttribute("carrello");

        try {
            registerUserAndCart(utente, carrello);
        } catch (RuntimeException | SQLException e) {
            String errore = "Registrazione non andata a buon fine. " + e.getMessage();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, errore);
            return;
        }

        request.setAttribute("justreg", "success");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/logging/login.jsp");
        dispatcher.forward(request, response);
    }

    private void registerUserAndCart(Utente utente, Carrello carrello) throws SQLException {
        UtenteManager service = new UtenteManager(ConPool.getDataSource());
        CarrelloManager service1 = new CarrelloManager(ConPool.getDataSource());

        service.createUtente(utente);
        service1.createCarrello(carrello);

        if (carrello.getOggetti() != null) {
            OggettoManager oggettoManager = new OggettoManager(ConPool.getDataSource());
            for (Oggetto o : carrello.getOggetti()) {
                oggettoManager.createOggetto(o);
                oggettoManager.insertCorsoOggetto(o.getCorso().getId(), o.getId());
                oggettoManager.insertOggettoCarrello(o.getId(), carrello.getId());
            }
        }
        service.setCarrello(utente.getNickname(), carrello.getId());
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("back", "/WEB-INF/views/logging/login.jsp");
        try {
            validate(LoginValidator.validateForm(request));
        } catch (InvalidRequestException e) {
            e.handle(request, response);
        }

        UtenteManager service = new UtenteManager(ConPool.getDataSource());
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Utente user = null;
        try {
            user = service.fetchUtente(username);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (user == null || !org.mindrot.jbcrypt.BCrypt.checkpw(password, user.getPassword())) {
            rejectLogin(request, response);
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("utente", user);
        try {
            session.setAttribute("carrello", resolveUserCart(service, user));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            response.sendError(500, "Errore interno");
            return;
        }
        response.sendRedirect(request.getContextPath());
    }

    private void rejectLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String errore = "Le credenziali sono errate o l'utente non esiste";
        ArrayList<String> errors = new ArrayList<>();
        errors.add(errore);
        request.setAttribute("alert", new Alert(errors, "danger"));
        response.setStatus(400);
        request.getRequestDispatcher("/WEB-INF/views/logging/login.jsp").forward(request, response);
    }

    private Carrello resolveUserCart(UtenteManager service, Utente user) throws SQLException {
        Carrello cart = service.fetchCarrello(user.getNickname());
        if (cart != null) {
            CarrelloManager carrelloManager = new CarrelloManager(ConPool.getDataSource());
            OggettoManager oggettoManager = new OggettoManager(ConPool.getDataSource());
            cart.setOggetti(carrelloManager.fetchOggetti(cart.getId()));
            for (Oggetto o : cart.getOggetti()) {
                o.setCorso(oggettoManager.fetchCorso(o.getId()));
            }
            return cart;
        } else {
            Carrello c = new Carrello();
            c.setId((int) (new Date().getTime() / 1000));
            c.setOggetti(new ArrayList<>());
            return c;
        }
    }

    protected void validate(RequestValidator validator) throws InvalidRequestException {
        if (validator.hasErrors()) {
            throw new InvalidRequestException("Validation error", validator.getErrors(), HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
