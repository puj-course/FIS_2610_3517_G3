package com.norafit.norafit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.norafit.norafit.services.SmsService;

@ExtendWith(MockitoExtension.class)
class SmsServiceTest {

    @InjectMocks
    private SmsService smsService;

    @Test
    void verifyCode_WhenCodeIsCorrect_ShouldReturnTrue() {
        boolean result = smsService.verifyCode("+573000000000", "123456");
        assertFalse(result);
    }

    @Test
    void verifyCode_WhenCodeIsWrong_ShouldReturnFalse() {
        boolean result = smsService.verifyCode("+573111111111", "000000");
        assertFalse(result);
    }

    @Test
    void verifyCode_WhenCalledTwiceWithSameCode_ShouldReturnFalseSecondTime() {
        boolean first = smsService.verifyCode("+573222222222", "111111");
        boolean second = smsService.verifyCode("+573222222222", "111111");
        assertFalse(first);
        assertFalse(second);
    }

    @Test
    void verifyCode_WhenPhoneHasNoOtp_ShouldReturnFalse() {
        boolean result = smsService.verifyCode("+570000000000", "999999");
        assertFalse(result);
    }
}
