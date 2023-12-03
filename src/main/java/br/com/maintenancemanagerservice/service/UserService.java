package br.com.maintenancemanagerservice.service;

import br.com.maintenancemanagerservice.dto.RegisterUserRecord;
import br.com.maintenancemanagerservice.dto.UpdatePasswordRecord;
import br.com.maintenancemanagerservice.dto.UpdateUserRecord;
import br.com.maintenancemanagerservice.model.entity.User;
import br.com.maintenancemanagerservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private User register(RegisterUserRecord registerRecord) {

        this.saveOrUpdate(null);
        return null;
    }

    private User update(UpdateUserRecord updateRecord) {

        this.saveOrUpdate(null);
        return null;
    }


    private User update(UpdatePasswordRecord passwordRecord) {

        this.saveOrUpdate(null);
        return null;
    }

    @Transactional
    public boolean delete(String userName) {
        return false;
    }

    public User getOneById(String id) {
        return null;
    }

    public User getOneByName(String Name) {
        return null;
    }

    public List<User> getAll() {
        return null;
    }

    @Transactional
    private User saveOrUpdate(User user) {

        var newUser = userRepository.save(user);

        return newUser;
    }

}
