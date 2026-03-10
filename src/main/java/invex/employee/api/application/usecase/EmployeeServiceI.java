package invex.employee.api.application.usecase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import invex.employee.api.domain.model.EmployeeEntity;
import invex.employee.api.domain.ports.output.EmployeeRepository;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class EmployeeServiceI implements EmployeeService {
	
	
	@Autowired
	EmployeeRepository	repository;
	
	
	@Override
	public List<EmployeeEntity> readAll( ) throws Exception {
		List<EmployeeEntity>	forReturn	= null;
		try {
			forReturn = repository.findAll();
		} catch( Exception e ) {
			throw new Exception( "500" );
		}
		return forReturn;
	}
	@Override
	public EmployeeEntity readById( Long id ) throws Exception {
		EmployeeEntity	forReturn = null;
		try {
			if( repository.existsById(id) ) {
				forReturn = repository.findById(id).get();
			} else {
				throw new Exception( "404" );
			}
		} catch( Exception e ) {
			throw new Exception( "500" );
		}
		return forReturn;
	}
	@Override
	public List<EmployeeEntity> create( List<EmployeeEntity> employees ) throws Exception {
		List<EmployeeEntity> forReturn = new ArrayList<EmployeeEntity>();
		employees.parallelStream().forEach( employee -> 
			{
				EmployeeEntity savedEmployee = repository.saveAndFlush( employee );
				forReturn.add( savedEmployee );
			}
		);
		return forReturn;
	}
	@Override
	public EmployeeEntity updateById( EmployeeEntity employee ) throws Exception {
		EmployeeEntity forReturn = null;
		try {
			if( repository.existsById( employee.getId() ) ) {
				forReturn = repository.saveAndFlush( employee );
			} else {
				throw new Exception( "404" );
			}
		} catch( Exception e ) {
			throw new Exception( "500" );
		}
		return forReturn;
	}
	@Override
	public void deleteById( Long id ) throws Exception {
		try {
			if( repository.existsById( id ) ) {
				repository.deleteById( id );
			} else {
				throw new Exception( "404" );
			}
		} catch( Exception e ) {
			throw new Exception( "500" );
		}
	}
	@Override
	public List<EmployeeEntity> readByName( String firstName ) throws Exception {
		List<EmployeeEntity>	employees = null;
		try {
			employees = repository.findByFirstName( firstName );
		} catch( Exception e ) {
			throw new Exception( "500" );
		}
		return employees;
	}
}
