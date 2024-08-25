package io.github.kamarias.bean;

import java.io.Serializable;
import java.util.Set;

public interface AuthService extends Serializable {


    void setId(String id);

    void setPermissions(Set<String> permissions);

    void setRoles(Set<String> roles);


}
