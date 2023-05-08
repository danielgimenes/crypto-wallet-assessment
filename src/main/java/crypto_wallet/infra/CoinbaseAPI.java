package crypto_wallet.infra;

import crypto_wallet.domain.AssetPriceAPI;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CoinbaseAPI implements AssetPriceAPI {

    private final Logger logger = Logger.getLogger(CoinbaseAPI.class.getName());

    private final ForkJoinPool threadPool = new ForkJoinPool(3);

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

    Map<String, BigDecimal> fakePrices = Map.of(
            "BTC", new BigDecimal("10.00"),
            "ETH", new BigDecimal("20.00"),
            "USDT", new BigDecimal("30.00"),
            "ADA", new BigDecimal("40.00"),
            "XRP", new BigDecimal("50.00"),
            "DOGE", new BigDecimal("42.00")
    );

    private BigDecimal fetchAssetPrice(String symbol) {
        logger.info(String.format("[%s, start] fetching price of '%s'", Thread.currentThread().getName(), symbol));
        long start = System.currentTimeMillis();
        try {
            Thread.sleep((int) (2000.0*Math.random()));
        } catch (InterruptedException ignored) {}
        BigDecimal price = fakePrices.get(symbol);
        long elapsed = System.currentTimeMillis() - start;
        logger.info(String.format("[%s, start] got '%s' price of %s after %d ms", Thread.currentThread().getName(), symbol, price.toString(), elapsed));
        return price;
    }
}
