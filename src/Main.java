import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		//*Product Name	*Unique ID	*Parent Unique ID	*Description	*Tags	*Price	MSRP	*Quantity	*Shipping	*Category	Color	Size	Weight	Other Platform Product Url	*Main Image URL	Extra Image URL	Extra Image URL 1	Extra Image URL 2	Extra Image URL 3	Extra Image URL 4

		String path = "H://产品采集//5gamdw2t.csv";
		String outPath = "H://产品采集//out.csv";
		
		Main main  = new Main();
		//main.MoBuyOpretion(path, outPath);
		
		List<Word> words = WordSegmenter.seg("实拍2016夏新品特大码女装胖mm宽松连衣裙复古格子棉麻打底裙823");
		//List<Word> words = WordSegmenter.segWithStopWords("杨尚川是APDPlat应用级产品开发平台的作者");
		List<Word> words2 = WordSegmenter.seg("实拍2016夏新品特大码女装胖mm宽松连衣裙复古格子棉麻打底裙823");
		System.out.println(words);
		System.out.println(words2);
		
		String str = words2.toString();
		System.out.println(str);
	}
	
	
	// AliExpress
	public static void MoBuyOpretion(String inputPath, String outPath) throws IOException, InterruptedException{
		 String[] colors = {"red","green","black"};
	        String[] sizes = {"S","M","L","XL","XXL"};
	        
			File inFile = new File(inputPath);
			File outFile = new File(outPath);
			
			FileInputStream inputStr = new FileInputStream(inFile);
			FileOutputStream outputStr = new FileOutputStream(outFile);
			// 火车头采集回来的是GBK格式
	        CsvReader cr = new CsvReader(inputStr, Charset.forName("GBK"));
	        //CsvWriter cw = new CsvWriter(outpuStr, ",", Charset.forName("GBK"));
	        CsvWriter cw = new CsvWriter(outputStr, ',', Charset.forName("UTF-8"));
	        cr.readHeaders();
	        
	        // MoBuy文件的头部												
	        String[] headers = {
	        		"*Product Name",
	        		"*Unique ID",
	        		"*Parent Unique ID",
	        		"*Description",
	        		"*Tags", 
	        		"*Price",
	        		"MSRP",
	        		"*Quantity",
	        		"*Shipping",
	        		"*Category",
	        		"Color",
	        		"Size",
	        		"Weight",
	        		"Other Platform Product Url", 
	        		"*Main Image URL", 
	        		"Extra Image URL", 
	        		"Extra Image URL 1", 
	        		"Extra Image URL 2",
	        		"Extra Image URL 3",
	        		"Extra Image URL 4"
	        };
	        cw.writeRecord(headers);
	        while (cr.readRecord()) {
	        	String time = System.currentTimeMillis() + "";
	        	int radomInt = new Random().nextInt(9);
	        	int i = 0;
	        	float price = Float.valueOf(cr.get("*Price"));//floatV cr.get("*Price");
	        	
	        	DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
	        	String MSRP = decimalFormat.format(price * 3);//format 返回的是字符串
	        	
	        	for(String color : colors){
	        		for(String size : sizes){
	        			String quantity =  new Random().nextInt(99) + "";
	        			i++;
	                    String[] tmpStr ={
	                    		cr.get("*Product Name"),			//*Product Name
	                    		"sku"+time + radomInt + i,		//*Unique ID
	                    		"sku"+time +radomInt,				//*Parent Unique ID
	                    		cr.get("*Description"),				//*Description
	                    		cr.get("*Tags"),							//*Tags
	                    		price+"",										//*Price	自己卖的价格
	                    		MSRP,											//MSRP 市场售价
	                    		cr.get(quantity),							//*Quantity
	                    		cr.get("*Shipping"),					//*Shipping
	                    		cr.get("*Category"),					//*Category
	                    		color,											//Color
	                    		size,												//Size
	                    		cr.get("Weight"),							//Weight
	                    		cr.get("Other Platform Product Url"),//产品来源地址
	                    		cr.get("*Main Image URL"),		// *Main Image URL 主图
	                    		cr.get("Extra Image URL"),			// 配图一
	                    		cr.get("Extra Image URL 1"),		
	                    		cr.get("Extra Image URL 2"),
	                    		cr.get("Extra Image URL 3"),
	                    		cr.get("Extra Image URL 4"),
	                    		};

	                    cw.writeRecord(tmpStr);
	        		}
	        	}
	        	Thread.sleep(1);
	        }
	        // 关闭读取流
	        cr.close();
	        // 关闭输入流
	        inputStr.close();
	        
	        // 关闭写入流
	        cw.close();
	        // 关闭输出流
	        outputStr.close();
	        
	}
	/**
     * 读取CVS文件
     * @param filePath   文件全路径
     * @param encode     文件编码
     * @throws Exception 
     */
    public static void readCsv(String filePath, String encode) throws Exception {
        File f = new File(filePath);
        FileInputStream fi = new FileInputStream(f);
        String[] colors = {"red","green","black"};
        String[] sizes = {"S","M","L","XL","XXL"};
        CsvReader cr = new CsvReader(fi, Charset.forName(encode));
        cr.readHeaders();
        
//        *Product Name	*Unique ID	*Parent Unique ID	*Description	*Tags	MSRP	*Price	*Quantity	*Shipping	*Category	Color	Size	Weight	Other Platform Product Url	*Main Image URL	Extra Image URL	Extra Image URL 1	Extra Image URL 2	Extra Image URL 3	Extra Image URL 4	Extra Image URL 5	Extra Image URL 6	Extra Image URL 7

        while (cr.readRecord()) {
        	String time = System.currentTimeMillis() + "";
        	int radomInt = new Random().nextInt(9);
        	int i = 0;
        	for(String color : colors){
        		for(String size : sizes){
        			i++;
        			System.out.print(time + radomInt + i );
        			System.out.print(" | ");
        			System.out.print(cr.get("*Product Name"));
        			System.out.print(" | ");
                	System.out.print(color);
                	System.out.print(" | ");
                	System.out.print(size);
                    System.out.print("\n");
        		}
        	}
        	Thread.sleep(1);
        }
        cr.close();
        fi.close();
    }
    
    /**
     * 输出文件
     * @param ou   文件流
     * @param encode    编码
     * @param list      需要输出的数据
     * @throws IOException
     */
    public static void writeCsv(OutputStream ou, List<String[]> list) throws IOException {
        CsvWriter cw = new CsvWriter(ou, ',', Charset.forName("UTF-8"));
        for(String[] s: list) {
            cw.writeRecord(s);
        }
        //在文件中增加BOM，详细说明可以Google,该处的byte[] 可以针对不同编码进行修改
        ou.write(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF });
        cw.flush();
        cw.close();
    }
    
    /**
     * 输出文件
     * @param ou
     * @param encode
     * @param list
     * @throws IOException
     */
    public static void writeCsv2(OutputStream ou, String encode, List<String[]> list) throws IOException {
        
        //该处直接对文件写入流进行编码
        OutputStreamWriter ow = new OutputStreamWriter(ou, encode);
        CsvWriter cw = new CsvWriter(ow, ',');
        for(String[] s: list) {
            cw.writeRecord(s);
        }
        cw.flush();
        cw.close();
        ow.close();
    }
}