package ExtrenaData;

import com.shaft.tools.io.ExcelFileManager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ExcelFile {

    ExcelFileManager excelFileManager;

    @BeforeClass
    public void beforeClass(){
        excelFileManager = new ExcelFileManager("");
    }

    @Test
    public void excelFile(){

    }

}
