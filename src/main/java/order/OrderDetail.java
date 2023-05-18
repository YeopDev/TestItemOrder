package order;

import java.math.BigDecimal;

public record OrderDetail(long productId, String productName, BigDecimal price, int stockQuantity) {



}
