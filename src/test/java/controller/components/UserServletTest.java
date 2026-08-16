package controller.components;

import controller.UserServlet;
import model.corso.Corso;
import model.corso.CorsoManager;
import model.storage.ConPool;
import model.utente.Utente;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServletTest {

    //Testing that an unauthenticated user (no session "utente") is rejected with 403 when attempting a protected action.
    @Test
    void unauthenticatedUserIsForbidden() throws ServletException, IOException {
        UserServlet servlet = new UserServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("utente")).thenReturn(null);
        when(request.getPathInfo()).thenReturn("/unsub");

        servlet.doPost(request, response);

        verify(response).sendError(eq(403), anyString());
    }

    //Calls corsoManager's /executeCreateCorso to confirm the resulting state
    @Test
    void courseCreatedViaUserFlowHasNoAssociations() throws Exception {
        CorsoManager corsoManager = new CorsoManager(ConPool.getDataSource());
        Corso corso = new Corso();
        corso.setNome("Test UserFlow Corso");
        corso.setPrezzoBase(10.00);
        corso.setTesto("temp");
        corso.setCopertina("placeholder.jpg");

        boolean created = corsoManager.createCorso(corso);
        int corsoId = corsoManager.fetchCorsoId("Test UserFlow Corso");
        boolean creatorLinked = corsoManager.insertCreatore("marcorossi", corsoId);

        assertTrue(created);
        assertTrue(creatorLinked);

        Corso fetched = corsoManager.fetchCorso(corsoId);
        assertNull(fetched.getCategoria());
        assertNull(fetched.getTags());

        corsoManager.deleteCorso(corsoId);
    }

    //Testing /inspectCorsoPubblicato's revenue calculation (guadagno = numutenti * prezzoBase)
    @Test
    void inspectCorsoPubblicatoCalculatesRevenueCorrectly() throws Exception {
        CorsoManager corsoManager = new CorsoManager(ConPool.getDataSource());
        Corso corso = new Corso();
        corso.setNome("Test Revenue Corso");
        corso.setPrezzoBase(20.00);
        corso.setTesto("temp");
        corso.setCopertina(null);
        corsoManager.createCorso(corso);
        int corsoId = corsoManager.fetchCorsoId("Test Revenue Corso");
        corsoManager.insertPartecipante("marcorossi", corsoId);
        corsoManager.insertPartecipante("giuliabianchi", corsoId);

        UserServlet servlet = new UserServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        Utente utente = new Utente();
        utente.setNickname("marcorossi");

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("utente")).thenReturn(utente);
        when(request.getPathInfo()).thenReturn("/inspectCorsoPubblicato");
        when(request.getParameter("corsoid")).thenReturn(String.valueOf(corsoId));
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute("guadagno", 40.0);
        verify(request).setAttribute("numiscritti", 2);

        corsoManager.deleteUtentiPartecipanti(corsoId);
        corsoManager.deleteCorso(corsoId);
    }
}
