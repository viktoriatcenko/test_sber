package model;

import java.util.List;

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

    public void setResultPeople(List<Person> resultPeople) {
        this.resultPeople = resultPeople;
    }
}
