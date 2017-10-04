package models;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchProduct extends Product {
    private int isNew;

    public SearchProduct(JSONObject object) {
        super(object);
        try {
            this.isNew = object.getInt("new");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }
}
