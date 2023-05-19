package order;

import item.Item;
import user.User;

public record Order(User user) {
    private static final int DELIVERY_FEE = 2_500;

    public int calculateTotalPrice(){
        return user.orderItems().stream()
                .mapToInt(item -> item.price() * item.stockQuantity())
                .sum();
    }

    public void displayOrderList(){
        // 영수증을 보여주는건 맞는데 이렇게 sysout찍는게 맞는지 고민해봐야겠다.
        for (Item item : user.orderItems()) {
            System.out.println(item.name() + " - " + item.stockQuantity() + "개");
        }
    }

    public boolean checkDelivery(int totalPrice) {
        return totalPrice < 50000;
    }

    public int getDeliveryFee(){
        return DELIVERY_FEE;
    }

    public int totalAmountIncludingDeliveryFee(){
        int totalPrice = calculateTotalPrice();
        if(checkDelivery(totalPrice)){
            totalPrice += DELIVERY_FEE;
        }
        return totalPrice;
    }
}
