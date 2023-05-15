package order.parser;

import order.Order;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class ParserTest {
    Parser parser = new Parser.CsvParser("items_list.csv");

    @Test
    void parseCsv() throws IOException {
        List<Order> execute = parser.execute();

        Assertions.assertThat(execute).isNotEmpty();
    }
}