package item.yeopParser;

import item.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class ParserTTest {

    private static final String DEFUALT_PATH = "./src/main/resources/";
    private static final String REGEX = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
    
    @ParameterizedTest
    @ValueSource(strings = {"items_list.csv"})
    @DisplayName("생성자가 올바른 값을 할당 받았을 경우")
    void constructorNoThrownBy(String fileName){
        Assertions.assertThatCode(
                () -> new ParserT(fileName)
        ).doesNotThrowAnyException();
    }
    @ParameterizedTest
    @ValueSource(strings = {"items_list.csv"})
    @DisplayName("생성자가 올바르지 않은 값을 할당 받았을 경우")
    void constructorThrownBy(String fileName){
        Assertions.assertThatThrownBy(
                () -> new ParserT(fileName)
        ).isInstanceOf(IllegalArgumentException.class).hasMessage("파일이름 값이 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"items_list.csv"})
    @DisplayName("csv파일 파싱 성공")
    void csvParserSuccess(String fileName) throws IOException {
        Assertions.assertThatCode(
                () -> {
                    List<String> resultList = Files.readAllLines(Paths.get(DEFUALT_PATH+fileName));
                    Assertions.assertThat(resultList.size()).isEqualTo(20);
                }
        ).doesNotThrowAnyException();
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"12,\"[리퍼브/키친마켓] Fabrik Pottery Cup, Saucer (단품)\",10000,64"})
    @DisplayName("csv파일 파싱중 쉼표로 문자열을 자를때 큰따옴표 안에 있는 쉼표는 무시하고 자르기")
    void csvParserPattern(String raw){
        Assertions.assertThatCode(
                () -> {
                    String[] raws = raw.split(REGEX);
                    Assertions.assertThat(raws[0]).isEqualTo("12");
                    Assertions.assertThat(raws[1]).isEqualTo("\"[리퍼브/키친마켓] Fabrik Pottery Cup, Saucer (단품)\"");
                    Assertions.assertThat(raws[2]).isEqualTo("10000");
                    Assertions.assertThat(raws[3]).isEqualTo("64");
                }
        ).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {"items_list.csv"})
    @DisplayName("parserExcute 메소드가 정상적으로 실행 되었을 경우")
    void parserExcuteNoThrownBy(String fileName){
        Assertions.assertThatCode(
                () -> {
                    ParserT parserT = new ParserT(fileName);
                    List<Item> items = parserT.parserExcute();
                    Assertions.assertThat(items).isNotEmpty();
                }
        ).doesNotThrowAnyException();
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"items_list.c"})
    @DisplayName("parserExcute 메소드가 정상적으로 실행되지 않았을 경우")
    void parserExcuteThrownBy(String fileName){
        Assertions.assertThatThrownBy(
                () -> {
                    ParserT parserT = new ParserT(fileName);
                    List<Item> items = parserT.parserExcute();
                }
        ).isInstanceOf(IOException.class);
    }

}