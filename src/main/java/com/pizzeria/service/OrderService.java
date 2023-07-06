package com.pizzeria.service;

import com.pizzeria.persistence.entity.OrderEntity;
import com.pizzeria.persistence.projection.OrderSummary;
import com.pizzeria.persistence.repository.OrderRepository;
import com.pizzeria.service.dto.RandomOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    private static final String DELIVERY = "D";
    private static final String CARRYOUT = "C";
    private static final String ON_SITE = "S";


    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderEntity> getAll() {
        return this.orderRepository.findAll();
    }

    public List<OrderEntity> getTodayOrders() {
        LocalDateTime today = LocalDate.now().atTime(0, 0);
        return  this.orderRepository.findAllByDateAfter(today);
    }

    public List<OrderEntity> getOutsideOrders() {
        List<String> methods = Arrays.asList(DELIVERY, CARRYOUT);
        return  this.orderRepository.findAllByMethodIn(methods);
    }

    @Secured("ROLE_ADMIN")
    public List<OrderEntity> getCustomerorders(String idCustomer) {
        return  this.orderRepository.findCustomerOrders(idCustomer);
    }

    //Usando proyecciones para resultados personalizados
    public OrderSummary getSummary(int idOrder) {
        return  this.orderRepository.findSummary(idOrder);
    }

    public boolean saveRandomOrder(RandomOrderDto dto) {
        return this.orderRepository.saveRandomOrder(dto.getIdCustomer(), dto.getMethod());
    }
}
