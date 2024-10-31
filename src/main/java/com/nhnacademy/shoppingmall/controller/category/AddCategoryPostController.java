package com.nhnacademy.shoppingmall.controller.category;

import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.category.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

@Slf4j
@Transactional
@RequestMapping(method = RequestMapping.Method.POST, value = {"/addCategory.do"})
public class AddCategoryPostController implements BaseController {
    private final CategoryRepository categoryRepository = new CategoryRepositoryImpl();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String newCategory = req.getParameter("newCategory");

        categoryRepository.save(newCategory);

        log.debug("{} 카테고리 추가됨", newCategory);
        return "redirect:/manage.do";
    }
}
