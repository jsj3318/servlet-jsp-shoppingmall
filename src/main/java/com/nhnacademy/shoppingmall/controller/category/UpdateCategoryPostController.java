package com.nhnacademy.shoppingmall.controller.category;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.category.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.transaction.Transactional;

@Transactional
@RequestMapping(method = RequestMapping.Method.POST, value = {"/updateCategory.do"})
public class UpdateCategoryPostController implements BaseController {
    private final CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int category_id = Integer.parseInt(req.getParameter("category_id"));
        String newCategory = req.getParameter("newCategory");

        categoryRepository.update(category_id, newCategory);

        return "redirect:/manage.do";
    }
}
