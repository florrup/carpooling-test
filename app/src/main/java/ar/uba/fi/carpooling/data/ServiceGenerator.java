package ar.uba.fi.carpooling.data;

public class ServiceGenerator {
    private static UserService userService;

    public static UserService getUserService() {
        if (userService == null) {
            userService = RetrofitInstance.getRetrofit().create(UserService.class);
        }
        return userService;
    }
}
