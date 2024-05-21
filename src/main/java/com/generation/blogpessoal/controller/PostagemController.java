package com.generation.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

@RestController //anotação que diz para spring que essa é uma controladora de rotas e acesso aos métodos
@RequestMapping("/postagens") //rota para chegar nessa classe "insomnia"
@CrossOrigin(origins = "*", allowedHeaders = "*") //liberar o acesso a outras máquinas /allowedHeaders = liberar passagem de parâmetros no header
public class PostagemController {

	@Autowired //injeção de dependências  - instanciar a classe Postagem Repository
	private PostagemRepository postagemRepository;
	
	@GetMapping //define o verbo htp que atende esse metodo
	public ResponseEntity<List<Postagem>> getAll(){
		//Response Entity - Classe 
		return ResponseEntity.ok(postagemRepository.findAll());
	}
	
}
