package order;

import item.Item;
import item.yeopParser.ParserCsvStudyLast;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.List;

class OrderTest {
    
    @ParameterizedTest
    @CsvSource(value = {"items_list.csv,12"},delimiter = ',')
    @DisplayName("사용자가 주문한 상품의 재고수량이 0개보다 클경우 true 리턴")
    void hasProductItemThrownBy(String fileName, Long id) throws IOException {
        Assertions.assertThatCode(
                () -> {
                    ParserCsvStudyLast parserCsvStudyLast = new ParserCsvStudyLast(fileName);
                    List<Item> items = parserCsvStudyLast.excute();
                    Order order = new Order(items);
                    boolean result = order.hasStockQuantity(id);
                    Assertions.assertThat(result).isTrue();
                }
        ).doesNotThrowAnyException();
    }

}