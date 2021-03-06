package org.amhzing.clusterview.appui.web.controller.entry;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

import static org.amhzing.clusterview.web.controller.util.UserUtil.USER_COUNTRY;

@Controller
public class IndexController {

    @GetMapping(path = "/")
    public String index(final HttpServletRequest request) {

        final String userCountry = (String) request.getAttribute(USER_COUNTRY);

        return StringUtils.isNotBlank(userCountry) ? "redirect:/clusterview/" + userCountry : "redirect:/login";
    }
}
