package com.noticiario.servlet;

import com.noticiario.jdbc.dao.NoticiaDao;
import com.noticiario.jdbc.modelo.Noticia;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Classe NoticiaServlet
 * @author Pedro Antônio de Souza
 */
@WebServlet("/noticia")
public class NoticiaServlet extends HttpServlet {
    
    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response) {
    
        // Buscar parâmetros na requisição
        String titulo = request.getParameter("titulo");
        String corpo = request.getParameter("corpo");
        
        // Monta objeto Noticia
        Noticia noticia = new Noticia();
        noticia.setCorpo(corpo);
        noticia.setTitulo(titulo);
        
        // Instancia DAO
        NoticiaDao dao = new NoticiaDao();
        
        try {
            // Insere notícia
            dao.insere(noticia);
            
            // Resposta de criação
            response.setStatus(HttpServletResponse.SC_CREATED);
        }
        catch (RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
