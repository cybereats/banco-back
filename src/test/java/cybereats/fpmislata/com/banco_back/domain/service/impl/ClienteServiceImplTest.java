package cybereats.fpmislata.com.banco_back.domain.service.impl;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.domain.model.Page;
import cybereats.fpmislata.com.banco_back.domain.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private ClienteDto clienteDto;

    @BeforeEach
    void setUp() {
        clienteDto = new ClienteDto(1L, "jdoe", "password", "John", "Doe", "Smith", "12345678A", "token123");
    }

    @Nested
    @DisplayName("Tests para el método findAll")
    class FindAllTests {
        @Test
        @DisplayName("Debería devolver todos los clientes")
        void shouldReturnAllClientes() {
            Page<ClienteDto> page = new Page<>(List.of(clienteDto), 1, 1, 1L);
            when(clienteRepository.findAll()).thenReturn(page);

            Page<ClienteDto> result = clienteService.findAll();

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(1, result.data().size()));
            verify(clienteRepository).findAll();
        }
    }

    @Nested
    @DisplayName("Tests para el método findById")
    class FindByIdTests {
        @Test
        @DisplayName("Debería devolver el cliente cuando existe")
        void shouldReturnClienteWhenExists() {
            when(clienteRepository.findById(1L)).thenReturn(clienteDto);

            ClienteDto result = clienteService.findById(1L);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(clienteDto.id(), result.id()));
            verify(clienteRepository).findById(1L);
        }
    }

    @Nested
    @DisplayName("Tests para el método create")
    class CreateTests {
        @Test
        @DisplayName("Debería crear un cliente")
        void shouldCreateCliente() {
            when(clienteRepository.save(clienteDto)).thenReturn(clienteDto);

            ClienteDto result = clienteService.create(clienteDto);

            assertNotNull(result);
            assertEquals(clienteDto.login(), result.login());
            verify(clienteRepository).save(clienteDto);
        }
    }

    @Nested
    @DisplayName("Tests para el método update")
    class UpdateTests {
        @Test
        @DisplayName("Debería actualizar un cliente")
        void shouldUpdateCliente() {
            when(clienteRepository.save(clienteDto)).thenReturn(clienteDto);

            ClienteDto result = clienteService.update(clienteDto);

            assertNotNull(result);
            assertEquals(clienteDto.id(), result.id());
            verify(clienteRepository).save(clienteDto);
        }
    }

    @Nested
    @DisplayName("Tests para el método delete")
    class DeleteTests {
        @Test
        @DisplayName("Debería eliminar un cliente")
        void shouldDeleteCliente() {
            doNothing().when(clienteRepository).delete(1L);

            clienteService.delete(1L);

            verify(clienteRepository).delete(1L);
        }
    }
}
