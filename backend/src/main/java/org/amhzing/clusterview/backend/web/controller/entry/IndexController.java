package org.amhzing.clusterview.backend.web.controller.entry;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

import static org.amhzing.clusterview.backend.web.controller.appnav.CommonModelController.USER_COUNTRY;

@Controller
public class IndexController {

    @GetMapping(path = "/")
    public String index(final HttpSession httpSession) {

        final String userCountry = (String) httpSession.getAttribute(USER_COUNTRY);

        return StringUtils.isNotBlank(userCountry) ? "redirect:/clusterview/" + userCountry : "redirect:/login";
    }
}
