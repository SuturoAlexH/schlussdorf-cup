package stepDefinitions.customTypes;

import com.google.common.io.Files;
import com.schlussdorf.factory.ResultBuilder;
import io.cucumber.java.DataTableType;
import com.schlussdorf.model.Result;
import com.schlussdorf.constants.FolderConstants;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class ResultTransformer {
    @DataTableType
    public Result resultEntry(Map<String, String> entry) throws IOException {
        ResultBuilder resultBuilder = new ResultBuilder();

        createFolderIfNotExits(FolderConstants.IMAGE_FOLDER);
        UUID uuid = UUID.randomUUID();
        File i = new File(FolderConstants.IMAGE_FOLDER + uuid + ".jpeg");
        String imageName = entry.get("image") != null? entry.get("image"): "test_image_1.jpeg";
        File imageFile = new File(ResultTransformer.class.getResource("/images/" + imageName).getFile());
        Files.copy(imageFile, i);


        return resultBuilder.uuid(uuid)
                .fireDepartment(entry.get("fire department"))
                .place(entry.get("place") != null? Integer.parseInt(entry.get("place")): 0)
                .time(entry.get("time"))
                .mistakePoints(entry.get("mistake points"))
                .finalScore(entry.get("final score") != null? Double.parseDouble(entry.get("final score")): null)
                .image(i)
                .build();
    }

    private static void createFolderIfNotExits(final String folderPath){
        File saveFolder = new File(folderPath);
        if(!saveFolder.exists()){
            saveFolder.mkdir();
        }
    }
}
