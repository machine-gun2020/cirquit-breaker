package com.examples;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PaymentService {

    private static final Logger LOG = Logger.getLogger(PaymentService.class);

    @CircuitBreaker(
            requestVolumeThreshold = 5, // Número mínimo de llamadas para evaluar fallos
            failureRatio = 0.5,         // Umbral de fallo: 50%
            delay = 10000,              // Tiempo en estado abierto (10 segundos)
            successThreshold = 3        // Llamadas exitosas necesarias para cerrar el circuito
    )
    @Timeout(500) // Tiempo límite de respuesta (500 ms)
    @Fallback(fallbackMethod = "fallbackPayment") // Método de fallback cuando el Circuit Breaker se activa
    public String processPayment() {
        LOG.info("Procesando pago...");
        if (Math.random() < 0.6) { // Simula un 60% de fallos
            throw new RuntimeException("Error en el servicio de pago.");
        }
        return "Pago procesado con éxito.";
    }

    // Método de fallback que se ejecuta si el Circuit Breaker está abierto o la llamada falla
    public String fallbackPayment() {
        LOG.warn("Método de Fallback: Servicio de pago no disponible. Inténtelo más tarde.");
        return "Servicio de pago no disponible. Inténtelo más tarde.";
    }
}
