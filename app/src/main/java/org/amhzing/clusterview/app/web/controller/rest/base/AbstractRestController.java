package org.amhzing.clusterview.app.web.controller.rest.base;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.amhzing.clusterview.app.web.MediaType.APPLICATION_JSON_V1;
import static org.amhzing.clusterview.app.web.controller.rest.RestControllerPath.BASE_PATH;

@RestController
@RequestMapping(path = BASE_PATH + "/clusterview", produces = APPLICATION_JSON_V1)
public abstract class AbstractRestController {

}
