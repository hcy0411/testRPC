package testRPC;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hanchunyang on 2017/6/1.
 */
public class RPCFrame implements RPC {

    /**
     * 存储接口注册信息
     */
    private final Map<Class<?>, Object> implCache = new ConcurrentHashMap<Class<?>, Object>();

    /**
     * 存储接口代理信息
     */
    private final Map<Class<?>, Object> proxyCache = new ConcurrentHashMap<Class<?>, Object>();

    public synchronized void register(Class<?> inteface, Object instance) {
        //检测接口是否已经存在
        if (implCache.containsKey(inteface)) {
            throw new RuntimeException("This interface[" + inteface.getName() + "] have already been exposed.");
        } else {
            implCache.put(inteface, instance);
        }
    }

    public void expose() throws IOException {
        //暴露服务端口
        ServerSocket server = new ServerSocket(6666);
        Socket socket = null;
        while (true) {
            socket = server.accept();
            try (
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
                //获取请求数据 - 接收请求,并解码
                Class<?> interfaceName = (Class<?>) in.readObject();
                String methodName = in.readUTF();
                Class<?>[] parameterTypes = (Class<?>[]) in.readObject();
                Object[] args = (Object[]) in.readObject();
                //根据接口信息获得对应的接口实现 - Dispatcher
                Object instance = implCache.get(interfaceName);
                //获取被代理的方法
                Method method = instance.getClass().getMethod(methodName, parameterTypes);
                // 处理该方法，返回结果
                Object result = method.invoke(instance, args);
                //返回结果 - 编码,并返回结果
                out.writeObject(result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    @SuppressWarnings("unchecked")
    public synchronized <T> T getProxy(final Class<T> interfaceName) {
        //检测接口是否已经存在
        if (proxyCache.containsKey(interfaceName)) {
            return (T) proxyCache.get(interfaceName);
        }
        // 得到代理接口 并返回
        T proxy = (T) Proxy.newProxyInstance(interfaceName.getClassLoader(), interfaceName.getInterfaces(),
                (Object proxy1, Method method, Object[] args) -> {
                    Socket socket = new Socket(InetAddress.getLocalHost(), 6666);
                    try (
                            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                            ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {
                        //发送请求数据 - 编码,并发送请求，被代理方法参数
                        output.writeObject(interfaceName);
                        output.writeUTF(method.getName());
                        output.writeObject(method.getParameterTypes());
                        output.writeObject(args);
                        //被代理方法的结果，并返回
                        Object result = input.readObject();

                        return result;
                    }
                }
        );
        proxyCache.put(interfaceName, proxy);
        return proxy;
    }

}
