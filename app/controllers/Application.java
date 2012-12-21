package controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import beans.Subscription;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import services.SubscriptionService;
import views.html.index;

@Component
public class Application extends Controller {
	
	@Autowired
	private SubscriptionService subscriptionService;
		
	public Result index() {
		return ok(index.render());
	}
	
	public WebSocket<String> subscribe(Integer eventId) throws IOException {
		Subscription subscription = subscriptionService.subscribe(eventId);
		return subscription.getWebSocket();
	}
	
	public Result unsubscribe(Integer eventId) throws IOException {
		subscriptionService.unsubscribe(eventId);
		return ok(index.render());
	}	
}