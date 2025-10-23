package util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CookieUtil {
    /**
     * 保存cookie
     * @param response
     * @param request 设置保存路径
     * @param name
     * @param value
     * @param expiry
     */
    public static void save(HttpServletResponse response, HttpServletRequest request, String name, String value, int expiry){
        Cookie c=new Cookie(name,value);
        c.setMaxAge(expiry);
        c.setPath(request.getContextPath());
        response.addCookie(c);
    }
    public static Map<String,String> load(HttpServletRequest request){
        Map<String,String> map=new HashMap<String,String>();
        Cookie c[]=request.getCookies();
        if(c!=null) {
            for (int x = 0; x < c.length; x++) {
                if(!"JSESSIONID".equals(c[x].getName())){
                    map.put(c[x].getName(),c[x].getValue());
                }
            }
        }
        return map;
    }
    public static void clear(HttpServletRequest request, HttpServletResponse response){
        Map<String,String> map=load(request);
        Iterator<Map.Entry<String,String>> iter=map.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String,String> me=iter.next();
            Cookie c=new Cookie(me.getKey(),"");
            c.setPath(request.getContextPath());
            c.setMaxAge(0);
            response.addCookie(c);
        }
    }
}
