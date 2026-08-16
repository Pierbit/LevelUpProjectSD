package controller.components;

import controller.AccountServlet;
import model.storage.ConPool;
import model.utente.Utente;
import model.utente.UtenteManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServletTest {

    private final UtenteManager manager = new UtenteManager(ConPool.getDataSource());
    private static final String TEST_NICKNAME = "test_login_junit";

    @AfterEach
    void cleanup() throws SQLException {
        manager.deleteUtente(TEST_NICKNAME);
    }

    //Testing the full login flow confirms the servlet correctly verifies it against the stored hash and establishes a session.
    @Test
    void loginSucceedsWithCorrectPassword() throws SQLException, ServletException, IOException {
        Utente utente = new Utente();
        utente.setNickname(TEST_NICKNAME);
        utente.setEmail("logintest@test.com");
        utente.setPasswordHashed("CorrectPassword123");
        utente.setManager(false);
        manager.createUtente(utente);

        AccountServlet servlet = new AccountServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getPathInfo()).thenReturn("/login");
        when(request.getParameter("username")).thenReturn(TEST_NICKNAME);
        when(request.getParameter("password")).thenReturn("CorrectPassword123");
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("");

        servlet.doPost(request, response);

        verify(session).setAttribute(eq("utente"), any());
        verify(response).sendRedirect(anyString());
    }

    //Testing login fails correctly with a wrong password against a real and servlet responds with 400
    @Test
    void loginFailsWithWrongPassword() throws SQLException, ServletException, IOException {
        Utente utente = new Utente();
        utente.setNickname(TEST_NICKNAME);
        utente.setEmail("logintest@test.com");
        utente.setPasswordHashed("CorrectPassword123");
        utente.setManager(false);
        manager.createUtente(utente);

        AccountServlet servlet = new AccountServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getPathInfo()).thenReturn("/login");
        when(request.getParameter("username")).thenReturn(TEST_NICKNAME);
        when(request.getParameter("password")).thenReturn("WrongPassword");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(session, never()).setAttribute(eq("utente"), any());
        verify(response).setStatus(400);
    }

    //Testing login fails correctly for a username that doesn't exist at all
    @Test
    void loginFailsForNonExistentUser() throws ServletException, IOException {
        AccountServlet servlet = new AccountServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getPathInfo()).thenReturn("/login");
        when(request.getParameter("username")).thenReturn("nonexistent_user_junit");
        when(request.getParameter("password")).thenReturn("Whatever123");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(session, never()).setAttribute(eq("utente"), any());
        verify(response).setStatus(400);
    }

    //Testing user registration works
    @Test
    void registerCreatesUserWithHashedPassword() throws SQLException, ServletException, IOException {
        AccountServlet servlet = new AccountServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getPathInfo()).thenReturn("/register");
        when(request.getParameter("username")).thenReturn(TEST_NICKNAME);
        when(request.getParameter("email")).thenReturn("regtest@test.com");
        when(request.getParameter("password")).thenReturn("NewPassword123");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("carrello")).thenReturn(new model.carrello.Carrello() {{ setId(222222); setOggetti(new java.util.ArrayList<>()); }});
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        servlet.doPost(request, response);

        Utente created = manager.fetchUtente(TEST_NICKNAME);
        assertNotNull(created);
        assertTrue(org.mindrot.jbcrypt.BCrypt.checkpw("NewPassword123", created.getPassword()));

        manager.deleteUtente(TEST_NICKNAME);
        new model.carrello.CarrelloManager(ConPool.getDataSource()).deleteCarrello(222222);
    }
}
