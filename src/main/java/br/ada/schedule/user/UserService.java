package br.ada.schedule.user;

import br.ada.schedule.common.ResourceNotFoundException;
import br.ada.schedule.user.exception.UserException;
import br.ada.schedule.user.exception.UsernameChangedException;
import br.ada.schedule.user.exception.UsernameInUseException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class UserService {

    private final UserRepository repository;

    public UserService(final UserRepository repository) {
        this.repository = repository;
    }

    public User create(@Valid User user) throws UserException {
        validatedUsernameInUse(user.getUsername());
        return repository.save(user);
    }

    public List<User> list() {
        return repository.findAll();
    }

    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    public User update(@Valid User user) throws UserException {
        final User inDatabase = findById(user.getId())
                .orElseThrow(ResourceNotFoundException::new);
        if (!inDatabase.getUsername().equalsIgnoreCase(user.getUsername())) {
            throw new UsernameChangedException(user, user.getUsername());
        }
        return repository.save(user);
    }

    private void validatedUsernameInUse(String username) throws UserException {
        final User user = repository.findByUsername(username).orElse(null);
        if (user != null) {
            throw new UsernameInUseException(user, user.getUsername());
        }
    }

}
