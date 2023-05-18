package item.parser;

import item.Item;

import java.util.List;

public interface ItemRepository {
    List<Item> findAll();
}
