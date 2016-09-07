package suiteinstaller.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class YamlSpliter {
	public static List<String> split(String file, String delimiter) throws Exception{
		List<String> fileList = new ArrayList<String>();
		File yamlFile = new File(file);
		try {
			FileInputStream fis = new FileInputStream(yamlFile);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			
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
		}
		
		return fileList;
	}

	private static void writePartYaml(String file, List<String> fileList, StringBuffer sb, int part)
			throws IOException {
		String outputFile = file+"_part"+part+".yaml";
		File f = new File(outputFile);
		BufferedWriter bwr = new BufferedWriter(new FileWriter(f));
		bwr.write(sb.toString());
		bwr.flush();
		bwr.close();
		fileList.add(f.getAbsolutePath());
	}
	
//	public static void main(String[] args){
//		System.out.println(YamlSpliter.split("C:\\Users\\Administrator\\Desktop\\test\\amsuite.yaml", "---"));
//	}
}
