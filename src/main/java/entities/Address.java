package entities;

import entities.cityinfo.CityInfo;
import entities.person.Person;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import org.apache.ibatis.annotations.One;

@Entity
@NamedQueries({
    @NamedQuery(name = "Address.deleteAllRows", query = "DELETE from Address")
})
public class Address implements Serializable {

    private static final long serialVersionUID = -2623243581967034060L;

    @Id
    @Column(nullable = false, length = 50)
    private String street;

    private String additionalInfo;

    @ManyToOne
    private CityInfo cityInfo;

    @OneToMany(mappedBy = "address")
    private List<Person> people;

    public Address() {
    }

    public Address(String street, String additionalInfo) {
        this.street = street;
        this.additionalInfo = additionalInfo;
        this.people = new ArrayList<>();
    }

    // Making sure to keep track of both counterparts
    public void setCityInfo(CityInfo cityInfo) {
        if (cityInfo != null) {
            this.cityInfo = cityInfo;
            cityInfo.addAddress(this);
        }
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public CityInfo getCityInfo() {
        return cityInfo;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    @Override
    public String toString() {
        return "Address{" +
            ", street='" + street + '\'' +
            ", additionalInfo='" + additionalInfo + '\'' +
            ", cityInfo=" + cityInfo +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(getStreet(), address.getStreet()) && Objects
            .equals(getAdditionalInfo(), address.getAdditionalInfo()) && Objects
            .equals(getCityInfo(), address.getCityInfo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStreet(), getAdditionalInfo(), getCityInfo());
    }
}
