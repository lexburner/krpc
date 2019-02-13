package moe.cnkirito.krpc.remoting;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.Promise;
import moe.cnkirito.krpc.remoting.codec.KrpcPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author daofeng.xjf
 * @date 2019/1/10
 */
public class KrpcClientHandler extends SimpleChannelInboundHandler<KrpcPacket> {

    private final static Logger LOGGER = LoggerFactory.getLogger(KrpcClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, KrpcPacket krpcPacket) throws Exception {
        long requestId = krpcPacket.getRequestId();
        Promise<KrpcPacket> promise = PromiseMap.instance.remove(requestId);
        if (promise != null) {
            promise.trySuccess(krpcPacket);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("channel now is inactive");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            LOGGER.info("client idle state:{}", idleStateEvent.state());
        }
        super.userEventTriggered(ctx, evt);
    }
}
