package com.postgres.demopg.factory;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.postgres.demopg.models.ERole;
import com.postgres.demopg.models.Role;
import com.postgres.demopg.repository.RoleRepository;

/**
 * Factory Method implementation for turning role aliases into persisted Role entities.
 */
@Component
public class DefaultRoleAssignmentFactory implements RoleAssignmentFactory {

    private final RoleRepository roleRepository;

    public DefaultRoleAssignmentFactory(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Set<Role> resolveRoles(Set<String> requestedRoles) {
        Set<Role> resolvedRoles = new HashSet<>();

        if (requestedRoles == null || requestedRoles.isEmpty()) {
            resolvedRoles.add(findRole(ERole.ROLE_USER));
            return resolvedRoles;
        }

        requestedRoles.forEach(role -> resolvedRoles.add(resolveSingleRole(role)));
        return resolvedRoles;
    }

    private Role resolveSingleRole(String role) {
        if (role == null) {
            return findRole(ERole.ROLE_USER);
        }

        String normalized = role.trim().toLowerCase(Locale.ROOT);
        return switch (normalized) {
            case "admin" -> findRole(ERole.ROLE_ADMIN);
            case "mod", "moderator" -> findRole(ERole.ROLE_MODERATOR);
            default -> findRole(ERole.ROLE_USER);
        };
    }

    private Role findRole(ERole roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    }
}
