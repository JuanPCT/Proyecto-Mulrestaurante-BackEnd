package com.multiristorante.app.backend.controllers;

import com.multiristorante.app.backend.Shared.dto.UsuarioDto;
import com.multiristorante.app.backend.models.requests.UsuarioDetailsRequestModel;
import com.multiristorante.app.backend.models.responses.UsuarioRest;
import com.multiristorante.app.backend.service.EmailService;
import com.multiristorante.app.backend.service.UsuarioService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    EmailService emailService;
    

    public UsuarioController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public UsuarioRest getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getPrincipal().toString();

        UsuarioDto usuarioDto =  usuarioService.getUser(email);

        UsuarioRest userToReturn = new UsuarioRest();

        BeanUtils.copyProperties(usuarioDto, userToReturn);

        return userToReturn;
    }

    @PostMapping
    public UsuarioRest postUsuarios(@RequestBody UsuarioDetailsRequestModel usuario) {

        UsuarioRest usuarioToReturn = new UsuarioRest();
        UsuarioDto usuarioDto = new UsuarioDto();

        BeanUtils.copyProperties(usuario, usuarioDto);

        UsuarioDto createdUser = usuarioService.createUsuario(usuarioDto);

        this.emailService.sendListEmail(usuario.getEmail());
        
        BeanUtils.copyProperties(createdUser, usuarioToReturn);

        return usuarioToReturn;
    }
    @GetMapping("/email")
    public void sendEmail(String email){
        this.emailService.sendListEmail(email);
    }
}
