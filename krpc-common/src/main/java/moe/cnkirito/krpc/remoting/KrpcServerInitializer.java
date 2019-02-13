package moe.cnkirito.krpc.remoting;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import moe.cnkirito.krpc.remoting.codec.KrpcDecoder;
import moe.cnkirito.krpc.remoting.codec.KrpcEncoder;

/**
 * @author daofeng.xjf
 * @date 2019/1/11
 */
public class KrpcServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new KrpcEncoder())
                .addLast(new KrpcDecoder())
                .addLast(new IdleStateHandler(0, 0, 15))
                .addLast(new KrpcServerHandler());
    }
}
