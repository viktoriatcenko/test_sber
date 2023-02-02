package model;

import java.util.List;

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

    public void setResultPeople(List<Person> resultPeople) {
        this.resultPeople = resultPeople;
    }

}
