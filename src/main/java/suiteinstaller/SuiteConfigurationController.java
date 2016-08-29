package suiteinstaller;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

}
