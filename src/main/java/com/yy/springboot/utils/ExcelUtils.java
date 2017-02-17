package com.yy.springboot.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.yy.springboot.base.CheckBean;
import com.yy.springboot.utils.CommEnum.BookDataEnum;
import com.yy.springboot.utils.CommEnum.CellDataFormatEnum;
import com.yy.springboot.utils.CommEnum.CheckResultTypeEnum;
import com.yy.springboot.utils.CommEnum.CheckTitleEnum;
import com.yy.springboot.utils.CommEnum.ColumnCheckTypeEnum;
import com.yy.springboot.utils.CommEnum.CompanyTypeEnum;
import com.yy.springboot.utils.CommEnum.DataDisplayMode;
import com.yy.springboot.utils.CommEnum.ExcelSheetEnum;

/**
 * Excel组件
 * 
 * @author Snowolf
 * @version 1.0
 * @since 1.0
 */
public class ExcelUtils {
	private static final Logger log = LoggerFactory.getLogger(ExcelUtils.class);
	/**
	 * Excel 2003
	 */
	private final static String XLS = "xls";
	/**
	 * Excel 2007
	 */
	private final static String XLSX = "xlsx";

	/***
	 * 导入Excel所有sheet数据
	 * 
	 * <li>1、读取excel数据</li>
	 * <li>2、校验数据的合法性（日期，金额，字符长度（和数据库结构比较））</li>
	 * <li>3、合法数据绑定到bean对象中（反射）</li>
	 * <li>4、得到数据层面校验通过的bean对象集合，</li>
	 * 
	 * @param file
	 *            导入数据文件
	 * @param entityClassMap
	 *            bean对象类型bean.class
	 * @param columnArrayMap
	 *            字段列数组 （需要导入的字段数组）
	 * @param checkColumnMap
	 *            需要校验格式的字段列Map
	 * @throws Exception 
	 * @throws SQLException
	 */
	public static <T extends CheckBean> Map<String, ArrayList<CheckBean>> excelAllToList(File file,
			Map<String, Class> entityClassMap, Map<String, String[]> columnArrayMap,
			Map<String, Map<String, ColumnCheckTypeEnum>> checkColumnMap) throws Exception {
		Map<String, ArrayList<CheckBean>> map = new HashMap<String, ArrayList<CheckBean>>();
		List<CheckBean> list = new ArrayList<CheckBean>();
		Workbook workbook = null;
		if (XLS.equalsIgnoreCase(FilenameUtils.getExtension(file.getName()))) {
			workbook = new HSSFWorkbook(new FileInputStream(file));
		} else if (XLSX.equalsIgnoreCase(FilenameUtils.getExtension(file.getName()))) {
			workbook = new XSSFWorkbook(new FileInputStream(file));
		} else {
			throw new IOException("导入excel出错，不支持文件类型！");
		}
		for (String sheetName : entityClassMap.keySet()) {
			if (!entityClassMap.containsKey(sheetName)) {
				throw new IOException("参数中没有sheet【" + sheetName + "】对应的对象类型！");
			}
			if (!columnArrayMap.containsKey(sheetName)) {
				throw new IOException("参数中没有sheet【" + sheetName + "】对应的字段列数组！");
			}
			list = workbookToList(workbook, sheetName, entityClassMap.get(sheetName), columnArrayMap.get(sheetName),
					checkColumnMap.get(sheetName));
			map.put(sheetName, (ArrayList<CheckBean>) list);
		}
		return map;
	}

