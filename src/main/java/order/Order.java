package order;

import java.math.BigDecimal;

import static java.util.Objects.isNull;

public record Order(Long productId,
                    String productName,
                    BigDecimal price,
                    Integer stockQuantity) {

    private static final String REGEX = "[0-9]";

    public Order{
        if(isNull(productId) || productId < 0){
            throw new IllegalArgumentException("상품코드가 올바르지 않습니다.");
        }
        if(isNull(productName) || productName.equals("") || productName.contains(REGEX)){
            throw new IllegalArgumentException("상품이름이 올바르지 않습니다.");
        }
        if(isNull(price)){
            throw new IllegalArgumentException("상품가격이 올바르지 않습니다.");
        }
        if(isNull(stockQuantity) || stockQuantity < 0){
            throw new IllegalArgumentException("재고수량 올바르지 않습니다.");
        }
    }


}
