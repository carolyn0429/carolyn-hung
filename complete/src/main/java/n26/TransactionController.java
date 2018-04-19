package n26;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(value="/transactionservice")
public class TransactionController {

    @Autowired
    private TransactionService service;

    private final AtomicLong counter = new AtomicLong();

    @PutMapping(value = "/transaction/{transaction_id}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> addTransaction(
            @PathVariable long transaction_id,
            @RequestBody Map<String, Object> payload) throws IOException{
        System.out.println(payload);
        Transaction newTransaction;
        String type = (String) payload.get("type");
        double amount = (double) payload.get("amount");
        String parentId = String.valueOf(payload.get("parent_id"));
        if (payload.get("parent_id") == null){
            newTransaction =
                    service.addTransaction(transaction_id, type, amount);
        } else {
            Long parent_id =  Long.valueOf(parentId);
            newTransaction =
                    service.addTransaction(transaction_id, type, amount, parent_id);
        }

        if (newTransaction != null){
            JSONObject entity = new JSONObject();
            entity.put("status", "ok");
            return new ResponseEntity<>(entity, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value="/transaction/{transaction_id}", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> getTransaction(@PathVariable long transaction_id) {
        Transaction transaction = service.getTransactionById(transaction_id);
        if (transaction != null){
            JSONObject entity = new JSONObject();
            entity.put("amount", transaction.getAmount());
            entity.put("type", transaction.getType());
            if (transaction.getType() != null){
                entity.put("parent_id", transaction.getParent_id());
            }
            return new ResponseEntity<>(entity, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value="/types/{type}", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Long>> getTypes(@PathVariable String type)
    {
        List<Long> transactions = service.getTransactionByType(type);
        if (transactions == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<> (transactions, HttpStatus.OK);
        }
    }

    @GetMapping(value="/sum/{transaction_id}", produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> getSum(@PathVariable long transaction_id) {
        double sum = service.getSumById(Long.valueOf(transaction_id));
        JSONObject entity = new JSONObject();
        entity.put("sum", sum);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }
}
