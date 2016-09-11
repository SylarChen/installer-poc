package suiteinstaller.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class YamlUtils {
	
	//Split Yaml File contains mutiple instance.
	public static List<String> split(String file, String delimiter) throws Exception{
		List<String> fileList = new ArrayList<String>();
		File yamlFile = new File(file);
		FileInputStream fis = new FileInputStream(yamlFile);
		BufferedReader in = new BufferedReader(new InputStreamReader(fis));
		try {

			String aLine;
			StringBuffer sb = new StringBuffer();
			int part = 0;
			while ((aLine = in.readLine()) != null) {
				if(!aLine.startsWith(delimiter)){
					sb.append(aLine).append(System.lineSeparator());
				}else{
					writePartYaml(file, fileList, sb, part);
	                sb = new StringBuffer();
	                part++;
				}
			}
			if(sb.toString().contains("apiVersion")){
				writePartYaml(file, fileList, sb, part);
			}
			
		} catch (Exception e) {
			throw e;
		}finally{
			if(in != null){
				in.close();
			}
		}
		
		return fileList;
	}

	private static void writePartYaml(String file, List<String> fileList, StringBuffer sb, int part) throws IOException{
		String outputFile = file+"_part"+part+".yaml";
		File f = witreFile(sb, outputFile);
		fileList.add(f.getAbsolutePath());
	}

	private static File witreFile(StringBuffer sb, String outputFile) throws IOException {
		File f = new File(outputFile);
		BufferedWriter bwr = new BufferedWriter(new FileWriter(f));
		try {
			bwr.write(sb.toString());
			bwr.flush();
		} catch (IOException e) {
			throw e;
		}finally{
			if(bwr != null){
				bwr.close();
			}
		}
		return f;
	}
	
	//add double quotes for number value in yaml file
	public static void addQuotesForNum(String file) throws IOException{
		File yamlFile = new File(file);
		FileInputStream fis = new FileInputStream(yamlFile);
		BufferedReader in = new BufferedReader(new InputStreamReader(fis));
		try {
			String aLine;
			StringBuffer sb = new StringBuffer();
			while ((aLine = in.readLine()) != null) {
				String[] keyValue = aLine.split(":");
				//handle key value pair only
				if(keyValue.length == 2){
					String value = keyValue[1].trim();
					try{
						int num = Integer.parseInt(value);
						String numWhithQuotes = "\"" + num + "\"";
						aLine = keyValue[0] + ":" + keyValue[1].replace(value, numWhithQuotes);
					}catch(NumberFormatException e){
						//not a number, do nothing
					}
				}
				sb.append(aLine).append(System.lineSeparator());
			}
			YamlUtils.witreFile(sb, file);
		} catch (Exception e) {
			//if exception occurs, don't change anything in this file
			e.printStackTrace();
		}finally{
			in.close();
		}
	}
	
//	public static void main(String[] args){
//		System.out.println(YamlSpliter.split("C:\\Users\\Administrator\\Desktop\\test\\amsuite.yaml", "---"));
//	}
//	public static void main(String[] args) throws Exception{
//		YamlUtils.addQuotesForNum("C:\\Users\\Administrator\\Desktop\\suiteconfig_cm.yaml");
//	}
}
