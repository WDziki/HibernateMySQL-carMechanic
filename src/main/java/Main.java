import javax.persistence.*;
import java.util.List;

public class Main {
    // Create an EntityManagerFactory when you start the application
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory
            ("MyHibernateProject");

    public static void main(String[] args) {
        addCustomer( "Sue", "Smith", 123456789);
        addCustomer( "Sam", "Smith",789456123);
        addCustomer( "Sid", "Smith", 123789456);
        addCustomer( "Sally", "Smith", 987654321);
        getCustomer(1);
        getCustomers();
        changeFName(4, "Mark");
//        deleteCustomer(3);

        addVehicle( "mazda","mx5",2007,"gp56 bmo");
        addVehicle( "mazda","rx5",2003,"abcd efg");
        addVehicle( "mercedes","w203",2002,"rjs 86m8");
        getVehicle(1);

        addVehicleToCustomer(1,1);
        addVehicleToCustomer(2,1);
        addVehicleToCustomer(3,1);
        //addVehicleToCustomer(2,2);

        showCustomerVehicles(1);

        ENTITY_MANAGER_FACTORY.close();
    }

    public static void addCustomer(String fname, String lname, Integer phone) {
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
            //cust.setID(id);
            cust.setFName(fname);
            cust.setLName(lname);
            cust.setKsywa(phone);

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

        String strQuery = "SELECT c FROM Customer c WHERE c.id IS NOT NULL";
        TypedQuery<Customer> tq = em.createQuery(strQuery, Customer.class);
        List<Customer> custs = null;
        try {
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
    public static void addVehicle( String make, String model,int year,  String numberPlate) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();

            Vehicle veh = new Vehicle();
            //veh.setId(id);
            veh.setMake(make);
            veh.setModel(model);
            veh.setModelYear(year);
            veh.setNumberPlate(numberPlate);

            em.persist(veh);
            et.commit();
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }
    public static Vehicle getVehicle(int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        String query = "SELECT v FROM Vehicle v WHERE v.id = :vehId";
        TypedQuery<Vehicle> tq = em.createQuery(query, Vehicle.class);
        tq.setParameter("vehId", id);

        Vehicle veh = null;
        try {
            veh = tq.getSingleResult();
            System.out.println("id " + veh.getId() +" "+ veh.getMake() +" "+ veh.getModel());
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
        Customer cust = null;
        Vehicle veh = null;
        List<Vehicle> vehs;

        try {
            et = em.getTransaction();
            et.begin();

            cust = em.find(Customer.class, customerId);
            veh = em.find(Vehicle.class, vehicleId);
            vehs = cust.getVehicles();
            vehs.add(veh);
            cust.setVehicles(vehs);
            veh.setCustomer(cust);

            em.persist(cust);
            et.commit();
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static List<Vehicle> showCustomerVehicles(int customerId) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        String strQuery = "SELECT v FROM Vehicle v WHERE customerId = :custID";
        TypedQuery<Vehicle> tq = em.createQuery(strQuery, Vehicle.class);
        tq.setParameter("custID", customerId);

        List<Vehicle> vehs = null;
        try {
            vehs = tq.getResultList();
            vehs.forEach(veh -> System.out.println("customer " + customerId + " has " + veh.getId() + " " + veh.getMake() + " " + veh.getModel()));
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return vehs;
    }

} // Right click and run this file as a Java Application

