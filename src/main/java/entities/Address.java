package entities;

import entities.cityinfo.CityInfo;
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

@Entity
@NamedQueries({
    @NamedQuery(name = "Address.deleteAllRows", query = "DELETE from Address")
})
public class Address implements Serializable {

    private static final long serialVersionUID = -2623243581967034060L;

    @Id
    @Column(unique = true, nullable = false, length = 50)
    private String street;

    private String additionalInfo;

    @ManyToOne
    private CityInfo cityInfo;

    public Address() {
    }

    public Address(String street, String additionalInfo) {
        this.street = street;
        this.additionalInfo = additionalInfo;
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
