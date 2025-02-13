import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadTimeWeaverConfig {

    @Bean
    public LoadTimeWeaver loadTimeWeaver() {
        return new InstrumentationLoadTimeWeaver();
    }
}
