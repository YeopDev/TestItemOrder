package order;

import item.Item;
import user.User;

import java.util.List;

public record Order(User user) {
    public List<Item> orderDetail() {
        return user.orderItems();
    }

    public boolean checkDelivery(int totalPrice) {
        return totalPrice < 50000;
    }
}
