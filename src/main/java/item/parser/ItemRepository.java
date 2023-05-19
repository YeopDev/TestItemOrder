package item.parser;

import item.Item;

import java.util.List;

public interface ItemRepository {
    List<Item> findAll();

    List<Item> changeItemList(List<Item> orderItems);

    List<Item> updateItems(List<Item> orderItems);

    boolean hasSufficientStock(List<Item> orderItems);

    void checkProductStock(long productId, int quantity);
}
