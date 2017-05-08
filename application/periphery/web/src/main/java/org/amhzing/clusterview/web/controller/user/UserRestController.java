package org.amhzing.clusterview.web.controller.user;

import org.amhzing.clusterview.adapter.web.UserAdapter;
import org.amhzing.clusterview.adapter.web.api.user.UsersDTO;
import org.amhzing.clusterview.core.domain.user.Page;
import org.amhzing.clusterview.core.domain.user.User;
import org.amhzing.clusterview.web.timing.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.amhzing.clusterview.web.MediaTypes.APPLICATION_JSON_V1_VALUE;
import static org.amhzing.clusterview.web.controller.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.web.controller.RestControllerPath.MANAGE_PATH;
import static org.amhzing.clusterview.web.controller.appnav.CommonLinks.USERS_PAGE_PREFIX;
import static org.amhzing.clusterview.web.controller.appnav.CommonLinks.homeLink;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = MANAGE_PATH + BASE_PATH + "/users", produces = APPLICATION_JSON_V1_VALUE)
public class UserRestController {

    private UserAdapter userAdapter;

    @Autowired
    public UserRestController(final UserAdapter userAdapter) {
        this.userAdapter = notNull(userAdapter);
    }

    @LogExecutionTime
    @GetMapping(path = "/page/{pageNumber}")
    public ResponseEntity<Resource<UsersDTO>> users(final @PathVariable int pageNumber) {
        isTrue(pageNumber > 0, "Pages start from 1 and above");

        final ControllerLinkBuilder selfLink = linkTo(methodOn(UserRestController.class).users(pageNumber));

        final Resource<UsersDTO> usersDto = new Resource<>(userAdapter.users(pageNumber));
        usersDto.add(selfLink.withSelfRel());
        usersDto.add(homeLink());
        usersDto.add(userLinks(userAdapter.pagedUsers(pageNumber), pageNumber));

        return ResponseEntity.ok(usersDto);
    }

    private List<Link> userLinks(final Page<User> users, final int currentPage) {

        final List<Link> userLinks = IntStream.rangeClosed(1, users.getTotalPages())
                                              .filter(page -> page != currentPage)
                                              .boxed()
                                              .map(page -> linkTo(methodOn(UserRestController.class).users(page)).withRel(USERS_PAGE_PREFIX + page))
                                              .collect(Collectors.toList());

        return userLinks;
    }
}
