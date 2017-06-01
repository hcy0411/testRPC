package testRPC;

import java.io.IOException;

/**
 * Created by hanchunyang on 2017/5/31.
 */
public interface RPC {

    void register(Class<?> className,Object instance);

    void expose() throws IOException;

    <T> T getProxy(final Class<T> inteface);

}
