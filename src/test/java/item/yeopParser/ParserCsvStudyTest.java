package item.yeopParser;

import item.Item;
import item.yeopParser.ParserCsvStudy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ParserCsvStudyTest {

    private static final String DEFAULT_PATH = "./src/main/resources/";
    private static final String REGEX = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    @ParameterizedTest
    @ValueSource(strings = {"items_list.csv"})
    @DisplayName("생성자에 올바른 값이 들어왔을 경우")
    void constructorNoThrownBy(String fileName){
        assertThatCode(
                () -> new ParserCsvStudy(fileName)
        ).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {"null"})
    @DisplayName("생성자에 올바르지 않은 값이 들어왔을 경우")
    void constructorThrownBy(String fileName){
        assertThatThrownBy(
                () -> new ParserCsvStudy(fileName)
        ).isInstanceOf(IllegalArgumentException.class).hasMessage("파일 이름이 잘못되었습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"items_list.csv"})
    @DisplayName("csv파싱을 성공적으로 했음")
    void csvParseSuccess(String fileName) throws IOException {
        List<String> result = Files.readAllLines(Paths.get(DEFAULT_PATH + fileName));

        assertThat(result.size()).isEqualTo(20);
    }

    @ParameterizedTest
    @ValueSource(strings = {"12,\"[리퍼브/키친마켓] Fabrik Pottery Cup, Saucer (단품)\",10000,64"})
    @DisplayName("csv파일을 쉼표로 잘라서 파싱 중 큰따옴표 안에 쉼표가 있는 케이스는 자르지 않는다.")
    void csvParserPattern(String raw){
        String[] raws = raw.split(REGEX);

        System.out.println("raws[0] = " + raws[0]);
        System.out.println("raws[1] = " + raws[1]);
        System.out.println("raws[2] = " + raws[2]);
        System.out.println("raws[3] = " + raws[3]);
        assertThat(raws[0]).isEqualTo("12");
        assertThat(raws[1]).isEqualTo("\"[리퍼브/키친마켓] Fabrik Pottery Cup, Saucer (단품)\"");
        assertThat(raws[2]).isEqualTo("10000");
        assertThat(raws[3]).isEqualTo("64");
    }

    @ParameterizedTest
    @ValueSource(strings = {"items_list.csv"})
    @DisplayName("parserItem 메소드 테스트")
    void parserItemNoThrownBy(String fileName){
        assertThatCode(
                () -> {
                    ParserCsvStudy parserCsvStudy = new ParserCsvStudy(fileName);
                    List<Item> items = parserCsvStudy.parserItem();
                    assertThat(items).isNotEmpty();
                }
        ).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"items_list.c"})
    @DisplayName("parserItem 메소드 테스트")
    void parserItemThrownBy(String fileName){
        assertThatThrownBy(
                () -> {
                    ParserCsvStudy parserCsvStudy = new ParserCsvStudy(fileName);
                    List<Item> items = parserCsvStudy.parserItem();
                }
        ).isInstanceOf(IOException.class);
    }

}