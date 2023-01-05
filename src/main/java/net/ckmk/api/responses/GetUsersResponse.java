package net.ckmk.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.ckmk.api.prototypes.SafeUser;
import net.ckmk.api.prototypes.User;

import java.util.ArrayList;

public class GetUsersResponse extends Response{
    @JsonProperty
    private final ArrayList<SafeUser> users;

    public GetUsersResponse (ArrayList<User> u){
        if (u != null){
            users = new ArrayList<>();
            for (User user : u){
                users.add(new SafeUser(user.getId(), user.getEmail(), user.isAdmin(), user.isAllowed(), user.getFullName(), user.getDbSpaceTaken()));
            }
        } else users = null;
    }

    public ArrayList<SafeUser> getUsers() {
        return users;
    }
}
