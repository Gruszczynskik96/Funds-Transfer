package com.transfer.transfer.calculate.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CalculateServiceTest {

    @Autowired
    private CalculateService calculateService;

    @Test
    public void shouldSumTwoVariables() {
        Assertions.assertEquals(1.0, calculateService.sum(0.0, 1.0));
        Assertions.assertEquals(12.0, calculateService.sum(7.0, 5.0));
        Assertions.assertEquals(127.57, calculateService.sum(43.89, 83.68));
    }

    @Test
    public void shouldSubtractTwoVariables() {
        Assertions.assertEquals(-1.0, calculateService.subtract(0.0, 1.0));
        Assertions.assertEquals(2.0, calculateService.subtract(7.0, 5.0));
        Assertions.assertEquals(83.68, calculateService.subtract(127.57, 43.89));
    }

    @Test
    public void shouldMultiplyTwoVariables() {
        Assertions.assertEquals(0.0, calculateService.multiply(0.0, 1.0));
        Assertions.assertEquals(35.0, calculateService.multiply(7.0, 5.0));
        Assertions.assertEquals(3672.72, calculateService.multiply(43.89, 83.68));
    }
}
