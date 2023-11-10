package wo.it.models;

import io.quarkus.runtime.annotations.RegisterForReflection;
import wo.it.core.enums.Feature;

@RegisterForReflection
public record Classification(Feature feature, Long points) {

}
