package iplm.repository.user;

import iplm.models.UserModel;
import iplm.repository.IUserRepository;

import java.util.ArrayList;
import java.util.List;

public class SQLiteUserRepository implements IUserRepository {
    private List<UserModel> users = new ArrayList<>();

    @Override
    public void add(UserModel user) {
        users.add(user);
    }

    @Override
    public void remove(UserModel user) {
        users.remove(user);
    }

    @Override
    public UserModel findById(int id) {
        return users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<UserModel> findAll() {
        return new ArrayList<>(users);
    }

    @Override
    public UserModel findByUsername(String name) {
        return null;
    }
}
