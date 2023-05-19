package order;

import item.Item;
import item.parser.ItemRepository;

import java.util.List;

public record Order(List<Item> orderList) {
    public int calculateTotalPrice(ItemRepository itemRepository){
        return itemRepository.totalPrice(orderList);
    }

    public List<Item> displayItems(ItemRepository itemRepository){
        return itemRepository.detailInfo(orderList);
    }


    public boolean checkDelivery(int totalPrice) {
        return totalPrice < 50000;
    }
}
