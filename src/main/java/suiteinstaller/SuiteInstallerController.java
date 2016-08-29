package suiteinstaller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/suiteinstaller")
public class SuiteInstallerController {
	
	@RequestMapping(value="/createPod", method=RequestMethod.GET)
	@ResponseBody
    public String createPod() {
		//Execute command
		//curl -H "Content-Type: application/yaml" -X POST http://16.187.189.90:8080/api/v1/namespaces/default/pods -d "$(cat pg-2.yaml)"
		try {
			Runtime.getRuntime().exec("/createpod.sh");
		} catch (IOException e) {
			e.printStackTrace();
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
				System.out.println("Create pod : " + path + ", Type: " + yaml.getType());
				String[] commands = {null, path};
				if("service".equalsIgnoreCase(yaml.getType())){
					commands[0] = "createsingleservice.sh";
				}else if("pod".equalsIgnoreCase(yaml.getType())){
					commands[0] = "/createsinglepod.sh";
				}else if("configmap".equalsIgnoreCase(yaml.getType())){
					commands[0] = "createsingleconfigmap.sh";
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
}

/*
http://16.187.189.90:30000/suiteinstaller/install
{
  "suiteName": "itsma",
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
		return "Suite [suiteName=" + suiteName + ", yamlList=" + yamlList + "]";
	}

	private String suiteName;
    private List<SuiteYaml> yamlList;
	
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