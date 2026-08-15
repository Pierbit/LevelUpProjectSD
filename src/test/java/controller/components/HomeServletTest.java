package controller.components;

import controller.HomeServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HomeServletTest {

    //Testing /loadCategorie returns a JSON response containing a "categorie" key
    @Test
    void loadCategorieReturnsValidJson() throws ServletException, IOException {
        HomeServlet servlet = new HomeServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getPathInfo()).thenReturn("/loadCategorie");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);
        writer.flush();

        String output = stringWriter.toString();
        assertTrue(output.contains("categorie"));
        verify(response).setContentType("application/json");
    }

    //Testing browseCorsi category search flow works correctly by attaching a result to the request via setAttribute without error
    //Several issues encountered (tags problem, ordini problem)
    @Test
    void browseCorsiWithCategoryReturnsWithoutError() throws ServletException, IOException {
        HomeServlet servlet = new HomeServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getPathInfo()).thenReturn("/browseCorsi");
        when(request.getParameter("categoriaName")).thenReturn("tecnologia");
        when(request.getParameterNames()).thenReturn(Collections.enumeration(Collections.singletonList("categoriaName")));
        when(request.getRequestDispatcher(anyString())).thenReturn(mock(javax.servlet.RequestDispatcher.class));

        servlet.doGet(request, response);

        verify(request).setAttribute(eq("corsicercati"), any());
    }
}
