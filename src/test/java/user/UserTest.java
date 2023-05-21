package user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {

    @ParameterizedTest
    @CsvSource(value = {"0:yeop:10000"}, delimiter = ':')
    @DisplayName("생성자에 올바른 값이 들어왔을 경우")
    void userConstructorNoThrownBy(Long id, String name, int money) {
        assertThatCode(
                () -> new User(id, name, money)
        ).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {"-1:yeop:0"}, delimiter = ':')
    @DisplayName("생성자에 올바르지 않은 id값이 들어왔을 경우")
    void userConstructorIdThrownBy(Long id, String name, int money) {
        assertThatThrownBy(
                () -> new User(id, name, money)
        ).isInstanceOf(IllegalArgumentException.class).hasMessage("id가 올바른 값이 아닙니다.");
    }

    @ParameterizedTest
    @MethodSource("invalidUserArguments")
    @DisplayName("생성자에 올바르지 않은 name값이 들어왔을 경우")
    void userConstructorNameThrownBy(Long id, String name, int money) {
        Assertions.assertThatThrownBy(
                () -> new User(id, name, money)
        ).isInstanceOf(IllegalArgumentException.class).hasMessage("이름이 올바른 값이 아닙니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"0:yeop:-100"}, delimiter = ':')
    @DisplayName("생성자에 올바르지 않은 소지금값이 들어왔을 경우")
    void userConstructorMoneyThrownBy(Long id, String name, int money) {
        Assertions.assertThatThrownBy(
                () -> new User(id, name, money)
        ).isInstanceOf(IllegalArgumentException.class).hasMessage("소지금이 올바른 값이 아닙니다.");
    }

    private static Stream<Arguments> invalidUserArguments() {
        return Stream.of(
                Arguments.of(0L, " ", 100000)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {36_000})
    @DisplayName("결제진행 시 정상적인 실행이 가능할 경우")
    void paymentProgressNoThrownBy(int totalAmountIncludingDeliveryFee) {
        Assertions.assertThatCode(
                () -> {
                    User user = new User(0L, "yeop", 100_000);
                    user.paymentProgress(totalAmountIncludingDeliveryFee);
                    Assertions.assertThat(user.money()).isEqualTo(64000);
                }
        ).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {360_000})
    @DisplayName("결제진행 시 정상적인 실행이 안되는 경우")
    void paymentProgressThrownBy(int totalAmountIncludingDeliveryFee) {
        Assertions.assertThatThrownBy(
                () -> {
                    User user = new User(0L, "yeop", 100_000);
                    user.paymentProgress(totalAmountIncludingDeliveryFee);
                }
        ).isInstanceOf(IllegalArgumentException.class).hasMessage("소지금이 지불금액보다 작습니다.");
    }
}