package com.p2c.p2c.seed;

import com.p2c.p2c.Utils.ConfigUtility;
import com.p2c.p2c.constants.Gender;
import com.p2c.p2c.model.Child;
import com.p2c.p2c.model.Parent;
import com.p2c.p2c.model.Role;
import com.p2c.p2c.repository.ChildRepository;
import com.p2c.p2c.repository.ParentRepository;
import com.p2c.p2c.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner
{

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private ChildRepository childRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ConfigUtility configUtility;


    @Override
    public void run(String... args) throws Exception
    {

        if (!configUtility.getProperty("spring.jpa.hibernate.ddl-auto").equalsIgnoreCase("create")) {
            return;
        }

        Role admin = Role.builder()
            .id(1)
            .role(com.p2c.p2c.constants.Role.ADMIN.name())
            .build();

        Role registeredUser = Role.builder()
            .id(2)
            .role(com.p2c.p2c.constants.Role.REGISTERED_USER.name())
            .build();

        Role siteUser = Role.builder()
            .id(3)
            .role(com.p2c.p2c.constants.Role.SITE_USER.name())
            .build();

        List<Role> roleList = new ArrayList<>();
        roleList.add(admin);
        roleList.add(registeredUser);
        roleList.add(siteUser);

        roleList.forEach(role -> roleRepository.save(role));
        //  this.parentRepository.deleteAll();
        Parent p1 = buildParent("Tom", "Cruise", "password", "tom@gmail.com", 1, admin);
        Parent p2 = buildParent("Tom1", "Cruise1", "password", "tom1@gmail.com", 1, admin);
        Parent p3 = buildParent("Tom2", "Cruise2", "password", "tom2@gmail.com", 1, registeredUser);

        buildChild("Tomd", "Cruise", new Date(), Gender.FEMALE, p1);
        buildChild("Tom1d", "Cruise", new Date(), Gender.FEMALE, p2);
        buildChild("Tom2d", "Cruise", new Date(), Gender.MALE, p3);

    }

    private Parent buildParent(String firstName, String lastName, String password, String email,
        int isActive, Role role)
    {
        Parent parent = new Parent();
        parent.setFirstName(firstName);
        parent.setLastName(lastName);
        parent.setUsername(email);
        parent.setPassword(this.passwordEncoder.encode(password));
        parent.setEmail(email);
        parent.setIsActive(isActive);
        parent.setRole(role);
        parent.setCreated(new Date());
        parent.setLastUpdated(new Date());
        parentRepository.save(parent);
        return parent;

    }

    private Child buildChild(String firstName, String lastName, Date dob, Gender gender, Parent parent)
    {
        Child child = new Child();
        child.setFirstName(firstName);
        child.setLastName(lastName);
        child.setGender(gender);
        child.setDob(dob);
        child.setCreated(new Date());
        child.setLastUpdated(new Date());
        child.setParent(parent);
        childRepository.save(child);
        return child;
    }

}
