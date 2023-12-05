package by.grsu.les2.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(scale = 2, precision = 6)
    private BigDecimal price;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable = false)
    private Set<String> images;

    @OneToMany(mappedBy = "item")
    private Set<OrderItem> orderItems;


    public Set<String> getImages(){
        if (null == images){
            images = new HashSet<>();
        }

        return images;
    }

    public void addImage(String path){
        getImages().add(path);
    }
}
