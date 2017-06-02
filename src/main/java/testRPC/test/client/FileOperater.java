package testRPC.test.client;

import testRPC.RPC;
import testRPC.RPCFrame;
import testRPC.test.api.Calculator;

public class CalculatorCaller {

    public static void main(String[] args) {
        RPC rpc = new RPCFrame();

        Calculator calc = rpc.getProxy(Calculator.class);

        for (int i = 0; i < 10; i++) {
            int result = calc.multiply(i, (i + 1));
            System.out.println(result);
        }
    }
}
