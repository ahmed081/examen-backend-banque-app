package org.glsid.AuthenticationService.service;

import org.glsid.AuthenticationService.entities.AppRole;
import org.glsid.AuthenticationService.entities.AppUser;

import java.util.Collection;

public interface AccountService {
    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);
    void addRoleToUser(String userName, String roleName);
    AppUser loadUserByUserName(String userName);
    Collection<AppUser> listUsers();
}
