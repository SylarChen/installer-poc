package suiteinstaller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/suiteconf")
public class SuiteConfigurationController {

	@RequestMapping(value="/copy", method=RequestMethod.GET)
	@ResponseBody
    public String copy() {
		//Execute command
		//Copy yamls to PV
        return "Copy yamls to PV";
    }
	
	
	@RequestMapping(value="/finish", method=RequestMethod.GET)
	@ResponseBody
    public String finish() {
		//send request to suite installer.
        return "Call Back Suite Installer...";
    }

}
