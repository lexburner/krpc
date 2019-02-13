package moe.cnkirito.krpc.remoting;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import moe.cnkirito.krpc.remoting.codec.KrpcPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author daofeng.xjf
 * @date 2019/1/10
 */
public class KrpcServerHandler extends SimpleChannelInboundHandler<KrpcPacket> {

    private final static Logger LOGGER = LoggerFactory.getLogger(KrpcServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, KrpcPacket request) throws Exception {
        if (KrpcPacket.PROTOCOL_MESSAGE == request.getType()) {
            // 正常请求
            String content = "re:" + new String(request.getContent());
            KrpcPacket response = new KrpcPacket();
            response.setProtocol(KrpcPacket.PROTOCOL_MESSAGE);
            response.setRequestId(request.getRequestId());
            response.setType(KrpcPacket.RESPONSE);
            response.setContent(content.getBytes());
            ctx.channel().writeAndFlush(response);
        } else if (KrpcPacket.PROTOCOL_HEART == request.getType()) {
            // 心跳请求
            KrpcPacket response = new KrpcPacket();
            response.setProtocol(KrpcPacket.PROTOCOL_HEART);
            response.setRequestId(request.getRequestId());
            response.setType(KrpcPacket.RESPONSE);
            response.setContent("heartbeat success".getBytes());
            ctx.channel().writeAndFlush(response);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            LOGGER.info("server idle state:{}", idleStateEvent.state());
        }
        super.userEventTriggered(ctx, evt);
    }

}
