package invex.employee.api.infrastructure.adapters.input;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import invex.employee.api.domain.model.EmployeeEntity;


@RestController
@RequestMapping( path = "/api/v1" )
public interface EmployeeController {
	
	
	@GetMapping( produces = "application/json", path = "/employees" )
	@ResponseBody ResponseEntity<?>		readAll();
	
	@GetMapping( produces = "application/json", path = "/employees/{id}" )
	@ResponseBody ResponseEntity<?>		readById( @PathVariable( name = "id", required = true ) Long id );
	
	@PostMapping( produces = "application/json", consumes = "application/json", path = "/employees" )
	@ResponseBody ResponseEntity<?>		create( @RequestBody( required = true ) List<EmployeeEntity> employees );
	
	@PutMapping( produces = "application/json", consumes = "application/json", path = "/employees/{id}" )
	@ResponseBody ResponseEntity<?>		updateById( @PathVariable( name = "id", required = true ) Long id, @RequestBody( required = true ) EmployeeEntity employee );
	
	@DeleteMapping( path = "/employees/{id}" )
	@ResponseBody ResponseEntity<?>		deleteById( @PathVariable( name = "id", required = true ) Long id );
	
	@GetMapping( produces = "application/json", path = "/employees/search" )
	@ResponseBody ResponseEntity<?>		readByName( @RequestParam( name = "name", required = true )	String name );
}
