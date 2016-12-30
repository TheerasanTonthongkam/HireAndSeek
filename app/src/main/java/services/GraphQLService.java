package services;

import model.Data;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface GraphQLService {
    @GET("/gql")
    Observable<ResponseBody> getAlbums(@Query("query") String query);
}
