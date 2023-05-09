package test_utils;

import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;
import org.mockito.Mockito;
import org.mockito.hamcrest.MockitoHamcrest;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Function;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class HttpMock {

    private HttpMock() {}

    public static void mockHttpResponse(HttpClient mockHttpClient, Function<String, Boolean> urlMatcher, String responseBody) throws IOException, InterruptedException {
        Matcher<HttpRequest> reqArgMatcher = new CustomTypeSafeMatcher<>("") {
            @Override
            protected boolean matchesSafely(HttpRequest req) {
                return urlMatcher.apply(req.uri().toString());
            }
        };
        @SuppressWarnings("unchecked")
        HttpResponse<Object> mockSearchAssetResponse = mock(HttpResponse.class);
        when(mockSearchAssetResponse.body()).thenReturn(responseBody);
        when(mockSearchAssetResponse.statusCode()).thenReturn(200);
        when(mockHttpClient.send(MockitoHamcrest.argThat(reqArgMatcher), Mockito.any())).thenReturn(mockSearchAssetResponse);
    }
}
