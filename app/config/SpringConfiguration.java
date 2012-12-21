package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Everybody loves annotation-based configuration. So, this is a really simple one.
 * It shows how you can use component scanning: for this example Spring
 * discovers the Application controller as a component and the PersonalizedHelloWorldService.
 * The normal HelloWorldService is being created by a bean method as you can see below.
 *
 */
@Configuration
@ComponentScan({"controllers", "services"})
public class SpringConfiguration {

}