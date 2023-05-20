package item;

import exception.SoldOutException;

import java.util.Objects;

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

    public Item checkStockQuantity(int quantity) {
        if (stockQuantity < quantity) {
            throw new SoldOutException("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
        }
        return new Item(id, name, price, quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id.equals(item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Item update(Item inItem) {
        return new Item(id, name, price, stockQuantity - inItem.stockQuantity);
    }
}
