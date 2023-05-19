package user;

import static java.util.Objects.isNull;

public record User(Long id, String name, int money, int amount) {
    public User {
        if (isNull(id) || id < 0) {
            throw new IllegalArgumentException("id가 올바른 값이 아닙니다.");
        }
        if (isNull(name) || name.equals("")) {
            throw new IllegalArgumentException("이름이 올바른 값이 아닙니다.");
        }
        if (money < amount) {
            throw new IllegalArgumentException("소지금이 지불금액보다 작습니다.");
        }
    }

    public User payment(int target) {
        int afterMoney = money - target;
        return new User(this.id, this.name, afterMoney, 0);
    }
}
