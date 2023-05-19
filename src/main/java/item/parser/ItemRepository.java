package item.parser;

import item.Item;

import java.math.BigDecimal;
import java.util.List;

public interface ItemRepository {
    List<Item> findAll();

    List<Item> detailInfo(List<Item> orderDetails, List<Item> items);

    BigDecimal totalPrice(List<Item> orderDetails, List<Item> items);
}
