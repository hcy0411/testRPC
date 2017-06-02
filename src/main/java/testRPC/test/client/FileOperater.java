package testRPC.test.client;

import testRPC.RPC;
import testRPC.RPCFrame;
import testRPC.test.api.FileOperate;
import testRPC.test.server.FileOperateImpl;

public class FileOperater {

    public static void main(String[] args) {
        RPC rpc = new RPCFrame();

        FileOperate fileOperate = new FileOperateImpl();

        FileOperate proxyFileOperate = rpc.getProxy(fileOperate.getClass());

        try {
            proxyFileOperate.write("test.txt", "haha\r\n");
            String context = proxyFileOperate.readOneLine("test.txt",3);
            System.out.print(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
