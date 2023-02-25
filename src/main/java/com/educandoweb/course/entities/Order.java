package com.educandoweb.course.entities;

import com.educandoweb.course.entities.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_order") // Evitar conflito com a palavra reservada ORDER do SQL
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant moment;

    // Internamente na nossa classe nós tratamos esse campo como Integer, pois salvamos um inteiro no banco
    // Entretando, pro mundo externo ainda é OrderStatus (olhar construtor)
    private Integer orderStatus;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    @OneToMany(mappedBy = "id.order") // No OrderItem nós temos o Id, que é do tipo OrderItemPK, que por sua vez tem o pedido
    private Set<OrderItem> items = new HashSet<>();

    // Em associações um para um, nós mapeamos as duas entidades para ter o mesmo id! Por isso é obrigatório definir o cascade
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;

    public Order() {}

    public Order(Long id, Instant moment, OrderStatus orderStatus, User client) {
        this.id = id;
        this.moment = moment;
        setOrderStatus(orderStatus);
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT") // GMT = Padrão UTC global
    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public OrderStatus getOrderStatus() {
        // Pegando o número inteiro interno da classe e convertendo para OrderStatus
        return OrderStatus.valueOf(orderStatus);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        if (orderStatus != null) {
            // Processo inverso do get, recebe um OrderStatus e internamente guarda um inteiro
            this.orderStatus = orderStatus.getCode(); // Pega o número inteiro correspondente ao OrderStatus
        }
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    // OBS: Na plataforma Java Enterprise (EE) o que vale é o Get! Por isso colocamos o get antes do nome do método para que isso apareça no JSON
    public Double getTotal() {
        double sum = 0.0;

        for (OrderItem orderItem : items) {
            sum += orderItem.getSubTotal();
        }
        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
