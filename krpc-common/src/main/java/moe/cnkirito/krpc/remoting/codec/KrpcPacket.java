package moe.cnkirito.krpc.remoting.codec;

/**
 * @author daofeng.xjf
 * @date 2019/1/10
 */
public class KrpcPacket {

    public static final int HEADER_LENGTH = 24;

    public static final int PROTOCOL_MESSAGE = 1;
    public static final int PROTOCOL_HEART = 2;

    public static final int REQUEST = 1;
    public static final int RESPONSE = 2;

    public static int DEFAULT_TIMEOUT = 3000;

    private int protocol;
    private int type;
    private int timeout = DEFAULT_TIMEOUT;
    private long requestId;
    private byte[] content;

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
