package crypto_wallet.application.io;

import crypto_wallet.domain.data.WalletPerformanceReport;
import format.NumberFormatter;

public class PerformanceReportWriter {
    private final NumberFormatter numberFormatter;

    public PerformanceReportWriter(NumberFormatter numberFormatter) {
        this.numberFormatter = numberFormatter;
    }

    public void printReport(WalletPerformanceReport report) {
        System.out.printf(
                "total=%s,best_asset=%s,best_performance=%s,worst_asset=%s,worst_performance=%s\n",
                numberFormatter.formatMoney(report.total()),
                report.bestAsset(),
                numberFormatter.formatDouble(report.bestPerformance(), 2),
                report.worstAsset(),
                numberFormatter.formatDouble(report.worstPerformance(), 2)
        );
    }
}
