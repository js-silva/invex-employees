package invex.employee.api.domain.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity( name = "invex" )
@Table( name = "employee" )
public class EmployeeEntity {
	
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private		Long	id;
	
	
	@Column( name = "firstn" )
	private		String	firstName;
	
	@Column( name = "secondn" )
	private		String	secondName;
	
	@Column( name = "lastnm" )
	private		String	lastNameMatern;
	
	@Column( name = "lastnp" )
	private		String	lastNamePatern;
	
	@Column( name = "age" )
	private		Integer	age;
	
	@Column( name = "gender" )
	private		String	gender;
	
	@JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
	@Column( name = "birthdate" )
	private		Date	birthdate;
	
	@Column( name = "position" )
	private		String	position;
	
	@JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
	@Column( name = "registration" )
	private		Date	registration;
	
	@Column( name = "status" )
	private		Boolean	status;
}
