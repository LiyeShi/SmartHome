package com.sict.soft.smarthome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2017/8/17.
 */

public class CeceActivity extends Activity{

    private Button map_back;

    private RadioGroup radioGroup_1=null;
    private RadioButton one_1,one_2;
    private String one = "0";

    private RadioGroup radioGroup_2=null;
    private RadioButton two_1,two_2,two_3;
    private String two = "0";

    private RadioGroup radioGroup_3=null;
    private RadioButton three_1,three_2,three_3;
    private String three = "0";

    private RadioGroup radioGroup_4=null;
    private RadioButton four_1,four_2,four_3;
    private String four = "0";

    private RadioGroup radioGroup_5=null;
    private RadioButton five_1,five_2,five_3;
    private String five = "0";

    private RadioGroup radioGroup_6=null;
    private RadioButton six_1,six_2,six_3;
    private String six = "0";

    private RadioGroup radioGroup_7=null;
    private RadioButton seven_1,seven_2,seven_3;
    private String seven = "0";

    private RadioGroup radioGroup_8=null;
    private RadioButton eight_1,eight_2;
    private String eight = "0";

    private RadioGroup radioGroup_9=null;
    private RadioButton nine_1,nine_2,nine_3;
    private String nine = "0";

    private RadioGroup radioGroup_10=null;
    private RadioButton ten_1,ten_2,ten_3,ten_4;
    private String ten = "0";

