package br.com.fiap.eurobackend.controller;

import br.com.fiap.eurobackend.dto.FrequenciaDto;
import br.com.fiap.eurobackend.service.FrequenciaService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/frequencias")
public class FrequenciaController {

    @Autowired
    private FrequenciaService frequenciaService;

    @GetMapping
    public ResponseEntity<List<FrequenciaDto>> getAll() {
        List<FrequenciaDto> list = frequenciaService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FrequenciaDto> getById(@PathVariable Long id) {
        FrequenciaDto dto = frequenciaService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/aula/{idAula}")
    public ResponseEntity<List<FrequenciaDto>> getByAulaId(@PathVariable Long idAula) {
        List<FrequenciaDto> list = frequenciaService.findByAulaId(idAula);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/matricula/{idMatricula}")
    public ResponseEntity<List<FrequenciaDto>> getByMatriculaId(@PathVariable Long idMatricula) {
        List<FrequenciaDto> list = frequenciaService.findByMatriculaId(idMatricula);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<FrequenciaDto> create(@Valid @RequestBody FrequenciaDto dto) {
        dto = frequenciaService.save(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FrequenciaDto> update(@PathVariable Long id, @Valid @RequestBody FrequenciaDto dto) {
        dto = frequenciaService.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        frequenciaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
