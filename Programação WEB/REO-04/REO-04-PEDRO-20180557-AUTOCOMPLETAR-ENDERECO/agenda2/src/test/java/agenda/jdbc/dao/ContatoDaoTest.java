/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda.jdbc.dao;

import agenda.jdbc.modelo.Contato;
import agenda.servlet.ContatoServlet;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author Pedro Antônio de Souza
 */
public class ContatoDaoTest extends Mockito {
    // Busca a DAO do Contato
    ContatoDao dao = new ContatoDao();
    
    @Before
    @After
    public void limpaTodosDados () {
        ArrayList<Contato> lista = dao.lista();
        
        for (Contato contato : lista) {
            dao.remove(contato);
        }
    }
    
    @Test
    public void testAdiciona () throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        
        when(request.getParameter("nome")).thenReturn("José da Silva");
        when(request.getParameter("telefone")).thenReturn("(35) 99988-7766");
        when(request.getParameter("email")).thenReturn("josedasilva@gmail.com");
        when(request.getParameter("cep")).thenReturn("37200-100");
        when(request.getParameter("endereco")).thenReturn("Travessa Doutor Fernando Haddad");
        when(request.getParameter("numero")).thenReturn("100");
        when(request.getParameter("bairro")).thenReturn("Centro");
        when(request.getParameter("estado")).thenReturn("MG");
        when(request.getParameter("cidade")).thenReturn("Lavras");
        
        StringWriter sw = new StringWriter();
        PrintWriter writer = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(writer);
        
        new ContatoServlet().doPost(request, response);
        
        writer.flush();
        assertEquals(HttpServletResponse.SC_CREATED, response.getStatus());
        
    }
}
