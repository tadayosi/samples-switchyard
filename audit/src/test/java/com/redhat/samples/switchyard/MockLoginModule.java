package com.redhat.samples.switchyard;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.switchyard.security.login.SwitchYardLoginModule;
import org.switchyard.security.principal.GroupPrincipal;
import org.switchyard.security.principal.RolePrincipal;
import org.switchyard.security.principal.UserPrincipal;

public class MockLoginModule extends SwitchYardLoginModule {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockLoginModule.class);

    private static final String USERNAME = "kermit";
    private static final String PASSWORD = "the-frog-1";

    @Override
    public boolean login() throws LoginException {
        NameCallback name = new NameCallback("Username");
        PasswordCallback password = new PasswordCallback("Password", false);
        try {
            getCallbackHandler().handle(new Callback[] { name, password });
        } catch (IOException | UnsupportedCallbackException e) {
            LOGGER.error(e.getMessage(), e);
            throw new LoginException(e.getMessage());
        }

        if (!USERNAME.equals(name.getName()) || !PASSWORD.equals(String.valueOf(password.getPassword()))) return false;

        UserPrincipal user = new UserPrincipal(name.getName());
        getSubject().getPrincipals().add(user);

        GroupPrincipal roles = new GroupPrincipal(GroupPrincipal.ROLES);
        roles.addMember(new RolePrincipal("friend"));
        getSubject().getPrincipals().add(roles);

        LOGGER.info("{}", getSubject());

        return true;
    }
}
