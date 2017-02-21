package org.amhzing.clusterview.backend.web.controller.base;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.amhzing.clusterview.backend.web.MediaType.APPLICATION_JSON_V1;

@RestController
@RequestMapping(path = "/backend/clusterview", produces = APPLICATION_JSON_V1)
public abstract class AbstractRestController {

}
