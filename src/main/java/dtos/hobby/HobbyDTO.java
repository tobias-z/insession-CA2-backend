package dtos.hobby;

import entities.hobby.Hobby;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HobbyDTO {

    private String name;
    private String wikiLink;
    private String category;
    private String type;

    public static List<HobbyDTO> getFromList(List<Hobby> hobbies) {
        return hobbies.stream()
            .map(hobby -> new HobbyDTO(hobby))
            .collect(Collectors.toList());
    }

    public HobbyDTO(String name, String wikiLink, String category, String type) {
        this.name = name;
        this.wikiLink = wikiLink;
        this.category = category;
        this.type = type;
    }

    public HobbyDTO(Hobby hobby) {
        this.name = hobby.getName();
        this.wikiLink = hobby.getWikiLink();
        this.category = hobby.getCategory();
        this.type = hobby.getType();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWikiLink() {
        return wikiLink;
    }

    public void setWikiLink(String wikiLink) {
        this.wikiLink = wikiLink;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "HobbyDTO{" +
            "name='" + name + '\'' +
            ", wikiLink='" + wikiLink + '\'' +
            ", category='" + category + '\'' +
            ", type='" + type + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HobbyDTO)) {
            return false;
        }
        HobbyDTO hobbyDTO = (HobbyDTO) o;
        return Objects.equals(getName(), hobbyDTO.getName()) && Objects
            .equals(getWikiLink(), hobbyDTO.getWikiLink()) && Objects
            .equals(getCategory(), hobbyDTO.getCategory()) && Objects
            .equals(getType(), hobbyDTO.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getWikiLink(), getCategory(), getType());
    }
}
