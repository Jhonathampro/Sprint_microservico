package br.com.fiap.eurobackend.controller;

import br.com.fiap.eurobackend.dto.MatriculaDto;
import br.com.fiap.eurobackend.service.MatriculaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    @GetMapping
    public ResponseEntity<List<MatriculaDto>> getAll() {
        List<MatriculaDto> list = matriculaService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatriculaDto> getById(@PathVariable Long id) {
        MatriculaDto dto = matriculaService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/aluno/{idAluno}")
    public ResponseEntity<List<MatriculaDto>> getByAlunoId(@PathVariable Long idAluno) {
        List<MatriculaDto> list = matriculaService.findByAlunoId(idAluno);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/turma/{idTurma}")
    public ResponseEntity<List<MatriculaDto>> getByTurmaId(@PathVariable Long idTurma) {
        List<MatriculaDto> list = matriculaService.findByTurmaId(idTurma);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<MatriculaDto> create(@Valid @RequestBody MatriculaDto dto) {
        dto = matriculaService.save(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatriculaDto> update(@PathVariable Long id, @Valid @RequestBody MatriculaDto dto) {
        dto = matriculaService.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        matriculaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
