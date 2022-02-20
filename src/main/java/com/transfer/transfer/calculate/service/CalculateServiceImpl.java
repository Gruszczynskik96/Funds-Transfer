package com.transfer.transfer.calculate.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CalculateServiceImpl implements CalculateService {
    @Override
    public double sum(double a, double b) {
        return new BigDecimal(a + b).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
    }

    @Override
    public double subtract(double a, double b) {
        return new BigDecimal(a - b).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
    }

    @Override
    public double multiply(double a, double b) {
        return new BigDecimal(a * b).setScale(2, RoundingMode.UP).doubleValue();
    }
}
