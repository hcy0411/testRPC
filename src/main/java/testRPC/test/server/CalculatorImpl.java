package testRPC.test.server;

import testRPC.test.api.Calculator;

public class CalculatorImpl implements Calculator {

    public int multiply(int a, int b) {
        return a * b;
    }

    public int devide(int a, int b) {
        return a / b;
    }

}
