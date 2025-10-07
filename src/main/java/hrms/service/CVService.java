package hrms.service;

import java.util.ArrayList;
import java.util.List;

import hrms.dao.CVsDAO;
import hrms.dao.JobDAO;
import hrms.dto.CVJobDetailDTO;
import hrms.model.CVs;
import hrms.model.JobDescription;

public class CVService {

    private final CVsDAO cvDao = new CVsDAO();
    private final JobDAO jobDao = new JobDAO();

    public List<CVJobDetailDTO> getAllCVWithJobTitle(List<CVs> cvsList) {
        List<CVJobDetailDTO> cvDTOList = new ArrayList<>();

        for (CVs cv : cvsList) {
            // Tìm Job tương ứng với JDID
            JobDescription job = jobDao.getJobByCvId(cv.getCvID());

            String jobTitle = (job != null) ? job.getJobTitle() : "N/A";

            CVJobDetailDTO dto = new CVJobDetailDTO(
                    cv.getCvID(),
                    jobTitle,
                    cv.getName(),
                    cv.getEmail(),
                    cv.getPhone(),
                    cv.getCv_Description(),
                    cv.getStatus()
            );

            cvDTOList.add(dto);
        }

        return cvDTOList;
    }

    public CVJobDetailDTO getCvWithJobDetail(int cvId) {
        CVs cv = cvDao.getCvById(cvId);
        if (cv == null) {
            return null; // Hoặc ném ngoại lệ nếu cần
        }

        JobDescription job = jobDao.getJobByCvId(cv.getCvID());
        String jobTitle = (job != null) ? job.getJobTitle() : "N/A";

        return new CVJobDetailDTO(
                cv.getCvID(),
                jobTitle,
                cv.getName(),
                cv.getEmail(),
                cv.getPhone(),
                cv.getCv_Description(),
                cv.getStatus()
        );
    }

}
