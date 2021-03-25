package dtos;

import dtos.cityinfo.CityInfoDTO;
import entities.Address;
import java.util.Objects;

public class AddressDTO {

    private String street;
    private String additionalInfo;
    CityInfoDTO cityInfo;

    public AddressDTO() {
    }

    public AddressDTO(Address address) {
        this.street = address.getStreet();
        this.additionalInfo = address.getAdditionalInfo();
        this.cityInfo = new CityInfoDTO(address.getCityInfo());
    }

    public AddressDTO(String street, String additionalInfo, CityInfoDTO cityInfoDTO) {
        this.street = street;
        this.additionalInfo = additionalInfo;
        this.cityInfo = cityInfoDTO;
    }

    public void setCityInfo(CityInfoDTO cityInfo) {
        this.cityInfo = cityInfo;
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

    public CityInfoDTO getCityInfo() {
        return cityInfo;
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
            ", street='" + street + '\'' +
            ", additionalInfo='" + additionalInfo + '\'' +
            ", cityInfoDTO=" + cityInfo +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AddressDTO)) {
            return false;
        }
        AddressDTO that = (AddressDTO) o;
        return Objects.equals(getStreet(), that.getStreet()) && Objects
            .equals(getAdditionalInfo(), that.getAdditionalInfo()) && Objects
            .equals(getCityInfo(), that.getCityInfo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStreet(), getAdditionalInfo(), getCityInfo());
    }
}
