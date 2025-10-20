package hrms.service;

import java.util.ArrayList;
import java.util.List;

import hrms.dao.CVsDAO;
import hrms.dao.JobDAO;
import hrms.dto.CVJobDetailDTO;
import hrms.model.CVs;
import hrms.model.JobDescription;

public class CvService {

    private final CVsDAO cvDao = new CVsDAO();
    private final JobDAO jobDao = new JobDAO();

    private CVJobDetailDTO mapToDTO(CVs cv, String jobTitle) {
        return new CVJobDetailDTO(
                cv.getCvID(),
                cv.getJdID(),
                jobTitle,
                cv.getName(),
                cv.getGender(),
                cv.getAddress(),
                cv.getNationality(),
                cv.getEmail(),
                cv.getPhone(),
                cv.getCv_Description(),
                cv.getStatus()
        );
    }

    public List<CVJobDetailDTO> getAllCVJobTitle() {
        List<CVs> cvList = cvDao.getAll();
        List<CVJobDetailDTO> dtoList = new ArrayList<>();

        for (CVs cv : cvList) {
            JobDescription job = jobDao.getJobByJobId(cv.getJdID());
            String jobTitle = (job != null) ? job.getJobTitle() : null;
            dtoList.add(mapToDTO(cv, jobTitle));
        }

        return dtoList;
    }

    public CVJobDetailDTO getCvWithJobTitle(int cvId) {
        CVs cv = cvDao.getCvById(cvId);
        if (cv == null) {
            return null;
        }

        JobDescription job = jobDao.getJobByJobId(cv.getJdID());
        String jobTitle = (job != null) ? job.getJobTitle() : null;

        return mapToDTO(cv, jobTitle);
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
        List<CVs> cvList = cvDao.searchCVs(jobID, name, email, phone, gender, status);
        List<CVJobDetailDTO> dtoList = new ArrayList<>();

        for (CVs cv : cvList) {
            JobDescription job = jobDao.getJobByJobId(cv.getJdID());
            String jobTitle = (job != null) ? job.getJobTitle() : null;
            dtoList.add(mapToDTO(cv, jobTitle));
        }

        return dtoList;
    }
}
