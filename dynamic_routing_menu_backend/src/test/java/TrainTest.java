//import org.example.Application;
//import org.example.entity.Train;
//import org.example.service.TrainService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.IOException;
//
//@SpringBootTest(classes = Application.class)
//public class TrainTest {
//
//
//    @Autowired
//    private TrainService trainService;
//
//    @Test
//    public void testTrainModel() throws IOException {
//        String csvSourcePath = "F:\\PythonWeb\\myDjango\\app\\dataset\\train_dataset_03.csv";
//        String csvDestPath = "";
//        String labelColumn = "Label";
//        String modelVersion = "v1.0.0";
//        Long batchSize = 32L;
//        Long rows = 3000L;
//        Long numEpochs = 1L;
//        double lr = 0.001;
//        Long stepSize = 32L;
//        double gamma = 0.1;
//        Long modeId = 1L;
//        String modelName = "model-01";
//        Long frameId = 1L;
//        String modelSavePath = "F:\\PythonWeb\\myDjango\\app\\model";
//        String taskId = "task_123";
//        String modelPath = "F:\\PythonWeb\\myDjango\\app\\model\\model_03.pkl";
//        boolean trainModelWays = false;
//
//        Train train = new Train();
//        train.setCsvSourcePath(csvSourcePath);
//        train.setCsvDestPath(csvDestPath);
//        train.setLabelColumn(labelColumn);
//        train.setBatchSize(batchSize);
//        train.setModelVersion(modelVersion);
//        train.setRows(rows);
//        train.setNumEpochs(numEpochs);
//        train.setLr(lr);
//        train.setModelPath(modelPath);
//        train.setTrainModelWays(trainModelWays);
//        train.setStepSize(stepSize);
//        train.setGamma(gamma);
//        train.setModelId(modeId);
//        train.setModelName(modelName);
//        train.setFrameId(frameId);
//        train.setModelSavePath(modelSavePath);
//        train.setSaveCsvPath(false);
//        train.setTaskId(taskId);
//        trainService.trainModel(train);
//    }
//
//}
