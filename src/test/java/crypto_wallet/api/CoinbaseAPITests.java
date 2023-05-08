package crypto_wallet.api;

import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.hamcrest.MockitoHamcrest;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CoinbaseAPITests {

    @Test
    public void fetchPricesWhenSingleAsset() throws IOException, InterruptedException {
        List<String> symbols = List.of("DOGE");
        HttpClient mockHttpClient = mock(HttpClient.class);

        // DOGE
        mockHttpResponse(
                mockHttpClient,
                url -> url.startsWith("https://api.coincap.io/v2/assets?search=DOGE"),
                "{\"data\": [{\"id\": \"dogecoin\"}]}"
        );
        mockHttpResponse(
                mockHttpClient,
                url -> url.contains("dogecoin/history"),
                "{\"data\": [{\"priceUsd\": \"2222222.00\"}]}"
        );

        CoinbaseAPI api = new CoinbaseAPI(mockHttpClient);

        Map<String, BigDecimal> expected = Map.of(
                "DOGE", new BigDecimal("2222222.00")
        );
        assertEquals(expected, api.fetchPrices(symbols));
    }

    @Test
    public void fetchPricesWhenMultipleAssets() throws IOException, InterruptedException {
        List<String> symbols = List.of("BTC", "ETH");
        HttpClient mockHttpClient = mock(HttpClient.class);

        // BTC
        mockHttpResponse(
                mockHttpClient,
                url -> url.startsWith("https://api.coincap.io/v2/assets?search=BTC"),
                "{\"data\": [{\"id\": \"bitcoin\"}]}"
        );
        mockHttpResponse(
                mockHttpClient,
                url -> url.contains("bitcoin/history"),
                "{\"data\": [{\"priceUsd\": \"10.00\"}]}"
        );

        // ETH
        mockHttpResponse(
                mockHttpClient,
                url -> url.startsWith("https://api.coincap.io/v2/assets?search=ETH"),
                "{\"data\": [{\"id\": \"ethereum\"}]}"
        );
        mockHttpResponse(
                mockHttpClient,
                url -> url.contains("ethereum/history"),
                "{\"data\": [{\"priceUsd\": \"20.00\"}]}"
        );

        CoinbaseAPI api = new CoinbaseAPI(mockHttpClient);

        Map<String, BigDecimal> expected = Map.of(
                "BTC", new BigDecimal("10.00"),
                "ETH", new BigDecimal("20.00")
        );
        assertEquals(expected, api.fetchPrices(symbols));
    }

    private void mockHttpResponse(HttpClient mockHttpClient, Function<String, Boolean> urlMatcher, String responseBody) throws IOException, InterruptedException {
        Matcher<HttpRequest> reqArgMatcher = new CustomTypeSafeMatcher<>("") {
            @Override
            protected boolean matchesSafely(HttpRequest req) {
                return urlMatcher.apply(req.uri().toString());
            }
        };
        HttpResponse<Object> mockSearchAssetResponse = mock(HttpResponse.class);
        when(mockSearchAssetResponse.body()).thenReturn(responseBody);
        when(mockSearchAssetResponse.statusCode()).thenReturn(200);
        when(mockHttpClient.send(MockitoHamcrest.argThat(reqArgMatcher), Mockito.any())).thenReturn(mockSearchAssetResponse);
    }

    @Test
    public void fetchPricesWhenEmptyAssets() {
        CoinbaseAPI api = new CoinbaseAPI();
        assertTrue(api.fetchPrices(List.of()).isEmpty());
    }
}