    private Button ok;
    private Button no;
    public static String lin;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cece);

        map_back = (Button) findViewById(R.id.cece_back);
        map_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CeceActivity.this, MainActivity.class));
                finish();
            }
        });

        ok = (Button) findViewById(R.id.cece_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject object = new JSONObject();
                try {
                    object.put("one", one);
                    object.put("two",two);
                    object.put("three",three);
                    object.put("four",four);
                    object.put("five",five);
                    object.put("six",six);
                    object.put("seven",seven);
                    object.put("eight",eight);
                    object.put("nine",nine);
                    object.put("ten",ten);
                    System.out.println(object);
                    final String lo = String.valueOf(object);//转化成字符串格式的
                    sendRequestWithHttpURLConnection(lo);
                    Toast.makeText(CeceActivity.this,"已提交",Toast.LENGTH_SHORT).show();
//                    Toast.makeText(CeceActivity.this,"已提交",Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(CeceActivity.this,LaorenActivity.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        no = (Button) findViewById(R.id.cece_no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CeceActivity.this, MainActivity.class));
                finish();
            }
        });

        one();
        two();
        three();
        four();
        five();
        six();
        seven();
        eight();
        nine();
        ten();

    }


    private void sendRequestWithHttpURLConnection(final  String encodeString) {
        // 开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://"+C.hou_IP+":11111/oldman");
                    connection = (HttpURLConnection) url.openConnection();//获取实例
                    connection.setRequestMethod("POST");//设置连接方式
                    connection.setConnectTimeout(800);//链接超时时间数
                    connection.setReadTimeout(800);//读取超时时间数
                    DataOutputStream dop = new DataOutputStream(connection.getOutputStream());
                    dop.writeBytes("Lo="+ URLEncoder.encode(encodeString,"utf-8"));
                    InputStream in = connection.getInputStream();
                    // 下面对获取到的输入流进行读取
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    lin = response.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();//关闭连接
                    }
                }
            }
        }).start();
    }

    private void one(){
        radioGroup_1 = (RadioGroup) findViewById(R.id.one_id);
        one_1 = (RadioButton) findViewById(R.id.one_1);
        one_2 = (RadioButton) findViewById(R.id.one_2);
        radioGroup_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.one_1:
                        one = "1";
                        break;
                    case R.id.one_2:
                        one = "2";
                        break;
                    default:
                        one = "0";
                        break;
                }
            }
        });
    }

    private void two() {
        radioGroup_2 = (RadioGroup) findViewById(R.id.two_id);
        two_1 = (RadioButton) findViewById(R.id.two_1);
        two_2 = (RadioButton) findViewById(R.id.two_2);
        two_3 = (RadioButton) findViewById(R.id.two_3);
        radioGroup_2.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.two_1:
                        two = "1";
                        break;
                    case R.id.two_2:
                        two = "2";
                        break;
                    case R.id.two_3:
                        two = "3";
                        break;
                    default:
                        two = "0";
                        break;
                }
            }
        });
    }

    private void three() {
        radioGroup_3 = (RadioGroup) findViewById(R.id.three_id);
        three_1 = (RadioButton) findViewById(R.id.three_1);
        three_2 = (RadioButton) findViewById(R.id.three_2);
        three_3 = (RadioButton) findViewById(R.id.three_3);
        radioGroup_3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.three_1:
                        three = "1";
                        break;
                    case R.id.three_2:
                        three = "2";
                        break;
                    case R.id.three_3:
                        three = "3";
                        break;
                    default:
                        three = "0";
                        break;
                }
            }
        });
    }

    private void four() {
        radioGroup_4 = (RadioGroup) findViewById(R.id.four_id);
        four_1 = (RadioButton) findViewById(R.id.four_1);
        four_2 = (RadioButton) findViewById(R.id.four_2);
        four_3 = (RadioButton) findViewById(R.id.four_3);
        radioGroup_4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.four_1:
                        four = "1";
                        break;
                    case R.id.four_2:
                        four = "2";
                        break;
                    case R.id.four_3:
                        four = "3";
                        break;
                    default:
                        four = "0";
                        break;
                }
            }
        });
    }

    private void five() {
        radioGroup_5 = (RadioGroup) findViewById(R.id.five_id);
        five_1 = (RadioButton) findViewById(R.id.five_1);
        five_2 = (RadioButton) findViewById(R.id.five_2);
        five_3 = (RadioButton) findViewById(R.id.five_3);
        radioGroup_5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.five_1:
                        five = "1";
                        break;
                    case R.id.five_2:
                        five = "2";
                        break;
                    case R.id.five_3:
                        five = "3";
                        break;
                    default:
                        five = "0";
                        break;
                }
            }
        });
    }

    private void six() {
        radioGroup_6 = (RadioGroup) findViewById(R.id.six_id);
        six_1 = (RadioButton) findViewById(R.id.six_1);
        six_2 = (RadioButton) findViewById(R.id.six_2);
        six_3 = (RadioButton) findViewById(R.id.six_3);
        radioGroup_6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.six_1:
                        six = "1";
                        break;
                    case R.id.five_2:
                        six = "2";
                        break;
                    case R.id.five_3:
                        six = "3";
                        break;
                    default:
                        six = "0";
                        break;
                }
            }
        });
    }

    private void seven() {
        radioGroup_7 = (RadioGroup) findViewById(R.id.seven_id);
        seven_1 = (RadioButton) findViewById(R.id.seven_1);
        seven_2 = (RadioButton) findViewById(R.id.seven_2);
        seven_3 = (RadioButton) findViewById(R.id.seven_3);
        radioGroup_7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.seven_1:
                        seven = "1";
                        break;
                    case R.id.seven_2:
                        seven = "2";
                        break;
                    case R.id.seven_3:
                        seven = "3";
                        break;
                    default:
                        seven = "0";
                        break;
                }
            }
        });
    }

    private void eight() {
        radioGroup_8 = (RadioGroup) findViewById(R.id.eight_id);
        seven_1 = (RadioButton) findViewById(R.id.eight_1);
        seven_2 = (RadioButton) findViewById(R.id.eight_2);
        radioGroup_8.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.eight_1:
                        eight = "1";
                        break;
                    case R.id.eight_2:
                        eight = "2";
                        break;
                    default:
                        eight = "0";
                        break;
                }
            }
        });
    }

    private void nine() {
        radioGroup_9 = (RadioGroup) findViewById(R.id.nine_id);
        nine_1 = (RadioButton) findViewById(R.id.nine_1);
        nine_2 = (RadioButton) findViewById(R.id.nine_2);
        nine_3 = (RadioButton) findViewById(R.id.nine_3);
        radioGroup_9.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.nine_1:
                        nine = "1";
                        break;
                    case R.id.nine_2:
                        nine = "2";
                        break;
                    case R.id.nine_3:
                        nine = "3";
                        break;
                    default:
                        nine = "0";
                        break;
                }
            }
        });
    }

    private void ten() {
        radioGroup_10 = (RadioGroup) findViewById(R.id.ten_id);
        ten_1 = (RadioButton) findViewById(R.id.ten_1);
        ten_2 = (RadioButton) findViewById(R.id.ten_2);
        ten_3 = (RadioButton) findViewById(R.id.ten_3);
        radioGroup_10.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.ten_1:
                        ten = "1";
                        break;
                    case R.id.ten_2:
                        ten = "2";
                        break;
                    case R.id.ten_3:
                        ten = "3";
                        break;
                    default:
                        ten = "0";
                        break;
                }
            }
        });
    }
}
