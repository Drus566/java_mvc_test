package iplm.mvc.models;

import iplm.data.config.DetailConfig;
import iplm.data.repository.RepositoryType;
import iplm.data.types.DetailName;
import iplm.data.types.DetailParameterType;
import iplm.interfaces.observer.IObservable;
import iplm.interfaces.observer.IObserver;
import iplm.data.service.DetailService;
import iplm.utility.DialogUtility;
import iplm.utility.FilesystemUtility;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DetailModel implements IModel, IObservable<iplm.data.types.Detail> {
    private List<IObserver<iplm.data.types.Detail>> m_observers = new ArrayList<>();
    private DetailService m_service;
    private String m_details_path;
    private final String m_svn_repo_path = "База";
    private final String m_detail_name_path = "Детали";

    public void setDetailsPath(String path) { m_details_path = path; }
    public String getDetailsPath() { return m_details_path; }

    public boolean openDetailPdf(String detail_name) {
        if (m_details_path != null && !m_details_path.isEmpty()) {
            Path name = Paths.get(detail_name);
            Path path = Paths.get(m_details_path);
            Path detail_name_dir_path = path.resolve(name);
            if (FilesystemUtility.isDirExists(detail_name_dir_path.toAbsolutePath().toString())) {
                ArrayList<Path> files = FilesystemUtility.getFilesEndsWith(detail_name_dir_path.toAbsolutePath().toString(), ".pdf");
                if (files != null) {
                    String f_path = files.get(0).toAbsolutePath().toString();
                    FilesystemUtility.openFile(f_path);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean openDetailsDir() {
        if (m_details_path == null || m_details_path.isEmpty()) {
            DialogUtility.showErrorDialog("Путь к детали не указан");
            return false;
        }
        FilesystemUtility.openDir(getDetailsPath());
        return true;
    }

    public boolean openDetailDir(String detail_name) {
        if (m_details_path != null && !m_details_path.isEmpty()) {
            Path name = Paths.get(detail_name);
            Path path = Paths.get(m_details_path);
            Path detail_name_dir_path = path.resolve(name);
            if (FilesystemUtility.isDirExists(detail_name_dir_path.toAbsolutePath().toString())) {
                FilesystemUtility.openDir(detail_name_dir_path.toAbsolutePath().toString());
                return true;
            }
        }
        return false;
    }

    // получем все детали, получаем все имена
    public boolean syncDetailDir() {
        if (m_details_path == null || m_details_path.isEmpty()) {
            DialogUtility.showErrorDialog("Путь к детали не указан");
            return false;
        }

        ArrayList<iplm.data.types.Detail> exists_details = getAllDetails();
        if (exists_details == null) {
            DialogUtility.showErrorDialog("Ошибка получения существующих деталей");
            return false;
        }

        ArrayList<DetailName> exists_names = getDetailNames();
        if (exists_names == null) {
            DialogUtility.showErrorDialog("Ошибка получения существующих наименований деталей");
            return false;
        }

        HashSet<String> new_names = new HashSet<>();
        HashMap<String, String> new_details = new HashMap();
        ArrayList<String> all_names = FilesystemUtility.getAllDirsNamesInDir(m_details_path);
        for (String n : all_names) {
            String[] parts = n.split(" - ");
            if (parts != null && parts.length > 1) {
                String decimal_number = parts[0].trim();
                String name = parts[1].trim();

                new_names.add(name);
                new_details.put(decimal_number, name);
            }
        }

        for (String name : new_names) {
            boolean exists = false;
            for (DetailName exist_name : exists_names) {
                if (exist_name.name.equals(name)) {
                    exists = true;
                    break;
                }
            }

            if (!exists && addDetailName(name) == null) {
                DialogUtility.showErrorIfExists();
                return false;
            }
        }

        for (Map.Entry<String, String> entry : new_details.entrySet()) {
            String decimal_number = entry.getKey();
            String name = entry.getValue();

            boolean exists = false;
            for (iplm.data.types.Detail d : exists_details) {
                if (d.decimal_number.equals(decimal_number)) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                iplm.data.types.Detail new_detail = new iplm.data.types.Detail();
                new_detail.busy = false;
                new_detail.user_busy = null;
                new_detail.decimal_number = decimal_number;
                new_detail.deleted = false;
                new_detail.name = name;
                new_detail.params = null;

                if (addDetail(new_detail) == null) {
                    DialogUtility.showErrorIfExists();
                    return false;
                }
            }
        }
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
        setDetailsPath(DetailConfig.getInstance().readSVNPath());
    }

    public String addDetailName(String name) { return m_service.addDetailName(name); }
    public boolean deleteDetailName(String id) { return m_service.deleteDetailName(id); }
    public String updateDetailName(DetailName detail_name) { return m_service.updateDetailName(detail_name); }
    public ArrayList<DetailName> getDetailNames() { return m_service.getDetailNames(); }

    public String addDetailParameterType(DetailParameterType detail_parameter_type) { return m_service.addDetailParameterType(detail_parameter_type); }
    public boolean deleteDetailParameterType(String id) { return m_service.deleteDetailParameterType(id); }
    public String updateDetailParameterType(DetailParameterType detail_parameter_type) { return m_service.updateDetailParameterType(detail_parameter_type); }
    public ArrayList<DetailParameterType> getDetailParameterTypes() { return m_service.getDetailParameterTypes(); }
    public ArrayList<String> getDetailParameterTypeReferences(String detail_parameter_type_rid) { return m_service.getDetailParameterTypeReferences(detail_parameter_type_rid); }

    public String addDetail(iplm.data.types.Detail detail) { return m_service.addDetail(detail); }
    public String updateDetail(iplm.data.types.Detail detail) { return m_service.updateDetail(detail); }
    public boolean deleteDetail(String id) { return m_service.deleteDetail(id); }
    public ArrayList<iplm.data.types.Detail> getAllDetails() { return m_service.getAllDetails(false); }
    public ArrayList<iplm.data.types.Detail> getAllDetailsWithDepends() { return m_service.getAllDetails(true); }
    public ArrayList<iplm.data.types.Detail> getDetails(String request) { return m_service.getDetails(request, false); }
    public ArrayList<iplm.data.types.Detail> getDetailsWithDepends(String request) { return m_service.getDetails(request, true); }
    public iplm.data.types.Detail getDetailByID(String id) { return m_service.getDetailByID(id, false); }
    public iplm.data.types.Detail getDetailByIDWithDepends(String id) { return m_service.getDetailByID(id, true); }

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
    public void addObserver(IObserver<iplm.data.types.Detail> observer) { m_observers.add(observer); }

    @Override
    public void removeObserver(IObserver<iplm.data.types.Detail> observer) {
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
