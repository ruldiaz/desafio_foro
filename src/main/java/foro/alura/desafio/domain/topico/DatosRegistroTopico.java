package foro.alura.desafio.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record DatosRegistroTopico(
        @NotNull
        @Positive
        Long idUsuario,

        @NotBlank
        String mensaje,

        @NotBlank
        String nombreCurso,

        @NotBlank
        String titulo,

        LocalDateTime fechacreacion
) {
}
