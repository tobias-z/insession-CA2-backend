package entities;

import dtos.PhoneDTO;
import entities.person.Person;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@NamedQueries({
    @NamedQuery(name = "Phone.deleteAllRows", query = "DELETE from Phone")
})
public class Phone implements Serializable {

    private static final long serialVersionUID = 3243058560663949893L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer phoneId;

    @Column(length = 30, nullable = false, unique = true)
    private String number;

    private String description;

    @ManyToOne
    private Person person;

    public Phone() {
    }

    public Phone(String number, String description) {
        this.number = number;
        this.description = description;
    }

    public Phone(PhoneDTO phoneDTO) {
        this.number = phoneDTO.getNumber();
        this.description = phoneDTO.getDescription();
    }

    public Integer getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Integer phoneId) {
        this.phoneId = phoneId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() { return "Phone{" +
            "number='" + number + '\'' +
            ", description='" + description + '\'' +
            ", person=" + person +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Phone)) {
            return false;
        }
        Phone phone = (Phone) o;
        return Objects.equals(getNumber(), phone.getNumber()) && Objects
            .equals(getDescription(), phone.getDescription()) && Objects
            .equals(getPerson(), phone.getPerson());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getDescription(), getPerson());
    }
}
