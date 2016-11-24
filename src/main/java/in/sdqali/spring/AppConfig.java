package in.sdqali.spring;

import in.sdqali.spring.feature.FeatureRepository;
import in.sdqali.spring.feature.FeatureToggle;
import in.sdqali.spring.intereceptor.FeatureInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {
  @Autowired
  Environment env;

  @Bean
  @FeatureToggle(feature = "feature.redis.session.store", expectedToBeOn = false)
  public SessionRepository mapSessionRepository() {
    return new MapSessionRepository();
  }

  @Bean
  @FeatureToggle(feature = "feature.redis.session.store")
  public SessionRepository redisSessionRepository(RedisConnectionFactory factory) {
    return new RedisOperationsSessionRepository(factory);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new FeatureInterceptor(new FeatureRepository(env)));
    super.addInterceptors(registry);
  }
}