	/***
	 * 导入Excel数据
	 * 
	 * <li>1、读取excel数据</li>
	 * <li>2、校验数据的合法性（日期，金额，字符长度（和数据库结构比较））</li>
	 * <li>3、合法数据绑定到bean对象中（反射）</li>
	 * <li>4、得到数据层面校验通过的bean对象集合，</li>
	 * 
	 * @param workbookToList
	 *            导入数据workbook
	 * @param entityClass
	 *            bean对象类型bean.class
	 * @param sheetIndex
	 *            sheet索引
	 * @param columnArray
	 *            字段列数组 （需要导入的字段数组）
	 * @param checkColumn
	 *            需要校验格式的字段列Map
	 * @throws Exception 
	 */
	private static <T extends CheckBean> List<T> workbookToList(Workbook workbook, String sheetName,
			Class<T> entityClass, String[] columnArray, Map<String, ColumnCheckTypeEnum> checkColumn)
					throws Exception {
		List<T> list = new ArrayList<T>();
		// sheet中要导出的列
		if (columnArray == null || columnArray.length < 1) {
			throw new NullPointerException("导入excel出错，导入列设置错误！");
		}

		// 拿到sheet
		Sheet sheet = workbook.getSheet(sheetName);
		if(sheet == null){
			throw new Exception("myException申报文件中没有sheet（" + sheetName + "）！");
		}
		// 解析公式结果
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		// 每个sheet中的数据
		List<Map<String, Object>> dataList = readSheet(sheet, evaluator, columnArray);

		// 不在数据库中的列
		List<String> outColumnDatas = new ArrayList<String>();
		List<String> columnList = new ArrayList<String>();

		for (int i = 0; i < columnArray.length; i++) {
			if (!columnList.contains(columnArray[i])) {
				outColumnDatas.add(columnArray[i]);
			}
		}

		// 遍历数据库表对应的字段列信息
		for (int j = 0; j < dataList.size(); j++) {
			// 拿到每一行的数据
			Map<String, Object> rowData = dataList.get(j);
			T o = null;
			try {
				o = (T) entityClass.newInstance();
			} catch (IllegalAccessException e) {
				throw new IllegalAccessException("导入excel出错，错误信息：" + e.getMessage());
			}
			if (checkColumn == null) {
				checkColumn = new HashMap<String, ColumnCheckTypeEnum>();
			}
			// 2、循环列（数据库中不包含）设置，依次设置每一列
			for (int k = 0; k < outColumnDatas.size(); k++) {
				String columnLowName = outColumnDatas.get(k);
				Object value = rowData.get(outColumnDatas.get(k));

				String errMsg = "导入excel出错，错误位置>>：sheet【" + sheetName + "】中，第【" + (j + 1 + 1) + "】行，第【"
						+ (getIndexOfArrayItem(columnLowName, columnArray) + 1 + 1) + "】列。错误信息：";
				// 1、先根据设置，校验自定义校验的列
				if (checkColumn.containsKey(columnLowName) == false
						|| ColumnCheckTypeEnum.CHECK_TYPE_NOTNULL.equals(checkColumn.get(columnLowName)) == false) {
					if (value == null) {
						continue;
					}
				}
				customColumnCheck(value, errMsg, checkColumn.get(columnLowName));
				// 通过属性名称获取属性，把值设置到属性里面
				Field field = entityClass.getDeclaredField(columnLowName);
				field.setAccessible(true); // 设置属性可访问， private
				try {
					field.set(o, value.toString());
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(errMsg + e.getMessage());
				} catch (IllegalAccessException e) {
					throw new IllegalAccessException(errMsg + e.getMessage());
				}
			}
			list.add(o);
		}
		return list;
	}

	/**
	 * @param value
	 * @param errMsg
	 * @param checkType
	 */
	private static void customColumnCheck(Object value, String errMsg, ColumnCheckTypeEnum checkType) {
		// 非空校验
		if (ColumnCheckTypeEnum.CHECK_TYPE_NOTNULL.equals(checkType)) {
			if (value == null) {
				throw new InputMismatchException(errMsg + "此列为非空列，请检查excel数据是否为空！");
			}
		}
		// 整数类型校验
		else if (ColumnCheckTypeEnum.CHECK_TYPE_LONG.equals(checkType)) {
			try {
				Long.parseLong(value.toString());
			} catch (NumberFormatException e) {
				throw new NumberFormatException(
						errMsg + "此列为整数列，单元格应设置为文本类型，请检查excel数据是否整数数字或单元格是否为文本类型！" + e.getMessage());
			}
		}
		// 价格类型校验，取2位小数
		else if (ColumnCheckTypeEnum.CHECK_TYPE_PRICE.equals(checkType)) {
			try {
				String price = value.toString();
				new BigDecimal(price);
				if (price.indexOf(".") > -1 && price.split("\\.")[1].length() > 2) {
					throw new InputMismatchException(errMsg + "此列为金额数值列，小数点后不超过2位小数，请检查excel数据是否合格！");
				}
			} catch (NumberFormatException e) {
				throw new NumberFormatException(errMsg + "此列为金额数值列，请检查excel数据是否合格！");
			}
		}
		// 价格类型校验，取4位小数
		else if (ColumnCheckTypeEnum.CHECK_TYPE_PRICE_1.equals(checkType)) {
			try {
				String price = value.toString();
				new BigDecimal(price);
				if (price.indexOf(".") > -1 && price.split("\\.")[1].length() > 4) {
					throw new InputMismatchException(errMsg + "此列为金额数值列，小数点后不超过4位小数，请检查excel数据是否合格！");
				}
			} catch (NumberFormatException e) {
				throw new NumberFormatException(errMsg + "此列为金额数值列，请检查excel数据是否合格！");
			}
		}
	}

