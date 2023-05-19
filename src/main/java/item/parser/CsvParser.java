package item.parser;

import item.Item;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvParser implements ItemRepository {
    private static final String DEFAULT_PATH = "./src/main/resources";

    private static final String REGEX = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
    private final String fileName = "items_list.csv";

    @Override
    public List<Item> findAll() {
       return read().stream()
                .skip(1)
                .map(s -> s.split(REGEX))
                .map(row -> new Item(Long.parseLong(row[0]), row[1], new BigDecimal(row[2]), Integer.parseInt(row[3])))
                .toList();
    }

    @Override
    public List<Item> detailInfo(List<Item> orderDetails, List<Item> items) {
        return orderDetails.stream()
                .map(orderItem -> {
                    long orderProductId = orderItem.productId();
                    int orderQuantity = orderItem.stockQuantity();
                    Optional<Item> matchingItem = items.stream().filter(item -> item.productId().equals(orderProductId)).findFirst();
                    if (matchingItem.isPresent()) {
                        Item getItem = matchingItem.get();
                        return new Item(getItem.productId(), getItem.productName(), getItem.price(), orderQuantity);
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal totalPrice(List<Item> orderDetails, List<Item> items) {
        return orderDetails.stream()
                .flatMap(orderDetail -> {
                    long itemProductId = orderDetail.productId();
                    int itemQuantity = orderDetail.stockQuantity();
                    Optional<Item> matchingItem = items.stream().filter(item -> item.productId() == itemProductId).findFirst();
                    if (matchingItem.isPresent()) {
                        Item item = matchingItem.get();
                        BigDecimal totalItemPrice = item.price().multiply(BigDecimal.valueOf(itemQuantity));
                        return Stream.of(totalItemPrice);
                    } else {
                        return Stream.empty();
                    }
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<String> read() {
        try {
            return Files.readAllLines(Paths.get(DEFAULT_PATH +"/"+ fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}