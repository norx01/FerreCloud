package com.ferrecloud.ferrecloud.service;

import com.ferrecloud.ferrecloud.dto.UsuariosDTO;
import com.ferrecloud.ferrecloud.model.Usuarios;
import com.ferrecloud.ferrecloud.repository.UsuariosRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuariosService {

    private final UsuariosRepository repository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuariosService(UsuariosRepository repository) {
        this.repository = repository;
    }

    public List<Usuarios> listar() {
        return repository.findAll();
    }

    public List<UsuariosDTO> listarDTO() {
        return repository.findAll()
                .stream()
                .map(UsuariosDTO::desde)
                .toList();
    }

    public Usuarios buscarPorId(String id) {
        return repository.findById(id).orElse(null);
    }

    public Usuarios guardar(Usuarios usuario) {
        if (usuario.getId() != null && usuario.getId().isBlank()) {
            usuario.setId(null);
        }

        if (usuario.getPassword() != null && !usuario.getPassword().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }

        return repository.save(usuario);
    }

    public void eliminar(String id) {
        repository.deleteById(id);
    }

    public Usuarios login(String documento, String password) {
        Usuarios usuario = repository.findByDocumento(documento).orElse(null);

        if (usuario == null) {
            return null;
        }

        boolean coincide = passwordEncoder.matches(password, usuario.getPassword());

        return coincide ? usuario : null;
    }
}