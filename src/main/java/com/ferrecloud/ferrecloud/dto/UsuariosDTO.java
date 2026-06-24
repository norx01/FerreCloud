package com.ferrecloud.ferrecloud.dto;

import com.ferrecloud.ferrecloud.model.Usuarios;
import lombok.Data;

@Data
public class UsuariosDTO {

    private String id;
    private String nombre;
    private String documento;
    private String telefono;
    private String direccion;
    private String correoElectronico;
    private String cargo;
    private Double salario;
    private Usuarios.Rol rol;

    // Convierte un Usuarios (con password) a UsuariosDTO (sin password)
    public static UsuariosDTO desde(Usuarios usuarios) {
        UsuariosDTO dto = new UsuariosDTO();
        dto.setId(usuarios.getId());
        dto.setNombre(usuarios.getNombre());
        dto.setDocumento(usuarios.getDocumento());
        dto.setTelefono(usuarios.getTelefono());
        dto.setDireccion(usuarios.getDireccion());
        dto.setCorreoElectronico(usuarios.getCorreoElectronico());
        dto.setCargo(usuarios.getCargo());
        dto.setSalario(usuarios.getSalario());
        dto.setRol(usuarios.getRol());
        return dto;
    }
}