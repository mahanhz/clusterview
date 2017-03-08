package org.amhzing.clusterview.app.web.controller.rest.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.amhzing.clusterview.app.web.controller.rest.RestControllerPath.BASE_PATH;

@Controller
@RequestMapping(path = BASE_PATH + "/clusteredit")
public abstract class AbstractEditRestController {

}
