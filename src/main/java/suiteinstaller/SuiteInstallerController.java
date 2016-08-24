package suiteinstaller;

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
        return "Pod created successfully on node xxx";
    }
//    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name) {
//        return "greeting";
//    }
}
