package br.com.fiap.eurobackend.controller;

import br.com.fiap.eurobackend.dto.TurmaDto;
import br.com.fiap.eurobackend.service.TurmaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    @GetMapping
    public ResponseEntity<List<TurmaDto>> getAll() {
        List<TurmaDto> list = turmaService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaDto> getById(@PathVariable Long id) {
        TurmaDto dto = turmaService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<TurmaDto> create(@Valid @RequestBody TurmaDto dto) {
        dto = turmaService.save(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmaDto> update(@PathVariable Long id, @Valid @RequestBody TurmaDto dto) {
        dto = turmaService.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        turmaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
