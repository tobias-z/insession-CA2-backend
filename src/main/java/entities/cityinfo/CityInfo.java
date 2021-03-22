package entities.cityinfo;

import entities.Address;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries({
    @NamedQuery(name = "CityInfo.deleteAllRows", query = "DELETE from CityInfo")
})
public class CityInfo implements Serializable {

    private static final long serialVersionUID = -5290526122114188782L;

    @Id
    @Column(length = 4)
    private String zipCode;

    @Column(length = 35)
    private String city;

    @OneToMany(mappedBy = "cityInfo")
    private List<Address> addresses;

    public CityInfo() {
    }

    public CityInfo(String zipCode, String city) {
        this.zipCode = zipCode;
        this.city = city;
        this.addresses = new ArrayList<>();
    }


    public void addAddress(Address address) {
        this.addresses.add(address);
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    @Override
    public String toString() {
        return "CityInfo{" +
            "zipCode='" + zipCode + '\'' +
            ", city='" + city + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CityInfo)) {
            return false;
        }
        CityInfo cityInfo = (CityInfo) o;
        return Objects.equals(getZipCode(), cityInfo.getZipCode()) && Objects
            .equals(getCity(), cityInfo.getCity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getZipCode(), getCity());
    }
}
