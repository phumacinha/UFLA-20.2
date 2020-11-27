package agenda.servlet;

import agenda.jdbc.dao.ContatoDao;
import agenda.jdbc.dao.DAOException;
import agenda.jdbc.dao.DAOException.DAOExceptionType;
import agenda.jdbc.modelo.Contato;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 * 
 * @author Pedro Antônio de Souza
 */
@WebServlet("/contatos")
public class ContatoServlet extends HttpServlet {
    // Requisição POST cria contato no banco de dados
    @Override
    protected void doPost(HttpServletRequest request, 
            HttpServletResponse response) throws IOException, ServletException {
        
        // Buscando parametros no request
        String nome = request.getParameter("nome");
        String telefone = request.getParameter("telefone");
        String email = request.getParameter("email");
        String cep = request.getParameter("cep");
        String endereco = request.getParameter("endereco");
        String numero = request.getParameter("numero");
        String bairro = request.getParameter("bairro");
        String estado = request.getParameter("estado");
        String cidade = request.getParameter("cidade");
        
        // Monta objeto Contato
        Contato contato = new Contato();
        contato.setNome(nome);
        contato.setTelefone(telefone);
        contato.setEmail(email);
        contato.setCep(cep);
        contato.setEndereco(endereco);
        contato.setNumero(numero);
        contato.setBairro(bairro);
        contato.setEstado(estado);
        contato.setCidade(cidade);
        
        // Busca DAO do contato
        ContatoDao dao = new ContatoDao();
        
        try {
            // Salva contato
            dao.adiciona(contato);
            
            // Resposta de sucesso
            response.setStatus(HttpServletResponse.SC_CREATED);
            
            // Busca o writer
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            // Converte contato para JSON
            ObjectMapper mapper = new ObjectMapper();
            String contatoJsonString = mapper.writeValueAsString(contato);
            
            out.print(contatoJsonString);
            out.flush();
            
        }
        catch (DAOException e) {
            // Erro do servidor
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            
            out.print(e);
        }
        
        
    }
    
    // Requisição PUT altera contato no banco de dados
    @Override
    protected void doPut(HttpServletRequest request, 
            HttpServletResponse response) throws IOException, ServletException {

        BufferedReader br = new BufferedReader(
                                new InputStreamReader(request.getInputStream())
                            );

        JSONObject dados = new JSONObject(br.readLine());
        
        // Buscando parametros no request
        Long id = Long.parseLong(dados.getString("id"));
        String nome = dados.getString("nome");
        String telefone = dados.getString("telefone");
        String email = dados.getString("email");
        String cep = dados.getString("cep");
        String endereco = dados.getString("endereco");
        String numero = dados.getString("numero");
        String bairro = dados.getString("bairro");
        String estado = dados.getString("estado");
        String cidade = dados.getString("cidade");
        
        // Monta objeto Contato
        Contato contato = new Contato();
        contato.setId(id);
        contato.setNome(nome);
        contato.setTelefone(telefone);
        contato.setEmail(email);
        contato.setCep(cep);
        contato.setEndereco(endereco);
        contato.setNumero(numero);
        contato.setBairro(bairro);
        contato.setEstado(estado);
        contato.setCidade(cidade);
        
        // Busca DAO do contato
        ContatoDao dao = new ContatoDao();
        
        try {
            // Altera contato
            dao.altera(contato);
            
            // Resposta de sucesso
            response.setStatus(HttpServletResponse.SC_OK);
            
            // Busca o writer
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            // Converte contato para JSON
            ObjectMapper mapper = new ObjectMapper();
            String contatoJsonString = mapper.writeValueAsString(contato);
            
            out.print(contatoJsonString);
            out.flush();
            
        }
        catch (DAOException e) {
            if (e.getType().equals(DAOExceptionType.NENHUMA_LINHA_AFETADA)) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
            else {
                // Erro do servidor
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
        
        
    }
    
    // Requisição DELETE remove contato no banco de dados
    @Override
    protected void doDelete(HttpServletRequest request, 
            HttpServletResponse response) throws IOException, ServletException {

        BufferedReader br = new BufferedReader(
                                new InputStreamReader(request.getInputStream())
                            );

        JSONObject dados = new JSONObject(br.readLine());
        
        // Buscando parametros no request
        Long id = Long.parseLong(dados.getString("id"));
        String nome = dados.getString("nome");
        String telefone = dados.getString("telefone");
        String email = dados.getString("email");
        String cep = dados.getString("cep");
        String endereco = dados.getString("endereco");
        String numero = dados.getString("numero");
        String bairro = dados.getString("bairro");
        String estado = dados.getString("estado");
        String cidade = dados.getString("cidade");
        
        // Monta objeto Contato
        Contato contato = new Contato();
        contato.setId(id);
        contato.setNome(nome);
        contato.setTelefone(telefone);
        contato.setEmail(email);
        contato.setCep(cep);
        contato.setEndereco(endereco);
        contato.setNumero(numero);
        contato.setBairro(bairro);
        contato.setEstado(estado);
        contato.setCidade(cidade);
        
        // Busca DAO do contato
        ContatoDao dao = new ContatoDao();
        
        try {
            // Altera contato
            dao.remove(contato);
            
            // Resposta de sucesso
            response.setStatus(HttpServletResponse.SC_OK);
        }
        catch (DAOException e) {
            if (e.getType().equals(DAOExceptionType.NENHUMA_LINHA_AFETADA)) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
            else {
                // Erro do servidor
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
        
        
    }
    
    // Requisição GET retorna lista de contatos
    @Override
    protected void doGet(HttpServletRequest request, 
            HttpServletResponse response) throws IOException, ServletException {
        // Busca DAO
        ContatoDao dao = new ContatoDao();
        
        try {
            // Busca lista de contatos
            ArrayList<Contato> lista = dao.lista();
            
            // Busca o writer
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            // Converte contato para JSON
            ObjectMapper mapper = new ObjectMapper();
            String contatoJsonString = mapper.writeValueAsString(lista);
            
            out.print(contatoJsonString);
            out.flush();
        }
        catch (DAOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
}
