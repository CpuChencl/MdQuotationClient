package ts;

import java.nio.ByteBuffer;

import org.springframework.util.StringUtils;

import com.lion.flatbuffers.FbEsTapAPIQuoteWhole;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 *
 * Created by louyuting on 16/12/1.
 *
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<Object>{

    private int counter=0;

    private static final String REQ = "MDQC:CL1804,W1805,QM1805,6B1806,6A1806,GC1804,C1805,BO1805,FDAX1803$_";

    /**
     * 当收到连接成功的通知,发送一条消息.
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

//        for(int i=0; i<10; i++){
//            ctx.writeAndFlush( Unpooled.copiedBuffer(REQ.getBytes()) );
//        }
    }

    /**
     * 每当收到数据时这个方法会被调用.打印收到的消息日志
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
//    	ByteBuf buf = (ByteBuf) msg;  
//        byte[] data = new byte[buf.readableBytes()];  
//        buf.readBytes(data);
    	String recive = (String) msg;
    	byte [] data = recive.getBytes();
    	ByteBuffer bb = ByteBuffer.wrap(data);
		System.out.println(data.length);
		FbEsTapAPIQuoteWhole marketData = FbEsTapAPIQuoteWhole.getRootAsFbEsTapAPIQuoteWhole(bb);
		String contructNo = marketData.Contract().Commodity().CommodityNo() + marketData.Contract().ContractNo1();
		System.out.println(data.length+"|C|futureCode:[{"+contructNo+"}],markettime:[{"+marketData.DateTimeStamp()+"}],qlprice:[{"+marketData.QLastPrice()+"}]");
		if (StringUtils.isEmpty(marketData.DateTimeStamp()) || marketData.QLastPrice() == 0) {//如果当前价为0则过滤到
			System.out.println("client received: " + "counter:" + (++counter) + "  msg:"+new String(data));
		}
    }

    /**
     * 异常发生时,记录错误日志,关闭channel
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();//打印堆栈的错误日志
        ctx.close();
    }
}
