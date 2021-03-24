package entities.hobby;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @Column(length = 50)
    private String name;

    private String wikiLink;

    private String category;

    private String type;

    public Hobby() {
    }

    public Hobby(String name, String wikiLink, String category, String type) {
        this.name = name;
        this.wikiLink = wikiLink;
        this.category = category;
        this.type = type;
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
        return Objects.equals(getName(), hobby.getName()) && Objects
            .equals(getWikiLink(), hobby.getWikiLink()) && Objects
            .equals(getCategory(), hobby.getCategory()) && Objects.equals(getType(), hobby.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getWikiLink(), getCategory(), getType());
    }
}
