package crypto_wallet.application.io;

import crypto_wallet.application.adapter.CryptoAssetAdapter;
import crypto_wallet.domain.data.CryptoAsset;
import format.NumberFormatter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class WalletReader {

    private final CryptoAssetAdapter adapter;

    public WalletReader(NumberFormatter numberFormatter) {
        this.adapter = new CryptoAssetAdapter(numberFormatter);
    }

    public List<CryptoAsset> readAssetsFromCSV(String csvFilepath) throws FileNotFoundException {
        return fileLinesAsStream(csvFilepath)
                .filter(this::isNotTheHeader)
                .map(line -> {
                    List<String> values = Arrays.asList(line.split(","));
                    return adapter.toModel(values.get(0), values.get(1), values.get(2));
                })
                .toList();
    }

    private boolean isNotTheHeader(String line) {
        return !line.startsWith("symbol,");
    }

    private Stream<String> fileLinesAsStream(String filepath) throws FileNotFoundException {
        InputStream stream = new FileInputStream(filepath);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream, StandardCharsets.UTF_8));
        return reader.lines();
    }
}
