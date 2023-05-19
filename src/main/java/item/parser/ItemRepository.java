package item.parser;

import item.Item;

import java.util.List;

public interface ItemRepository {
    List<Item> findAll();

    List<Item> detailInfo(List<Item> orderDetails, List<Item> items);

    int totalPrice(List<Item> orderDetails, List<Item> items);

    boolean hasSufficientStock(List<Item> orderDetails, List<Item> items);
}
