package n26;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class Transaction {

    private long transaction_id;
    private String type;
    private double amount;

    @Value("${optional}")
    private Long parent_id;

    public Transaction(long id, String type, double amount) {
        this.transaction_id = id;
        this.type = type;
        this.amount = amount;
    }

    public Transaction(long id, String type, double amount,
                       @Autowired(required = false) Long parent_id) {
        this.transaction_id = id;
        this.type = type;
        this.amount = amount;
        this.parent_id = parent_id;
    }

    public long getTransactionId() {
        return transaction_id;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

}
