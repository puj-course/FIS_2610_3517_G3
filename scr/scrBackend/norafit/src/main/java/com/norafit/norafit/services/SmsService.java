package com.norafit.norafit.services;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {

    private static final Logger log = LoggerFactory.getLogger(SmsService.class);

    private final Map<String, String> otpStore = new ConcurrentHashMap<>();
    private final SecureRandom random = new SecureRandom();

    @Value("${twilio.phone-number}")
    private String twilioPhoneNumber;

    // Genera un OTP de 6 dígitos, lo guarda y envía el SMS.
    public void sendVerificationCode(String toPhoneNumber) {
        String code = generateOtp();
        otpStore.put(toPhoneNumber, code);

        log.info("[SMS] Enviando código OTP al número: {} | Código: {}", toPhoneNumber, code);

        try {
            Message message = Message.creator(
                    new PhoneNumber(toPhoneNumber),
                    new PhoneNumber(twilioPhoneNumber),
                    "Tu código de verificación NoraFit es: " + code + ". Válido por 10 minutos."
            ).create();

            log.info("[SMS] Mensaje enviado exitosamente. SID: {} | Estado: {}", message.getSid(), message.getStatus());

        } catch (Exception e) {
            log.error("[SMS] Error al enviar SMS al número {}: {}", toPhoneNumber, e.getMessage());
            throw new RuntimeException("No se pudo enviar el SMS de verificación. Verifica el número de teléfono.");
        }
    }

    // Verifica si el código ingresado por el usuario es correcto.
    public boolean verifyCode(String phoneNumber, String code) {
        String stored = otpStore.get(phoneNumber);

        if (stored == null) {
            log.warn("[SMS] No se encontró código OTP para el número: {}", phoneNumber);
            return false;
        }

        boolean valid = stored.equals(code);

        if (valid) {
            otpStore.remove(phoneNumber);
            log.info("[SMS] Código OTP verificado exitosamente para: {}", phoneNumber);
        } else {
            log.warn("[SMS] Código OTP incorrecto para: {} | Esperado: {} | Recibido: {}", phoneNumber, stored, code);
        }

        return valid;
    }

    // Genera un código numérico aleatorio de 6 dígitos usando SecureRandom.
    private String generateOtp() {
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
