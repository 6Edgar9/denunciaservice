package com.regalado.denunciaservice.controller;

import com.regalado.denunciaservice.entity.Denuncia;
import com.regalado.denunciaservice.service.DenunciaService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/denuncias")
public class DenunciaController {

    private final DenunciaService denunciaService;

    public DenunciaController(DenunciaService denunciaService) {
        this.denunciaService = denunciaService;
    }

    @PostMapping("/")
    public ResponseEntity<Denuncia> registrar(@RequestBody Denuncia denuncia) {
        return ResponseEntity.status(HttpStatus.CREATED).body(denunciaService.registrar(denuncia));
    }

    @GetMapping("/")
    public ResponseEntity<Page<Denuncia>> obtenerPaginado(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(denunciaService.obtenerPaginado(offset, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Denuncia> obtenerPorId(@PathVariable Integer id) {
        return denunciaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/ciudadano/{dni}")
    public ResponseEntity<List<Denuncia>> buscarPorDni(@PathVariable String dni) {
        return ResponseEntity.ok(denunciaService.buscarPorDni(dni));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Denuncia> atender(@PathVariable Integer id) {
        return denunciaService.atender(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
