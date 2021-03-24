package dtos.hobby;

import entities.hobby.Hobby;
import java.util.List;
import java.util.Objects;

public class HobbiesDTO {

    private List<HobbyDTO> all;

    public HobbiesDTO(List<Hobby> all) {
        this.all = HobbyDTO.getFromList(all);
    }

    public List<HobbyDTO> getAll() {
        return all;
    }

    public void setAll(List<HobbyDTO> all) {
        this.all = all;
    }

    @Override
    public String toString() {
        return "HobbiesDTO{" +
            "all=" + all +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HobbiesDTO)) {
            return false;
        }
        HobbiesDTO that = (HobbiesDTO) o;
        return Objects.equals(getAll(), that.getAll());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAll());
    }
}