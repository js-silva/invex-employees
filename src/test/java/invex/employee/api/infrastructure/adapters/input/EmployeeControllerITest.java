package invex.employee.api.infrastructure.adapters.input;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import invex.employee.api.application.usecase.EmployeeService;
import invex.employee.api.domain.model.EmployeeEntity;

@ExtendWith(MockitoExtension.class)
@DisplayName("EmployeeControllerI - Pruebas Unitarias")
class EmployeeControllerITest {

    @Mock
    private EmployeeService service;

    @InjectMocks
    private EmployeeControllerI controller;

    private EmployeeEntity employee1;

    @BeforeEach
    void setUp() {
        employee1 = new EmployeeEntity(1L, "JOSÉ", null, "SÁNCHEZ", "SILVA", 52, "H", new Date(), "PROGRAMADOR SENIOR BACKEND JAVA", new Date(), true);
    }
    
    // ── readAll ──────────────────────────────────────────────

    @Test
    @DisplayName("readAll - Debe retornar HTTP 200 con lista de empleados")
    void readAll_shouldReturn200WithList() throws Exception {
        when(service.readAll()).thenReturn(Arrays.asList(employee1));

        ResponseEntity<?> response = controller.readAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("readAll - Debe retornar HTTP 500 cuando el servicio falla")
    void readAll_shouldReturn500WhenServiceFails() throws Exception {
        when(service.readAll()).thenThrow(new Exception("500"));

        ResponseEntity<?> response = controller.readAll();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // ── readById ─────────────────────────────────────────────

    @Test
    @DisplayName("readById - Debe retornar HTTP 200 con el empleado")
    void readById_shouldReturn200WithEmployee() throws Exception {
        when(service.readById(1L)).thenReturn(employee1);

        ResponseEntity<?> response = controller.readById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee1, response.getBody());
    }

    @Test
    @DisplayName("readById - Debe retornar HTTP 400 cuando el ID es nulo")
    void readById_shouldReturn400WhenIdIsNull() {
        ResponseEntity<?> response = controller.readById(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("readById - Debe retornar HTTP 400 cuando el ID es menor o igual a 0")
    void readById_shouldReturn400WhenIdIsZeroOrNegative() {
        ResponseEntity<?> response = controller.readById(0L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // ── create ───────────────────────────────────────────────

    @Test
    @DisplayName("create - Debe retornar HTTP 200 con empleados creados")
    void create_shouldReturn200WithCreatedEmployees() throws Exception {
        List<EmployeeEntity> input = Arrays.asList(employee1);
        when(service.create(input)).thenReturn(input);

        ResponseEntity<?> response = controller.create(input);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("create - Debe retornar HTTP 400 cuando la lista es nula")
    void create_shouldReturn400WhenListIsNull() {
        ResponseEntity<?> response = controller.create(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("create - Debe retornar HTTP 400 cuando la lista esta vacia")
    void create_shouldReturn400WhenListIsEmpty() {
        ResponseEntity<?> response = controller.create(Collections.emptyList());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // ── updateById ───────────────────────────────────────────

    @Test
    @DisplayName("updateById - Debe retornar HTTP 200 con empleado actualizado")
    void updateById_shouldReturn200WithUpdatedEmployee() throws Exception {
        when(service.updateById(any(EmployeeEntity.class))).thenReturn(employee1);

        ResponseEntity<?> response = controller.updateById(1L, employee1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee1, response.getBody());
    }

    @Test
    @DisplayName("updateById - Debe retornar HTTP 400 cuando el ID es nulo")
    void updateById_shouldReturn400WhenIdIsNull() {
        ResponseEntity<?> response = controller.updateById(null, employee1);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // ── deleteById ───────────────────────────────────────────

    @Test
    @DisplayName("deleteById - Debe retornar HTTP 200 cuando se elimina correctamente")
    void deleteById_shouldReturn200WhenDeleted() throws Exception {
        doNothing().when(service).deleteById(1L);

        ResponseEntity<?> response = controller.deleteById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("OK", response.getBody());
    }

    @Test
    @DisplayName("deleteById - Debe retornar HTTP 400 cuando el ID es nulo")
    void deleteById_shouldReturn400WhenIdIsNull() {
        ResponseEntity<?> response = controller.deleteById(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // ── readByName ───────────────────────────────────────────

    @Test
    @DisplayName("readByName - Debe retornar HTTP 200 con empleados encontrados")
    void readByName_shouldReturn200WithEmployees() throws Exception {
        when(service.readByName("JOSÉ")).thenReturn(Arrays.asList(employee1));

        ResponseEntity<?> response = controller.readByName("JOSÉ");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("readByName - Debe retornar HTTP 400 cuando el nombre es nulo")
    void readByName_shouldReturn400WhenNameIsNull() {
        ResponseEntity<?> response = controller.readByName(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("readByName - Debe retornar HTTP 400 cuando el nombre esta vacio")
    void readByName_shouldReturn400WhenNameIsBlank() {
        ResponseEntity<?> response = controller.readByName("   ");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
