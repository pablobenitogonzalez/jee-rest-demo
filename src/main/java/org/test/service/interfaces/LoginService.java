package org.test.service.interfaces;

import org.test.model.Login;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.security.NoSuchAlgorithmException;

@SuppressWarnings("unused")
public interface LoginService {
    public Login getLogin(@NotNull @Min(value = 1) Long id);
    public Login createLogin(@NotNull @Valid Login login);
    public Login updateLogin(@NotNull @Valid Login login);
    public void deleteLogin(@NotNull @Min(value = 1) Long id);
    public Login findLoginByEmailAndPassword(@NotNull String email, @NotNull String password) throws NoSuchAlgorithmException;
}
