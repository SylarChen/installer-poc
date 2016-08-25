package suiteinstaller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
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
	
	//https://spring.io/guides/tutorials/bookmarks/
//	@RequestMapping(value="/installsuite", method=RequestMethod.POST)
//	@ResponseBody
//    public String greeting(@RequestBody requestBody) {
//        return "greeting";
//    }
	
//	@RequestMapping(value="/installsuite", method=RequestMethod.POST)
//	@ResponseBody
//    public String greeting(@RequestParam(value="suite", required=true) String suite) {
//        return "greeting";
//    }
}
