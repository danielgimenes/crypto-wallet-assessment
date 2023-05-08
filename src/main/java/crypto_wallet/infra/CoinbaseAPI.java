package crypto_wallet.infra;

import crypto_wallet.domain.PriceAPI;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CoinbaseAPI implements PriceAPI {
    @Override
    public Map<String, BigDecimal> fetchPrices(List<String> symbols) {
        return symbols.stream().collect(
                Collectors.toMap(Function.identity(), this::fetchAssetPrice));
    }

    private BigDecimal fetchAssetPrice(String symbol) {
        return new BigDecimal("10.00");
    }
}
