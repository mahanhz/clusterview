package org.amhzing.clusterview.controller.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.amhzing.clusterview.controller.RestControllerPath.BASE_PATH;


@Controller
@RequestMapping(path = BASE_PATH + "/clusteredit")
public abstract class AbstractEditRestController {

}
