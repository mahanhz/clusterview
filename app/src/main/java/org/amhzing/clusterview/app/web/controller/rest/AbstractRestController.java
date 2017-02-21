package org.amhzing.clusterview.app.web.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.amhzing.clusterview.app.web.MediaType.APPLICATION_JSON_V1;

@RestController
@RequestMapping(path = "/backend/clusterview", produces = APPLICATION_JSON_V1)
public abstract class AbstractRestController {

}
