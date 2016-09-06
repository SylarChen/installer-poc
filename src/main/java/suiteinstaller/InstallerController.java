package suiteinstaller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InstallerController {
	
	private static final Map<String, String> K8S_TYPE_MAP = new HashMap<String, String>();
	static{
		//namespace
		K8S_TYPE_MAP.put("namespaces", "namespace");
		
		//normal types
		K8S_TYPE_MAP.put("configmap", "configmaps");
		K8S_TYPE_MAP.put("services", "service");
		K8S_TYPE_MAP.put("replicationcontrollers", "replicationcontroller");
		K8S_TYPE_MAP.put("pods", "pod");
		
		//extension types
		K8S_TYPE_MAP.put("ingresses", "ingress");
		K8S_TYPE_MAP.put("deployments", "deployment");
	}
	
	private static final String YAML_FOLDER_TEMPLATE =  "/pv_suite_install/$SUITE/output/";
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

				String[] commands = {SHELL_PATH, API_SERVER_HOST, API_SERVER_PORT, namespace, type, yamlFile};
				
				Runtime.getRuntime().exec(commands);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "Creating yaml : " + suite.getYamlList();
	}
	
}