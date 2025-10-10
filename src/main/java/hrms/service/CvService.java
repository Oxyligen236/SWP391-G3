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

    public boolean updateCvStatus(int cvId, String status) {
        return cvDao.updateCvStatus(cvId, status);
    }

    public boolean addCV(CVs cv) {
        return cvDao.addCV(cv);
    }

    public List<CVJobDetailDTO> searchCVs(int jobID, String name, String email, String phone, String gender, String status) {
        return cvDao.searchCVs(jobID, name, email, phone, gender, status);
    }

}
