package com.sistema.examenes.controller;

import com.sistema.examenes.entity.*;
import com.sistema.examenes.projection.ResponsableProjection;
import com.sistema.examenes.repository.UsuarioRepository;
import com.sistema.examenes.services.RolService;
import com.sistema.examenes.services.UsuarioRolService;
import com.sistema.examenes.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @Autowired
    private UsuarioRepository uR;
    @Autowired
    private UsuarioRolService userrol;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void init() {
        Rol usuario1 = new Rol(1L, "ADMIN");
        Rol usuario2 = new Rol(2L, "SUPERADMIN");
        Rol usuario3 = new Rol(3L, "RESPONSABLE");
        Rol usuario4 = new Rol(4L, "AUTORIDAD");

        rolService.save(usuario1);
        rolService.save(usuario2);
        rolService.save(usuario3);
        rolService.save(usuario4);
    }
    // @PostMapping("/")
    // public Usuario guardarUsuario(@RequestBody Usuario usuario) throws Exception{
    // Set<UsuarioRol> usuarioRoles = new HashSet<>();
    //
    // Rol rol = new Rol();
    // rol.setRolId(2L);
    // rol.setRolNombre("NORMAL");
    //
    // UsuarioRol usuarioRol = new UsuarioRol();
    // usuarioRol.setUsuario(usuario);
    // usuarioRol.setRol(rol);
    //
    // usuarioRoles.add(usuarioRol);
    // return usuarioService.guardarUsuario(usuario,usuarioRoles);

    // }

    @PostMapping("/crear/{rolId}")
    public ResponseEntity<Usuario> crear(@RequestBody Usuario r, @PathVariable Long rolId) {
        try {
            if (usuarioService.obtenerUsuario(r.getUsername()) == null) {
                // Buscar el rol por ID
                Rol rol = rolService.findById(rolId);
                r.setPassword(this.bCryptPasswordEncoder.encode(r.getPassword()));
                r.setVisible(true);
                // Crear un nuevo UsuarioRol y establecer las referencias correspondientes
                UsuarioRol usuarioRol = new UsuarioRol();
                usuarioRol.setUsuario(r);
                usuarioRol.setRol(rol);

                // Agregar el UsuarioRol a la lista de roles del usuario
                r.getUsuarioRoles().add(usuarioRol);

                // Guardar el usuario en la base de datos
                // Usuario nuevoUsuario = usuarioService.save(r);
                return new ResponseEntity<>(usuarioService.save(r), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> obtenerLista() {
        try {

            return new ResponseEntity<>(usuarioService.findByAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // listar solo los responsables
    @GetMapping("/listarResponsable")
    public ResponseEntity<List<Usuario>> obtenerListaResponsables() {
        try {
            // List<Usuario> responsables = uR.listaResponsables();
            return new ResponseEntity<>(uR.listaResponsables(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listarv")
    public ResponseEntity<List<Usuario>> obtenerListav() {
        try {
            return new ResponseEntity<>(uR.listar(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listarResDatos")
    public ResponseEntity<List<Usuario>> obtenerListaRespoDatos() {
        try {

            return new ResponseEntity<>(uR.listaResponsablesDatos(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listarResponsableAdmin")
    public ResponseEntity<List<Usuario>> obtenerListaResponsableAdmin() {
        try {
            return new ResponseEntity<>(uR.listaResponsablesAdmin(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/responsables")
    public ResponseEntity<List<ResponsableProjection>> Responsables() {
        try {
            return new ResponseEntity<>(uR.responsables(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/buscar/{username}")
    public Usuario obtenerUsuario(@PathVariable("username") String username) {
        return usuarioService.obtenerUsuario(username);
    }

    @GetMapping("/buscaruser/{username}")
    public Usuario obtenerIdUsuario(@PathVariable("username") String username) {
        return usuarioService.obtenerId(username);
    }

    @DeleteMapping("/{usuarioId}")
    public void eliminarUsuario(@PathVariable("usuarioId") Long usuarioId) {
        usuarioService.delete(usuarioId);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Usuario> actualizarCliente(@PathVariable Long id, @RequestBody Usuario p) {
        Usuario usu = usuarioService.findById(id);
        UsuarioRol urol=userrol.findByUsuario_UsuarioId(id);
        if (usu == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                usu.setPassword(this.bCryptPasswordEncoder.encode(p.getPassword()));
                return new ResponseEntity<>(usuarioService.save(usu), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }

    @PutMapping("/eliminarlogic/{id}")
    public ResponseEntity<?> eliminarlogic(@PathVariable Long id) {
        Usuario a = usuarioService.findById(id);
        if (a == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                a.setVisible(false);
                return new ResponseEntity<>(usuarioService.save(a), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }

    // public List<Usuario> listaAdminDatos();
    @GetMapping("/listarAdminDatos")
    public ResponseEntity<List<Usuario>> obtenerListaAdminDatos() {
        try {

            return new ResponseEntity<>(uR.listaAdminDatos(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listarsoloResponsables")
    public ResponseEntity<List<Usuario>> solloresonsables() {
        try {

            return new ResponseEntity<>(uR.listaSOLORESPONSABLES(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
