package moe.cnkirito.krpc.remoting.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author daofeng.xjf
 * @date 2019/1/10
 */
public class KrpcEncoder extends MessageToByteEncoder<KrpcPacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, KrpcPacket msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getProtocol());
        out.writeInt(msg.getType());
        out.writeInt(msg.getTimeout());
        out.writeLong(msg.getRequestId());
        out.writeInt(msg.getContent().length);
        out.writeBytes(msg.getContent());
    }
}
