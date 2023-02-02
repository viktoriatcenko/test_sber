package model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Minimum {


    private List<Person> resultPeople;

    public Minimum() {
    }

    public Minimum(List<Person> resultPeople) {
        this.resultPeople = resultPeople;
    }

    public List<Person> getResultPeople() {
        return resultPeople;
    }

    @XmlElement(name = "Person")
    public void setResultPeople(List<Person> resultPeople) {
        this.resultPeople = resultPeople;
    }

}
