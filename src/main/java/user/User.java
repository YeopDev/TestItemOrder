package user;

import java.math.BigDecimal;

import static java.util.Objects.isNull;

public record User(Long id, String name, BigDecimal money, BigDecimal payAmount) {
    public User {
        if (isNull(id) || id < 0) {
            throw new IllegalArgumentException("id가 올바른 값이 아닙니다.");
        }
        if (isNull(name) || name.equals("")) {
            throw new IllegalArgumentException("이름이 올바른 값이 아닙니다.");
        }
        if (isNull(money)) {
            throw new IllegalArgumentException("소지금이 올바른 값이 아닙니다.");
        }
        if (money.compareTo(payAmount) < 0) {
            throw new IllegalArgumentException("소지금이 지불금액보다 작습니다.");
        }
    }

    public User payment(BigDecimal target) {
        return new User(this.id, this.name, this.money.subtract(target),new BigDecimal(0));
    }
}
