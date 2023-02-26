package com.educandoweb.course.services;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired // Injeção de dependência
    private UserRepository repository;

    public List<User> findAll() {
        return  repository.findAll();
    }

    public User findById(Long id) {
        Optional<User> obj = repository.findById(id);

        return obj.get(); // Retorna o objeto do tipo User que estiver dentro do Optional
    }

    public User insert(User obj) {
        return repository.save(obj);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public User upddate(Long id, User obj) {
        // getReferenceById: Instancia um usuário sem ir diretamente no banco de dados, ele deixa disponível um objeto monitorado pelo JPA para trabalharmos com ele e em seguida, realizar alguma operação com banco de dados
        // Isso é melhor que usar o findById, já que ele necessariamente vai no BD e trás o objeto
        User entity = repository.getReferenceById(id);
        updateData(entity, obj);
        
        return  repository.save(entity);
    }

    private void updateData(User entity, User obj) {
        // Atualiza os dados permitidos de entity com base no que chega em obj (exceções: Id e senha)
        entity.setName(obj.getName());
        entity.setEmail(obj.getEmail());
        entity.setPhone(obj.getPhone());
    }
}
