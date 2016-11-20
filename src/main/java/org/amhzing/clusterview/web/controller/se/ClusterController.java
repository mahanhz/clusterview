package org.amhzing.clusterview.web.controller.se;

import org.amhzing.clusterview.web.controller.AbstractController;
import org.amhzing.clusterview.web.model.Cluster;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class ClusterController extends AbstractController {

    @GetMapping(path = "/{country}/{region}/{cluster}")
    public ModelAndView cluster(@ModelAttribute @Valid final Cluster cluster,
                                final BindingResult bindingResult) {
        return new ModelAndView(cluster.getCountry() + "/" + cluster.getRegion() + "/" + cluster.getCluster());
    }
}
