package item;

import order.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    @ParameterizedTest
    @CsvSource(value = {"779989:버드와이저 HOME DJing 굿즈 세트:35000:2"},delimiter = ':')
    void orderConstructorNoThrownBy(Long productId, String productName, BigDecimal price, Integer stockQuantity){
        assertThatCode(
                () -> new Item(productId,productName,price,stockQuantity)
        ).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {"10:굿즈1:0:0"},delimiter = ':')
    void orderConstructorThrownBy(Long productId, String productName, BigDecimal price, Integer stockQuantity){
        assertThatThrownBy(
                () -> new Item(productId,productName,price,stockQuantity)
        ).isInstanceOf(IllegalArgumentException.class).hasMessage("상품이름이 올바르지 않습니다.");
    }
}