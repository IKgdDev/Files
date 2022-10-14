import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {
    private List<String[]> entry = new ArrayList<>();

    public ClientLog() {
        entry.add(new String[]{"productNum", "amount"});
    }

    public void log(int productNum, int amount) {
        entry.add(new String[]{Integer.toString(productNum), Integer.toString(amount)});
    }

    public void exportAsCSV(File file) throws IOException {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(file))) {
            csvWriter.writeAll(entry);
        }
    }
}
