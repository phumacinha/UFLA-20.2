package com.noticiario.jdbc.dao;

import com.noticiario.jdbc.ConnectionFactory;
import com.noticiario.jdbc.modelo.Noticia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe NoticiaDao
 * @author Pedro Antônio de Souza
 */
public class NoticiaDao {
    // Armazena a conexão com o banco de dados
    private final Connection connection;
    
    public NoticiaDao () {
        this.connection = new ConnectionFactory().getConnection();
    }
    
    public void insere (Noticia noticia) {
        String sql = "INSERT INTO noticia (titulo, corpo) VALUES (?, ?);";
        
        try (PreparedStatement stmt = this.connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, noticia.getTitulo());
            stmt.setString(2, noticia.getCorpo());
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas == 0) {
                throw new RuntimeException("DAO Error: Nenhuma linha afetada.");
            }
            
            ResultSet idGerado = stmt.getGeneratedKeys();
            
            if (idGerado.next()) {
                noticia.setId(idGerado.getLong(1));
            }
            else {
                throw new RuntimeException("DAO Error: Nenhum ID obtido.");
            }
        }
        catch (SQLException e) {
            throw new RuntimeException("DAO Error: Erro ao inserir.");
        }
        finally {
            try {
                this.connection.close();
            }
            catch (SQLException e) {
                throw new RuntimeException("Erro ao fechar conexão com o BD.");
            }
        }
    }
}
