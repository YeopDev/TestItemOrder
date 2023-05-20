package item.parser;

import item.Item;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class CsvParser implements ItemRepository {
    private static final String DEFAULT_PATH = "./src/main/resources";
    private static final String REGEX = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
    private final String fileName = "items_list.csv";
    private List<Item> items;

    @Override
    public List<Item> findAll() throws IOException {
        if (items == null) {
            this.items = read().stream()
                    .skip(1)
                    .map(s -> s.split(REGEX))
                    .map(row -> new Item(Long.parseLong(row[0]), row[1], Integer.parseInt(row[2]), Integer.parseInt(row[3])))
                    .toList();
        }
        return items;
    }

    @Override
    public Optional<Item> findById(Long id) {
        return items.stream()
                .filter(item -> item.id().equals(id))
                .findFirst();
    }

    @Override
    public void updateItems(Item inItem) {
        this.items = items.stream()
                .map(item -> {
                    if (item.equals(inItem)) {
                        return item.update(inItem);
                    }
                    return item;
                }).toList();
    }

    private List<String> read() throws IOException {
        return Files.readAllLines(Paths.get(DEFAULT_PATH + "/" + fileName));
    }
}