package item;

import exception.SoldOutException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ItemTest {
    @ParameterizedTest
    @CsvSource(value = {"779989:버드와이저 HOME DJing 굿즈 세트:35000:2"}, delimiter = ':')
    void itemConstructorNoThrownBy(Long id, String name, int price, int stockQuantity) {
        assertThatCode(
                () -> new Item(id, name, price, stockQuantity)
        ).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {"1: :35000:5"}, delimiter = ':')
    void itemConstructorThrownBy(Long id, String name, int price, int stockQuantity) {
        assertThatThrownBy(
                () -> new Item(id, name, price, stockQuantity)
        ).isInstanceOf(IllegalArgumentException.class).hasMessage("상품이름이 올바르지 않습니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"779989:버드와이저 HOME DJing 굿즈 세트:35000:10"}, delimiter = ':')
    @DisplayName("아이템 수량 업데이트 시 정상적인 동작을 할 경우")
    void itemUpdateNoThrownBy(Long id, String name, int price, int stockQuantity) {
        Assertions.assertThatCode(
                () -> {
                    Item item = new Item(id, name, price, stockQuantity);
                    Item inItem = item.checkStockQuantity(2);
                    item = item.update(inItem);
                    Assertions.assertThat(item.stockQuantity()).isEqualTo(8);
                }
        ).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {"779989:버드와이저 HOME DJing 굿즈 세트:35000:10"}, delimiter = ':')
    @DisplayName("아이템 수량 업데이트 시 정상적이지 않은 동작을 할 경우")
    void itemUpdateThrownBy(Long id, String name, int price, int stockQuantity) {
        Assertions.assertThatThrownBy(
                () -> {
                    Item item = new Item(id, name, price, stockQuantity);
                    Item inItem = item.checkStockQuantity(12);
                    item = item.update(inItem);
                }
        ).isInstanceOf(SoldOutException.class).hasMessage("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
    }
}