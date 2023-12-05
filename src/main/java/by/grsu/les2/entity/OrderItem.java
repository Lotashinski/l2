package by.grsu.les2.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    private Order order;

    @Column(scale = 2, precision = 6)
    private BigDecimal cost;

    @Column
    private long count;

    public BigDecimal getCost() {
        if (null == cost) {
            return item.getPrice();
        }

        return cost;
    }

    public BigDecimal getTotalCost() {
        return getCost().multiply(BigDecimal.valueOf(count));
    }
}
