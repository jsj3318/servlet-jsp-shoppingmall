package com.nhnacademy.shoppingmall.controller;

import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.transaction.Transactional;
import java.util.Objects;

public class ControllerProxy implements BaseController {
    private final BaseController baseController;

    public ControllerProxy() {
        baseController = null;
    }

    public ControllerProxy(BaseController baseController) {
        this.baseController = baseController;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        Transactional transactional = baseController.getClass().getAnnotation(Transactional.class);
        if(Objects.isNull(transactional)){
            return baseController.execute(req, resp);
        }

        String view = "";

        try {

            DbConnectionThreadLocal.initialize();

            view = baseController.execute(req, resp);


        } catch (Exception e){
            DbConnectionThreadLocal.setSqlError(true);
        } finally {
            DbConnectionThreadLocal.reset();

        }

        return view;
    }

}
