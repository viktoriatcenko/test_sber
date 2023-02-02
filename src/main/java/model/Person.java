package model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAttribute;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class Person {
    private String name;
    private BigDecimal wallet;
    private BigDecimal appendFromBank = BigDecimal.ZERO;

    public Person() {
    }

    public String getName() {
        return name;
    }

    @XmlAttribute(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getWallet() {
        return wallet;
    }

    @XmlAttribute(name = "wallet")
    public void setWallet(BigDecimal wallet) {
        this.wallet = wallet;
    }

    public BigDecimal getAppendFromBank() {
        return appendFromBank;
    }

    @XmlAttribute(name = "appendFromBank")
    public void setAppendFromBank(BigDecimal appendFromBank) {
        this.appendFromBank = appendFromBank;
    }

    public void addMoneyFromBank(BigDecimal money) {
        this.wallet = this.wallet.add(money);
        this.appendFromBank = this.appendFromBank.add(money);
    }
}
