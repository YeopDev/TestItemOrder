package item.yeopParser;

import item.Item;
import item.yeopParser.ParserCsvStudyLast;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ParserCsvStudyLastTest {

    private static final String DEFAULT_PATH = "./src/main/resources/";
    private static final String REGEX = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    @ParameterizedTest
    @ValueSource(strings={"items_list.csv"})
    @DisplayName("생성자에 올바른 값이 들어왔을 경우")
    void parserCsvStudyLastConstructorNoThrownBy(String fileName){
        assertThatCode(
                () -> new ParserCsvStudyLast(fileName)
        ).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value={"Null"})
    @DisplayName("생성자에 올바르지 않은 값이 들어왔을 경우")
    void parserCsvStudyLastConstructorThrownBy(String fileName){
        assertThatThrownBy(
                () -> new ParserCsvStudyLast(fileName)
        ).isInstanceOf(IllegalArgumentException.class).hasMessage("파일이름 값이 null 입니다.");
    }

    @ParameterizedTest
    @ValueSource(strings={"items_list.csv"})
    @DisplayName("csv파싱 성공")
    void csvPassingSucc(String fileName) throws IOException {
        List<String> result = Files.readAllLines(Paths.get(DEFAULT_PATH + fileName));
        assertThat(result.size()).isEqualTo(20);
    }

    @ParameterizedTest
    @ValueSource(strings = {"12,\"[리퍼브/키친마켓] Fabrik Pottery Cup, Saucer (단품)\",10000,64"})
    @DisplayName("csv파싱 시 쉼표로 문자열을 자를때 큰따옴표 안에 쉼표는 무시하고 잘라야 할때")
    void csvParserPattern(String raw){
        String[] raws = raw.split(REGEX);

        assertThat(raws[0]).isEqualTo("12");
        assertThat(raws[1]).isEqualTo("\"[리퍼브/키친마켓] Fabrik Pottery Cup, Saucer (단품)\"");
        assertThat(raws[2]).isEqualTo("10000");
        assertThat(raws[3]).isEqualTo("64");
    }

    @ParameterizedTest
    @ValueSource(strings = {"items_list.csv"})
    @DisplayName("메소드로 Order객체를 가지고 있는 리스트를 만들 경우")
    void parserCsvStudyLastExcuteTest(String fileName){
        assertThatCode(
                () -> {
                    ParserCsvStudyLast parserCsvStudyLast = new ParserCsvStudyLast(fileName);
                    List<Item> orders = parserCsvStudyLast.excute();
                    assertThat(orders).isNotEmpty();
                }
        ).doesNotThrowAnyException();
    }

}