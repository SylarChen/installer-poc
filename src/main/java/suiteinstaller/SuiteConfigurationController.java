package suiteinstaller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

@Controller
@RequestMapping("/suiteconf")
public class SuiteConfigurationController {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/copy", method=RequestMethod.GET)
	@ResponseBody
    public String copy(@RequestParam(value="user", required=false, defaultValue="postgres") String user, 
    		@RequestParam(value="password", required=false, defaultValue="postgres") String password) {
		System.out.println("Going to edit config map, user input user/pwd: " + user + "/" + password);

		try {
			//replace var in configmap
//			String yamlFile = "C:\\WorkSpace\\MyGitHub\\installer-poc\\src\\main\\resources\\configmap.yaml";
			String yamlFile = "/suite_yamls/configmap.yaml";
			YamlReader reader = new YamlReader(new FileReader(yamlFile));
			Map configmap = (Map) reader.read();
			Map data = (Map) configmap.get("data");
			System.out.println("default pg.user: " + data.get("pg.user"));
			System.out.println("default pg.password: " + data.get("pg.pwd"));
			data.put("pg.user", user);
			data.put("pg.pwd", password);
			YamlWriter writer = new YamlWriter(new FileWriter(yamlFile));
			writer.write(configmap);
			writer.close();
			//----------------------------------------------------------------------------------------------
			
			//copy yamls to PV
			Runtime.getRuntime().exec("/copyYamls.sh");
		} catch (IOException e) {
			e.printStackTrace();
		}
        return "Copy yamls to /var/vols/ITOM/suite-install/itsma/output";
    }
	
	
	
	@RequestMapping(value="/finish", method=RequestMethod.GET)
	@ResponseBody
    public String finish() {
		try {
			Runtime.getRuntime().exec("/askCreateBusinessPod.sh");
		} catch (IOException e) {
			e.printStackTrace();
		}
        return "Call Back Suite Installer...";
    }
	
	@RequestMapping(value="/status", method=RequestMethod.GET)
	@ResponseBody
    public String status() {
		URL url;
		String suite_installer_ip = System.getenv().get("SUITE_INSTALLER_IP");
		String suite_installer_port = System.getenv().get("SUITE_INSTALLER_PORT");
		String api = "http://$SUITE_INSTALLER_IP:$SUITE_INSTALLER_PORT/suiteinstaller/install?podname=$POD_NAME"
				.replace("$SUITE_INSTALLER_IP", suite_installer_ip==null?"null":suite_installer_ip)
				.replace("$SUITE_INSTALLER_PORT", suite_installer_port==null?"null":suite_installer_port)
				.replace("$POD_NAME", "suite-postgres");
		System.out.println("Suite-Config: Get pod status: " + api);
		
		StringBuffer output = new StringBuffer();
		try {

			url = new URL(api);
//			url = new URL("http://IP:PORT/suiteinstaller/install?podname=suite-postgres");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			System.out.println("Output from Server ....");
			String str;
			while ((str = br.readLine()) != null) {
				output.append(str).append(System.lineSeparator());
			}
			conn.disconnect();
			
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}


        return output.toString().trim();
    }

}
