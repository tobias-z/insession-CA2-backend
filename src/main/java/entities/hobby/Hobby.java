package entities.hobby;

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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
    @NamedQuery(name = "Hobby.deleteAllRows", query = "DELETE from Hobby")
})
public class Hobby implements Serializable {

    private static final long serialVersionUID = 2701673906158199769L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hobbyId;

    @Column(length = 50, unique = true)
    private String name;

    private String wikiLink;

    private String category;

    private String type;
    
    @ManyToMany(
            mappedBy = "hobbies")
    List<Person> persons;

    public Hobby() {
    }

    public Hobby(String name, String wikiLink, String category, String type) {
        this.name = name;
        this.wikiLink = wikiLink;
        this.category = category;
        this.type = type;
        this.persons = new ArrayList<>();
    }

    public Integer getHobbyId() {
        return hobbyId;
    }

    public void setHobbyId(Integer hobbyId) {
        this.hobbyId = hobbyId;
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
    
    public List<Person> getPersons() {
        return persons;
    }
    
  

    @Override
    public String toString() {
        return "Hobby{" +
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
        if (!(o instanceof Hobby)) {
            return false;
        }
        Hobby hobby = (Hobby) o;
        return Objects.equals(getHobbyId(), hobby.getHobbyId()) && Objects
            .equals(getName(), hobby.getName()) && Objects.equals(getWikiLink(), hobby.getWikiLink())
            && Objects.equals(getCategory(), hobby.getCategory()) && Objects
            .equals(getType(), hobby.getType()) && Objects.equals(getPersons(), hobby.getPersons());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHobbyId(), getName(), getWikiLink(), getCategory(), getType(), getPersons());
    }

    // ??
    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

 
 
}
