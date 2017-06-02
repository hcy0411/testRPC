package testRPC.test.client;

import testRPC.RPC;
import testRPC.RPCFrame;
import testRPC.test.api.FileOperate;

public class FileOperater {

    public static void main(String[] args) {
        RPC rpc = new RPCFrame();

        FileOperate fileOperate = rpc.getProxy(FileOperate.class);

        try {
            fileOperate.write("test.txt", "haha\r\n");
            String context = fileOperate.readOneLine("test.txt",3);
            System.out.print(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
