package wo.it.resources;

import io.quarkus.runtime.annotations.StaticInitSafe;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@StaticInitSafe
@ConfigMapping(prefix = "wisegress")
public interface ApplicationConfig {

    @WithName("message")
    String message();

}
