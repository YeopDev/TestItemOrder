package item.yeopParser;

import item.Item;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public record ParserCsvStudy(String fileName) {

    private static final String DEFAULT_PATH = "./src/main/resources/";

    private static final String REGEX = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    private static Path filePath;

    public ParserCsvStudy{
        if(isNull(fileName) || fileName.equals("") || fileName.equals("null")){
            throw new IllegalArgumentException("파일 이름이 잘못되었습니다.");
        }
        filePath = Paths.get(DEFAULT_PATH + fileName);
    }

    public List<Item> parserItem() throws IOException{
        return Files.readAllLines(filePath)
                .subList(1,Files.readAllLines(filePath).size())
                .stream()
                .map(raw -> byRaw(raw.split(REGEX)))
                .collect(Collectors.toList());
    }

    public Item byRaw(String ... raws){
        return new Item(
                Long.valueOf(raws[0]),
                raws[1],
                new BigDecimal(raws[2]),
                Integer.valueOf(raws[3])
        );
    }

}
