package com.noticiario.jdbc.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe Noticia
 * @author Pedro Antônio de Souza
 */
@NoArgsConstructor
@Getter
@Setter
public class Noticia {
    private Long id;
    private String titulo;
    private String corpo;
}
