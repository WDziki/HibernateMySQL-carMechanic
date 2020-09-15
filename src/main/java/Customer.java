import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/*
 * 2. The Java Persistance API (JPA) makes it easy to use object data with
 * a database. With it you can Persist object data in a database.
 *
 * Hibernate is a Object Relational Mapping system that implements JPA.
 */

// Entity defines which objects should be persisted in the database
@Entity
// Defines the name of the table created for the entity
@Table(name = "customer")
public class Customer implements Serializable {
    //add default serialVersionID
    private static final long serialVersionID = 1l;

    // All entities must define a primary key which you define with
    // the @Id annotation
    @Id
    // You can override the default column name
    // Map id to the CustID field in the DB
    // You can have it auto generate
    // @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "custID", unique = true) private int id;
    @Column(name = "firstName", nullable = false) private String fName;
    @Column(name = "lastName", nullable = false) private String lName;

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
