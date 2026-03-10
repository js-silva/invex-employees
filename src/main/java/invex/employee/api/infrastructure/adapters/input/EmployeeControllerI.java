package invex.employee.api.infrastructure.adapters.input;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import invex.employee.api.application.usecase.EmployeeService;
import invex.employee.api.domain.model.EmployeeEntity;
import lombok.NoArgsConstructor;


@Component
@NoArgsConstructor
public class EmployeeControllerI implements EmployeeController {
	
	
	@Autowired
	EmployeeService		service;
	
	
	@Override
	public ResponseEntity<?> readAll() {
		ResponseEntity<?> forReturn = null;
		try {
			List<EmployeeEntity> employees = service.readAll();
			forReturn = new ResponseEntity<List<EmployeeEntity>>( employees, HttpStatus.OK );
		} catch( Exception e ) {
			forReturn = new ResponseEntity<String>( e.getMessage(), HttpStatus.valueOf( Integer.parseInt( e.getMessage() ) ) );
		}
		return forReturn;
	}
	@Override
	public ResponseEntity<?> readById(Long id) {
		ResponseEntity<?> forReturn = null;
		if( id == null || id <= 0 ) {
			return forReturn = new ResponseEntity<String>( "ERROR", HttpStatus.BAD_REQUEST );
		}
		try {
			EmployeeEntity employee = service.readById( id );
			forReturn = new ResponseEntity< EmployeeEntity>( employee, HttpStatus.OK );
		} catch( Exception e ) {
			forReturn = new ResponseEntity<String>( "ERROR", HttpStatus.valueOf( e.getMessage() ) );
		}
		return forReturn;
	}
	@Override
	public ResponseEntity<?> create(List<EmployeeEntity> employees) {
		ResponseEntity<?> forReturn = null;
		if( employees == null || employees.isEmpty() ) {
			return forReturn = new ResponseEntity<String>( "ERROR", HttpStatus.BAD_REQUEST );
		}
		try {
			List<EmployeeEntity> employeesL = service.create(employees);
			forReturn = new ResponseEntity< List<EmployeeEntity>>( employeesL, HttpStatus.OK );
		} catch( Exception e ) {
			forReturn = new ResponseEntity<String>( "ERROR", HttpStatus.valueOf( e.getMessage() ) );
		}
		return forReturn;
	}
	@Override
	public ResponseEntity<?> updateById(Long id, EmployeeEntity employee) {
		ResponseEntity<?> forReturn = null;
		if( id == null || id <= 0 ) {
			return forReturn = new ResponseEntity<String>( "ERROR", HttpStatus.BAD_REQUEST );
		}
		try {
			employee.setId( id );
			EmployeeEntity employeeU = service.updateById(employee);
			forReturn = new ResponseEntity< EmployeeEntity>( employeeU, HttpStatus.OK );
		} catch( Exception e ) {
			forReturn = new ResponseEntity<String>( "ERROR", HttpStatus.valueOf( e.getMessage() ) );
		}
		return forReturn;
	}
	@Override
	public ResponseEntity<?> deleteById( Long id ) {
		ResponseEntity<?> forReturn = null;
		if( id == null || id <= 0 ) {
			return forReturn = new ResponseEntity<String>( "ERROR", HttpStatus.BAD_REQUEST );
		}
		try {
			service.deleteById( id );
			forReturn = new ResponseEntity<String>( "OK", HttpStatus.OK );
		} catch( Exception e ) {
			forReturn = new ResponseEntity<String>( "ERROR", HttpStatus.valueOf( e.getMessage() ) );
		}
		return forReturn;
	}
	@Override
	public ResponseEntity<?> readByName(String name) {
		ResponseEntity<?> forReturn = null;
		if( name == null || name.isBlank() || name.isEmpty() ) {
			return forReturn = new ResponseEntity<String>( "ERROR", HttpStatus.BAD_REQUEST );
		}
		try {
			List<EmployeeEntity> employeesL = service.readByName(name);
			forReturn = new ResponseEntity< List<EmployeeEntity>>( employeesL, HttpStatus.OK );
		} catch( Exception e ) {
			forReturn = new ResponseEntity<String>( "ERROR", HttpStatus.valueOf( e.getMessage() ) );
		}
		return forReturn;
	}
}
