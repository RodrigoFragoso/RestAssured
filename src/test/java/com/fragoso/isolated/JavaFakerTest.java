package com.fragoso.isolated;
import com.fragoso.pojo.Pessoa;
import org.junit.Before;
import org.junit.Test;
import com.github.javafaker.Faker;
import java.util.Locale;

public class JavaFakerTest {
    Pessoa pessoa = new Pessoa();
    Faker faker = new Faker(new Locale("pt-BR"));
    @Before
    public void setup() {
        pessoa.setNomeCompleto(faker.name().fullName());
        pessoa.setCidade(faker.address().cityName());
        pessoa.setEndereco(faker.address().fullAddress());
        pessoa.setCartaoDeCredito(faker.finance().creditCard());
    }

    @Test
    public void testDadosPessoais(){
        pessoa.getNomeCompleto();
        pessoa.getCidade();
        pessoa.getEndereco();
        pessoa.getCartaoDeCredito();
    }
}
