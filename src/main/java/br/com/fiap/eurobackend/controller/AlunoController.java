package br.com.fiap.eurobackend.controller;

import br.com.fiap.eurobackend.dto.AlunoDto;
import br.com.fiap.eurobackend.service.AlunoService;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public ResponseEntity<List<AlunoDto>> getAll() {
        List<AlunoDto> list = alunoService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDto> getById(@PathVariable Long id) {
        AlunoDto dto = alunoService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<AlunoDto> create(@Valid @RequestBody AlunoDto dto) {
        dto = alunoService.save(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoDto> update(@PathVariable Long id, @Valid @RequestBody AlunoDto dto) {
        dto = alunoService.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alunoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
