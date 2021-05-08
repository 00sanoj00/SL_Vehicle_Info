package com.sanoj.devildeveloper.srilankavehicleinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    private Button mFind;
    private TextView mTV1;
    private TextView mTV2;
    private TextView mTV3;
    private TextView mTV4;
    private TextView mTV5;
    private TextView mTV6;
    private TextView mTV7;
    private TextView mLTV1;
    private TextView mLTV2;
    private TextView mLTV3;
    private EditText mEditText;
    private TextView mNote;
    private String myvheno;
    private String body;
    private InputStream myresponxml;
    private LinearLayout mLinear;
    private LinearLayout mAnimiLinear;
    private LottieAnimationView mLottieView;
    private String recodeinfo = "No Record Found";
    private String ownernote = "UNABLE TO SHOW OWNER";
    private String serch_note;
    private String error_note;
    private String welcome_note;
    private String URL_Data;
    private RequestQueue reqQue;
    private String liceninfoURL = "http://api.lankagate.gov.lk:8280/RevenueLicenseStatus/1.0/revstatusProxy?vRegNo=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFind = findViewById(R.id.find_btn);

        mEditText = findViewById(R.id.vhnumber);
        mEditText.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        mLinear = findViewById(R.id.centerHome);
        mAnimiLinear = findViewById(R.id.animation);
        mLottieView = findViewById(R.id.animationView);
        mNote = findViewById(R.id.note);

        RequestQueue queue = Volley.newRequestQueue(this);

        licen();

        mTV1 = findViewById(R.id.tv1);
        mTV2 = findViewById(R.id.tv2);
        mTV3 = findViewById(R.id.tv3);
        mTV4 = findViewById(R.id.tv4);
        mTV5 = findViewById(R.id.tv5);
        mTV6 = findViewById(R.id.tv6);
        mTV7 = findViewById(R.id.tv7);

        mLTV1 = findViewById(R.id.ltv1);
        mLTV2 = findViewById(R.id.ltv2);
        mLTV3 = findViewById(R.id.ltv3);

        serch_note = getString(R.string.serch_note);
        error_note = getString(R.string.error_msg);
        welcome_note = getString(R.string.welcom_note);


        mFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    mLottieView.setAnimation(R.raw.search);
                    mLottieView.playAnimation();
                    mLinear.setVisibility(View.GONE);
                    mNote.setText(serch_note);
                    mAnimiLinear.setVisibility(View.VISIBLE);
                    cleartext();
                    myvheno = mEditText.getText().toString();
                    body = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:v1=\"http://schemas.conversesolutions.com/xsd/dmticta/v1\">\n" +
                            "   <soapenv:Header/>\n" +
                            "   <soapenv:Body>\n" +
                            "      <v1:GetVehicleLimitedInfo>\n" +
                            "         <v1:vehicleNo>"+myvheno+"</v1:vehicleNo>\n" +
                            "         <v1:phoneNo>94000000000</v1:phoneNo>\n" +
                            "      </v1:GetVehicleLimitedInfo>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";

                    URL_Data = liceninfoURL+mEditText.getText().toString();
                    Log.d("SANOJL",  URL_Data);
                    requestWithSomeHttpHeaders();
                }catch (Exception IO) {
                    Log.d("Error_APP", IO.toString());
                }


            }
        });



    }
    public void requestWithSomeHttpHeaders() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://api.lankagate.gov.lk:8280/GetVehicleLimitedInfoDMT/1.0";
        StringRequest getRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        myresponxml = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));
                        try1();
                        try2();

                        if(mTV1.getText().toString().equals("")||mTV3.getText().toString().equals("")||mTV4.getText().toString().equals("")||mTV5.getText().toString().equals("")||mTV6.getText().toString().equals("")||mTV7.getText().toString().equals("")){
                            mLinear.setVisibility(View.GONE);
                            mNote.setText(error_note);
                            mLottieView.setAnimation(R.raw.error);
                            mLottieView.playAnimation();
                        }else{
                            mLinear.setVisibility(View.VISIBLE);
                            mAnimiLinear.setVisibility(View.GONE);
                            mLottieView.pauseAnimation();
                            if(mTV2.getText().toString().equals("")){
                                mTV2.setText(ownernote);
                            }
                        }

                    }
                },
                new Response.ErrorListener()
                {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("ERROR","error => "+error.toString());
                    }
                }
        ) {

            @Override
            public String getBodyContentType() {
                // set body content type
                return "text/xml; charset=UTF-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError {

                try {
                    return body.getBytes("UTF-8");
                } catch (UnsupportedEncodingException uee) {
                    // TODO consider if some other action should be taken
                    return null;
                }
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Host","api.lankagate.gov.lk:8280");
                params.put("Connection","keep-alive");
                params.put("Accept","application/xml, text/xml, */*; q=0.01");
                params.put("Origin","file://");
                params.put("Authorization","Bearer  798f7fb2-ecce-3c76-a675-143038467dd6");
                params.put("User-Agent","Mozilla/5.0 (Linux; Android 10; SM-M205F Build/QP1A.190711.020; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/81.0.4044.138 Mobile Safari/537.36");
                params.put("Content-Type","text/xml; charset=UTF-8");
                params.put("X-Requested-With","lk.icta.mobile.apps.dmt");
                params.put("Accept-Encoding","gzip, deflate");
                params.put("Accept-Language","en-GB,en-US;q=0.9,en;q=0.8");

                return params;
            }
        };
        queue.add(getRequest);

    }

    public void try1(){
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(myresponxml);

            Element element=doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("GetVehicleLimitedInfoResponse");
            for (int i=0; i<nList.getLength(); i++) {

                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element) node;

                    mTV1.setText(getValue("VehicleNumber", element2));
                    mTV3.setText(getValue("EngineNo", element2));
                    mTV4.setText(getValue("ClassOfVehicle", element2));
                    mTV5.setText(getValue("Make", element2));
                    mTV6.setText(getValue("Model", element2));
                    mTV7.setText(getValue("YearOfManufacture", element2));
                    mTV2.setText(getValue("AbsoluteOwner", element2));
                }
            }//end of for loop

        } catch (Exception e) {e.printStackTrace();}

    }
    private static String getValue(String tag, Element element) {
         NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
    return node.getNodeValue();
    }
    public void cleartext(){
        mTV1.setText("");
        mTV3.setText("");
        mTV4.setText("");
        mTV5.setText("");
        mTV6.setText("");
        mTV7.setText("");
        mTV2.setText("");
    }
    public void licen(){
        new iOSDialogBuilder(MainActivity.this)
                .setTitle(getString(R.string.tital))
                .setSubtitle(getString(R.string.welcom_note))
                .setBoldPositiveLabel(true)
                .setCancelable(false)
                .setPositiveListener(getString(R.string.ok),new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();

                    }
                })
                .build().show();
    }


    public void try2(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_Data, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    mLTV1.setText(response.getString("License_Issued_Date"));
                    mLTV2.setText(response.getString("License_Expiry_Date"));
                    mLTV3.setText(response.getString("License_No"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    mLTV1.setText(recodeinfo);
                    mLTV2.setText(recodeinfo);
                    mLTV3.setText(recodeinfo);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                mLTV1.setText(recodeinfo);
                mLTV2.setText(recodeinfo);
                mLTV3.setText(recodeinfo);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Host","api.lankagate.gov.lk:8280");
                params.put("Connection","keep-alive");
                params.put("Accept","application/json, text/javascript, */*; q=0.01");
                params.put("Authorization","Bearer  798f7fb2-ecce-3c76-a675-143038467dd6");
                params.put("User-Agent","Mozilla/5.0 (Linux; Android 10; SM-M201F Build/QP1A.190711.020; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/81.0.4044.138 Mobile Safari/537.36");
                params.put("X-Requested-With","lk.icta.mobile.apps.dmt");
                params.put("Accept-Encoding","gzip, deflate");
                params.put("Accept-Language","en-GB,en-US;q=0.9,en;q=0.8");
                return params;
            }
        };
        reqQue = Volley.newRequestQueue(this);
        reqQue.add(request);
    }

}
