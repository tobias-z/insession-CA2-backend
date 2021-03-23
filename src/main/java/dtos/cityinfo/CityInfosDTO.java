package dtos.cityinfo;

import entities.cityinfo.CityInfo;
import java.util.List;
import java.util.Objects;

public class CityInfosDTO {

    private List<CityInfoDTO> all;

    public CityInfosDTO(List<CityInfo> all) {
        this.all = CityInfoDTO.getFromList(all);
    }

    public List<CityInfoDTO> getAll() {
        return all;
    }

    public void setAll(List<CityInfoDTO> all) {
        this.all = all;
    }

    @Override
    public String toString() {
        return "CityInfosDTO{" +
            "all=" + all +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CityInfosDTO)) {
            return false;
        }
        CityInfosDTO that = (CityInfosDTO) o;
        return Objects.equals(getAll(), that.getAll());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAll());
    }
}
