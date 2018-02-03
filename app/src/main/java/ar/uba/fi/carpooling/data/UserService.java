package ar.uba.fi.carpooling.data;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {
    @GET("pokemon")
    Call<UserResponse> getUsers();
}
