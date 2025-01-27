package iplm.data.repository.user;

import iplm.data.types.User;
import iplm.data.repository.IUserRepository;

import java.util.ArrayList;
import java.util.List;

public class SQLiteUserRepository implements IUserRepository {
    private List<User> users = new ArrayList<>();

    @Override
    public boolean add(User user) {
        users.add(user);
        return false;
    }

    @Override
    public boolean remove(User user) {
        users.remove(user);
        return false;
    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public User findByUsername(String name) {
        return null;
    }
}
