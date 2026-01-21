package com.ismael.order.application.port.in;

import java.util.UUID;

public interface CancelOrderInputPort {
    void cancel(UUID orderId);
}