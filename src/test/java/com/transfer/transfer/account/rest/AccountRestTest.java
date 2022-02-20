package com.transfer.transfer.account.rest;

import com.transfer.transfer.account.model.AccountModel;
import com.transfer.transfer.account.service.AccountService;
import com.transfer.transfer.account.validation.AccountValidation;
import com.transfer.transfer.account.validation.exception.AccountException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(AccountRestController.class)
public class AccountRestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountValidation accountValidation;

    @MockBean
    private AccountService accountService;

    @Test
    public void shouldReturnHttpStatusNotFoundWhenUserIsNotSearchable() throws Exception {
        Mockito.when(accountService.getAccount(Mockito.anyLong())).thenReturn(null);
        Mockito.doThrow(AccountException.class).when(accountValidation).validateAccountExists(Mockito.anyLong());
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/account/10000"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(0));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/account/10001"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(0));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/account/10002"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(0));
    }

    @Test
    public void shouldReturnJsonWithUserDataFromServer() throws Exception {
        Mockito.when(accountService.getAccount(10001L)).thenReturn(constructNewUserModel(10001L, "USD", 0.0));

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/account/10001"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"balance\":0.0,\"currency\":\"USD\",\"userID\":10001}"));

        Mockito.when(accountService.getAccount(10002L)).thenReturn(constructNewUserModel(10002L, "PLN", 10.0));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/account/10002"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"balance\":10.0,\"currency\":\"PLN\",\"userID\":10002}"));

        Mockito.when(accountService.getAccount(10003L)).thenReturn(constructNewUserModel(10003L, "EUR", 152.7));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/account/10003"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"balance\":152.7,\"currency\":\"EUR\",\"userID\":10003}"));
    }

    private AccountModel constructNewUserModel(long userID, String currency, double balance) {
        AccountModel accountModel = new AccountModel();
        accountModel.setUserID(userID);
        accountModel.setCurrency(currency);
        accountModel.setBalance(balance);
        return accountModel;
    }
}
