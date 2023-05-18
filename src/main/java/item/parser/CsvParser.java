package item.parser;

import item.Item;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CsvParser implements ItemRepository {
    private static final String DEFAULT_PATH = "./src/main/resources";

    private static final String REGEX = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
    private String fileName = "items_list.csv";

    public List<Item> findAll() {
       return read().stream()
                .skip(1)
                .map(s -> s.split(REGEX))
                .map(row -> new Item(Long.parseLong(row[0]), row[1], new BigDecimal(row[2]), Integer.parseInt(row[3])))
                .toList();
    }

    private List<String> read() {
        try {
            return Files.readAllLines(Paths.get(DEFAULT_PATH +"/"+ fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}