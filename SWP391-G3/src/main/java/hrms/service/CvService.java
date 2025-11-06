package hrms.service;

import java.util.ArrayList;
import java.util.List;

import hrms.dao.CVsDAO;
import hrms.dao.JobDAO;
import hrms.dto.CVJobDetailDTO;
import hrms.model.CVs;

public class CvService {

    private final CVsDAO cvDao = new CVsDAO();
    private final JobDAO jobDao = new JobDAO();

    private CVJobDetailDTO convertToDTO(CVs cv) {
        if (cv == null) {
            return null;
        }

        String jobTitle = null;
        if (cv.getJdID() > 0) {
            var job = jobDao.getJobByJobId(cv.getJdID());
            if (job != null) {
                jobTitle = job.getJobTitle();
            }
        }

        return new CVJobDetailDTO(
                cv.getCvID(),
                cv.getJdID(),
                jobTitle,
                cv.getName(),
                cv.getDob(),
                cv.getGender(),
                cv.getAddress(),
                cv.getNationality(),
                cv.getEmail(),
                cv.getPhone(),
                cv.getExperience(),
                cv.getEducation(),
                cv.getSkills(),
                cv.getAboutMe(),
                cv.getStatus()
        );
    }

    private List<CVJobDetailDTO> convertToDTOList(List<CVs> cvList) {
        List<CVJobDetailDTO> dtoList = new ArrayList<>();
        if (cvList != null) {
            for (CVs cv : cvList) {
                CVJobDetailDTO dto = convertToDTO(cv);
                if (dto != null) {
                    dtoList.add(dto);
                }
            }
        }
        return dtoList;
    }

    public List<CVJobDetailDTO> getAllCVJobTitle() {
        List<CVs> cvList = cvDao.getAll();
        return convertToDTOList(cvList);
    }

    public CVJobDetailDTO getCvWithJobTitle(int cvId) {
        CVs cv = cvDao.getCvById(cvId);
        return convertToDTO(cv);
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
        return convertToDTOList(cvList);
    }
}
