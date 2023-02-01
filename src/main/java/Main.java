import model.Bank;
import model.Person;

import org.jdom2.Attribute;
import org.jdom2.CDATA;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javax.xml.XMLConstants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final String FILENAME = "src/main/resources/test.xml";
    private static final String RESULT = "src/main/resources/result.xml";


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
            people.forEach(x -> x.setAppendFromBank(round(allMoney / people.size()) - x.getWallet()));
            writeXml(System.out, people);

        } catch (JDOMException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static Double round(Double d) {
        return (double) (Math.round(d * 100) / 100);
    }

    private static void writeXml(OutputStream output, List<Person> people) throws IOException {

        Document doc = new Document();
        Element total = new Element("total");
        doc.setRootElement(total);

        Element result = new Element("result");

        List<Element> totalList = new ArrayList<>();
        for (Person x: people) {
            totalList.add(new Element("Person").setAttribute("name", x.getName())
                .setAttribute("wallet", String.valueOf(x.getWallet()))
                .setAttribute("appendFromBank", String.valueOf(x.getAppendFromBank())));
        }
        result.addContent(totalList);

        Element minimum = new Element("minimum");
        List<Person> sortedPeople = people.stream()
            .sorted(Comparator.comparingDouble(Person::getAppendFromBank))
            .limit(3)
            .collect(Collectors.toList());

        List<Element> minimumList = new ArrayList<>();
            for (Person x: sortedPeople) {
                minimumList.add(new Element("Person").setAttribute("name", x.getName()));
            }
        minimum.addContent(minimumList);

        total.addContent(result);
        total.addContent(minimum);

        XMLOutputter xmlOutputter = new XMLOutputter();

        xmlOutputter.setFormat(Format.getPrettyFormat());
        xmlOutputter.output(doc, output);

//        try(FileOutputStream fileOutputStream =
//                new FileOutputStream(RESULT)){
//            xmlOutputter.output(doc, fileOutputStream);
//        }
    }
}
