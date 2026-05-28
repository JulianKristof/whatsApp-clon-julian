package com.postgres.demopg.factory;

import java.util.Set;

import com.postgres.demopg.models.Role;

/**
 * Factory abstraction for resolving user roles from signup request payload.
 */
public interface RoleAssignmentFactory {
    Set<Role> resolveRoles(Set<String> requestedRoles);
}
