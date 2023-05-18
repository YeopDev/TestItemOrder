package order;

import item.Item;
import user.User;

import java.math.BigDecimal;
import java.util.List;

public record Order(User user) {

    public List<Item> orderDetail() {
        return user.orderItems();
    }

    public boolean checkDelivery(BigDecimal totalPrice) {
        return totalPrice.compareTo(new BigDecimal(50000)) < 0;
    }
}
