package item.parser;

import item.Item;

import java.util.List;

public interface ItemRepository {
    List<Item> findAll();

    List<Item> detailInfo(List<Item> orderDetails);

    int totalPrice(List<Item> orderDetails);

    boolean hasSufficientStock(List<Item> orderDetails);

    void checkProductStock(long productId, int quantity);
}
