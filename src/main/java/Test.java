
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
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
            people.forEach(x -> x.setAppendFromBank((allMoney.divide(BigDecimal.valueOf(people.size()), 2, RoundingMode.UP))
                .subtract(x.getWallet())));
            System.out.println();
        } catch (JAXBException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
