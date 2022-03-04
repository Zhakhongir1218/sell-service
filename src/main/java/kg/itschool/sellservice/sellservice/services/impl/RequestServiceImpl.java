package kg.itschool.sellservice.sellservice.services.impl;

import kg.itschool.sellservice.sellservice.models.entities.Code;
import kg.itschool.sellservice.sellservice.models.entities.Request;
import kg.itschool.sellservice.sellservice.repositories.RequestRepo;
import kg.itschool.sellservice.sellservice.services.RequestService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RequestServiceImpl implements RequestService {
    private final RequestRepo requestRepo;

    public RequestServiceImpl(RequestRepo requestRepo) {
        this.requestRepo = requestRepo;
    }

    @Override
    public void saveRequest(Code code, boolean value) {
        Request request = new Request();
        request.setAdd_date(LocalDateTime.now());
        request.setCode(code);
        request.setSuccess(value);
    }

    @Override
    public int countOfFailed(Code code) {
        return requestRepo.countByCodeAndSuccess(code,false);
    }
}
