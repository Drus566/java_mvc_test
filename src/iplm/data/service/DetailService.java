package iplm.data.service;

import iplm.data.repository.IDetailRepository;

public class DetailService {
    private IDetailRepository m_detail_repository;

    public DetailService(IDetailRepository detail_repository) {
        m_detail_repository = detail_repository;
    }

    public DetailService() {
        // Заголовки столбцов
    }
}
