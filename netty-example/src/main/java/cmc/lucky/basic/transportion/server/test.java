package cmc.lucky.basic.transportion.server;

import basic.server.impl.DefaultNettyServer;
import basic.server.netty.NettyRequestProcessor;
import basic.server.protocol.RemotingCommand;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.nio.charset.Charset;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:04 2017/10/26
 */
public class test {
    public static final String defaultCharset = "GB18030";

    public static void main(String[] args) {


        NettyRequestProcessor processor = new NettyRequestProcessor() {
            //执行具体的业务流程操作
            @Override
            public RemotingCommand processRequest(ChannelHandlerContext ctx, RemotingCommand request) throws Exception {
                String body = new String(request.getBody(), defaultCharset);
                System.out.println("接受到了你的请求了，内容为{}" + body);
                ChannelPromise channelpromise = new DefaultChannelPromise(ctx.channel());
                channelpromise.addListener(new GenericFutureListener<Future<? super Void>>() {
                    @Override
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        System.out.println("我完成了监听");
                    }
                });
                String haha = "哈哈，谢谢你";
                RemotingCommand response = RemotingCommand.createResponseCommand(haha.getBytes(Charset.forName("GB18030")));
                return response;
            }
        };
        DefaultNettyServer server = new DefaultNettyServer(8888, "192.168.1.59", processor);
        server.start();
    }
}
