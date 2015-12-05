package helpers;
import org.testng.annotations.DataProvider;
import java.io.IOException;
import java.util.List;

/**
 * Created by Admin on 06.11.2015.
 */
public class DataProviders {
    private static final String reg_data_file = "./src/main/resources/RegistrationDate.csv";

    @DataProvider (name = "registrationDate")
    public static Object[][] getRegisterDate() throws IOException {
        return getData(reg_data_file, ";");
    }

    private static Object[][] getData(String path, String divider) throws IOException {
        List<String> data = Helper.readAllLines(path);
        Object[][] dataRows = new Object[data.size()][data.get(0).split(divider).length];
        for (int i = 0; i < data.size(); i++) {
            dataRows[i] = data.get(i).split(divider);
        }
        return dataRows;
    }
}
