////package com.ismael.order.infrastructure.adapter.in.rest;
////
////import com.ismael.order.application.usecase.CreateOrderUseCase;
////import com.ismael.order.application.usecase.ProcessPaymentUseCase;
////import com.ismael.order.domain.model.Order;
////import org.springframework.web.bind.annotation.*;
////
////import java.util.List;
////import java.util.UUID;
////
////@RestController
////@RequestMapping("/orders")
////public class OrderController {
////
////    private final CreateOrderUseCase createOrder;
////    private final ProcessPaymentUseCase payment;
////
////    public OrderController(CreateOrderUseCase createOrder, ProcessPaymentUseCase payment) {
////        this.createOrder = createOrder;
////        this.payment = payment;
////    }
////
////    @PostMapping
////    public UUID create(@RequestBody List<?> items) {
////        Order order = new Order(List.of());
////        Order saved = createOrder.create(order);
////        //payment.process(saved.getId());
////        return saved.getId();
////    }
////}
////
////
////
//////import com.ismael.order.application.usecase.CancelOrderUseCase;
//////import com.ismael.order.application.usecase.CreateOrderUseCase;
//////import com.ismael.order.application.usecase.ProcessOrderUseCase;
//////import com.ismael.order.application.usecase.ProcessPaymentUseCase;
//////import com.ismael.order.domain.model.Order; // Certifique-se do pacote correto do seu modelo
//////import org.springframework.http.HttpStatus;
//////import org.springframework.web.bind.annotation.*;
//////
//////import java.util.List;
//////import java.util.UUID;
//////
//////@RestController
//////@RequestMapping("/orders")
//////public class OrderController {
//////
//////    private final CreateOrderUseCase createOrderUseCase;
//////    private final CancelOrderUseCase cancelOrderUseCase;
//////    private final ProcessOrderUseCase processOrderUseCase;
//////    private final ProcessPaymentUseCase processPaymentUseCase;
//////
//////    // O Spring injetará os Beans que configuramos na BeanConfig
//////    public OrderController(CreateOrderUseCase createOrderUseCase,
//////                           CancelOrderUseCase cancelOrderUseCase,
//////                           ProcessOrderUseCase processOrderUseCase,
//////                           ProcessPaymentUseCase processPaymentUseCase) {
//////        this.createOrderUseCase = createOrderUseCase;
//////        this.cancelOrderUseCase = cancelOrderUseCase;
//////        this.processOrderUseCase = processOrderUseCase;
//////        this.processPaymentUseCase = processPaymentUseCase;
//////    }
//////
//////    @PostMapping
//////    @ResponseStatus(HttpStatus.CREATED)
//////    public UUID create(@RequestBody List<?> items) {
//////        // Mock de criação de objeto de domínio (ajuste conforme seu construtor de Order)
//////        Order order = new Order(UUID.randomUUID(), List.of()); 
//////        
//////        Order saved = createOrderUseCase.create(order);
//////        
//////        // Se o fluxo for síncrono, você chama o pagamento aqui. 
//////        // Se for SAGA/Assíncrono, isso seria feito via evento.
//////        processPaymentUseCase.process(saved.getId());
//////        
//////        return saved.getId();
//////    }
//////
//////    @PatchMapping("/{id}/process")
//////    @ResponseStatus(HttpStatus.NO_CONTENT)
//////    public void process(@PathVariable UUID id) {
//////        processOrderUseCase.process(id);
//////    }
//////
//////    @DeleteMapping("/{id}")
//////    @ResponseStatus(HttpStatus.NO_CONTENT)
//////    public void cancel(@PathVariable UUID id) {
//////        cancelOrderUseCase.cancel(id);
//////    }
//////}
//
//
//package com.ismael.order.infrastructure.adapter.in.rest;
//
//import com.ismael.order.application.usecase.CreateOrderUseCase;
//import com.ismael.order.domain.model.Order;
//import com.ismael.order.domain.model.OrderItem;
//import com.ismael.order.domain.model.OrderStatus;
//import com.ismael.order.infrastructure.adapter.in.rest.dto.CreateOrderItemRequest;
//
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/orders")
//public class OrderController {
//
//    private final CreateOrderUseCase createOrderUseCase;
//
//    public OrderController(CreateOrderUseCase createOrderUseCase) {
//        this.createOrderUseCase = createOrderUseCase;
//    }
//
////    @PostMapping
////    public UUID create(@RequestBody List<OrderItem> items) {       
////        Order order = new Order(UUID.randomUUID(), items);
////        order.pending();
////        Order saved = createOrderUseCase.create(order);
////        return saved.getId();
////    }
//    
//    @PostMapping
//    public UUID create(@RequestBody List<CreateOrderItemRequest> items) {
//        List<OrderItem> orderItems = items.stream()
//            .map(i -> new OrderItem(i.productId(), i.quantity(), i.price()))
//            .toList();
//
//        Order order = new Order(UUID.randomUUID(), orderItems);
//        Order saved = createOrderUseCase.create(order);
//        return saved.getId();
//    }
//}


package com.ismael.order.infrastructure.adapter.in.rest;

import com.ismael.order.application.usecase.CreateOrderUseCase;
import com.ismael.order.domain.model.OrderItem;
import com.ismael.order.infrastructure.adapter.in.rest.dto.CreateOrderItemRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID create(@RequestBody List<CreateOrderItemRequest> items) {

        List<OrderItem> orderItems = items.stream()
                .map(item -> new OrderItem(
                        item.productId(),
                        item.quantity(),
                        item.price()
                ))
                .toList();

        return createOrderUseCase.create(orderItems);
    }
}