	/***
	 * 读取单个sheet
	 * <p>
	 * 导入Excel数据使用私有方法
	 * </p>
	 * 
	 * @param sheet
	 *            单个sheet
	 * @param evaluator
	 *            解析公式结果
	 * @param columnArray
	 *            字段列数组
	 * @throws Exception 
	 */
	private static List<Map<String, Object>> readSheet(Sheet sheet, FormulaEvaluator evaluator, String... columnArray) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int firstRowNum = sheet.getFirstRowNum();
		int lastRowNum = sheet.getLastRowNum();
		int lastCellNum = sheet.getRow(0).getLastCellNum();
		if(lastCellNum != columnArray.length){
			throw new Exception("myException申报文件请使用标准模板（总共"+ columnArray.length +"列），实际"+ lastCellNum +"列，请返回“申报材料下载”页面下载最新模板进行申报！");
		}
		// 从第一行开始读取数据
		for (int rowIndex = firstRowNum; rowIndex <= lastRowNum; rowIndex++) {
			Row row = sheet.getRow(rowIndex);
			if(row == null){
				return list;
			}
			short firstCellNum = row.getFirstCellNum();
			// short maxColIx = row.getLastCellNum();
			Map<String, Object> rowMap = new HashMap<String, Object>();
			// 读取列的时候，按照设置好的字段列的数量长度循环读取
			for (short colIndex = firstCellNum; colIndex < columnArray.length; colIndex++) {
				Cell cell = row.getCell(new Integer(colIndex)); // 从第一列开始导入
				String cellFormula = "";
				try {
					cellFormula = cell.getCellFormula();
				} catch (Exception e) {
				}
				if (!StringUtil.isEmpty(cellFormula)) {
					throw new Exception("myException申报文件中sheet(" + sheet.getSheetName() + ")的第"+ (colIndex+1) +"列有单元格使用了公式，可能会造成该列的值取不到，请不要使用公式！");
				}
				CellValue cellValue = evaluator.evaluate(cell);
				if (cellValue == null) {
					continue;
				}
				// 经过公式解析，最后只存在Boolean、Numeric和String三种数据类型，此外就是Error了
				// 其余数据类型，根据官方文档，完全可以忽略http://poi.apache.org/spreadsheet/eval.html
				switch (cellValue.getCellType()) {
				case Cell.CELL_TYPE_BOOLEAN:
					rowMap.put(columnArray[colIndex], cellValue.getBooleanValue());
					break;
				case Cell.CELL_TYPE_NUMERIC:
					// 这里的日期类型会被转换为数字类型，需要判别后区分处理
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						rowMap.put(columnArray[colIndex], cell.getDateCellValue());
					} else {
						rowMap.put(columnArray[colIndex], cellValue.getNumberValue());
					}
					break;
				case Cell.CELL_TYPE_STRING:
					rowMap.put(columnArray[colIndex], cellValue.getStringValue());
					break;
				case Cell.CELL_TYPE_FORMULA:
					break;
				case Cell.CELL_TYPE_BLANK:
					break;
				case Cell.CELL_TYPE_ERROR:
					break;
				default:
					break;
				}
			}
			list.add(rowMap);
		}

		return list;
	}

	private static int getIndexOfArrayItem(String arrItem, String[] array) {
		int index = -1;
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(arrItem)) {
				index = i;
				break;
			}
		}
		return index;
	}

	/***
	 * 导出Excel数据
	 * 
	 * @param sheetName
	 *            sheet名称
	 * @param fileName
	 *            导出文件名
	 * @param list
	 *            要导出的数据
	 * @param dataFormatMap
	 *            可以设置某个列的数据格式 ，key：列名；value：格式值
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void exportDatas(String sheetName, String fileName, List<Map<String, Object>> list,
			Map<String, CellDataFormatEnum> dataFormatMap, HttpServletRequest request, HttpServletResponse response)
					throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName);
		if (CollectionUtils.isEmpty(list)) {
			// 输出空文件
			FileUtil.outputFile(fileName, request, response, wb, null);
			return;
		}
		int rowNum = 0;

		// 设置单元格样式(标题样式)
		HSSFCellStyle styleTitle = wb.createCellStyle();
		styleTitle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleTitle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleTitle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleTitle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 对齐方式
		styleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 字体
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		styleTitle.setFont(font);

		// 设置单元格样式(数据值样式)
		HSSFCellStyle styleContent = wb.createCellStyle();
		styleContent.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleContent.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleContent.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleContent.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleContent.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		// 标题行
		HSSFRow rowTitle = sheet.createRow(rowNum++);

		int i = 0;
		HSSFCell cellH0 = rowTitle.createCell(i++);
		cellH0.setCellValue("序号");
		cellH0.setCellStyle(styleTitle);
		for (Map.Entry<String, Object> tmp : list.get(0).entrySet()) {
			HSSFCell cellH = rowTitle.createCell(i++);
			cellH.setCellValue(tmp.getKey());
			cellH.setCellStyle(styleTitle);
		}

		int cellOrder = 1;
		for (Map<String, Object> map : list) {
			HSSFRow row = sheet.createRow(rowNum++);
			int j = 0;
			// 序号
			HSSFCell cellC0 = row.createCell(j++);
			cellC0.setCellValue(cellOrder++);
			cellC0.setCellStyle(styleContent);

			for (Map.Entry<String, Object> entry : map.entrySet()) {
				HSSFCell cellC = row.createCell(j++);
				sheet.setColumnWidth(cellC.getColumnIndex(), 256 * (11 + 10));
				cellC.setCellValue(entry.getValue() + "");
				// 动态设置单元格的格式 枚举值对应到HSSFDataFormat的值
				CellDataFormatEnum df = null;
				if (dataFormatMap != null) {
					df = dataFormatMap.get(entry.getKey());
				}
				if (df != null) {
					HSSFCellStyle styleContent1 = wb.createCellStyle();
					styleContent1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
					styleContent1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
					styleContent1.setBorderRight(HSSFCellStyle.BORDER_THIN);
					styleContent1.setBorderTop(HSSFCellStyle.BORDER_THIN);
					styleContent1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
					styleContent1.setDataFormat(df.getShortEnumCode());
					cellC.setCellStyle(styleContent1);
				} else {
					cellC.setCellStyle(styleContent);
				}
			}
		}

		// 输出文件
		FileUtil.outputFile(fileName, request, response, wb, fileName);
	}

	/***
	 * 导出指定模版相同规则数据
	 * 
	 * @param file
	 *            导出数据模版文件
	 * @param bookDataMap
	 *            bean和sheet映射
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "resource" })
	public static void exportTemplateDatas(File file, Map<String, HashMap<String, Object>> bookDataMap,
			HttpServletRequest request, HttpServletResponse response, String outFileNm) throws IOException {
		InputStream is = new FileInputStream(file);
		Workbook wb = null;
		String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1); // 后缀
		if (XLS.equalsIgnoreCase(FilenameUtils.getExtension(file.getName()))) {
			wb = new HSSFWorkbook(is);
		} else if (XLSX.equalsIgnoreCase(FilenameUtils.getExtension(file.getName()))) {
			wb = new XSSFWorkbook(is);
		} else {
			throw new IOException("excel模版导出出错，不支持文件类型！");
		}

		if (CollectionUtils.isEmpty(bookDataMap)) {
			// 输出空文件
			FileUtil.outputFile(file.getName(), request, response, wb, extension);
			return;
		}

		// 根据sheet名循环
		for (String sheetNm : bookDataMap.get(BookDataEnum.BOOK_DATA.getEnumCode()).keySet()) {
			// 获得sheet
			Sheet sheet = null;
			try {
				sheet = wb.getSheet(sheetNm);
				if (sheet == null)
					continue;
			} catch (Exception e) {
				continue;
			}
			// 获得sheet对应的数据
			List<Object> beanList = (ArrayList<Object>) bookDataMap.get(BookDataEnum.BOOK_DATA.getEnumCode())
					.get(sheetNm);
			// 获得sheet数据起始行
			int start = (int) bookDataMap.get(BookDataEnum.SHEET_STARROW.getEnumCode()).get(sheetNm);
			// 属性与列下标映射
			List<String> propCols = (List<String>) bookDataMap.get(BookDataEnum.PROP_INDEX.getEnumCode()).get(sheetNm);
			// 显示方式
			String disMode = (String) bookDataMap.get(BookDataEnum.DISPLAY_MODE.getEnumCode()).get(sheetNm);
			if (DataDisplayMode.VERTICAL.getEnumCode().equals(disMode)) {
				setSheetDate2(sheet, beanList, propCols, start);
			} else {
				setSheetDate1(sheet, beanList, propCols, start);
			}
		}

		// 输出文件
		FileUtil.outputFile(outFileNm, request, response, wb, extension);
	}

	/***
	 * 导出指定模版中的数据(水平)
	 * 
	 * @param <T>
	 * 
	 * @param sheet
	 *            sheet名
	 * @param beanList
	 *            数据
	 * @param beanClosMap
	 *            bean字段和列名的映射
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private static void setSheetDate1(Sheet sheet, List<?> beanList, List<String> propCols, int startRow) {
		int rownum = startRow;
		for (Object bean : beanList) {
			createRowCopy(sheet, rownum, startRow);
			Row row = sheet.getRow(rownum);
			for (int cellnum = 0; cellnum < propCols.size(); cellnum++) {
				String value = Reflections.getFieldValue(bean, propCols.get(cellnum)) == null ? null
						: Reflections.getFieldValue(bean, propCols.get(cellnum)).toString();
				row.getCell(cellnum).setCellValue(value);
			}
			rownum++;
		}
	}

	/***
	 * 导出指定模版中的数据(垂直)
	 * 
	 * @param <T>
	 * 
	 * @param sheet
	 *            sheet名
	 * @param beanList
	 *            数据
	 * @param beanClosMap
	 *            bean字段和列名的映射
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private static void setSheetDate2(Sheet sheet, List<?> beanList, List<String> propCols, int startCol) {
		int colnum = startCol;
		for (Object bean : beanList) {
			createColCopy(sheet, colnum, startCol);
			for (int rownum = 0; rownum < propCols.size(); rownum++) {
				Row row = sheet.getRow(rownum);
				String value = Reflections.getFieldValue(bean, propCols.get(rownum)) == null ? null
						: Reflections.getFieldValue(bean, propCols.get(rownum)).toString();
				row.getCell(colnum).setCellValue(value);
			}
			colnum++;
		}
	}

	/**
	 * 新增行拷贝
	 * 
	 * @param sheet
	 *            sheet
	 * @param sourceIndex
	 *            源标行下标
	 * @param targetIndex
	 *            目标行下标
	 */
	private static void createRowCopy(Sheet sheet, int targetIndex, int sourceIndex) {
		if (sourceIndex == targetIndex) {
			Row sourceRow = sheet.getRow(sourceIndex);
			for (int column = 0; column <= sourceRow.getLastCellNum(); column++) {
				if (sourceRow.getCell(column) == null)
					continue;
				sourceRow.getCell(column).setCellValue("");
			}
			return;
		}
		sheet.createRow(targetIndex);
		Row targetRow = sheet.getRow(targetIndex);
		Row sourceRow = sheet.getRow(sourceIndex);
		if (sourceRow.getRowStyle() != null) {
			targetRow.setRowStyle(sourceRow.getRowStyle());
		}
		for (int column = 0; column <= sourceRow.getLastCellNum(); column++) {
			if (sourceRow.getCell(column) == null) {
				continue;
			}
			targetRow.createCell(column);
			if (sourceRow.getCell(column).getCellStyle() != null) {
				targetRow.getCell(column).setCellStyle(sourceRow.getCell(column).getCellStyle());
			}
		}
	}

	/**
	 * 新增列拷贝
	 * 
	 * @param sheet
	 *            sheet
	 * @param sourceIndex
	 *            源标行下标
	 * @param targetIndex
	 *            目标行下标
	 */
	private static void createColCopy(Sheet sheet, int targetIndex, int sourceIndex) {
		if (sourceIndex == targetIndex) {
			for (int rownum = 0; rownum <= sheet.getLastRowNum(); rownum++) {
				Row row = sheet.getRow(rownum);
				if (row == null) {
					continue;
				}
				if (row.getCell(sourceIndex) == null)
					continue;
				row.getCell(sourceIndex).setCellValue("");
			}
			return;
		}
		for (int rownum = 0; rownum <= sheet.getLastRowNum(); rownum++) {
			Row row = sheet.getRow(rownum);
			if (row == null) {
				continue;
			}
			row.createCell(targetIndex);
			CellStyle style = row.getCell(sourceIndex) == null ? null : row.getCell(sourceIndex).getCellStyle();
			if (style != null) {
				row.getCell(targetIndex).setCellStyle(style);
			}

		}
	}

	/**
	 * sheet校验结果
	 * 
	 * @param springBootDrugBeans
	 * @param tempFile
	 * @param sheetName
	 */
	public static void setCheckInfo(Map<String, List<? extends CheckBean>> map, File tempFile) throws Exception {
		Workbook workBook = getSheet(tempFile);
		for (String sheetName : map.keySet()) {
			setSheetCheckInfo(map.get(sheetName), workBook.getSheet(sheetName));
		}

		String path = tempFile.getAbsolutePath();
		if (tempFile.exists()) {
			tempFile.delete();
		}
		tempFile = new File(path);
		OutputStream out = new FileOutputStream(tempFile);
		workBook.write(out);
		out.flush();
		out.close();
	}

	public static <T extends CheckBean> void setSheetCheckInfo(List<? extends CheckBean> list, Sheet sheet)
			throws Exception {
		int lastCol = sheet.getRow(0).getLastCellNum();

		// 设定内容
		int start = 1;
		if ("经营企业资质信息".equals(sheet.getSheetName())) {
			start = 0;
		} else {
			// 设定标题
			addResult(sheet.getRow(0), lastCol, CheckTitleEnum.TITLE_TYPE.getEnumCode(),
					CheckTitleEnum.TITLE_REMARK.getEnumLabel());
		}
		for (int i = 0 + start; i < list.size(); i++) {
			String col1Val = list.get(i).getCheckResult();
			if (StringUtil.isEmpty(col1Val)) {
				col1Val = CheckResultTypeEnum.CHECK_PASS.getEnumCode();
			}
			addResult(sheet.getRow(i), lastCol, col1Val, list.get(i).getCheckDesc());
		}
	}

	@SuppressWarnings("unused")
	private static Workbook getSheet(File file) throws Exception {
		Map<String, ArrayList<CheckBean>> map = new HashMap<String, ArrayList<CheckBean>>();
		List<CheckBean> list = new ArrayList<CheckBean>();
		Workbook workbook = null;
		if (XLS.equalsIgnoreCase(FilenameUtils.getExtension(file.getName()))) {
			workbook = new HSSFWorkbook(new FileInputStream(file));
		} else if (XLSX.equalsIgnoreCase(FilenameUtils.getExtension(file.getName()))) {
			workbook = new XSSFWorkbook(new FileInputStream(file));
		} else {
			throw new IOException("导入excel出错，不支持文件类型！");
		}
		return workbook;
	}

	/**
	 * 添加校验结果
	 * 
	 * @param row
	 * @param column
	 * @param type
	 * @param lastCol
	 * @param col2Val
	 */
	private static void addResult(Row row, int column, String type, String col2Val) {
		if (row.getCell(column) == null) {
			row.createCell(column);
			CellStyle style = row.getCell(column - 1) == null? null : row.getCell(column - 1).getCellStyle();
			if (style != null) {
				row.getCell(column).setCellStyle(style);
				row.getCell(column).getCellStyle().setWrapText(true);
			}
		}
		if (row.getCell(column + 1) == null) {
			row.createCell(column + 1);
			CellStyle style = row.getCell(column - 1) == null ? null : row.getCell(column - 1).getCellStyle();
			if (style != null) {
				row.getCell(column + 1).setCellStyle(style);
				row.getCell(column + 1).getCellStyle().setWrapText(true);
			}
		}
		String colVal1 = "";
		if (CheckResultTypeEnum.CHECK_TITLE.getEnumCode().equals(type)) {
			colVal1 = CheckTitleEnum.TITLE_TYPE.getEnumLabel();
		} else {
			colVal1 = DataUtil.getEnumType(type, CheckResultTypeEnum.class).getEnumLabel();
		}
		row.getCell(column).setCellValue(colVal1);
		row.getCell(column + 1).setCellValue(col2Val);

	}
	
	
	/**
	 * 获取根路径
	 * @return
	 */
	private static String getRootPath(){
		String rootPath ="";
		try{
			 File file = new File(ExcelUtils.class.getResource("/").getFile());
			 rootPath = file.getParentFile().getParentFile().getPath();
			 rootPath = java.net.URLDecoder.decode(rootPath,"utf-8");
			 return rootPath + "\\";
		}catch(Exception e){
//			e.printStackTrace();
		}
		return rootPath;
	}
	
	/***
	 * 导出指定模版相同规则数据
	 * @param companyType 机构类型
	 * @param templateFileName 导出数据模版文件名
	 * @param fileName 导出文件名
	 * @param map 要导出的数据 Map<ExcelSheetEnum, List<Map<String, Object>>>，key：sheet枚举，value:结果集（key：字段，value：字段的值）
	 * @param dataFormatMap 设置多个sheet中某个列的数据格式，Map<ExcelSheetEnum, Map<String, CellDataFormatEnum>>，sheet枚举，value:(sheet中列的格式map，key：列名；value：格式值)
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void exportTemplateDatas(CompanyTypeEnum companyType, String templateFileName, String fileName, Map<ExcelSheetEnum, List<Map<String, Object>>> map, Map<ExcelSheetEnum, Map<String, CellDataFormatEnum>> dataFormatMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String inputFilePath = getRootPath() + "datas\\" + templateFileName; // 模版路径
		File inputFile = new File(inputFilePath);
		// 临时文件
		String tmpFilePath = getRootPath() + "datas\\tmp\\tmp_" + System.currentTimeMillis() + "_" + templateFileName;
		File file = new File(tmpFilePath);
		FileUtils.copyFile(inputFile, file);
		InputStream is = new FileInputStream(file);
		Workbook wb = null;
		String extension = XLS; // 后缀
		if (XLS.equalsIgnoreCase(FilenameUtils.getExtension(file.getName()))) {
			wb = new HSSFWorkbook(is);
		} else if (XLSX.equalsIgnoreCase(FilenameUtils.getExtension(file.getName()))) {
			wb = new XSSFWorkbook(is);
			extension = XLSX;
		} else {
			throw new IOException("excel模版导出出错，不支持文件类型！");
		}
		if(CollectionUtils.isEmpty(map)) {
			try {
				file.delete();
			} catch (Exception e) {
				log.error("删除模版临时文件出错");
			}
			// 输出空文件
			outputFile(fileName, request, response, wb, extension);
			return;
		}
		for(Map.Entry<ExcelSheetEnum, List<Map<String, Object>>> dataMap : map.entrySet()) {
			ExcelSheetEnum excelSheet = dataMap.getKey();
			Sheet sheet = wb.getSheet(excelSheet.getEnumLabel());
			// 数据list，字段以map的形式存储
			List<Map<String,Object>> list = dataMap.getValue();
			// 没有结果集则不做额外处理， 直接保留此sheet
			if(CollectionUtils.isEmpty(list)) {
				continue;
			}
			
			Row colRow = sheet.getRow(0);
			// 列数
			int colNums = colRow.getPhysicalNumberOfCells();
			
			// 设置单元格样式(数据值样式)
			CellStyle cellStyle = wb.createCellStyle();
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			DataFormat format = wb.createDataFormat();
			cellStyle.setDataFormat(format.getFormat("TEXT")); // 文本格式
			int rowNum = 1;
			// 第二行开始，第一行列头,第二列填写标准
			if(ExcelSheetEnum.SHEET_1.equals(excelSheet)){
				rowNum = 2;
			}
			for (Map<String, Object> entityMap : list) {
				Row row = sheet.createRow(rowNum++);
				int j = 0;
				// 1、药品信息列
				for(Map.Entry<String, Object> entry : entityMap.entrySet()) {
					Cell cell = row.createCell(j++);
					sheet.setColumnWidth(cell.getColumnIndex(), 256 * (11 + 10));
					cell.setCellValue(entry.getValue()+"");
					// 动态设置单元格的格式  枚举值对应到HSSFDataFormat的值
					CellDataFormatEnum df = null;
					if(dataFormatMap!=null) {
						df = dataFormatMap.get(excelSheet).get(entry.getKey());
					}
					if(df!=null) {
						CellStyle styleContent1 = wb.createCellStyle();
						styleContent1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
						styleContent1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
						styleContent1.setBorderRight(HSSFCellStyle.BORDER_THIN);
						styleContent1.setBorderTop(HSSFCellStyle.BORDER_THIN);
						styleContent1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
						styleContent1.setDataFormat(df.getShortEnumCode());
						cell.setCellStyle(styleContent1);
					} else {
						cell.setCellStyle(cellStyle);
					}
				}
				if(ExcelSheetEnum.SHEET_1.equals(excelSheet)) {
					// 数据列
					int dataColSize = entityMap.size();
					if(CompanyTypeEnum.TYPE_PRODUCER.equals(companyType)) {
						// 2、经营企业名称
						Cell cellC = row.createCell(dataColSize);
						cellC.setCellStyle(cellStyle);
						// 3、申报企业类型
						Cell cellC1 = row.createCell(dataColSize+1);
						cellC1.setCellStyle(cellStyle);
						cellC1.setCellValue(companyType.getEnumCode());
						// 4、费药品信息列
						for (int i = dataColSize+2; i < colNums; i++) {
							Cell cell = row.createCell(i);
							cell.setCellStyle(cellStyle);
						}
					} else {
						// 3、申报企业类型
						Cell cellC1 = row.createCell(dataColSize);
						cellC1.setCellStyle(cellStyle);
						cellC1.setCellValue("");
						// 4、费药品信息列
						for (int i = dataColSize+1; i < colNums; i++) {
							Cell cell = row.createCell(i);
							cell.setCellStyle(cellStyle);
						}
					}
				}
			}
		}

		try {
			file.delete();
		} catch (Exception e) {
			log.error("删除模版临时文件出错");
		}
		// 输出文件
		outputFile(fileName, request, response, wb, extension);
	}
	
	/***
	 * 导出指定模版中的数据
	 * @param templateFileName 导出模版数据文件名
	 * @param fileName 导出文件名
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void exportTemplateDatasAll(String templateFileName, String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String inputFile = getRootPath() + "file\\" + templateFileName; // 模版路径
		File file = new File(inputFile);
		InputStream is = new FileInputStream(file);
		Workbook wb = null;
		String extension = XLS; // 后缀
		if (XLS.equalsIgnoreCase(FilenameUtils.getExtension(file.getName()))) {
			wb = new HSSFWorkbook(is);
		} else if (XLSX.equalsIgnoreCase(FilenameUtils.getExtension(file.getName()))) {
			wb = new XSSFWorkbook(is);
			extension = XLSX;
		} else {
			throw new IOException("excel模版导出出错，不支持文件类型！");
		}
		outputFile(fileName, request, response, wb, extension);
	}
	
	/**
	 * 输出文件
	 * @param fileName
	 * @param request
	 * @param response
	 * @param wb
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private static void outputFile(String fileName, HttpServletRequest request, HttpServletResponse response, HSSFWorkbook wb) throws UnsupportedEncodingException, IOException {
		String agent = request.getHeader("User-Agent");
		// 火狐浏览器导出文件不会url解码
		if(StringUtil.isEmpty(agent)==false && agent.toLowerCase().indexOf("firefox")>-1) {
			response.setHeader("Content-disposition", "attachment;filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1")+".xls\"");
		} else {
			response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1")+".xls");
		}
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		OutputStream out = response.getOutputStream();
		wb.write(out);
		out.flush();
		out.close();
	}
	
	/**
	 * 输出文件（兼容xls、xlsx）
	 * @param fileName
	 * @param request
	 * @param response
	 * @param wb
	 * @param extension 后缀
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private static void outputFile(String fileName, HttpServletRequest request, HttpServletResponse response, Workbook wb, String extension) throws UnsupportedEncodingException, IOException {
		String agent = request.getHeader("User-Agent");
		// 火狐浏览器导出文件不会url解码
		if(StringUtil.isEmpty(agent)==false && agent.toLowerCase().indexOf("firefox")>-1) {
			response.setHeader("Content-disposition", "attachment;filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1")+"."+extension+"\"");
		} else {
			response.setHeader("Content-disposition", "attachment;filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1")+"."+extension+"\"");
		}
		response.setContentType("application/vnd.ms-excel;charset=ISO-8859-1");
		OutputStream out = response.getOutputStream();
		wb.write(out);
		out.flush();
		out.close();
	}
	
	/**
	 * PDF文档下载
	 * @param templateFileName
	 * @param fileName
	 * @param request
	 * @param response
	 */
	public static void exportPdf(String templateFileName, String fileName, HttpServletRequest request, HttpServletResponse response) {
		try {
			String inputFile = getRootPath() + "datas\\" + templateFileName; // 模版路径
			String agent = request.getHeader("User-Agent");
			// 火狐浏览器导出文件不会url解码
			if(StringUtil.isEmpty(agent)==false && agent.toLowerCase().indexOf("firefox")>-1) {
				response.addHeader("Content-Disposition", "attachment;filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + ".pdf\"");
			} else {
				response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".pdf", "UTF-8"));
			}
			response.setContentType("application/pdf;charset=UTF-8");
			BufferedInputStream input = new BufferedInputStream(new FileInputStream(inputFile));
			byte buffBytes[] = new byte[1024];
			OutputStream os = response.getOutputStream();
			int read = 0;
			while ((read = input.read(buffBytes)) != -1) {
				os.write(buffBytes, 0, read);
			}
			os.flush();
			os.close();
			input.close(); // 下载指定PDF文件
		} catch (UnsupportedEncodingException e) {
			log.error("pdf文档下载出错", e);
		} catch (IOException e) {
			log.error("pdf文档下载出错", e);
		}
	}
}
