package iplm.repository;

import iplm.data.User;
import iplm.interfaces.repository.IRepository;
import iplm.models.UserModel;

public interface IUserRepository extends IRepository<User> {
    U findByUsername(String name);
}
