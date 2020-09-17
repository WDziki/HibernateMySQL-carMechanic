import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.persistence.*;

public class MainTest {

    public static EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("MyHibernateProject");


    private static void addDefaultCustomer(Integer id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        Customer customer = new Customer();
        customer.setID(id);
        customer.setFName("John");
        customer.setLName("Doe");
        customer.setKsywa("johnny");
        em.persist(customer);
        et.commit();
    }

    private static void deleteExampleCustomer(Integer id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        Customer cust = em.find(Customer.class, id);
        em.remove(cust);
        et.commit();
    }
    @After
    public void setUp() {
        MainTest.deleteExampleCustomer(0);
    }

    @Test
    public void shouldAddCustomer() {
        //given
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        //when
        Main.addCustomer(0, "John", "Doe", "johnny");

        String query = "SELECT c FROM Customer c WHERE c.id = 0";
        TypedQuery<Customer> tq = em.createQuery(query, Customer.class);
        Customer cust = tq.getSingleResult();
        //then
        assertEquals(cust.getID(), 0);
        assertEquals(cust.getFName(), "John");
        assertEquals(cust.getLName(), "Doe");
        assertEquals(cust.getKsywa(), "johnny");
    }

    @Test
    public void shouldChangeFname() {
        //given
        MainTest.addDefaultCustomer(0);
        //when
        Main.changeFName(0, "John2");

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT c FROM Customer c WHERE c.id = 0";
        TypedQuery<Customer> tq = em.createQuery(query, Customer.class);
        Customer cust = tq.getSingleResult();
        em.close();
        //then
        assertEquals(0, cust.getID());
        assertEquals("John2", cust.getFName());
    }

    @Test(expected = NoResultException.class)
    public void shouldDeleteCustomer() {
        //given
        MainTest.addDefaultCustomer(0);
        //when
        Main.deleteCustomer(0);

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT c FROM Customer c WHERE c.id = 0";
        TypedQuery<Customer> tq = em.createQuery(query, Customer.class);
        //then
        tq.getSingleResult();
        em.close();
    }
}


