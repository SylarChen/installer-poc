package suiteinstaller;

import java.io.IOException;

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
		try {
			Runtime.getRuntime().exec("/copyYamls.sh");
		} catch (IOException e) {
			e.printStackTrace();
		}
        return "Copy yamls to /var/vols/ITOM/suite-install/itsma/output";
    }
	
	
	@RequestMapping(value="/finish", method=RequestMethod.GET)
	@ResponseBody
    public String finish() {
		//send request to suite installer.
        return "Call Back Suite Installer...";
    }

}
