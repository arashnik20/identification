package common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:global_fa.properties")
@Data
public class AppProperties {

    @Value("${app.test:123}")
    private String test;

}
