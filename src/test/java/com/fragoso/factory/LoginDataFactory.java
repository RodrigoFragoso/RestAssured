package com.fragoso.factory;

import com.fragoso.pojo.Login;

public class LoginDataFactory {
    public static Login identity(){
        Login access = new Login();
        access.setEmail("rodrigo.qa@qa.com.br");
        access.setPassword("teste@123");
        return access;
    }
}
