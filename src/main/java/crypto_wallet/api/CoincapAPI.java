package crypto_wallet.api;

import crypto_wallet.domain.AssetPriceAPI;
import format.NumberFormatter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.SECONDS;

public class CoincapAPI implements AssetPriceAPI {

    private final HttpClient httpClient;

    private final NumberFormatter numberFormatter;

    private static final int PARALLELISM = 3;

    private final ForkJoinPool threadPool = new ForkJoinPool(PARALLELISM);

    private static final String SEARCH_ASSET_BASE_URL = "https://api.coincap.io/v2/assets?search=%s&limit=1";
    private static final String ASSET_HISTORY_BASE_URL = "https://api.coincap.io/v2/assets/%s/history?interval=d1&start=1617753600000&end=1617753601000";
    private static final Duration REQUEST_TIMEOUT = Duration.of(10, SECONDS);

    public CoincapAPI(NumberFormatter numberFormatter) {
        this.httpClient = HttpClient.newHttpClient();
        this.numberFormatter = numberFormatter;
    }

    public CoincapAPI(HttpClient httpClient, NumberFormatter numberFormatter) {
        this.httpClient = httpClient;
        this.numberFormatter = numberFormatter;
    }

    @Override
    public Map<String, BigDecimal> fetchPrices(List<String> symbols) {
        try {
            return threadPool.submit(() -> symbols.parallelStream().collect(
                    Collectors.toConcurrentMap(Function.identity(), this::fetchAssetPrice))).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private BigDecimal fetchAssetPrice(String symbol) {
        try {
            String assetId = fetchAssetId(symbol);
            String priceRaw = fetchPrice(assetId);

            // formatters aren't thread-safe! let's clone it before parsing
            NumberFormatter formatter = numberFormatter.clone();
            return formatter.parseMoney(priceRaw);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private String fetchAssetId(String symbol) throws IOException, InterruptedException, URISyntaxException {
            HttpResponse<String> response = coinbaseAPIJsonGET(String.format(SEARCH_ASSET_BASE_URL, symbol));
            if (response.statusCode() != 200) {
                throw new RuntimeException();
            }
            JSONArray resultArray = new JSONObject(response.body()).getJSONArray("data");
            JSONObject assetJsonObject = resultArray.getJSONObject(0); // results are limited to 1 by query param
            return assetJsonObject.getString("id");
    }

    private String fetchPrice(String assetId) throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> response = coinbaseAPIJsonGET(String.format(ASSET_HISTORY_BASE_URL, assetId));
        if (response.statusCode() != 200) {
            throw new RuntimeException();
        }
        JSONArray resultArray = new JSONObject(response.body()).getJSONArray("data");
        JSONObject assetJsonObject = resultArray.getJSONObject(0);
        return assetJsonObject.getString("priceUsd");
    }

    private HttpResponse<String> coinbaseAPIJsonGET(String url) throws IOException, InterruptedException, URISyntaxException {
        URI searchAssetURI = new URI(url);
        HttpRequest request = HttpRequest.newBuilder(searchAssetURI)
                .timeout(REQUEST_TIMEOUT)
                .header("accept", "application/json")
                .GET()
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

}
