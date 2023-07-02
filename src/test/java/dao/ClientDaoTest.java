package dao;

import model.Client;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientDaoTest {
    static final Logger logger = LogManager.getLogger();
    private static final DataSource dataSource = TestDatabaseUtil.getDataSource();
    private static final ClientDao clientDao = new ClientDao(dataSource);
    private static Client client1;

    @BeforeAll
    static void init() {
        client1 = new Client();
        client1.setEmail("testEmail@gmail.com");
        client1.setPassword("Password123");
        client1.setName("Jane");
        client1.setSurname("Duke");
        client1.setPhoneNumber("+380999999999");
        client1.setBalance(0);
        client1.setActive(true);
    }

    @Test
    @Order(1)
    void testAddClient() {
        clientDao.create(client1);
        Client client2 = clientDao.get(client1.getEmail());
        client1.setId(client2.getId());
        assertEquals(client1, client2);
    }

    @Test
    @Order(2)
    void testUpdateClient() {
        client1.setBalance(2000);
        client1.setPhoneNumber("+380666666666");
        client1.setSurname("Kelley");
        clientDao.update(client1);
        Client client2 = clientDao.get(client1.getId());
        assertEquals(client1, client2);
    }

    @Test
    @Order(3)
    void testDeleteClient() {
        clientDao.delete(client1.getId());
        Client client2 = clientDao.get(client1.getId());
        assertNull(client2);
    }

    @Test
    @Order(4)
    void testListAllClients() {
        Client client2 = new Client();
        client2.setEmail("testEmail2@gmail.com");
        client2.setPassword("5tgb^YHN");
        client2.setName("Bella");
        client2.setSurname("Jones");
        client2.setPhoneNumber("+380121231212");
        client2.setBalance(0);
        client2.setActive(true);
        Client client3 = new Client();
        client3.setEmail("testEmail3@gmail.com");
        client3.setPassword("3edc$RFV");
        client3.setName("Helen");
        client3.setSurname("Wilson");
        client3.setPhoneNumber("+450987654321");
        client3.setBalance(0);
        client3.setActive(true);
        Client client4 = new Client();
        client4.setEmail("testEmail4@gmail.com");
        client4.setPassword("6yhn&UJM");
        client4.setName("Lily");
        client4.setSurname("Smith");
        client4.setPhoneNumber("0951234567");
        client4.setBalance(0);
        client4.setActive(true);
        List<Client> clientList1 = new ArrayList<>();
        clientList1.add(client2);
        clientList1.add(client3);
        clientList1.add(client4);
        for (Client client: clientList1) {
            clientDao.create(client);
        }
        List<Client> clientList2 = clientDao.listAll();
        assertEquals(clientList1, clientList2);
    }

    @AfterAll
    static void cleanUp() {
        String clearTableQuery = "DELETE FROM clients";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(clearTableQuery)){
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
    }

}