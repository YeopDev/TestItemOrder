package order;

import item.Item;
import user.User;

import java.util.List;

public record Order(User user, List<Item> orderItems) {
    private static final int DELIVERY_FEE = 2_500;

    public int calculateTotalPrice(){
        return orderItems().stream()
                .mapToInt(item -> item.price() * item.stockQuantity())
                .sum();
    }

    public void displayOrderList(){
        for (Item item : orderItems()) {
            System.out.println(item.name() + " - " + item.stockQuantity() + "개");
        }
    }

    public boolean checkDelivery(int totalPrice) {
        return totalPrice < 50000;
    }

    public int getDeliveryFee(){
        return DELIVERY_FEE;
    }

    //bad bad case
    public User totalAmountPayment(){
        int totalAmountIncludingDeliveryFee = calculateTotalPrice();
        if(checkDelivery(totalAmountIncludingDeliveryFee)){
            totalAmountIncludingDeliveryFee += DELIVERY_FEE;
        }
        return new User(user.id(),user.name(),user.payment(totalAmountIncludingDeliveryFee),totalAmountIncludingDeliveryFee);
    }
}
