package com.lion.frame.mq;

import org.zeromq.ZMQ;

public abstract class _ZMQ {
	
	private MqConfig mqConfig;
	private ZMQ.Context context;
	protected ZMQ.Socket zmqSocket;
	
	public _ZMQ(MqConfig mqConfig)
    {
        this.mqConfig = mqConfig;
        context = ZMQ.context(1);
        if("PUB".equals(mqConfig.getModel())){
        	zmqSocket = context.socket(ZMQ.PUB);
        }
        else if("SUB".equals(mqConfig.getModel())){
    		zmqSocket = context.socket(ZMQ.SUB);
    		zmqSocket.subscribe("".getBytes());  
        }
        else if("REQ".equals(mqConfig.getModel())){
        	zmqSocket = context.socket(ZMQ.REQ);
        }
        else if("REP".equals(mqConfig.getModel())){
        	zmqSocket = context.socket(ZMQ.REP);
        }
        else if("PUSH".equals(mqConfig.getModel())){
        	zmqSocket = context.socket(ZMQ.PUSH);
        }
        else if("PULL".equals(mqConfig.getModel())){
        	zmqSocket = context.socket(ZMQ.PULL);
        }
        
        if ("CONNECT".equals(mqConfig.getMqType()))
        {
            zmqSocket.connect("tcp://" + mqConfig.getAddr() + ":" + mqConfig.getPort());
        }
        else
        {
            zmqSocket.bind("tcp://" + mqConfig.getAddr() + ":" + mqConfig.getPort());
        }

        init();
    }
	
	
	public abstract void init();
	
	public MqConfig getMqConfig(){
		return mqConfig;
	}
}