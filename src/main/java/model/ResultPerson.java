package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ResultPerson {
    private String name;

    public ResultPerson() {
    }

    public ResultPerson(Person person) {
        this.name = person.getName();
    }

    public String getName() {
        return name;
    }

    @XmlAttribute(name = "name")
    public void setName(String name) {
        this.name = name;
    }

}
