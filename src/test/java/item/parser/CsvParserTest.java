package item.parser;

import item.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvParserTest {
    @ParameterizedTest
    @CsvSource(value = {""})
    @DisplayName("CSV파일을 정상적으로 읽어 올 경우")
    void successCsvParserNoThrownBy(){
        Assertions.assertThatCode(
                () -> {
                    ItemRepository itemRepository = new CsvParser();
                    List<Item> items = itemRepository.findAll();
                    Assertions.assertThat(items.size()).isEqualTo(19);
                }
        ).doesNotThrowAnyException();
    }

}