package item.parser;

import item.Item;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    List<Item> findAll() throws IOException;

    Optional<Item> findById(Long id);

    void updateItems(Item item);
}
