package hrms.service;

import java.util.List;

import hrms.dao.CVsDAO;
import hrms.dto.CVJobDetailDTO;
import hrms.model.CVs;

public class CvService {

    private final CVsDAO cvDao = new CVsDAO();

    public List<CVJobDetailDTO> getAllCVJobTitle() {
        return cvDao.getAllCVJobTitle();
    }

    public CVJobDetailDTO getCvWithJobTitle(int cvId) {
        return cvDao.getCvWithJobTitle(cvId);
    }

    public List<CVs> getAllCVs() {
        return cvDao.getAll();
    }

    public void updateCV(CVs cv) {
        cvDao.updateCV(cv);
    }

    public boolean addCV(CVs cv) {
        return cvDao.addCV(cv);
    }

    public List<CVJobDetailDTO> searchCVs(String name, String email, String phone) {
        return cvDao.searchCVs(name, email, phone);
    }

}
