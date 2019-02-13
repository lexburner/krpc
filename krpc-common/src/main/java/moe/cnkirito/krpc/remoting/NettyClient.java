package moe.cnkirito.krpc.remoting;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import moe.cnkirito.krpc.remoting.codec.KrpcPacket;

/**
 * @author daofeng.xjf
 * @date 2019/1/9
 */
public class NettyClient implements Client {

    private String ip;
    private int port;

    private Channel channel;
    private static final NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
    private static final EventExecutor callbackThreads = new DefaultEventExecutor();

    public NettyClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        open();
    }

    @Override
    public void send(KrpcPacket request, GenericFutureListener<? extends Future<KrpcPacket>> listener) {
        if (channel == null || !channel.isActive()) {
            throw new RuntimeException("the channel isn't open");
        }
        Promise<KrpcPacket> krpcPacketPromise = new DefaultPromise<>(callbackThreads);
        if (listener != null) {
            krpcPacketPromise.addListener(listener);
        }
        PromiseMap.instance.put(request.getRequestId(), krpcPacketPromise);
        channel.writeAndFlush(request);

    }

    @Override
    public void reconnect() {
        if (!isConnected()) {
            open();
        }
    }

    @Override
    public boolean isConnected() {
        return channel != null && channel.isActive();
    }

    private void open() {
        Bootstrap b = new Bootstrap();
        b.group(nioEventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new KRpcClientInitializer())
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, false)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);
        ChannelFuture channelFuture = b.connect(ip, port);
        channelFuture.awaitUninterruptibly();
        if (channelFuture.isSuccess()) {
            this.channel = channelFuture.channel();
        }

    }
}
