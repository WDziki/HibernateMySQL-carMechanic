import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * 2. The Java Persistance API (JPA) makes it easy to use object data with
 * a database. With it you can Persist object data in a database.
 *
 * Hibernate is a Object Relational Mapping system that implements JPA.
 */

// Entity defines which objects should be persisted in the database
@Entity
// Defines the name of the table created for the entity
@Table(name = "Customers")
public class Customer implements Serializable {
    //add default serialVersionID
    private static final long serialVersionID = 1l;

    // All entities must define a primary key which you define with
    // the @Id annotation
    @Id
    // You can override the default column name
    // Map id to the CustID field in the DB
    // You can have it auto generate
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false) private int id;
    @Column(name = "first_Name", nullable = false) private String fName;
    @Column(name = "last_Name", nullable = false) private String lName;
    @Column(name = "ksywa", nullable = false) private String ksywa;
    @Transient private String ignoredField;
    @OneToMany(mappedBy = "customer")
    //@JoinColumn(name = "vehicleId") //MappedBy działa na poziomie encji, JoinColumn na poziomie bazy danych.
    // Dlatego przy użyciu mappedBy otrzymujemy relacje one-to-one bidirectional w aplikacji, lecz w bazie danych jest to one-to-one undirectional.
    //The mappedBy property is, what we use to tell Hibernate which variable we are using to represent the parent class in our child class.
    private List<Vehicle> vehicles;

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
    public String getKsywa() {
        return ksywa;
    }
    public void setKsywa(String ksywa) {
        this.ksywa = ksywa;
    }
    public int getID() {
        return id;
    }
    public void setID(int id) {
        this.id = id;
    }
    public String getFName() {
        return fName;
    }
    public void setFName(String fName) {
        this.fName = fName;
    }
    public String getLName() {
        return lName;
    }
    public void setLName(String lName) {
        this.lName = lName;
    }

// 4. Right Click Project -> New -> Source Folder -> name it resources
// Create folder in it named META-INF
// Create persistence.xml file in META-INF
}
