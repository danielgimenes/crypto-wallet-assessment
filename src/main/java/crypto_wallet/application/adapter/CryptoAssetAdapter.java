package crypto_wallet.application.adapter;

import crypto_wallet.domain.data.CryptoAsset;
import currency.MoneyFormatter;

import java.text.ParseException;

public class CryptoAssetAdapter {
    private final MoneyFormatter moneyFormatter;

    public CryptoAssetAdapter(MoneyFormatter moneyFormatter) {
        this.moneyFormatter = moneyFormatter;
    }

    public CryptoAsset toModel(String symbol, String quantity, String price) throws ParseException {
        return new CryptoAsset(symbol, Double.parseDouble(quantity), moneyFormatter.parse(price));
    }
}
