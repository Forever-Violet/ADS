package io.ads.common.utils;

import io.ads.common.exception.RenException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于抛出校验dto时产生的异常信息
 * @author 12508
 */
public class ValidDtoUtils {

    public static void throwValidateException(BindingResult result) {
        if (result.hasErrors()) {
            // dto校验出异常，在这里处理
            List<String> errMessages = new ArrayList<>();
            for (ObjectError error : result.getAllErrors()) {
                errMessages.add(error.getDefaultMessage());
            }
            throw new RenException(errMessages.toString());
        }
    }
}
