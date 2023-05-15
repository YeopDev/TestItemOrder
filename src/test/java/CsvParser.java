import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CsvParser {

    private static final String DEFAULT_PATH = "./src/main/resources";
    private static final String FILE_NAME = "/items_list.csv";

    @Test
    void parsingCsv() throws IOException {
        Path path = Paths.get(DEFAULT_PATH + FILE_NAME);
        // read
        List<String> result = Files.readAllLines(path);

        Assertions.assertThat(result.size()).isEqualTo(20);
    }

    @Test
    void parseLine(){
        String raw = "779049,\"[리퍼브/키친마켓] Fabrik Pottery Cup, Saucer (단품)\",10000,64";

        // ","는 무시하고 , 로 나눈다
        String[] split = raw.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

        System.out.println(split);
    }
}
