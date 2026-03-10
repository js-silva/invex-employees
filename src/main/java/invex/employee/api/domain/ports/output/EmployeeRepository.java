package invex.employee.api.domain.ports.output;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import invex.employee.api.domain.model.EmployeeEntity;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
	
	
	List<EmployeeEntity>	findByFirstName( String firstName );
}
