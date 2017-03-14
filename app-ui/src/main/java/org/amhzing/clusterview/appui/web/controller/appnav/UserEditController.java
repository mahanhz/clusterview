package org.amhzing.clusterview.appui.web.controller.appnav;

import org.amhzing.clusterview.app.annotation.LogExecutionTime;
import org.amhzing.clusterview.app.web.adapter.UserAdapter;
import org.amhzing.clusterview.app.web.model.user.ChangePasswordModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static org.apache.commons.lang3.Validate.notNull;

@Controller
@RequestMapping(path = "/useredit")
public class UserEditController {

    public static final String CHANGE_PASSWORD_PATH = "/changepassword";

    private UserAdapter userAdapter;

    @Autowired
    public UserEditController(final UserAdapter userAdapter) {
        this.userAdapter = notNull(userAdapter);
    }

    @ModelAttribute
    public ChangePasswordModel changePasswordModel() {
        return new ChangePasswordModel();
    }

    @LogExecutionTime
    @GetMapping(path = CHANGE_PASSWORD_PATH)
    public String changePassword() {
        return changePasswordView();
    }

    @LogExecutionTime
    @GetMapping(path = "/passwordchanged")
    public String success() {
        return passwordChangedView();
    }

    @LogExecutionTime
    @PutMapping(path = CHANGE_PASSWORD_PATH)
    public String changePassword(@ModelAttribute @Valid final ChangePasswordModel changePasswordModel,
                                 final BindingResult bindingResult,
                                 final RedirectAttributes redirectAttributes) {

        if (passwordsDoNotMatch(changePasswordModel)) {
            final ObjectError error = new ObjectError("confirmNewPassword", "Confirm new password does not match");
            bindingResult.addError(error);
        }

        if (bindingResult.hasErrors()) {
            return changePasswordView();
        }

        userAdapter.changePassword(changePasswordModel.getNewPassword());

        redirectAttributes.addFlashAttribute("changePasswordResponse", "Your password has been successfully changed");

        return redirectToConfirmation();
    }

    private boolean passwordsDoNotMatch(final ChangePasswordModel changePasswordModel) {
        return !changePasswordModel.getNewPassword().equals(changePasswordModel.getConfirmNewPassword());
    }

    private String redirectToConfirmation() {
        return "redirect:/useredit/passwordchanged";
    }

    private String passwordChangedView() {
        return "/password-changed";
    }

    private String changePasswordView() {
        return "/change-password";
    }
}
