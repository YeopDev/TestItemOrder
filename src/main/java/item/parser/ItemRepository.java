package item.parser;

import item.Item;

import java.io.IOException;
import java.util.List;

public interface ItemRepository {
    List<Item> findAll() throws IOException;

    List<Item> changeItemList(List<Item> orderItems);

    List<Item> updateItems(List<Item> orderItems);

    void hasSufficientStock(List<Item> orderItems);
}
