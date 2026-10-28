package com.regalado.denunciaservice.service;

import com.regalado.denunciaservice.entity.Denuncia;
import com.regalado.denunciaservice.repository.DenunciaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DenunciaService {

    private final DenunciaRepository denunciaRepository;

    public DenunciaService(DenunciaRepository denunciaRepository) {
        this.denunciaRepository = denunciaRepository;
    }

    @Transactional
    public Denuncia registrar(Denuncia denuncia) {
        denuncia.setId(null);
        return denunciaRepository.save(denuncia);
    }

    /**
     * Obtiene denuncias paginadas.
     *
     * @param offset número de página (inicia en 0)
     * @param limit  cantidad de registros por página
     */
    @Transactional(readOnly = true)
    public Page<Denuncia> obtenerPaginado(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, "id"));
        return denunciaRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Denuncia> obtenerPorId(Integer id) {
        return denunciaRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Denuncia> buscarPorDni(String dni) {
        return denunciaRepository.findByDni(dni);
    }

    @Transactional
    public Optional<Denuncia> atender(Integer id) {
        return denunciaRepository.findById(id).map(denuncia -> {
            denuncia.setEstado(0);
            return denunciaRepository.save(denuncia);
        });
    }
}
