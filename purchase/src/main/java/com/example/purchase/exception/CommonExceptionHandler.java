package com.example.purchase.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.purchase.vo.StatusMessageVO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

/**
 * CommonExceptionHandler class
 * description: TODO
 *
 * @author huangyiran
 * @date 2022/9/15
 */

@RestControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSONObject handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        StatusMessageVO statusMessageVO = new StatusMessageVO();
        statusMessageVO.setStatus("F");
        statusMessageVO.setMessage(ex.getBindingResult().getFieldError().getDefaultMessage());
        return (JSONObject) JSON.toJSON(statusMessageVO);
    }

}
