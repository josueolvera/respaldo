package mx.bidg.service;

import mx.bidg.model.BranchsGoals;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by josueolvera on 12/09/16.
 */
public interface BranchsGoalsService {
    BranchsGoals  save (BranchsGoals branchsGoals);
    BranchsGoals update(BranchsGoals branchsGoals);
    BranchsGoals findById(Integer idBranch);
    List<BranchsGoals> findAll();
    boolean delete(BranchsGoals branchsGoals);
    List<BranchsGoals> updateFromExcel(MultipartFile file) throws IOException, InvalidFormatException;
}
