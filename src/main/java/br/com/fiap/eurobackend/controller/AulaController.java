package br.com.fiap.eurobackend.controller;

import br.com.fiap.eurobackend.dto.AulaDto;
import br.com.fiap.eurobackend.service.AulaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/aulas")
public class AulaController {

    @Autowired
    private AulaService aulaService;

    @GetMapping
    public ResponseEntity<List<AulaDto>> getAll() {
        List<AulaDto> list = aulaService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AulaDto> getById(@PathVariable Long id) {
        AulaDto dto = aulaService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<AulaDto> create(@Valid @RequestBody AulaDto dto) {
        dto = aulaService.save(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AulaDto> update(@PathVariable Long id, @Valid @RequestBody AulaDto dto) {
        dto = aulaService.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        aulaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
