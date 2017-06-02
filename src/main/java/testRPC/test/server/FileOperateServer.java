package testRPC.test.server;

import java.io.IOException;

import testRPC.RPC;
import testRPC.RPCFrame;
import testRPC.test.api.FileOperate;

public class FileOperateServer {

    public static void main(String[] args) throws IOException {
        //实例化helloWorld
        FileOperateImpl calculator = new FileOperateImpl();
        //创建rpc框架实例
        RPC rpc = new RPCFrame();
        //注册接口和对应实现
        rpc.register(FileOperate.class, calculator);
        //暴露服务接口，监听服务
        rpc.expose();
    }
}
