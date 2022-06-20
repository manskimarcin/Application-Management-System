package pl.manski.marcin.applicationmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import pl.manski.marcin.applicationmanagementsystem.model.Application;
import pl.manski.marcin.applicationmanagementsystem.service.ApplicationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping("/applications")
    public List<Application> getApplications(@RequestParam(required = false) Integer page, Sort.Direction sort) {
        int pageNumber = page != null && page > 0 ? page : 0;
        Sort.Direction sortDirection = sort != null ? sort : Sort.Direction.ASC;
        return applicationService.getApplications(pageNumber, sortDirection);
    }


    @PostMapping("applications")
    public Application addApplication(@RequestBody Application application) {
        return applicationService.addApplication(application);
    }

    @PutMapping("/applications")
    public Application editApplication(@RequestBody Application application) {
            return applicationService.manageEdit(application);
    }
}
