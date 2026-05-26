package com.tienda.usuarios.service;

import com.tienda.usuarios.dto.UsuarioSimpleDTO;
import com.tienda.usuarios.model.Usuario;
import com.tienda.usuarios.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario guardarUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return repository.save(usuario);
    }
    public UsuarioSimpleDTO buscarPorIdSimple(Integer id) {
        Optional<Usuario> usuarioOpt = repository.findById(id);
        
        if (usuarioOpt.isEmpty()) {
            return null;
        }
        
        Usuario usuario = usuarioOpt.get();
        UsuarioSimpleDTO dto = new UsuarioSimpleDTO();
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        
        return dto;
    }
    
    public Optional<Usuario> buscarPorId(Integer id){
        return repository.findById(id);
    }



   // public void eliminarporId(Integer id){
       // repository.deleteById(id);
//
  //  }

   public String eliminar(Integer id) {
       List<Usuario> lista = repository.findAll();
       for (Usuario p : lista) {
           if (p.getId().equals(id)) {
               repository.deleteById(id);
               return "Usuario eliminado correctamente";
           }

       }
       return "Usuario no encontrado";
   }

   public String actualizar(Usuario usuario) {
       List<Usuario> lista = repository.findAll();
       for (Usuario p : lista) {
           if (p.getId().equals(usuario.getId())) {
               repository.save(usuario);
               return "Usuario actualizado";
           }
       }
       return "Usuario no encontrado";

   }
}