package com.github.qcloudsms;

import com.github.qcloudsms.httpclient.HTTPResponse;
import org.json.JSONException;
import org.json.JSONObject;


public class TtsVoiceSenderResult extends SmsResultBase {

    public int result;
    public String errMsg;
    public String ext;
    public String callid;

    public TtsVoiceSenderResult() {
        this.errMsg = "";
        this.ext = "";
        this.callid = "";
    }

    @Override
    public TtsVoiceSenderResult parseFromHTTPResponse(HTTPResponse response)
            throws JSONException {

        JSONObject json = parseToJson(response);

        result = json.getInt("result");
        errMsg = json.getString("errmsg");

        if (json.has("ext")) {
            ext = json.getString("ext");
        }
        if (json.has("callid")) {
            callid = json.getString("callid");
        }

        return this;
    }
}
