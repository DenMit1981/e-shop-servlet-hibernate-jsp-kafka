package com.training.eshop.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "goods")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Good implements Serializable {

    private static final long serialVersionUID = 3906771677381811334L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @ManyToMany(mappedBy = "goods", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private List<Order> orders = new ArrayList<>();
}

