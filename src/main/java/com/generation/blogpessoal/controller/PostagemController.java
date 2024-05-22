package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

import jakarta.validation.Valid;

@RestController //anotação que diz para spring que essa é uma controladora de rotas e acesso aos métodos
@RequestMapping("/postagens") //rota para chegar nessa classe "insomnia"
@CrossOrigin(origins = "*", allowedHeaders = "*") //liberar o acesso a outras máquinas /allowedHeaders = liberar passagem de parâmetros no header
public class PostagemController {

	@Autowired //injeção de dependências  - instanciar a classe PostagemRepository
	private PostagemRepository postagemRepository;
	
	@GetMapping //define o verbo htp que atende esse metodo
	public ResponseEntity<List<Postagem>> getAll(){
		
		//ResponseEntity - Classe do Spring que trata a saida de dados para o formato HTTP
		//List garante que a gente consiga retornar uma lista com mais de uma postagem
		//Postagem para indicar que será o objeto Postagem que queremos retornar
		// getAll nome do nosso método
				
		//return esta trazendo o status de ok- 200 caso ele consiga fazer o método findAll da JpaRepository
		return ResponseEntity.ok(postagemRepository.findAll());
		//SELECT * FROM tb_postagens
		
		/*
		 * Processo até aqui
		 * criar o projeto
		 * criar conexão com o banco de dados - cria o database apenas
		 * criar a model para ter a estrutura do projeto e também estrutura da tabela tb_postagens (cria a tabela apenas)
		 * realizar o teste se o banco de dados esta sendo criado junto com a tabela
		 * criar a Repository para garantir a comunicação dos Querys no banco de dados (select, insert, update e delete para os dados da tabela)
		 * criar a controller com os métodos para recuperar os dados da tabela do banco de dados
		 */
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable Long id){
		return postagemRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@PostMapping
	public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(postagemRepository.save(postagem));
	}
	
	@PutMapping
	public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem){
		return postagemRepository.findById(postagem.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK)
				.body(postagemRepository.save(postagem)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Postagem> postagem = postagemRepository.findById(id);
		
		if(postagem.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		postagemRepository.deleteById(id);
	}
	
}
