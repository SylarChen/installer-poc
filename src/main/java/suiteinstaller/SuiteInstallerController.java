package suiteinstaller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

@Controller
@RequestMapping("/suiteinstaller")
public class SuiteInstallerController {
	
	@RequestMapping(value="/createPod", method=RequestMethod.GET)
	@ResponseBody
    public String createPod() {
		//Execute command
		//curl -H "Content-Type: application/yaml" -X POST http://16.187.189.90:8080/api/v1/namespaces/default/pods -d "$(cat pg-2.yaml)"
		String host = "\"" + System.getenv("SUITE_INSTALLER_SVC_SERVICE_HOST") + "\"";
		String port = "\"" + System.getenv("SUITE_INSTALLER_SVC_SERVICE_PORT") + "\"";
		String nfs_host = "\"" + System.getenv("NFS_SERVER") + "\"";
		String nfs_path = "\"" + System.getenv("NFS_OUTPUT_PATH") + "\"";
		
		System.out.println("Going to replace suite config yamls: " + host + " " + port + " " +  nfs_host + " " + nfs_path); 
		String yamlFile = "/suite_conf_yamls/itsma/suiteconfig_cm.yaml";

		try {
			YamlReader reader = new YamlReader(new FileReader(yamlFile));
			Map configmap = (Map) reader.read();
			Map data = (Map) configmap.get("data");
			data.put("installer.ip", host);
			data.put("installer.port", port);
			data.put("nfs.ip", nfs_host);
			data.put("nfs.expose", nfs_path);
			YamlWriter writer = new YamlWriter(new FileWriter(yamlFile));
			writer.write(configmap);
			writer.close();		
			Runtime.getRuntime().exec("/createpod.sh");
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		}
        return "Pod is createing";
    }
	
	@RequestMapping(value="/install", method=RequestMethod.POST)
	@ResponseBody
    public String greeting(@RequestBody Suite suite) {
		System.out.println(suite);
		String yamlFolder = "/pv_suite_install/" + suite.getSuiteName() + "/output/";
		System.out.println("yamlFolder : " + yamlFolder);
		
		try {
			for(SuiteYaml yaml : suite.getYamlList()){
				String path = yamlFolder + yaml.getYaml();
				System.out.println("Build Yaml : " + path + ", Type: " + yaml.getType() + ", NameSpace: "+ suite.getSuiteNameSpace());
				String[] commands = {null, path, suite.getSuiteNameSpace()};
				if("service".equalsIgnoreCase(yaml.getType())){
					System.out.println("Create srevice...");
					commands[0] = "/createsingleservice.sh";
				}else if("pod".equalsIgnoreCase(yaml.getType())){
					System.out.println("Create pod...");
					commands[0] = "/createsinglepod.sh";
				}else if("configmap".equalsIgnoreCase(yaml.getType())){
					System.out.println("Create configmap...");
					commands[0] = "/createsingleconfigmap.sh";
				}else{
					throw new Exception("Can't find type for yaml: " + path);
				}
				Runtime.getRuntime().exec(commands);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "Creating yaml : " + suite.getYamlList();
	}
	
	@RequestMapping(value="/install", method=RequestMethod.GET)
	@ResponseBody
    public String getPodStatus(@RequestParam(value="podname", required=true) String podname) {
		String api_server_ip = System.getenv().get("API_SERVER_IP");
		String api_server_port = System.getenv().get("API_SERVER_PORT");
//		String api_server_ip = "16.187.189.90";
//		String api_server_port = "8080";
		String api = "http://$API_SERVER_IP:$API_SERVER_PORT/api/v1/namespaces/itsma/pods/$POD_NAME/status"
				.replace("$API_SERVER_IP", api_server_ip==null?"null":api_server_ip)
				.replace("$API_SERVER_PORT", api_server_port==null?"null":api_server_port)
				.replace("$POD_NAME", podname);
		System.out.println("Suite-Installer: Get pod status: " + api);
		
		//Send request to API server to query status of pod
		URL url;
		StringBuffer output = new StringBuffer();
		try {
			url = new URL(api);
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
			return "Suite Installer Internal Error!";
		}
		
        return output.toString().trim();
    }
}

/*
http://16.187.189.90:30000/suiteinstaller/install
{
  "suiteName": "itsma",
  "suiteNameSpace": "itsma"
  "yamlList": [
    {
      "yaml": "configmap.yaml",
      "type": "configmap"
    },
    {
      "yaml": "test.yaml",
      "type": "pod"
    },
    {
      "yaml": "test_svc.yaml",
      "type": "service"
    }
  ]
}

*/

class Suite {


	@Override
	public String toString() {
		return "Suite [suiteName=" + suiteName + ", suiteNameSpace="
				+ suiteNameSpace + ", yamlList=" + yamlList + "]";
	}

	private String suiteName;
	private String suiteNameSpace;
	private List<SuiteYaml> yamlList;
	
    public String getSuiteNameSpace() {
		return suiteNameSpace;
	}

	public void setSuiteNameSpace(String suiteNameSpace) {
		this.suiteNameSpace = suiteNameSpace;
	}

    public String getSuiteName() {
		return suiteName;
	}

	public List<SuiteYaml> getYamlList() {
		return yamlList;
	}

    public void setSuiteName(String suiteName) {
        this.suiteName = suiteName;
    }
    
    public void setYamlList(List<SuiteYaml> yamlList) {
        this.yamlList = yamlList;
    }
}

class SuiteYaml {
	private String yaml;
	private String type;
    public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getYaml() {
		return yaml;
	}
	public void setYaml(String yaml) {
		this.yaml = yaml;
	}
}