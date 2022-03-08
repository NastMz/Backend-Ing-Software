package com.zhopy.categoryservice.validator;

import com.zhopy.categoryservice.dto.CategoryDTO;
import com.zhopy.categoryservice.dto.CategoryRequest;
import com.zhopy.categoryservice.service.interfaces.ICategoryService;
import com.zhopy.categoryservice.utils.exeptions.ApiNotFound;
import com.zhopy.categoryservice.utils.exeptions.ApiUnprocessableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("CategoryValidator")
public class CategoryValidatorImplement implements ICategoryValidator {

    @Autowired
    @Qualifier("CategoryService")
    private ICategoryService categoryService;

    @Override
    public void validator(CategoryRequest request) throws ApiUnprocessableEntity {
        validateData(request);
        if (categoryService.existsByCategoryName(request.getCategoryName())) {
            this.message422("The name already exists");
        }
    }

    @Override
    public void validatorUpdate(CategoryRequest request) throws ApiUnprocessableEntity {
        validateData(request);
        validateName(request);
    }

    @Override
    public void validatorById(Long id) throws ApiNotFound {
        if (!categoryService.existsByCategoryCode(id)) {
            this.message404("The code does not exist");
        }
    }

    @Override
    public void validatorByIdRequest(Long urlCode, Long roleCode) throws ApiNotFound, ApiUnprocessableEntity {
        if (!categoryService.existsByCategoryCode(urlCode)) {
            this.message404("The code does not exist");
        }
        if (!urlCode.equals(roleCode)) {
            this.message422("The request body code does not match the uri code");
        }
    }

    private void validateData(CategoryRequest request) throws ApiUnprocessableEntity {
        if (request.getCategoryName() == null || request.getCategoryName().isBlank()) {
            this.message422("The name cannot be empty");
        }
    }

    private void validateName(CategoryRequest roleRequest) throws ApiUnprocessableEntity {
        CategoryDTO categorySearch = this.categoryService.findByCategoryCode(roleRequest.getCategoryCode());
        if (!roleRequest.getCategoryName().equals(categorySearch.getCategoryName()) && categoryService.existsByCategoryName(roleRequest.getCategoryName())) {
            this.message422("The name already exists");
        }
    }

    private void message422(String message) throws ApiUnprocessableEntity {
        throw new ApiUnprocessableEntity(message);
    }

    private void message404(String message) throws ApiNotFound {
        throw new ApiNotFound(message);
    }
}
