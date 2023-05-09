package crypto_wallet.application.io;

import crypto_wallet.application.ConsoleApp;
import crypto_wallet.domain.data.WalletPerformanceReport;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static test_utils.Formatters.defaultNumberFormatter;

class PerformanceReportWriterTest {

    @Test
    public void printReport() {
        WalletPerformanceReport report = new WalletPerformanceReport(
                new BigDecimal("16984.62"),
                "BTC",
                1.51,
                "ETH",
                1.01
        );

        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        new PerformanceReportWriter(defaultNumberFormatter).printReport(report);

        String expectedPrint = "total=16984.62,best_asset=BTC,best_performance=1.51,worst_asset=ETH,worst_performance=1.01\n";
        assertEquals(expectedPrint, output.toString());
    }

    @Test
    public void printReportWithCorrectFormatAndRoundingUp() {
        WalletPerformanceReport report = new WalletPerformanceReport(
                new BigDecimal("16984.615444444444"),
                "BTC",
                1.50533333333,
                "ETH",
                1.00533333333
        );

        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        new PerformanceReportWriter(defaultNumberFormatter).printReport(report);

        String expectedPrint = "total=16984.62,best_asset=BTC,best_performance=1.51,worst_asset=ETH,worst_performance=1.01\n";
        assertEquals(expectedPrint, output.toString());
    }
}