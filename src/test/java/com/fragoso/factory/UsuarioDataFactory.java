package com.fragoso.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fragoso.pojo.Usuario;

import java.io.FileInputStream;
import java.io.IOException;

public class UsuarioDataFactory {
    public static Usuario criarUsuarioAdm(String nome, String email) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Usuario usuarioAdm = objectMapper.readValue(new FileInputStream("src/test/resources/requestBody/postUsuario.json"), Usuario.class);
        usuarioAdm.setNome(nome);
        usuarioAdm.setEmail(email);
        usuarioAdm.setAdministrador("true");
        return usuarioAdm;
    }

    public static Usuario criarUsuario(String nome, String email) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Usuario usuario = objectMapper.readValue(new FileInputStream("src/test/resources/requestBody/postUsuario.json"), Usuario.class);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setAdministrador("false");
        return usuario;
    }
}
