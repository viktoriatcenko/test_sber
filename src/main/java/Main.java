
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import model.*;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

public class Main {
    private static JAXBContext jaxbContext;
    private static Bank bank;
    public static void main(String[] args) {
            List<Person> people = parseXML();
            count(people, bank.getWallet());
            writeXML(people);
    }



    /**
     * Метод рассчитывает сумму из Bank по всем Person в списке
     * @param people - список всех Person
     * @param moneyFromBank - деньги, которые пришли из Bank
     */
    public static void count(List<Person> people, BigDecimal moneyFromBank) {
         BigDecimal max = people.stream()
                 .max(Comparator.comparing(Person::getWallet))
                 .get().getWallet();

        // сумма, необходимая, чтобы уравнять всех по максимальному значению денег (max)
         BigDecimal delta = people.stream()
                .map(Person::getWallet)
                .reduce(ZERO, (x, y) -> x.add(max.subtract(y)));

        // delta больше чем деньги из банка
        if (delta.compareTo(moneyFromBank) > 0) {
            List<Person> filteredList =  people.stream()
                    .filter(p -> !p.getWallet().equals(max))
                    .collect(Collectors.toList());
            count(filteredList, moneyFromBank);
        } else {
            separatePeople(people, max);
            moneyFromBank = moneyFromBank.subtract(delta);
            divideMoney(people, moneyFromBank);
        }
    }

    /**
     * Метод распределяет все деньги, которые были получены из Bank
     * @param people - список всех Person
     * @param money - деньги, которые пришли из Bank
     */
    public static void divideMoney(List<Person> people, BigDecimal money) {
        double ps = (double) people.size() / 100;
        BigDecimal ost = money.remainder(valueOf(ps));
        BigDecimal ceiling = money.subtract(ost).divide(BigDecimal.valueOf(people.size()));
        double dCeiling = (ps * 100);
        double part = ps / dCeiling;
        people.forEach(p -> p.addMoneyFromBank(ceiling));
        people.stream()
                .limit(ost.multiply(valueOf(100)).longValue())
                .forEach(p -> p.addMoneyFromBank(BigDecimal.valueOf(part)));
    }

    /**
     * Вспомогательный метод, который распределяет деньги, пришедшие из дельты между максимальным кол-вом
     * денег владельца из списка и остальными
     * @param people- список всех Person
     * @param delta - деньги, которые пришли из дельты
     */
    public static void separatePeople(List<Person> people, BigDecimal delta) {
        people.forEach(person -> person.addMoneyFromBank(delta.subtract(person.getWallet())));
    }


    /**
     * Метод парсит XML файлы и возвращает лист POJO-объектов
     * @return
     */
    public static List<Person> parseXML() {
        try {
            jaxbContext = JAXBContext.newInstance(Bank.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            bank = (Bank) jaxbUnmarshaller.unmarshal(new FileReader("src/main/resources/test.xml"));
        } catch (JAXBException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return bank.getPeople();
    }

    /**
     * Вспомогательный метод, возвращающий общее кол-во денег
     * @return
     */
    public static BigDecimal getMoney(List<Person> people) {
        BigDecimal peopleMoney = people.stream()
                .map(Person::getWallet)
                .reduce(ZERO, BigDecimal::add);
        return peopleMoney.add(bank.getWallet());
    }

    /**
     * Метод записывает итоговый XML файл с всеми сущностями
     */
    public static void writeXML(List<Person> people) {
        BigDecimal min = people.stream()
                .map(Person::getWallet)
                .min(Comparator.comparing(BigDecimal::doubleValue)).get();
        List<ResultPerson> sortedPeople = people.stream()
                .filter(p -> p.getWallet().equals(min))
                .map(ResultPerson::new)
                .collect(Collectors.toList());

        Result result = new Result(people);
        Minimum minimum = new Minimum(sortedPeople);
        Total total = new Total(result, minimum);

        try {
            jaxbContext = JAXBContext.newInstance(Total.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            File file = new File("src/main/resources/result.xml");
            jaxbMarshaller.marshal(total, file);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }


}
