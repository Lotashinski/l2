package by.grsu.les2.entity;

import jakarta.persistence.*;
import lombok.*;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    private User customer;

    @OneToMany(mappedBy = "order")
    private Set<OrderItem> items;

    public Set<OrderItem> getItems() {
        if (null == items) {
            items = new HashSet<>();
        }

        return items;
    }

    public BigDecimal getTotalCost() {
        return getItems().stream()
                .map(OrderItem::getTotalCost)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public void addPosition(OrderItem item) {
        item.setOrder(this);
        Optional<OrderItem> current = getItems().stream()
                .filter(oi -> Objects.equals(oi.getItem().getId(), item.getItem().getId()))
                .findFirst();
        if (current.isPresent()) {
            OrderItem c =  current.get();
            c.setCount(item.getCount() + c.getCount());
        } else {
            getItems().add(item);
        }
    }

    public void addItem(Item item, Long count) {
        addPosition(
                OrderItem.builder()
                        .item(item)
                        .cost(item.getPrice())
                        .count(count)
                        .build()
        );
    }
}
