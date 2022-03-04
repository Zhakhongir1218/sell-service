package kg.itschool.sellservice.sellservice.services;

import kg.itschool.sellservice.sellservice.models.entities.Code;

public interface RequestService {

    void saveRequest(Code codeChecking, boolean b);

    int countOfFailed(Code codeChecking);
}
