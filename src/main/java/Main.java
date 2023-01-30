import model.Bank;
import model.Person;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import javax.xml.XMLConstants;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String FILENAME = "src/main/resources/test.xml";


    public static void main(String[] args) {
        parse();
    }

    public static void parse()  {
        try {
            SAXBuilder sax = new SAXBuilder();
            sax.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            sax.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

            Document doc = sax.build(new File(FILENAME));

            Element rootNode = doc.getRootElement();
            Double bankWallet = Double.parseDouble(rootNode.getAttributeValue("wallet"));
            List<Element> list = rootNode.getChildren("Person");
            List<Person> people = new ArrayList<>();
            list.forEach(x -> people.add(new Person(x.getAttributeValue("name"),
                    Double.parseDouble(x.getAttributeValue("wallet")), null)));
            Double allMoney = bankWallet + people.stream()
                    .map(Person::getWallet)
                    .reduce((double) 0, Double::sum);
            people.forEach(x -> x.setAppendFromBank((allMoney / people.size()) - x.getWallet()));
            System.out.println(allMoney);

        } catch (JDOMException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}
