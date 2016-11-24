package in.sdqali.spring.feature;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

public class FeatureCondition implements Condition {
  @Override
  public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
    if(annotatedTypeMetadata.isAnnotated(FeatureToggle.class.getCanonicalName())) {
      Map<String, Object> annotationAttributes = annotatedTypeMetadata
          .getAnnotationAttributes(FeatureToggle.class.getCanonicalName());
      String feature = (String) annotationAttributes.get("feature");
      boolean expectedToBeOn = Boolean.parseBoolean(String.valueOf(annotationAttributes.get("expectedToBeOn")));
      boolean isOn = Boolean.parseBoolean(conditionContext.getEnvironment().getProperty(feature));
      return expectedToBeOn == isOn;
    }
    return true;
  }
}
