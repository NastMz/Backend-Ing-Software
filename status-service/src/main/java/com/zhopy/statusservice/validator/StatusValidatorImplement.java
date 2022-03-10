package com.zhopy.statusservice.validator;

import com.zhopy.statusservice.dto.StatusDTO;
import com.zhopy.statusservice.dto.StatusRequest;
import com.zhopy.statusservice.service.interfaces.IStatusService;
import com.zhopy.statusservice.utils.exeptions.ApiNotFound;
import com.zhopy.statusservice.utils.exeptions.ApiUnprocessableEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("StatusValidator")
public class StatusValidatorImplement implements IStatusValidator {

    @Autowired
    @Qualifier("StatusService")
    private IStatusService statusService;

    @Override
    public void validator(StatusRequest request) throws ApiUnprocessableEntity {
        validateData(request);
        if (statusService.existsByStatusName(request.getStatusName())) {
            this.message422("The name already exists");
        }
    }

    @Override
    public void validatorUpdate(StatusRequest request) throws ApiUnprocessableEntity {
        validateData(request);
        validateName(request);
    }

    @Override
    public void validatorById(Long id) throws ApiNotFound {
        if (!statusService.existsByStatusCode(id)) {
            this.message404("The code does not exist");
        }
    }

    @Override
    public void validatorByIdRequest(Long urlCode, Long statusCode) throws ApiNotFound, ApiUnprocessableEntity {
        if (!statusService.existsByStatusCode(urlCode)) {
            this.message404("The code does not exist");
        }
        if (!urlCode.equals(statusCode)) {
            this.message422("The request body code does not match the uri code");
        }
    }

    private void validateData(StatusRequest request) throws ApiUnprocessableEntity {
        if (request.getStatusName() == null || StringUtils.isBlank(request.getStatusName())) {
            this.message422("The name cannot be empty");
        }
    }

    private void validateName(StatusRequest statusRequest) throws ApiUnprocessableEntity {
        StatusDTO statusSearch = this.statusService.findByStatusCode(statusRequest.getStatusCode());
        if (!statusRequest.getStatusName().equals(statusSearch.getStatusName()) && statusService.existsByStatusName(statusRequest.getStatusName())) {
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
