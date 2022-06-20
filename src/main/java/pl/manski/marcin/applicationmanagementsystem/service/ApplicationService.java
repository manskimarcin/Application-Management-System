package pl.manski.marcin.applicationmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.manski.marcin.applicationmanagementsystem.model.Application;
import pl.manski.marcin.applicationmanagementsystem.model.State;
import pl.manski.marcin.applicationmanagementsystem.repository.ApplicationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private static final int PAGE_DEFAULT_SIZE = 10;

    private final ApplicationRepository applicationRepository;

    public List<Application> getSortedApplications(Integer page, Sort.Direction sort) {
        Pageable sortedByNameAndState;
        if (sort.isAscending()) {
            sortedByNameAndState = PageRequest.of(page, PAGE_DEFAULT_SIZE, Sort.by("name").and(Sort.by("state")));
        } else {
            sortedByNameAndState = PageRequest.of(page, PAGE_DEFAULT_SIZE, Sort.by("name").descending().and(Sort.by("state").descending()));
        }
        return applicationRepository.findAllApplications(sortedByNameAndState);
    }

    public Application manageEdit(Application application) {
        Application applicationEdited = applicationRepository.findById(application.getId()).orElseThrow();
        switch (applicationEdited.getState()) {
            case CREATED:
                return manageCreated(application, applicationEdited);
            case VERIFIED:
                return manageVerified(application, applicationEdited);
            case ACCEPTED:
                return manageAccepted(application, applicationEdited);
            default:
                throw new IllegalStateException("Operation not permitted!");
        }
    }

    public Application manageCreated(Application application, Application applicationEdited) {
        applicationEdited.setContent(application.getContent());
        if (application.getState() == State.DELETED) {
            applicationEdited.setState(State.DELETED);
            applicationEdited.setRejectionReason(application.getRejectionReason());
        }
        else if (application.getState() == State.VERIFIED) {
            applicationEdited.setState(State.VERIFIED);
        }
        return applicationRepository.save(applicationEdited);
    }

    public Application manageVerified(Application application, Application applicationEdited) {
        applicationEdited.setContent(application.getContent());
        if (application.getState() == State.ACCEPTED) {
            applicationEdited.setState(State.ACCEPTED);
        }
        else if (application.getState() == State.REJECTED) {
            applicationEdited.setState(State.REJECTED);
            applicationEdited.setRejectionReason(application.getRejectionReason());
        }
        return applicationRepository.save(applicationEdited);
    }

    public Application manageAccepted(Application application, Application applicationEdited) {
        if (application.getState() == State.PUBLISHED) {
            applicationEdited.setState(State.PUBLISHED);
            applicationEdited.setPublicationId(application.getId());
        }
        else if (application.getState() == State.REJECTED) {
            applicationEdited.setState(State.REJECTED);
            applicationEdited.setRejectionReason(application.getRejectionReason());
        }
        return applicationRepository.save(applicationEdited);
    }

    public Application addApplication(Application application) {
        Application applicationToSave = new Application();
        applicationToSave.setId(application.getId());
        applicationToSave.setName(application.getName());
        applicationToSave.setContent(application.getContent());
        applicationToSave.setState(State.CREATED);

        if (applicationToSave.getName() == null || applicationToSave.getContent() == null) {
            throw new IllegalStateException("Either Name or Content is empty!");
        }
        return applicationRepository.save(applicationToSave);
    }
}
