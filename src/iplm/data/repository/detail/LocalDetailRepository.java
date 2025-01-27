package iplm.data.repository.detail;

import iplm.data.types.Detail;
import iplm.data.repository.IDetailRepository;

import java.util.ArrayList;
import java.util.List;

public class LocalDetailRepository implements IDetailRepository {
    List<Detail> details = new ArrayList<>();

    @Override
    public int getParam1(String name) {
        return 0;
    }

    @Override
    public boolean add(Detail entity) {
        return false;
    }

    @Override
    public boolean remove(Detail entity) {
        return false;
    }

    @Override
    public Detail findById(int id) {
        return null;
    }

    @Override
    public List<Detail> getAll() {
        return null;
    }
}
