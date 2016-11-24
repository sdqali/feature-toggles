package in.sdqali.spring.web;

import in.sdqali.spring.feature.FeatureToggle;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/foo")
@FeatureToggle(feature = "feature.foo")
public class FooController {
  @RequestMapping("")
  public Map hello() {
    return Collections.singletonMap("message", "hello foo!");
  }
}
