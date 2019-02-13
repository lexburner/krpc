package moe.cnkirito.krpc.demo;

import moe.cnkirito.krpc.remoting.NettyClient;
import moe.cnkirito.krpc.remoting.codec.KrpcPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author daofeng.xjf
 * @date 2019/1/10
 */
public class KrpcClientApp {

    private final static Logger LOGGER = LoggerFactory.getLogger(KrpcClientApp.class);

    public static void main(String[] args) throws IOException {
        NettyClient nettyClient1 = new NettyClient("127.0.0.1", 9898);
        NettyClient nettyClient2 = new NettyClient("127.0.0.1", 9898);

        nettyClient1.send(createRandomRequest(), future -> {
            KrpcPacket krpcPacket = future.get();
            LOGGER.info(new String(krpcPacket.getContent()));
        });
        nettyClient2.send(createRandomRequest(), future -> {
            KrpcPacket krpcPacket = future.get();
            LOGGER.info(new String(krpcPacket.getContent()));
        });

        System.in.read();
    }

    private static AtomicLong id = new AtomicLong();

    private static Long generateRequestId() {
        return id.getAndIncrement();
    }

    private static KrpcPacket createRandomRequest() {
        KrpcPacket krpcPacket = new KrpcPacket();
        krpcPacket.setProtocol(KrpcPacket.PROTOCOL_MESSAGE);
        krpcPacket.setType(KrpcPacket.REQUEST);
        Long requestId = generateRequestId();
        krpcPacket.setRequestId(requestId);
        krpcPacket.setContent(("hello " + requestId).getBytes());
        return krpcPacket;
    }

}
