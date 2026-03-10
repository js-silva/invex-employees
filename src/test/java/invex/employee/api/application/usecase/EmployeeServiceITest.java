package invex.employee.api.application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import invex.employee.api.domain.model.EmployeeEntity;
import invex.employee.api.domain.ports.output.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("EmployeeServiceI - Pruebas Unitarias")
class EmployeeServiceITest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeServiceI service;

    private EmployeeEntity employee1;
    private EmployeeEntity employee2;
    
    @BeforeEach
    void setUp() {
        employee1 = new EmployeeEntity (1L, "JOSÉ", null, "SÁNCHEZ", "SILVA", 52, "H", new Date(), "PROGRAMADOR SENIOR BACKEND JAVA", new Date(), true);
        employee2 = new EmployeeEntity (2L, "VALENTINA", null, "VARGAS", "RÍOS", 23, "M", new Date(), "ANALISTA DE NEGOCIOS", new Date(), true);
    }
    
    // ── readAll ──────────────────────────────────────────────

    @Test
    @DisplayName("readAll - Debe retornar lista de empleados")
    void readAll_shouldReturnEmployeeList() throws Exception {
        when(repository.findAll()).thenReturn(Arrays.asList(employee1, employee2));

        List<EmployeeEntity> result = service.readAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("readAll - Debe lanzar excepcion cuando el repositorio falla")
    void readAll_shouldThrowExceptionWhenRepositoryFails() {
        when(repository.findAll()).thenThrow(new RuntimeException("DB error"));

        Exception ex = assertThrows(Exception.class, () -> service.readAll());
        assertEquals("500", ex.getMessage());
    }

    // ── readById ─────────────────────────────────────────────

    @Test
    @DisplayName("readById - Debe retornar empleado cuando existe el ID")
    void readById_shouldReturnEmployeeWhenExists() throws Exception {
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.findById(1L)).thenReturn(Optional.of(employee1));

        EmployeeEntity result = service.readById(1L);

        assertNotNull(result);
        assertEquals("JOSÉ", result.getFirstName());
        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("readById - Debe lanzar excepcion 500 cuando el ID no existe")
    void readById_shouldThrowExceptionWhenNotFound() {
        when(repository.existsById(99L)).thenReturn(false);

        Exception ex = assertThrows(Exception.class, () -> service.readById(99L));
        assertEquals("500", ex.getMessage());
    }

    // ── create ───────────────────────────────────────────────

    @Test
    @DisplayName("create - Debe guardar y retornar lista de empleados")
    void create_shouldSaveAndReturnEmployees() throws Exception {
        when(repository.saveAndFlush(any(EmployeeEntity.class))).thenReturn(employee1);

        List<EmployeeEntity> result = service.create(Arrays.asList(employee1));

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(repository, atLeastOnce()).saveAndFlush(any(EmployeeEntity.class));
    }

    // ── updateById ───────────────────────────────────────────

    @Test
    @DisplayName("updateById - Debe actualizar y retornar empleado cuando existe")
    void updateById_shouldUpdateWhenExists() throws Exception {
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.saveAndFlush(employee1)).thenReturn(employee1);

        EmployeeEntity result = service.updateById(employee1);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).saveAndFlush(employee1);
    }

    @Test
    @DisplayName("updateById - Debe lanzar excepcion 500 cuando el ID no existe")
    void updateById_shouldThrowExceptionWhenNotFound() {
        when(repository.existsById(99L)).thenReturn(false);
        employee1.setId(99L);

        Exception ex = assertThrows(Exception.class, () -> service.updateById(employee1));
        assertEquals("500", ex.getMessage());
    }

    // ── deleteById ───────────────────────────────────────────

    @Test
    @DisplayName("deleteById - Debe eliminar empleado cuando existe el ID")
    void deleteById_shouldDeleteWhenExists() throws Exception {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        assertDoesNotThrow(() -> service.deleteById(1L));
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteById - Debe lanzar excepcion 500 cuando el ID no existe")
    void deleteById_shouldThrowExceptionWhenNotFound() {
        when(repository.existsById(99L)).thenReturn(false);

        Exception ex = assertThrows(Exception.class, () -> service.deleteById(99L));
        assertEquals("500", ex.getMessage());
    }

    // ── readByName ───────────────────────────────────────────

    @Test
    @DisplayName("readByName - Debe retornar empleados que coincidan con el nombre")
    void readByName_shouldReturnMatchingEmployees() throws Exception {
        when(repository.findByFirstName("JOSÉ")).thenReturn(Arrays.asList(employee1));

        List<EmployeeEntity> result = service.readByName("JOSÉ");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("JOSÉ", result.get(0).getFirstName());
    }

    @Test
    @DisplayName("readByName - Debe lanzar excepcion cuando el repositorio falla")
    void readByName_shouldThrowExceptionWhenRepositoryFails() {
        when(repository.findByFirstName(any())).thenThrow(new RuntimeException("DB error"));

        Exception ex = assertThrows(Exception.class, () -> service.readByName("JOSÉ"));
        assertEquals("500", ex.getMessage());
    }
}
