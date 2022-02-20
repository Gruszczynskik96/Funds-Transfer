package com.transfer.transfer.calculate.service;

public interface CalculateService {
    /**
     * Sums two double type variables, using BigDecimal.
     * @param a The first value.
     * @param b The second value.
     * @return The result.
     */
    double sum(double a, double b);
    /**
     * Subtracts two double type variables, using BigDecimal.
     * @param a The first value.
     * @param b The second value.
     * @return The result.
     */
    double subtract(double a, double b);
    /**
     * Multiplies two double type variables, using BigDecimal.
     * @param a The first value.
     * @param b The second value.
     * @return The result.
     */
    double multiply(double a, double b);
}
