package com.training.eshop.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 3906771677381811334L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "order_good",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "good_id"))
    private List<Good> goods = new ArrayList<>();
}
