package com.transfer.transfer.transfer.rest;

import com.transfer.transfer.account.model.AccountModel;
import com.transfer.transfer.account.service.AccountService;
import com.transfer.transfer.account.validation.AccountValidation;
import com.transfer.transfer.transfer.service.TransferService;
import com.transfer.transfer.transfer.validation.TransferValidation;
import com.transfer.transfer.transfer.validation.exception.TransferException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(TransferRestController.class)
public class TransferRestControllerTest {

    private static final String TRANSFER_SUCCESSFUL_MESSAGE = "Transfer successful!";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;
    @MockBean
    private AccountValidation accountValidation;
    @MockBean
    private TransferValidation transferValidation;
    @MockBean
    private TransferService transferService;

    @Test
    public void shouldReturnHttpStatusException() throws Exception {
        Mockito.when(accountService.getAccount(Mockito.anyLong())).thenReturn(Mockito.mock(AccountModel.class));
        Mockito.when(accountService.getAccount(Mockito.anyLong())).thenReturn(Mockito.mock(AccountModel.class));
        Mockito.doThrow(TransferException.class).when(transferService).transferMoneyBetweenAccounts(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyDouble());

        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/funds/10001")
                        .param("userID", "10002")
                        .param("amount", "100.1"))
                .andExpect(MockMvcResultMatchers.status().is(0));
    }

    @Test
    public void shouldReturnSuccessfulMessage() throws Exception {
        Mockito.when(accountService.getAccount(10001L)).thenReturn(constructNewUserModel(10001L, "USD", 100.0));
        Mockito.when(accountService.getAccount(10002L)).thenReturn(constructNewUserModel(10002L, "USD", 100.0));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/funds/10001")
                        .param("userID", "10002")
                        .param("amount", "100"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(TRANSFER_SUCCESSFUL_MESSAGE));

        Mockito.when(accountService.getAccount(10001L)).thenReturn(constructNewUserModel(10003L, "USD", 100.0));
        Mockito.when(accountService.getAccount(10002L)).thenReturn(constructNewUserModel(10004L, "PLN", 100.0));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/funds/10003")
                        .param("userID", "10004")
                        .param("amount", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(TRANSFER_SUCCESSFUL_MESSAGE));
    }

    private AccountModel constructNewUserModel(long userID, String currency, double balance) {
        AccountModel accountModel = new AccountModel();
        accountModel.setUserID(userID);
        accountModel.setCurrency(currency);
        accountModel.setBalance(balance);
        return accountModel;
    }
}
