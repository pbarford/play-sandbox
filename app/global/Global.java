package global;

import config.RabbitMqConfiguration;
import config.SpringConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import play.Application;
import play.GlobalSettings;

/**
 * In the global object we are bootstraping the application context
 * and make it available through a delegating method.
 */
public class Global extends GlobalSettings {

    private static ApplicationContext applicationContext;

    @Override
    public void onStart(Application application) {
        applicationContext = new AnnotationConfigApplicationContext(SpringConfiguration.class, RabbitMqConfiguration.class);
    }

    public static <T> T getBean(Class<T> beanClass) {
        if (applicationContext == null) {
            throw new IllegalStateException("application context is not initialized");
        }
        return applicationContext.getBean(beanClass);
    }
}