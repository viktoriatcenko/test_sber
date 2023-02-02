package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "total")
public class Total {

    private Result result;

    private Minimum minimum;

    public Total() {
    }

    public Total(Result result, Minimum minimum) {
        this.result = result;
        this.minimum = minimum;
    }

    public Result getResult() {
        return result;
    }

    @XmlElement(name = "result")
    public void setResult(Result result) {
        this.result = result;
    }

    public Minimum getMinimum() {
        return minimum;
    }

    @XmlElement(name = "minimum")
    public void setMinimum(Minimum minimum) {
        this.minimum = minimum;
    }
}
