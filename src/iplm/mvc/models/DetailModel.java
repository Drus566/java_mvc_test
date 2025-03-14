package iplm.mvc.models;

import iplm.data.config.Config;
import iplm.data.repository.RepositoryType;
import iplm.data.types.Detail;
import iplm.data.types.DetailName;
import iplm.data.types.DetailParameterType;
import iplm.interfaces.observer.IObservable;
import iplm.interfaces.observer.IObserver;
import iplm.data.service.DetailService;
import iplm.utility.DialogUtility;
import iplm.utility.FilesystemUtility;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DetailModel implements IModel, IObservable<Detail> {
    private List<IObserver<Detail>> m_observers = new ArrayList<>();
    private DetailService m_service;
    private String m_details_path;
    private final String m_svn_repo_path = "База";
    private final String m_detail_name_path = "Детали";

    public void setDetailsPath(String path) { m_details_path = path; }
    public String getDetailsPath() { return m_details_path; }

    public boolean openDetailDir() {
        if (m_details_path == null || m_details_path.isEmpty()) {
            DialogUtility.showErrorDialog("Путь к детали не указан");
            return false;
        }
        FilesystemUtility.openDir(getDetailsPath());
        return true;
    }

//    public boolean deleteDetailDir() {
//        if (m_details_path == null || m_details_path.isEmpty()) {
//            DialogUtility.showErrorDialog("Путь к детали не указан");
//            return false;
//        }
//        FilesystemUtility.openDir(getDetailsPath());
//        return true;
//    }
//
//    public boolean renameDetailDir() {
//        if (m_details_path == null || m_details_path.isEmpty()) {
//            DialogUtility.showErrorDialog("Путь к детали не указан");
//            return false;
//        }
//        FilesystemUtility.openDir(getDetailsPath());
//        return true;
//    }

    public boolean scanDetailDir() {
        ArrayList<Path> roots = FilesystemUtility.getRootDirs();
        for (Path r : roots) {
            r = r.resolve(Paths.get(m_svn_repo_path));
            r = r.resolve(Paths.get(m_detail_name_path));

            String path = r.toAbsolutePath().toString();
            if (FilesystemUtility.isDirExists(path)) {
                setDetailsPath(path);
//                System.out.println(FilesystemUtility.getAllDirsNamesInDir(getDetailsPath()));
//                System.out.println(FilesystemUtility.getAllDirsNamesInDir(getDetailsPath()).get(0));
                return true;
            }
        }
        return false;
    }

    public DetailModel() {
        m_service = new DetailService(RepositoryType.ORIENTDB);
        /* Подгружаем путь к деталям из конфига */
        setDetailsPath(Config.getInstance().readSVNPath());
    }

    public String addDetailName(String name) { return m_service.addDetailName(name); }
    public boolean deleteDetailName(String id) { return m_service.deleteDetailName(id); }
    public String updateDetailName(DetailName detail_name) { return m_service.updateDetailName(detail_name); }
    public ArrayList<DetailName> getDetailNames() { return m_service.getDetailNames(); }

    public String addDetailParameterType(DetailParameterType detail_parameter_type) { return m_service.addDetailParameterType(detail_parameter_type); }
    public boolean deleteDetailParameterType(String id) { return m_service.deleteDetailParameterType(id); }
    public String updateDetailParameterType(DetailParameterType detail_parameter_type) { return m_service.updateDetailParameterType(detail_parameter_type); }
    public ArrayList<DetailParameterType> getDetailParameterTypes() { return m_service.getDetailParameterTypes(); }

    public String addDetail(Detail detail) { return m_service.addDetail(detail); }
    public String updateDetail(Detail detail) { return m_service.updateDetail(detail); }
    public boolean deleteDetail(String id) { return m_service.deleteDetail(id); }
    public ArrayList<Detail> getAllDetails() { return m_service.getAllDetails(false); }
    public ArrayList<Detail> getAllDetailsWithDepends() { return m_service.getAllDetails(true); }
    public ArrayList<Detail> getDetails(String request) { return m_service.getDetails(request, false); }
    public ArrayList<Detail> getDetailsWithDepends(String request) { return m_service.getDetails(request, true); }
    public Detail getDetailByID(String id) { return m_service.getDetailByID(id, false); }
    public Detail getDetailByIDWithDepends(String id) { return m_service.getDetailByID(id, true); }

//    public ArrayList<Detail> getAll() { return m_service.getAll(); }
//
//    public Detail getById(String id) { return m_service.getById(id); }
//
//
//    /* get detail list */
//    public ArrayList<Detail> get(String request) { return m_service.get(request); }
//
//    /* return id */
//    public String add(Detail d) {
//        return m_service.add(d);
//    }
//
//    /* return id */
//    public String delete(String id) { return m_service.delete(id); }
//
//    /* return id */
//    public String update(Detail detail) { return m_service.update(detail); }
//
//    public boolean rebuildIndex() { return m_service.rebuildIndex(); }

    @Override
    public void addObserver(IObserver<Detail> observer) { m_observers.add(observer); }

    @Override
    public void removeObserver(IObserver<Detail> observer) {
        m_observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
//        for (IObserver<Detail> observer : observers) {
//            if (observer != null) observer.update();
//
//        }
    }

    @Override
    public void init() {

    }
}
