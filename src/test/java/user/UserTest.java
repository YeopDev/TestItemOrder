package user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {

    @ParameterizedTest
    @CsvSource(value = {
            "0:yeop:10000",
    },
            delimiter = ':')
    void userBuyItemNoThrownBy(Long id, String name, BigDecimal money) {
        assertThatCode(
                () -> new User(id, name, money)
        ).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "-1:yeop:0",
    }, delimiter = ':')
    void userBuyItemThrownBy(Long id, String name, BigDecimal money) {
        assertThatThrownBy(
                () -> new User(id,name,money)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0:yeop:10000",
    }, delimiter = ':')
    void userMethodNoThrownBy(Long id, String name, BigDecimal money){
        assertThatCode(
                () -> {
                    User user = new User(id,name,money).payment(BigDecimal.valueOf(1000L));
                    Assertions.assertThat(user.money()).isEqualTo(BigDecimal.valueOf(9000L));
                }
        ).doesNotThrowAnyException();
    }

}