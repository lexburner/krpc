package moe.cnkirito.krpc.remoting;

import io.netty.util.concurrent.Promise;
import moe.cnkirito.krpc.remoting.codec.KrpcPacket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author daofeng.xjf
 * @date 2019/1/10
 */
public final class PromiseMap {

    public static Map<Long, Promise<KrpcPacket>> instance = new ConcurrentHashMap<>();

}
