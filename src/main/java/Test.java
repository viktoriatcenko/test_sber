
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import model.*;

public class Test {
    public static void main(String[] args) {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(Bank.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Bank bank = (Bank) jaxbUnmarshaller.unmarshal(new FileReader("src/main/resources/test.xml"));
            System.out.println(bank);
            bank.getPeople().forEach(System.out::println);
            System.out.println("");
            List<Person> people = bank.getPeople();
            BigDecimal peopleMoney = people.stream()
                    .map(Person::getWallet)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal allMoney = peopleMoney.add(bank.getWallet());
            people.forEach(x -> {
                BigDecimal fe = allMoney.divide(BigDecimal.valueOf(people.size()), 2, RoundingMode.UP);
                x.setAppendFromBank(fe.subtract(x.getWallet()));
                x.setWallet(fe.add(x.getWallet()));
            });
            System.out.println();

            List<Person> sortedPeople = people.stream()
                .sorted(Comparator.comparing(Person::getAppendFromBank))
                .limit(3)
                .collect(Collectors.toList());

            Result result = new Result(people);
            Minimum minimum = new Minimum(sortedPeople);
            Total total = new Total(result, minimum);

            jaxbContext = JAXBContext.newInstance(Bank.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            File file = new File("src/main/resources/result.xml");

            jaxbMarshaller.marshal(total, file);

        } catch (JAXBException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
