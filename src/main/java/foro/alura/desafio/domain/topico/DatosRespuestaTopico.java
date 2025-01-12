package foro.alura.desafio.domain.topico;

import foro.alura.desafio.domain.curso.Curso;
import foro.alura.desafio.domain.usuarios.DatosRespuestaUsuario;
import foro.alura.desafio.domain.usuarios.Usuario;

import java.time.LocalDateTime;

public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        Status status,
        DatosRespuestaUsuario autor,
        String nombre,
        LocalDateTime fechacreacion
) {
}
