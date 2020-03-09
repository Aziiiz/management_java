package tom.management;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	
	@Autowired
	private AuthenticInterceptor authenticInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authenticInterceptor)
				.addPathPatterns(Arrays.asList(new String[]{
						"/page/**",
						"/api/**"}));
		
		WebMvcConfigurer.super.addInterceptors(registry);
	}
}
