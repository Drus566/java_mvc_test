package iplm.data.repository;

import iplm.data.types.User;
import iplm.interfaces.repository.IRepository;

public interface IUserRepository extends IRepository<User> {
    User findByUsername(String name);
}
