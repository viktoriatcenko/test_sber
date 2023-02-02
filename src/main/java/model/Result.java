package model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Result {
    private List<Person> resultPeople;

    public Result() {
    }

    public Result(List<Person> resultPeople) {
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
