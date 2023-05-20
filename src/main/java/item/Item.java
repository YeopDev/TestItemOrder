package item;

import exception.SoldOutException;

import java.util.Optional;

import static java.util.Objects.isNull;

public record Item(Long id, String name, int price, int stockQuantity) {
    public Item {
        if (isNull(id) || id < 0) {
            throw new IllegalArgumentException("상품코드가 올바르지 않습니다.");
        }
        if (isNull(name) || name.equals("")) {
            throw new IllegalArgumentException("상품이름이 올바르지 않습니다.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("상품가격이 올바르지 않습니다.");
        }
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("재고수량 올바르지 않습니다.");
        }
    }

    public String itemInfo() {
        return id() + "\t" + name() + "\t" + price() + "\t" + stockQuantity();
    }

    public boolean checkProductQuantity(int quantity) {
        if (stockQuantity < quantity) {
            throw new SoldOutException("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
        }
        return true;
    }
}
