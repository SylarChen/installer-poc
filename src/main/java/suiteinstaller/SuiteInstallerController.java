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
			for(String yaml : suite.getYamlList()){
				String path = yamlFolder + yaml;
				System.out.println("Create pod : " + path);
				String[] commands = {"/createsinglepod.sh", path};
				Runtime.getRuntime().exec(commands);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "Creating yaml : " + suite.getYamlList();
	}
}

/*
http://16.187.189.90:30000/suiteinstaller/install
{
    "suiteName" : "itsma",
    "yamlList" : ["test1.yaml", "test2.yaml"]
}
*/

class Suite {
	@Override
	public String toString() {
		return "Suite [suiteName=" + suiteName + ", yamlList=" + yamlList + "]";
	}

	private String suiteName;
    private List<String> yamlList;
	
    public String getSuiteName() {
		return suiteName;
	}

	public List<String> getYamlList() {
		return yamlList;
	}

    public void setSuiteName(String suiteName) {
        this.suiteName = suiteName;
    }
    
    public void setYamlList(List<String> yamlList) {
        this.yamlList = yamlList;
    }
}