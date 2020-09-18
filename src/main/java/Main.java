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
//        deleteCustomer(3);

        addVehicle(1, "mazda","mx5",2007,"gp56 bmo");
        addVehicle(2, "mazda","rx5",2003,"abcd efg");

        addVehicleToCustomer(1,2);
        addVehicleToCustomer(2,2);
        addVehicleToCustomer(2,1);

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

    public static Customer getCustomer(int id) {
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
        return cust;
    }

    public static List<Customer> getCustomers() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        // the lowercase c refers to the object
        // :custID is a parameterized query thats value is set below
        String strQuery = "SELECT c FROM Customer c WHERE c.id IS NOT NULL";

        // Issue the query and get a matching Customer
        TypedQuery<Customer> tq = em.createQuery(strQuery, Customer.class);
        List<Customer> custs = null;
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
        return custs;
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
    public static void addVehicle(int id, String make, String model,int year,  String numberPlate) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();

            // Create and set values for new customer
            Vehicle veh = new Vehicle();
            veh.setId(id);
            veh.setMake(make);
            veh.setModel(model);
            veh.setModelYear(year);
            veh.setNumberPlate(numberPlate);

            // Save the customer object
            em.persist(veh);
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
    public static Vehicle getVehicle(int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        // the lowercase c refers to the object
        // :custID is a parameterized query thats value is set below
        String query = "SELECT v FROM Vehicle v WHERE v.id = :ID";

        // Issue the query and get a matching Customer
        TypedQuery<Vehicle> tq = em.createQuery(query, Vehicle.class);
        tq.setParameter("ID", id);

        Vehicle veh = null;
        try {
            // Get matching customer object and output
            veh = tq.getSingleResult();
            System.out.println("id " + veh.getId());
        }
        catch(NoResultException ex) {
            ex.printStackTrace();
        }
        finally {
            em.close();
        }
        return veh;
    }

    public static void addVehicleToCustomer (int vehicleId, int customerId) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();

            // Find customer and vehicle
            Customer cust = em.find(Customer.class, customerId);
            Vehicle veh = em.find(Vehicle.class, vehicleId);
            //update veh value
            cust.setVehicle(veh);

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
} // Right click and run this file as a Java Application

