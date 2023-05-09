# crypto-wallet

Given a CSV file of crypto assets in a wallet, this program fetches the current price of those assets and generate a report with total updated value, and best/worst performing assets with their percent of variation.

Sample input:
```csv
symbol,quantity,price
BTC,0.12345,37870.5058
ETH,4.89532,2004.9774
DOGE,32344.2222,0.3000
ADA,2000.99999,2.5000
USDT,4.999,0.59234
XRP,333.222,0.40999
```

Sample output:
```text
total=21694.72,best_asset=XRP,best_performance=2.37,worst_asset=DOGE,worst_performance=0.20
```

This project is part of a take-home assessment for a selection process. The requirements are described in [this PDF document]("doc/CodeChallenge-CryptoWallet.pdf").

## Design decisions

 - the package layout and classes structure is following the Hexagonal Architecture pattern. The I/O logics and pumbling are separate from domain logic and data models, and the domain classes are not coupled with external integrations. This facilitates maintaining automated tests, should facilitate understanding of each component, and expanding the software capabilities without breaking unrelated code. I have decided to use this pattern despite the problem being simple, to demonstrate an extensible design. It is possible to solve the assessment with less classes/code.
 - I decided to create a `NumberFormatter` that would handle all money and number formatting/parsing to make sure that it would run the same on every environment. Depending on Locale or a program-wide configuration was avoided with the intention of increasing stability. 
 - as part of the requirements, I've added parallelism on fetching the prices with a concurrency of 3 threads. This is done using a custom thread pool that is used by `parallelStream()` on `CoincapAPI`.
 - error handling is done in a broad sense. I've tried to separate file reading, parsing and fetching errors to facilitate debugging. Other than that, every failure is fatal and exits the program with an error message.
 - the price request uses history API of Coincap with a fixated datetime. This is to follow a requirement set of the challenge to facilitate validating the solution correctness.

## Dependencies

- `json org` lib: used to parse the responses of Coincap API
- `junit`: to build automated unit and integration tests
- `mockito`, `hamcrest`: to test classes that depend on other components by mocking them and checking behavior.
- `build-helper` and `surefire` maven plugins: to separate integration and unit tests.
- `maven-assembly` plugin: to build a JAR file with embedded dependencies.

## Build

The project uses Maven to manage building, running tests and other processes.

To build the project run `mvn package` or execute `build.sh`.

After that, you can execute the program with `java -jar target/crypto-wallet-1.0-jar-with-dependencies.jar` or executing `run.sh`.

## Tests

Run all tests with `mvn integration-test` or executing `integration-tests.sh` at the project base directory. You can selectively run only unit tests with `mvn test` or executing `unit-tests.sh`.
