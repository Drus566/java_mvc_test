package iplm.repository;

import iplm.interfaces.repository.IRepository;
import iplm.models.UserModel;

public interface IUserRepository extends IRepository<UserModel> {
    UserModel findByUsername(String name);
}
