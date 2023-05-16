package item.parser;

import item.Item;
import item.parser.Parser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class ParserTest {
    Parser parser = new Parser.CsvParser("items_list.csv");

    @Test
    void parseCsv() throws IOException {
        List<Item> execute = parser.execute();

        Assertions.assertThat(execute).isNotEmpty();
    }
}