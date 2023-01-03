package net.ckmk.api.requests;

import java.util.ArrayList;

public class GetUsersRequest extends RequestValidator{
    private ArrayList<Integer> userIds;

    public ArrayList<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(ArrayList<Integer> userIds) {
        this.userIds = userIds;
    }
}
