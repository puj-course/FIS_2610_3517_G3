package com.norafit.norafit;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.norafit.norafit.services.SmsService;

class SmsServiceTest {

    private SmsService smsService;

    @BeforeEach
    void setUp() {
        smsService = new SmsService();
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> getOtpStore() throws Exception {
        Field field = SmsService.class.getDeclaredField("otpStore");
        field.setAccessible(true);
        return (Map<String, String>) field.get(smsService);
    }

    @Test
    void verifyCode_WhenPhoneHasNoOtp_ShouldReturnFalse() {
        boolean result = smsService.verifyCode("+570000000000", "999999");

        assertFalse(result);
    }

    @Test
    void verifyCode_WhenCodeIsCorrect_ShouldReturnTrueAndRemoveOtp() throws Exception {
        Map<String, String> otpStore = getOtpStore();
        otpStore.put("+573000000000", "123456");

        boolean result = smsService.verifyCode("+573000000000", "123456");

        assertTrue(result);
        assertFalse(otpStore.containsKey("+573000000000"));
    }

    @Test
    void verifyCode_WhenCodeIsWrong_ShouldReturnFalseAndKeepOtp() throws Exception {
        Map<String, String> otpStore = getOtpStore();
        otpStore.put("+573111111111", "123456");

        boolean result = smsService.verifyCode("+573111111111", "000000");

        assertFalse(result);
        assertEquals("123456", otpStore.get("+573111111111"));
    }

    @Test
    void verifyCode_WhenCalledTwiceWithSameCode_ShouldReturnFalseSecondTime() throws Exception {
        Map<String, String> otpStore = getOtpStore();
        otpStore.put("+573222222222", "111111");

        boolean first = smsService.verifyCode("+573222222222", "111111");
        boolean second = smsService.verifyCode("+573222222222", "111111");

        assertTrue(first);
        assertFalse(second);
    }
}
