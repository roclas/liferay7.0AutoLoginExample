package exampleautologin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.security.auto.login.AutoLoginException;
import com.liferay.portal.kernel.service.persistence.UserUtil;
import com.liferay.portal.kernel.util.PortalUtil;

@Component(immediate = true)
public class MyAutoLogin implements AutoLogin {

    public String[] handleException(
            HttpServletRequest request, HttpServletResponse response,
            Exception e)
        throws AutoLoginException {
        /* This method is no longer used and can be left empty */
		return null;
    }

    public String[] login(
            HttpServletRequest request, HttpServletResponse response)
        throws AutoLoginException {
    	String ivuser = request.getHeader("iv-user");
    	if(ivuser!=null && !ivuser.isEmpty()){
    		long companyId = PortalUtil.getCompanyId(request);
    		User user =null;
			try {
				System.out.println(String.format("trying to log in as %s ...",ivuser));
				user=UserUtil.findByC_SN(companyId, ivuser);
			} catch (NoSuchUserException e) {
				System.out.println(String.format("user %s does not exist",ivuser));
				try {
					user=UserUtil.findByC_EA(companyId, ivuser);
				} catch (NoSuchUserException e1) {
					System.out.println(String.format("email %s does not exist",ivuser));
					return null;
				}
			}
			if(user==null)return null;
    		return new String[]{""+user.getUserId(),user.getPassword(), Boolean.TRUE.toString()};
    	}else return null;
    }

}
