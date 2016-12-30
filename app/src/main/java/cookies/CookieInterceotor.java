package cookies;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class CookieInterceotor implements Interceptor {

    private String header;

    public CookieInterceotor(String header) {
        this.header = header;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        chain.request().headers(this.header);

        return null;
    }
}
