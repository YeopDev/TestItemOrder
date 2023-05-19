package order;

import item.Item;
import item.parser.CsvParser;
import item.parser.ItemRepository;

import java.util.List;

public record Order(List<Item> orderList) {

//고객이 주문한 상품을 나타내는 객체입니다.
//주문한 상품과 수량을 저장하고, 주문의 총 가격을 계산하는 기능을 제공합니다.

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
