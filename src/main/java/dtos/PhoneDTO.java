package dtos;

import entities.Phone;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PhoneDTO {

    private Integer id;
    private String number;
    private String description;

    public static List<PhoneDTO> getFromList(List<Phone> phones) {
        return phones.stream()
            .map(phone -> new PhoneDTO(phone))
            .collect(Collectors.toList());
    }

    public PhoneDTO() {
    }

    public PhoneDTO(Phone phone) {
        this.id = phone.getPhoneId();
        this.number = phone.getNumber();
        this.description = phone.getDescription();
    }


    public PhoneDTO(String number, String description) {
        this.number = number;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhoneDTO)) {
            return false;
        }
        PhoneDTO phoneDTO = (PhoneDTO) o;
        return Objects.equals(getId(), phoneDTO.getId()) && Objects
            .equals(getNumber(), phoneDTO.getNumber()) && Objects
            .equals(getDescription(), phoneDTO.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumber(), getDescription());
    }

    @Override
    public String toString() {
        return "PhoneDTO{" +
            "id=" + id +
            ", number='" + number + '\'' +
            ", description='" + description + '\'' +
            '}';
    }
}
