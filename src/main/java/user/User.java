package user;

import static java.util.Objects.isNull;

public class User {
    private Long id;
    private String name;
    private int money;

    public User(Long id, String name, int money) {
        if (isNull(id) || id < 0) {
            throw new IllegalArgumentException("id가 올바른 값이 아닙니다.");
        }
        if (isNull(name) || name.equals("") ||name.equals(" ")) {
            throw new IllegalArgumentException("이름이 올바른 값이 아닙니다.");
        }
        if (money < 0){
            throw new IllegalArgumentException("소지금이 올바른 값이 아닙니다.");
        }
        this.id = id;
        this.name = name;
        this.money = money;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public int money() {
        return money;
    }

    public void paymentProgress(int totalAmountIncludingDeliveryFee) {
        this.money = payment(totalAmountIncludingDeliveryFee);
    }

    //rename method
    private int payment(int target) {
        if (money < target) {
            throw new IllegalArgumentException("소지금이 지불금액보다 작습니다.");
        }
        return money - target;
    }
}
