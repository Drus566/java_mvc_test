package iplm.repository.user;

import iplm.data.User;
import iplm.models.UserModel;
import iplm.repository.IUserRepository;

import java.util.ArrayList;
import java.util.List;

public class LocalUserRepository implements IUserRepository {
    List<User> users = new ArrayList<>();

    @Override
    public void add(UserModel entity) {

    }

    @Override
    public void remove(UserModel entity) {

    }

    @Override
    public UserModel findById(int id) {
        return null;
    }

    @Override
    public List<UserModel> findAll() {
        return null;
    }

    @Override
    public UserModel findByUsername(String name) {
        return null;
    }
}
