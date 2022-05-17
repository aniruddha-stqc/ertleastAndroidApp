package com.ertleast.android;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Chetan on 12/09/16.
 */
interface VolleyCallback {
    void onSuccess(JSONObject result) throws JSONException, IOException, XmlPullParserException;

    void onError(String result);
}
