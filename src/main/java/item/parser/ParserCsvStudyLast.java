package item.parser;

import item.Item;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public record ParserCsvStudyLast(String fileName) {

    private static final String DEFUALT_PATH = "./src/main/resources/";
    private static final String REGEX = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
    private static Path filePath;

    public ParserCsvStudyLast{
        if(isNull(fileName) || fileName.equals("") || fileName.equals("null")){
            throw new IllegalArgumentException("파일이름 값이 없습니다.");
        }
        filePath = Paths.get(DEFUALT_PATH + fileName);
    }

    public List<Item> excute() throws IOException {
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
