package org.amhzing.clusterview.controller.base;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.amhzing.clusterview.MediaTypes.APPLICATION_JSON_V1_VALUE;
import static org.amhzing.clusterview.controller.RestControllerPath.BASE_PATH;

@RestController
@RequestMapping(path = BASE_PATH + "/clusterview", produces = APPLICATION_JSON_V1_VALUE)
public abstract class AbstractRestController {

}
