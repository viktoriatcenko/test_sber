package model;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;


@Data
@XmlRootElement(name = "Bank")
public class Bank {
    private BigDecimal wallet;

    private List<Person> people;

    public BigDecimal getWallet() {
        return wallet;
    }

    @XmlAttribute(name = "wallet")
    public void setWallet(BigDecimal wallet) {
        this.wallet = wallet;
    }

    public List<Person> getPeople() {
        return people;
    }

    @XmlElement(name = "Person")
    public void setPeople(List<Person> people) {
        this.people = people;
    }
}
