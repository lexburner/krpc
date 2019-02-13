package moe.cnkirito.krpc.demo;

import moe.cnkirito.krpc.remoting.NettyServer;

/**
 * @author daofeng.xjf
 * @date 2019/1/10
 */
public class KrpcServerApp {

    public static void main(String[] args) {
        new NettyServer(9898).start();
    }

}
