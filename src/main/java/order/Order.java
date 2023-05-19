package order;

import item.Item;

import java.util.List;

public record Order(List<Item> orderList) {
    public int calculateTotalPrice(){
        return orderList.stream()
                .mapToInt(item -> item.price() * item.stockQuantity())
                .sum();
    }

    public void displayOrderList(){
        // 영수증을 보여주는건 맞는데 이렇게 sysout찍는게 맞는지 고민해봐야겠다.
        for (Item item : orderList) {
            System.out.println(item.name() + " - " + item.stockQuantity() + "개");
        }
    }

    public boolean checkDelivery(int totalPrice) {
        return totalPrice < 50000;
    }
}
