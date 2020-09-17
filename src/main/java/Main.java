import javax.persistence.*;
import java.util.List;

public class Main {
    // Create an EntityManagerFactory when you start the application
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("MyHibernateProject");

    public static void main(String[] args) {
        addCustomer(1, "Sue", "Smith", "maly");
        addCustomer(2, "Sam", "Smith","duzy");
        addCustomer(3, "Sid", "Smith", "bobi");
        addCustomer(4, "Sally", "Smith", "warchus");
        getCustomer(1);
        getCustomers();
        changeFName(4, "Mark");
        deleteCustomer(3);
        ENTITY_MANAGER_FACTORY.close();
    }

    public static void addCustomer(int id, String fname, String lname, String ksywa) {
        // The EntityManager class allows operations such as create, read, update, delete
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        // Used to issue transactions on the EntityManager
        EntityTransaction et = null;

        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();

            // Create and set values for new customer
            Customer cust = new Customer();
            cust.setID(id);
            cust.setFName(fname);
            cust.setLName(lname);
            cust.setKsywa(ksywa);

            // Save the customer object
            em.persist(cust);
            et.commit();
        } catch (Exception ex) {
            // If there is an exception rollback changes
            if (et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        } finally {
            // Close EntityManager
            em.close();
        }
    }

    public static void getCustomer(int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        // the lowercase c refers to the object
        // :custID is a parameterized query thats value is set below
        String query = "SELECT c FROM Customer c WHERE c.id = :custID";

        // Issue the query and get a matching Customer
        TypedQuery<Customer> tq = em.createQuery(query, Customer.class);
        tq.setParameter("custID", id);

        Customer cust = null;
        try {
            // Get matching customer object and output
            cust = tq.getSingleResult();
            System.out.println("id " + cust.getID() +" "+ cust.getFName() + " " + cust.getLName());
        }
        catch(NoResultException ex) {
            ex.printStackTrace();
        }
        finally {
            em.close();
        }
    }

    public static void getCustomers() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        // the lowercase c refers to the object
        // :custID is a parameterized query thats value is set below
        String strQuery = "SELECT c FROM Customer c WHERE c.id IS NOT NULL";

        // Issue the query and get a matching Customer
        TypedQuery<Customer> tq = em.createQuery(strQuery, Customer.class);
        List<Customer> custs;
        try {
            // Get matching customer object and output
            custs = tq.getResultList();
            custs.forEach(cust->System.out.println("id " + cust.getID() +" "+ cust.getFName() + " " + cust.getLName()));
        }
        catch(NoResultException ex) {
            ex.printStackTrace();
        }
        finally {
            em.close();
        }
    }

    public static void changeFName(int id, String fname) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Customer cust = null;

        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();

            // Find customer and make changes
            cust = em.find(Customer.class, id);
            cust.setFName(fname);

            // Save the customer object
            em.persist(cust);
            et.commit();
        } catch (Exception ex) {
            // If there is an exception rollback changes
            if (et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        } finally {
            // Close EntityManager
            em.close();
        }
    }

    public static void deleteCustomer(int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Customer cust = null;

        try {
            et = em.getTransaction();
            et.begin();
            cust = em.find(Customer.class, id);
            em.remove(cust);
            et.commit();
        } catch (Exception ex) {
            // If there is an exception rollback changes
            if (et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        } finally {
            // Close EntityManager
            em.close();
        }
    }
} // Right click and run this file as a Java Application

