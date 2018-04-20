package n26;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RestController
@RequestMapping(value="/transactionservice/", headers = "Accept=application/json")
public class TransactionService {

        //Initialize Data
        static Transaction txn_1 = new Transaction(1l, "groceries", 47.25);
        static Transaction txn_2 = new Transaction(2l, "groceries", 2.75, 1l);
    public List<Transaction> transactions = new ArrayList<>(Arrays.asList(txn_1, txn_2));

    public Transaction addTransaction(long id, String type, double amount,
                                      @Autowired(required = false) Long parent_id){

        if(type == null || type.length() == 0) return null;
        for(Transaction txn: transactions){
            if (txn.getTransactionId() == id){
                return null;
            }
        }
        Transaction transaction = new Transaction(id, type, amount, parent_id);
        transactions.add(transaction);
        return transaction;
    }

    public Transaction addTransaction(long id, String type, double amount){

        if(type == null || type.length() == 0) return null;
        for(Transaction txn: transactions){
            if (txn.getTransactionId() == id){
                return null;
            }
        }
        Transaction transaction = new Transaction(id, type, amount);
        transactions.add(transaction);
        return transaction;
    }
    public List<Transaction> retrieveTransactions(){
        return transactions;
    }

    public Transaction getTransactionById(long transaction_id){
        if (transaction_id == 0) return null;
        for(Transaction txn: transactions){
            if (txn.getTransactionId() == transaction_id){
                return txn;
            }
        }
        return null;
    }

    public double getSumById(long transaction_id){
        double result = 0.00;
        for(Transaction txn: transactions){
            if(txn.getTransactionId() == transaction_id
                    || (txn.getParent_id()!=null && txn.getParent_id().equals(Long.valueOf(transaction_id)))){
                result += txn.getAmount();
            }
        }
        return result;
    }

    public List<Long> getTransactionByType(String type){
        List<Long> transactionIds = new ArrayList<>();
        for(Transaction txn: transactions){
            if (txn.getType().equals(type)){
                transactionIds.add(txn.getTransactionId());
            }
        }
        return transactionIds;
    }
}
