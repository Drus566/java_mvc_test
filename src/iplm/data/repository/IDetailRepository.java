package iplm.data.repository;

import iplm.data.types.Detail;
import iplm.interfaces.repository.IRepository;

public interface IDetailRepository extends IRepository<Detail> {
    int getParam1(String name);
}
