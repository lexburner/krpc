package moe.cnkirito.krpc.remoting.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author daofeng.xjf
 * @date 2019/1/10
 */
public class KrpcDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 消息头的总长度
        if (in.readableBytes() < KrpcPacket.HEADER_LENGTH) {
            return;
        }
        in.markReaderIndex();
        int protocol = in.readInt();
        int type = in.readInt();
        int timeout = in.readInt();
        long requestId = in.readLong();
        int len = in.readInt();
        if (in.readableBytes() < len) {
            in.resetReaderIndex();
            return;
        }
        byte[] content = new byte[len];
        in.readBytes(content);

        KrpcPacket krpcPacket = new KrpcPacket();
        krpcPacket.setProtocol(protocol);
        krpcPacket.setType(type);
        krpcPacket.setTimeout(timeout);
        krpcPacket.setRequestId(requestId);
        krpcPacket.setContent(content);

        out.add(krpcPacket);
    }

}
