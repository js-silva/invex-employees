package invex.employee.api.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import invex.employee.api.domain.model.EmployeeEntity;

@Service
public interface EmployeeService {
	
	
	List<EmployeeEntity>	readAll() 									throws Exception;
	EmployeeEntity			readById( Long id ) 						throws Exception;
	List<EmployeeEntity>	create( List<EmployeeEntity> employees )	throws Exception;
	EmployeeEntity			updateById( EmployeeEntity employee )		throws Exception;
	void 					deleteById( Long id )						throws Exception;
	List<EmployeeEntity>	readByName( String firstName )				throws Exception;
}
