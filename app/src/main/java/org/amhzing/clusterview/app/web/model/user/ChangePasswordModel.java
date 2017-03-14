package org.amhzing.clusterview.app.web.model.user;

import javax.validation.constraints.Size;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;

public class ChangePasswordModel {

    public static final String MESSAGE = "Password length must be min 8 and max 15";

    @Size(min = 8, max = 15, message = "New " + MESSAGE)
    private String newPassword;

    @Size(min = 8, max = 15, message = "Confirm New" + MESSAGE)
    private String confirmNewPassword;

    public ChangePasswordModel() {
    }

    private ChangePasswordModel(final String newPassword, final String confirmNewPassword) {
        this.newPassword = notBlank(newPassword);
        this.confirmNewPassword = notBlank(confirmNewPassword);
    }

    public static ChangePasswordModel create(final String newPassword, final String confirmNewPassword) {
        return new ChangePasswordModel(newPassword, confirmNewPassword);
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(final String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ChangePasswordModel)) return false;
        final ChangePasswordModel that = (ChangePasswordModel) o;
        return Objects.equals(newPassword, that.newPassword) &&
                Objects.equals(confirmNewPassword, that.confirmNewPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newPassword, confirmNewPassword);
    }

    @Override
    public String toString() {
        return "ChangePasswordModel{" +
                "newPassword='" + newPassword + '\'' +
                ", confirmNewPassword='" + confirmNewPassword + '\'' +
                '}';
    }
}
