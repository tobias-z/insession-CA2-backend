package dtos;

import java.util.Objects;

public class PhoneDTO {

    private String number;
    private String description;

    public PhoneDTO() {
    }

    public PhoneDTO(String number, String description) {
        this.number = number;
        this.description = description;
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

    @Override
    public String toString() {
        return "PhoneDTO{" +
            "number='" + number + '\'' +
            ", description='" + description + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhoneDTO)) {
            return false;
        }
        PhoneDTO phoneDTO = (PhoneDTO) o;
        return Objects.equals(getNumber(), phoneDTO.getNumber()) && Objects
            .equals(getDescription(), phoneDTO.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getDescription());
    }
}
