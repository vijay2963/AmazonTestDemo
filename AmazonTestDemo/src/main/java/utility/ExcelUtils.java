package utility;
import java.io.IOException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtils extends BaseClass{
	static XSSFWorkbook workbook;
	static XSSFSheet sheet;

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To initialize a workBook.
	 * Attribute: excelPath - To pass the excel path in the attribute. 
	 */

	public ExcelUtils() {

		try {
			workbook= new XSSFWorkbook(System.getProperty("user.dir")+"\\TestData\\InputData.xlsx");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To get the row count of the excel.
	 *
	 */
	public static void getRowCount() {
		int rowRount=	sheet.getLastRowNum();
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To get the data from the excel.
	 *Attribute: sheetname,colName,romNum - excel sheet name, name of the coloumn and rownum. 
	 */
	public static String getCellData(String sheetName,String colName,int rowNum){

		int index = workbook.getSheetIndex(sheetName);
		sheet = workbook.getSheetAt(index);
		XSSFRow row = sheet.getRow(0);
		int col_Num = 0;
		for(int i=0;i<row.getLastCellNum();i++){
			if(row.getCell(i).getStringCellValue().trim().equals(colName.trim())){
				col_Num=i;
				break;
			}
		}
		row = sheet.getRow(rowNum-1);
		XSSFCell cell = row.getCell(col_Num);
		if(cell==null){
			System.out.println("cell is not present");
			return "";
		}
		switch (cell.getCellTypeEnum()) {
		case STRING:
			return cell.getStringCellValue();
		default:
			System.out.println("no matching enum date type found");
			break;
		}
		return colName;
	}

}
