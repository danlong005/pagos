package com.pagos.wallet.layouts;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

public class ModelAndViewManager {
    public static ModelAndView render(String viewName, Map<String, Object> properties) {
        properties.put("body", viewName + ".ftl");
        return new ModelAndView("layout", properties);
    }
    
    public static ModelAndView render(String viewName) {
        Map<String, Object> properties = new HashMap<>();
        return render(viewName, properties);
    }

    public static ModelAndView redirect(String url) {
        return new ModelAndView("redirect:" + url);
    }
}
