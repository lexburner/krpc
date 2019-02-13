package moe.cnkirito.krpc.remoting;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author daofeng.xjf
 * @date 2019/1/10
 */
public class NettyServer implements Server {

    private final static Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);

    private static EventLoopGroup workerGroup = new NioEventLoopGroup();

    private int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new KrpcServerInitializer())
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, false);
        try {
            Channel channel = bootstrap.bind(port).sync().channel();
            LOGGER.info("server start at localhost:{}", port);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            LOGGER.error("server closed cause by interrupted exception", e);
        }

    }

}
