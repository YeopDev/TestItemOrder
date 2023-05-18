package order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

class OrderTest {

    @ParameterizedTest
    @ValueSource(strings = {""})
    @DisplayName("생성자에 올바른 값이 들어왔을 경우")
    void constructorNoThrownBy(){
        Assertions.assertThatCode(
                () -> new Order(new ArrayList<>(),new ArrayList<>())
        ).doesNotThrowAnyException();
    }
}