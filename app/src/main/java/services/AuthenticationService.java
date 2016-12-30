package services;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface AuthenticationService {
    @FormUrlEncoded
    @POST("/")
    Observable<Response<ResponseBody>> login(@Field("username") String username, @Field("password") String password);
}

