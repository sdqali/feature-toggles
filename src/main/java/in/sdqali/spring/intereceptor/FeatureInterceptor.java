package in.sdqali.spring.intereceptor;

import in.sdqali.spring.feature.FeatureRepository;
import in.sdqali.spring.feature.FeatureToggle;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FeatureInterceptor implements HandlerInterceptor {
  private final FeatureRepository featureRepository;

  public FeatureInterceptor(FeatureRepository featureRepository) {
    this.featureRepository = featureRepository;
  }

  @Override
  public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
    HandlerMethod handlerMethod = (HandlerMethod) handler;
    FeatureToggle methodAnnotation = handlerMethod.getMethodAnnotation(FeatureToggle.class);
    if (methodAnnotation == null) {
      return true;
    }

    if(featureRepository.isOn(methodAnnotation.feature()) == null) {
      return true;
    }

    if(methodAnnotation.expectedToBeOn() == featureRepository.isOn(methodAnnotation.feature())) {
      return true;
    }

    httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
    return false;
  }

  @Override
  public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

  }

  @Override
  public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

  }
}
