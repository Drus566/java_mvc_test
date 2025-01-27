package iplm.data.repository.user;

import iplm.data.types.User;
import iplm.data.repository.IUserRepository;

import java.util.ArrayList;
import java.util.List;

public class LocalUserRepository implements IUserRepository {
    List<User> users = new ArrayList<>();

    @Override
    public boolean add(User user) {
        users.add(user);
        return true;
    }

    @Override
    public boolean remove(User user) {
        users.remove(user);
        return true;
    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User findByUsername(String name) {
        return null;
    }
}
