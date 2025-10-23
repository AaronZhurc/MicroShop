package util.validate;

public class ValidateUtil {
    /**
     * 验证输入数据是否为空
     * @param data
     * @return
     */
    public static boolean validateEmpty(String data){
        if(data==null||"".equals(data)){
            return false;
        }
        return true;
    }

    /**
     * 正则操作验证
     * @param data
     * @param regex
     * @return
     */
    public static boolean validateRegex(String data,String regex){
        if(validateEmpty(data)){
            return data.matches(regex);
        }
        return false;
    }

    /**
     * 验证相同不区分大小写
     * @param dataa
     * @param datab
     * @return
     */
    public static boolean validateSame(String dataa,String datab){
        if(validateEmpty(dataa)&&validateEmpty(datab)){
            return dataa.equalsIgnoreCase(datab);
        }
        return false;
    }
}
