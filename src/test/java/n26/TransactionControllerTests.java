package n26;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TransactionService serviceMock;

    @Test
    public void paramTransactionIdShouldReturnTransaction() throws Exception {

        // Arrange
        Transaction txn1 = new Transaction(1l, "cars", 20.00);

        when(serviceMock.getTransactionById(1l)).thenReturn(txn1);
        this.mockMvc.perform(get("/transactionservice/transaction/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void paramTransactionIdShouldReturnSum() throws Exception {

        this.mockMvc.perform(get("/transactionservice/sum/{id}", 1))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.sum").value("50.0"));
    }

    @Test
    public void addNewTransactionWithIdShouldReturnTransactionSuccessfully() throws Exception {

        // Arrange
        Transaction newTransaction = new Transaction(100, "rent", 1500.00, 2l);
        ObjectMapper mapper =  new ObjectMapper();
        String request = mapper.writeValueAsString(newTransaction);

        this.mockMvc.perform(put("/transactionservice/transaction/{id}", 100)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(request))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void paramTypeShouldReturnTransactions() throws Exception {

        List<Long> expectedValue = new ArrayList<>();
        expectedValue.add(1l);
        expectedValue.add(2l);
        MvcResult result =mockMvc.perform(get("/transactionservice/types/{type}", "groceries"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        Assert.assertEquals("[1,2]", result.getResponse().getContentAsString());

    }

}
