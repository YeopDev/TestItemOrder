package order;

import user.User;

public record Order(User user) {

    public boolean checkDelivery(int totalPrice) {
        return totalPrice < 50000;
    }
}
