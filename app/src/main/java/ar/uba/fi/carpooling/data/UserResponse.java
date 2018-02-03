package ar.uba.fi.carpooling.data;

import java.util.ArrayList;

public class UserResponse {
    private ArrayList<UserEntity> results;

    public ArrayList<UserEntity> getResults() {
        return results;
    }

    public void setResults(ArrayList<UserEntity> results) {
        this.results = results;
    }
}

