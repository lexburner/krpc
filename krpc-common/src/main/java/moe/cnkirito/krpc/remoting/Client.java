package moe.cnkirito.krpc.remoting;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import moe.cnkirito.krpc.remoting.codec.KrpcPacket;

/**
 * @author daofeng.xjf
 * @date 2019/1/9
 */
public interface Client {

    void send(KrpcPacket request,GenericFutureListener<? extends Future<KrpcPacket>> listener);

    void reconnect();

    boolean isConnected();

}
