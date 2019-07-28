package com.p2c.p2c.constants;

public class RoleBuilder
{

    public static com.p2c.p2c.model.Role registeredUser;
    public static com.p2c.p2c.model.Role admin;
    public static com.p2c.p2c.model.Role siteUser;

    static {
        admin = com.p2c.p2c.model.Role.builder()
            .id(1)
            .role(Role.ADMIN.name())
            .build();

        registeredUser = com.p2c.p2c.model.Role.builder()
            .id(2)
            .role(com.p2c.p2c.constants.Role.REGISTERED_USER.name())
            .build();

        siteUser = com.p2c.p2c.model.Role.builder()
            .id(3)
            .role(Role.SITE_USER.name())
            .build();

    }

}
