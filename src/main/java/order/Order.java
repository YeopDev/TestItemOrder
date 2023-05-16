package order;

import item.Item;

import java.util.List;

public record Order(List<Item> itemList) {

    public Order{

    }

}
