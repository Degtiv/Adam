package space.deg.adam.domain.goals;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class GoalUtils {
    public static void saveImage(String uploadPath, MultipartFile image, Goal goal) throws IOException {
        if (image != null && !image.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String imageName = UUID.randomUUID().toString() + "." + image.getOriginalFilename();
            image.transferTo(new File(uploadPath + "/" + imageName));
            goal.setImage(imageName);
        }
    }
}
