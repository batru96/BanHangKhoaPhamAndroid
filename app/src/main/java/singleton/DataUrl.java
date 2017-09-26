package singleton;

import org.json.JSONArray;
import org.json.JSONException;

public class DataUrl {
    private static final String ipaddress = "192.168.1.111";
    public static String indexUrl = "http://" + ipaddress + "/khoapham_ban_hang/app/index.php";
    public static String imageProductUrl = "http://" + ipaddress + "/khoapham_ban_hang/app/images/product/";
    public static String imageTypeUrl = "http://" + ipaddress + "/khoapham_ban_hang/app/images/type/";
    public static String searchProductUrl = "http://" + ipaddress + "/khoapham_ban_hang/app/search.php?key=";
    public static String productDetailUrl = "http://" + ipaddress + "/khoapham_ban_hang/app/product_detail.php?id=";
    public static String loginUrl = "http://" + ipaddress + "/khoapham_ban_hang/app/login.php";
    public static String checkLoginUrl = "http://" + ipaddress + "/khoapham_ban_hang/app/check_login.php";

    public static String[] convertJsonImgArrToStrArr(JSONArray imageArray) {
        String[] images = new String[2];
        for (int i = 0; i < imageArray.length(); i++) {
            try {
                String imgName = imageArray.getString(i);
                imgName = imgName.replaceAll("jpeg", "jpg");
                images[i] = imageProductUrl + imgName;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return images;
    }
}
