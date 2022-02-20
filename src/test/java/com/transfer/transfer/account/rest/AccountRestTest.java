package com.transfer.transfer.account.rest;

import com.google.gson.Gson;
import com.transfer.transfer.account.model.AccountModel;
import com.transfer.transfer.account.service.AccountService;
import com.transfer.transfer.account.validation.AccountValidation;
import com.transfer.transfer.account.validation.exception.AccountException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;

@WebMvcTest(AccountRestController.class)
public class AccountRestTest {

    private static final String ACCOUNT_CREATED_MESSAGE = "Account was created for user ID: ";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountValidation accountValidation;

    @MockBean
    private AccountService accountService;

    @Test
    public void shouldReturnUnfinishedStatusWhenExceptionWillBeThrown() throws Exception {
        Mockito.when(accountService.getAccount(Mockito.anyLong())).thenReturn(null);
        Mockito.doThrow(AccountException.class).when(accountValidation).validateAccountExists(Mockito.anyLong());
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/account/10000"))
                .andExpect(MockMvcResultMatchers.status().is(0));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/account/10001"))
                .andExpect(MockMvcResultMatchers.status().is(0));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/account/10002"))
                .andExpect(MockMvcResultMatchers.status().is(0));
    }

    @Test
    public void shouldReturnJsonWithUsersDataFromServer() throws Exception {
        Mockito.when(accountService.getAccounts()).thenReturn(Collections.emptyList());
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/account"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));

        Mockito.when(accountService.getAccounts()).thenReturn(Collections.singletonList(constructNewUserModel(10001L, "USD", 100.0)));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/account"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[{\"balance\":100.0,\"currency\":\"USD\",\"userID\":10001}]"));

        Mockito.when(accountService.getAccounts()).thenReturn(Arrays.asList(constructNewUserModel(10001L, "USD", 0.0),
                constructNewUserModel(10002L, "PLN", 100.0)));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/account"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[{\"balance\":0.0,\"currency\":\"USD\",\"userID\":10001}" +
                        ",{\"balance\":100.0,\"currency\":\"PLN\",\"userID\":10002}]"));
    }

    @Test
    public void shouldCreateUserAccountAndReturnSuccessfulMessage() throws Exception {
        AccountModel firstAccountModel = constructNewUserModel(10001L, "USD", 0.0);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(firstAccountModel)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(ACCOUNT_CREATED_MESSAGE + 10001L));

        AccountModel accountModel = constructNewUserModel(10002L, "PLN", 100.0);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(accountModel)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(ACCOUNT_CREATED_MESSAGE + 10002L));
    }

    @Test
    public void shouldReturnBadRequestWhenAccountModelIsIncorrect() throws Exception {
        AccountModel firstAccountModel = constructNewUserModel(10001L, "", 0.0);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(firstAccountModel)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        AccountModel secondAccountModel = constructNewUserModel(10002L, null, 0.0);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(secondAccountModel)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldReturnJsonWithUserDataFromServer() throws Exception {
        Mockito.when(accountService.getAccount(10001L)).thenReturn(constructNewUserModel(10001L, "USD", 0.0));

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/account/10001"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"balance\":0.0,\"currency\":\"USD\",\"userID\":10001}"));

        Mockito.when(accountService.getAccount(10002L)).thenReturn(constructNewUserModel(10002L, "PLN", 10.0));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/account/10002"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"balance\":10.0,\"currency\":\"PLN\",\"userID\":10002}"));

        Mockito.when(accountService.getAccount(10003L)).thenReturn(constructNewUserModel(10003L, "EUR", 152.7));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/account/10003"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"balance\":152.7,\"currency\":\"EUR\",\"userID\":10003}"));
    }

    private AccountModel constructNewUserModel(Long userID, String currency, Double balance) {
        AccountModel accountModel = new AccountModel();
        accountModel.setUserID(userID);
        accountModel.setCurrency(currency);
        accountModel.setBalance(balance);
        return accountModel;
    }
}
