package org.example.cinema.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice //Annotation này giúp Spring Boot biết đây là một class xử lý exception
//hàm này sẽ xử lý nếu có lỗi sẽ trả về trang error
public class GlobalExceptionHandler {

    //Annotation này giúp Spring Boot biết phương thức này sẽ xử lý exception
    @ExceptionHandler(Exception.class)
    //Phương thức này sẽ nhận exception và RedirectAttributes để truyền thông điệp lỗi
    public String handleException(Exception e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());
        return "redirect:/error";
    }
}