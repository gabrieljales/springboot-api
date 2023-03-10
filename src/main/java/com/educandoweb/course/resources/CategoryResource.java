package com.educandoweb.course.resources;

import com.educandoweb.course.entities.Category;
import com.educandoweb.course.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    @Autowired // Injeção de dependência
    private CategoryService service;

    // ResponseEntity: Tipo expecífico do spring para retornar respostas web (perceba que é um genérico)
    @GetMapping // Requisição do tipo GET
    public ResponseEntity<List<Category>> findAll() {
        List<Category> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}") // Indica que a requisição aceitará um id dentro da URL
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        Category obj = service.findById(id);

        return ResponseEntity.ok().body(obj);
    }
}
