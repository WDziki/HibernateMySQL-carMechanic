import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "Vehicle")
public class Vehicle implements Serializable {
    @Id
    @Column (name = "ID", unique = true) private int id;
    @Column (name = "numerRejestracyjny", nullable = false)private String numberPlate;
    @Column (name = "marka", nullable = false)private String make;
    @Column (name = "model", nullable = false)private String model;
    @Column (name = "rokProdukcji", nullable = false)private int modelYear;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getModelYear() {
        return modelYear;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }
}
