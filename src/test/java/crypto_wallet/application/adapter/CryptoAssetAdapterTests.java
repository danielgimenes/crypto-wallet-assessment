package crypto_wallet.application.adapter;

import crypto_wallet.domain.WalletPerformance;
import crypto_wallet.domain.data.CryptoAsset;
import org.junit.jupiter.api.Test;
import test_utils.Formatters;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;
import static test_utils.Formatters.defaultNumberFormatter;

class CryptoAssetAdapterTests {

    @Test
    public void toModel() throws ParseException {
        String symbol = "BTC";
        String quantity = "0.12345";
        String price = "21345.21345";
        CryptoAssetAdapter adapter = new CryptoAssetAdapter(defaultNumberFormatter);

        BigDecimal priceBigDec = new BigDecimal("21345.21345").setScale(2, RoundingMode.HALF_UP);
        CryptoAsset expected = new CryptoAsset("BTC", 0.12345, priceBigDec);

        assertEquals(expected, adapter.toModel(symbol, quantity, price));
    }

    @Test
    public void toModelWithRounding() throws ParseException {
        String symbol = "BTC";
        String quantity = "0.12345";
        String price = "21345.213455";
        CryptoAssetAdapter adapter = new CryptoAssetAdapter(defaultNumberFormatter);

        BigDecimal priceBigDec = new BigDecimal("21345.21346").setScale(2, RoundingMode.HALF_UP);
        CryptoAsset expected = new CryptoAsset("BTC", 0.12345, priceBigDec);

        assertEquals(expected, adapter.toModel(symbol, quantity, price));
    }

    @Test
    public void toModelWhenInvalidString() {
        String symbol = "BTC";
        String quantity = "ABC123";
        String price = "eita";
        CryptoAssetAdapter adapter = new CryptoAssetAdapter(defaultNumberFormatter);

        assertThrows(RuntimeException.class,
                () -> adapter.toModel(symbol, quantity, price));
    }

}