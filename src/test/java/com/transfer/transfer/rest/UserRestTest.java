package com.transfer.transfer.rest;

import com.transfer.transfer.user.model.UserModel;
import com.transfer.transfer.user.rest.UserRestController;
import com.transfer.transfer.user.service.UserService;
import com.transfer.transfer.user.validation.UserValidation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@WebMvcTest(UserRestController.class)
public class UserRestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserValidation userValidation;

    @MockBean
    private UserService userService;

    @Test
    public void userControllerShouldReturnJsonWithUserDataFromServer() throws Exception {
        Mockito.when(userService.getUserModel(10001L)).thenReturn(constructNewUserModel(10001L, "USD", 0.0));

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/user/10001"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"balance\":0.0,\"currency\":\"USD\",\"userID\":10001}"));

        Mockito.when(userService.getUserModel(10002L)).thenReturn(constructNewUserModel(10002L, "PLN", 10.0));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/10002"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"balance\":10.0,\"currency\":\"PLN\",\"userID\":10002}"));

        Mockito.when(userService.getUserModel(10003L)).thenReturn(constructNewUserModel(10003L, "EUR", 152.7));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/10003"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"balance\":152.7,\"currency\":\"EUR\",\"userID\":10003}"));
    }

    private Optional<UserModel> constructNewUserModel(long userID, String currency, double balance) {
        UserModel userModel = new UserModel();
        userModel.setUserID(userID);
        userModel.setCurrency(currency);
        userModel.setBalance(balance);
        return Optional.of(userModel);
    }
}
