package beans;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

import play.Logger;
import play.libs.F.Callback;
import play.mvc.WebSocket;

public class Subscription implements MessageListener {
	
	private final SimpleMessageListenerContainer container;
	private WebSocket<String> socket;
	private WebSocket.Out<String> socketOut;
		
	public Subscription(SimpleMessageListenerContainer container) throws IOException {
		this.container = container;
        		
        System.out.println("Subscription:init:WebSocket");
		socket = new WebSocket<String>() {
			public void onReady(play.mvc.WebSocket.In<String> in, play.mvc.WebSocket.Out<String> out) {
				System.out.println("Subscription:WebSocket:onReady");
				socketOut = out;
				in.onMessage(new Callback<String>() {
					public void invoke(String event) {
						Logger.info(event);
					}
				});
			}
		};
	}
	
	@Override
	public void onMessage(Message msg) {
		System.out.println("Subscription:onMessage --> " + msg);
		socketOut.write(new String(msg.getBody()));
	}
	
	public WebSocket<String> getWebSocket() {
		return socket;
	}
	
	public void start() {
		System.out.println("Subscription:start");
		container.setMessageListener(this);
		container.start();
	}
	
	public void shutdown() throws IOException {
		System.out.println("Subscription:shutdown");
		container.shutdown();
		socketOut.close();
	}


}
