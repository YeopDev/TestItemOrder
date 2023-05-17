package item.yeopParser;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PassingFileTest {

    private static final String DEFAULT_PATH = "./src/main/resources/";

    @ParameterizedTest
    @ValueSource(strings = {"items_list.csv"})
    @DisplayName("파싱전용 생성자에 올바른 값이 들어왔을 경우")
    void constructorNoThrownBy(String fileName) {
        Assertions.assertThatCode(
                () -> new PassingFile(fileName)
        ).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"null"})
    @DisplayName("파싱전용 생성자에 올바르지 않은 값이 들어왔을 경우")
    void constructorThrownBy(String fileName) {
        Assertions.assertThatThrownBy(
                () -> new PassingFile(fileName)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"items_list.csv"})
    @DisplayName("파싱 실행 메소드가 정상적으로 동작했을 경우")
    void passingExcuteNoThrownBy(String fileName) {
        Assertions.assertThatCode(
                () -> {
                    PassingFile passingFile = new PassingFile(fileName);
                    List<String> passingList = passingFile.passingExcute();
                    Assertions.assertThat(passingList).isNotEmpty();
                }
        ).doesNotThrowAnyException();
    }

}