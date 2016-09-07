package suiteinstaller;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esotericsoftware.yamlbeans.YamlReader;

import suiteinstaller.common.YamlSpliter;

@Controller
public class InstallerController {
	
	private static final Map<String, String> K8S_TYPE_MAP = new HashMap<String, String>();
	static{
		//namespace
		K8S_TYPE_MAP.put("namespace", "namespaces");
		
		//normal types
		K8S_TYPE_MAP.put("configmap", "configmaps");
		K8S_TYPE_MAP.put("service", "services");
		K8S_TYPE_MAP.put("replicationcontroller", "replicationcontrollers");
		K8S_TYPE_MAP.put("pod", "pods");
		
		//extension types
		K8S_TYPE_MAP.put("ingress", "ingresses");
		K8S_TYPE_MAP.put("deployment", "deployments");
	}
	
//	private static final String YAML_FOLDER_TEMPLATE =  "/pv_suite_install/$SUITE/output/";
	private static final String YAML_FOLDER_TEMPLATE =  "C:\\Users\\Administrator\\Desktop\\test\\";
	
	private static final String SHELL_PATH = "/bin/kubectl_create.sh";
	
	@RequestMapping(value="/v1/suite-installer/installation", method=RequestMethod.POST)
	@ResponseBody
    public String installation(@RequestBody Suite suite) {
		System.out.println(suite);
//		String yamlFolder = "/pv_suite_install/" + suite.getSuiteName() + "/output/";
		String yamlFolder = YAML_FOLDER_TEMPLATE.replace("$SUITE", suite.getSuiteName());
		
		try {
			String API_SERVER_HOST = System.getenv("API_SERVER_HOST");
			String API_SERVER_PORT = System.getenv("API_SERVER_PORT");
			String namespace = suite.getSuiteNameSpace();
			for(SuiteYaml yaml : suite.getYamlList()){
				String type = K8S_TYPE_MAP.get(yaml.getType());
				String yamlFile = yamlFolder + yaml.getYaml();
				if(type==null){
					List<String> fileList = YamlSpliter.split(yamlFile, "---");
					
					YamlReader reader;
					for(String filePart : fileList){
						reader = new YamlReader(new FileReader(filePart));
						Map map = (Map) reader.read();
						String kind = (String) map.get("kind");
						type = K8S_TYPE_MAP.get(kind.toLowerCase());
						createKubeInstance(API_SERVER_HOST, API_SERVER_PORT, namespace, type, filePart);
					}
				}else{
					createKubeInstance(API_SERVER_HOST, API_SERVER_PORT, namespace, type, yamlFile);
				}

			}
		} catch (Exception e) {
			return e.getMessage();
		}
		
		return "Creating yaml : " + suite.getYamlList();
	}

	private void createKubeInstance(String API_SERVER_HOST, String API_SERVER_PORT, String namespace, String type,
			String yamlFile) throws IOException {
		String[] commands = {SHELL_PATH, API_SERVER_HOST, API_SERVER_PORT, namespace, type, yamlFile};
		System.out.println("Going to Execute commands : " +  Arrays.toString(commands));
//		Runtime.getRuntime().exec(commands);
	}
}

//{
//	  "suiteName": "itsma",
//	  "suiteNameSpace": "default",
//	  "yamlList": [
//	    {
//	      "yaml": "amsuite.yaml"
//	    },
//	    {
//	      "yaml": "configmap.yaml",
//	      "type": "configmap"
//	    }
//	  ]
//	}