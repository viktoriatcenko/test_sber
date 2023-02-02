package model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Minimum {


    private List<ResultPerson> resultPeople;

    public Minimum() {
    }

    public Minimum(List<ResultPerson> resultPeople) {
        this.resultPeople = resultPeople;
    }

    public List<ResultPerson> getResultPeople() {
        return resultPeople;
    }

    @XmlElement(name = "Person")
    public void setResultPeople(List<ResultPerson> resultPeople) {
        this.resultPeople = resultPeople;
    }

}
