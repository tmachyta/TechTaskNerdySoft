package com.example.techtasknerdysoft.service.role;

import com.example.techtasknerdysoft.model.Role;
import com.example.techtasknerdysoft.model.Role.RoleName;

public interface RoleService {
    Role getRoleByRoleName(RoleName roleName);
}
