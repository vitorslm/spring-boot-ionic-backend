package com.vitormukai.cursomc.resources;

import com.vitormukai.cursomc.dto.CategoriaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vitormukai.cursomc.domain.Categoria;
import com.vitormukai.cursomc.services.CategoriaService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sun.reflect.generics.tree.VoidDescriptor;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value ="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaVO objVo){
		Categoria obj = service.fromVO(objVo);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaVO objVo, @PathVariable Integer id) {
		Categoria obj = service.fromVO(objVo);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value ="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping( method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaVO>> findAll() {
		List<Categoria> list = service.findAll();
		List<CategoriaVO> listVo = list.stream().map(obj -> new CategoriaVO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listVo);
	}

	@RequestMapping(value = "/page", method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaVO>> findPage(
				@RequestParam(value = "page", defaultValue = "0") Integer page,
				@RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
				@RequestParam(value = "orderBy", defaultValue = "nome")String orderBy,
				@RequestParam(value = "direction", defaultValue = "ASC")String direction){
		Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaVO> listVo = list.map(obj -> new CategoriaVO(obj));
		return ResponseEntity.ok().body(listVo);
	}
}
