package com.zhopy.statusservice.service.implementation;

import com.zhopy.statusservice.dto.StatusDTO;
import com.zhopy.statusservice.dto.StatusRequest;
import com.zhopy.statusservice.entity.Status;
import com.zhopy.statusservice.repository.StatusRepository;
import com.zhopy.statusservice.service.interfaces.IStatusService;
import com.zhopy.statusservice.utils.helpers.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
@Qualifier("StatusService")
public class StatusImplement implements IStatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public List<StatusDTO> findAll() {
        List<StatusDTO> dto = new ArrayList<>();
        Iterable<Status> statuses = this.statusRepository.findAll();

        for (Status status : statuses) {
            StatusDTO statusDTO = MapperHelper.modelMapper().map(status, StatusDTO.class);
            dto.add(statusDTO);
        }

        return dto;
    }

    @Override
    public StatusDTO findByStatusCode(Long statusCode) {
        Optional<Status> status = this.statusRepository.findById(statusCode);
        if (!status.isPresent()) {
            return null;
        }
        return MapperHelper.modelMapper().map(status.get(), StatusDTO.class);
    }

    @Override
    public StatusDTO findByStatusName(String statusName) {
        Optional<Status> status = this.statusRepository.findByStatusName(statusName);
        if (status.isPresent()) {
            return null;
        }
        return MapperHelper.modelMapper().map(status, StatusDTO.class);
    }

    @Override
    public void save(StatusRequest statusRequest) {
        Status status = MapperHelper.modelMapper().map(statusRequest, Status.class);
        this.statusRepository.save(status);
    }

    @Override
    public void update(StatusRequest statusRequest, Long statusCode) {
        Status status = MapperHelper.modelMapper().map(statusRequest, Status.class);
        this.statusRepository.save(status);

    }

    @Override
    public void delete(Long statusCode) {
        statusRepository.deleteById(statusCode);
    }

    @Override
    public boolean existsByStatusCode(Long statusCode) {
        return statusRepository.existsByStatusCode(statusCode);
    }

    @Override
    public boolean existsByStatusName(String statusName) {
        return statusRepository.existsByStatusName(statusName);
    }

}
