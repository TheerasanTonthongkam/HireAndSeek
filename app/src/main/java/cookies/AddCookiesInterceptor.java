package cookies;


import java.io.IOException;
import java.net.HttpCookie;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookiesInterceptor implements Interceptor {

    private HttpCookie cookie;

    public AddCookiesInterceptor(HttpCookie cookie) {
        this.cookie = cookie;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        builder.addHeader("Cookie", cookie.toString());
        return chain.proceed(builder.build());
    }
}

