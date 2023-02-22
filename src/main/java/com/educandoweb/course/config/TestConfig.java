package com.educandoweb.course.config;

import com.educandoweb.course.entities.Order;
import com.educandoweb.course.entities.User;
import com.educandoweb.course.entities.enums.OrderStatus;
import com.educandoweb.course.repositories.OrderRepository;
import com.educandoweb.course.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.Arrays;

@Configuration // Instruir ao Spring que é uma class de configuração
@Profile("test") // Especificar para qual perfil será utilizado essa configuração
public class TestConfig implements CommandLineRunner {

    @Autowired // Injeção de dependência: Spring resolve a dependência abaixo e associa uma instância do repository ao TestConfig
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    // Implementando a interface CommandLineRunner para executar o método run quando a aplicação for iniciada
    @Override
    public void run(String... args) throws Exception {
        // Instanciando objetos para salvar no bd (id null porque o banco gera)
        User u1 = new User(null, "Maria Brown", "maria@gmail.com", "988888888", "123456");
        User u2 = new User(null, "Alex Green", "alex@gmail.com", "977777777", "123456");

        Order o1 = new Order(null, Instant.parse("2019-06-20T19:53:07Z"), OrderStatus.PAID, u1);
        Order o2 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"), OrderStatus.WAITING_PAYMENT, u2);
        Order o3 = new Order(null, Instant.parse("2019-07-22T15:21:22Z"), OrderStatus.WAITING_PAYMENT, u1);

        userRepository.saveAll(Arrays.asList(u1, u2)); // Salva uma lista de objetos no banco
        orderRepository.saveAll(Arrays.asList(o1, o2, o3));
    }
}
