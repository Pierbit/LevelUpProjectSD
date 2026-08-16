package controller.components;

import controller.ManagerServlet;
import model.utente.Utente;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

class ManagerServletTest {

    //Testing that a non-admin user is rejected with 403, regardless of which admin action they attempt
    @Test
    void nonAdminUserIsForbidden() throws ServletException, IOException {
        ManagerServlet servlet = new ManagerServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        Utente nonAdmin = new Utente();
        nonAdmin.setManager(false);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("utente")).thenReturn(nonAdmin);
        when(request.getPathInfo()).thenReturn("/showAllUtente");

        servlet.doGet(request, response);

        verify(response).sendError(eq(HttpServletResponse.SC_FORBIDDEN), anyString());
    }

    //Testing that an admin user is allowed through to a real action
    @Test
    void adminUserIsAllowedThrough() throws ServletException, IOException, java.sql.SQLException {
        ManagerServlet servlet = new ManagerServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        javax.servlet.RequestDispatcher dispatcher = mock(javax.servlet.RequestDispatcher.class);

        Utente admin = new Utente();
        admin.setManager(true);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("utente")).thenReturn(admin);
        when(request.getPathInfo()).thenReturn("/showAllCategoria");
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(response, never()).sendError(eq(HttpServletResponse.SC_FORBIDDEN), anyString());
        verify(request).setAttribute(eq("elencoCategorie"), any());
    }
}
