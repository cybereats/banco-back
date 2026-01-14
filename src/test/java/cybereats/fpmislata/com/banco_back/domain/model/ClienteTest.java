package cybereats.fpmislata.com.banco_back.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para Cliente")
class ClienteTest {

    @Test
    @DisplayName("Debería crear un cliente con el constructor completo")
    void shouldCreateWithFullConstructor() {
        Cliente cliente = new Cliente(1L, "login", "password", "Nombre", "Apellido1", "Apellido2", "12345678A",
                "token");

        assertEquals(1L, cliente.getId());
        assertEquals("login", cliente.getLogin());
        assertEquals("password", cliente.getPassword());
        assertEquals("Nombre", cliente.getNombre());
        assertEquals("Apellido1", cliente.getApellido1());
        assertEquals("Apellido2", cliente.getApellido2());
        assertEquals("12345678A", cliente.getDni());
        assertEquals("token", cliente.getApiToken());
    }

    @Test
    @DisplayName("Debería funcionar con getters y setters")
    void shouldWorkWithGettersAndSetters() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setLogin("login");
        cliente.setPassword("pass");
        cliente.setNombre("Name");
        cliente.setApellido1("Last1");
        cliente.setApellido2("Last2");
        cliente.setDni("DNI");
        cliente.setApiToken("Token");

        assertEquals(1L, cliente.getId());
        assertEquals("login", cliente.getLogin());
        assertEquals("pass", cliente.getPassword());
        assertEquals("Name", cliente.getNombre());
        assertEquals("Last1", cliente.getApellido1());
        assertEquals("Last2", cliente.getApellido2());
        assertEquals("DNI", cliente.getDni());
        assertEquals("Token", cliente.getApiToken());
    }
}
