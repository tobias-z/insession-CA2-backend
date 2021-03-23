package dtos.cityinfo;

import entities.cityinfo.CityInfo;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CityInfoDTO {

    private String zipCode;
    private String city;

    public static List<CityInfoDTO> getFromList(List<CityInfo> cityInfos) {
        return cityInfos.stream()
            .map(cityinfo -> new CityInfoDTO(cityinfo))
            .collect(Collectors.toList());
    }

    public CityInfoDTO(CityInfo cityInfo) {
        this.zipCode = cityInfo.getZipCode();
        this.city = cityInfo.getCity();
    }

    public CityInfoDTO(String zipCode, String city) {
        this.zipCode = zipCode;
        this.city = city;
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

    @Override
    public String toString() {
        return "CityInfoDTO{" +
            "zipCode='" + zipCode + '\'' +
            ", city='" + city + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CityInfoDTO)) {
            return false;
        }
        CityInfoDTO that = (CityInfoDTO) o;
        return Objects.equals(getZipCode(), that.getZipCode()) && Objects
            .equals(getCity(), that.getCity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getZipCode(), getCity());
    }
}
