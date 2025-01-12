package foro.alura.desafio.controller;

import foro.alura.desafio.domain.curso.Curso;
import foro.alura.desafio.domain.curso.CursoRepository;
import foro.alura.desafio.domain.topico.DatosRegistroTopico;
import foro.alura.desafio.domain.topico.DatosRespuestaTopico;
import foro.alura.desafio.domain.topico.Topico;
import foro.alura.desafio.domain.topico.TopicoRepository;
import foro.alura.desafio.domain.usuarios.DatosRespuestaUsuario;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder) {
        // Buscar el Curso por el nombre recibido en los datos del Topico
        Curso curso = cursoRepository.findByNombre(datosRegistroTopico.nombreCurso());
        if(curso == null){
            throw new RuntimeException("Curso no encontrado");
        }

        // Crear el Topico usando los datos recibidos en el DTO
        Topico topico = new Topico(datosRegistroTopico);

        // Asociar el Topico con el Curso
        topico.setCurso(curso);

        // Guardar el Topico en la base de datos
        Topico topicoGuardado = topicoRepository.save(topico);

        // Preparar la respuesta
        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(
                topicoGuardado.getId(),
                topicoGuardado.getTitulo(),
                topicoGuardado.getMensaje(),
                topicoGuardado.getStatus(),
                new DatosRespuestaUsuario(topicoGuardado.getAutor().getNombre()),
                topicoGuardado.getCurso().getNombre(),
                topicoGuardado.getFechacreacion()
        );

        // Construir la URI del nuevo recurso creado
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topicoGuardado.getId()).toUri();

        // Devolver la respuesta con la URI del nuevo Topico
        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosRespuestaTopico>> listadoTopicos(@PageableDefault(size=10)Pageable paginacion){
        return ResponseEntity.ok(topicoRepository.findAll(paginacion).map(topico -> new DatosRespuestaTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(),topico.getStatus(), new DatosRespuestaUsuario(topico.getAutor().getNombre()), topico.getCurso().getNombre(), topico.getFechacreacion())));

    }

    // Buscar un Topico por su ID
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> obtenerTopicoPorId(@PathVariable Long id) {
        // Buscar el Topico por su ID
        Topico topico = topicoRepository.findById(id).orElse(null);

        // Si no se encuentra el Topico, devolver un 404
        if (topico == null) {
            return ResponseEntity.notFound().build();
        }

        // Devolver la respuesta con los datos del Topico
        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getStatus(),
                new DatosRespuestaUsuario(topico.getAutor().getNombre()),
                topico.getCurso().getNombre(),
                topico.getFechacreacion()
        );

        return ResponseEntity.ok(datosRespuestaTopico);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        // Buscar el Tópico por ID
        Optional<Topico> optionalTopico = topicoRepository.findById(id);

        // Verificar si el Tópico existe
        if (!optionalTopico.isPresent()) {
            // Si no existe, devolver un error 404 (Not Found)
            return ResponseEntity.notFound().build();
        }

        // Eliminar el Tópico si existe
        topicoRepository.deleteById(id);

        // Devolver una respuesta exitosa (204 No Content) tras la eliminación
        return ResponseEntity.noContent().build();
    }


    // Método para actualizar un Topico por su ID
    @PutMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosRegistroTopico datosRegistroTopico) {
        // Buscar el Topico por su ID
        Topico topico = topicoRepository.findById(id).orElse(null);

        // Si no se encuentra el Topico, devolver un 404
        if (topico == null) {
            return ResponseEntity.notFound().build();
        }

        // Buscar el Curso por nombre recibido
        Curso curso = cursoRepository.findByNombre(datosRegistroTopico.nombreCurso());
        if (curso == null) {
            throw new RuntimeException("Curso no encontrado");
        }

        // Actualizar los datos del Topico
        topico.setTitulo(datosRegistroTopico.titulo());
        topico.setMensaje(datosRegistroTopico.mensaje());
        topico.setCurso(curso);

        // Actualizar la fecha de creación si es necesario (si no se envía un valor, se puede omitir)
        if (datosRegistroTopico.fechacreacion() != null) {
            topico.setFechacreacion(datosRegistroTopico.fechacreacion());
        }

        // Guardar el Topico actualizado en la base de datos
        Topico topicoActualizado = topicoRepository.save(topico);

        // Preparar la respuesta
        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(
                topicoActualizado.getId(),
                topicoActualizado.getTitulo(),
                topicoActualizado.getMensaje(),
                topicoActualizado.getStatus(),
                new DatosRespuestaUsuario(topicoActualizado.getAutor().getNombre()),
                topicoActualizado.getCurso().getNombre(),
                topicoActualizado.getFechacreacion()
        );

        return ResponseEntity.ok(datosRespuestaTopico);
    }
}


