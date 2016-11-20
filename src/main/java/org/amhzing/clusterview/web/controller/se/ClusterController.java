package org.amhzing.clusterview.web.controller.se;

import org.amhzing.clusterview.web.adapter.ClusterAdapter;
import org.amhzing.clusterview.web.controller.AbstractController;
import org.amhzing.clusterview.web.model.Cluster;
import org.amhzing.clusterview.web.model.GroupModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Set;

import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class ClusterController extends AbstractController {

    final ClusterAdapter clusterAdapter;

    @Autowired
    public ClusterController(final ClusterAdapter clusterAdapter) {
        this.clusterAdapter = notNull(clusterAdapter);
    }

    @GetMapping(path = "/{country}/{region}/{cluster}")
    public ModelAndView cluster(@ModelAttribute @Valid final Cluster cluster,
                                final BindingResult bindingResult,
                                final Model model) {

        final Set<GroupModel> groups = clusterAdapter.groups(cluster.getCluster());
        model.addAttribute("groups", groups);

        return new ModelAndView(cluster.getCountry() + "/" + cluster.getRegion() + "/" + cluster.getCluster());
    }
}
