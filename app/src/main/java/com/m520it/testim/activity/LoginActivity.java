package com.m520it.testim.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.m520it.testim.R;
import com.m520it.testim.common.Constants;
import com.m520it.testim.common.MyApplication;
import com.m520it.testim.util.OkHttpUtils;
import com.m520it.testim.util.RYUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

public class LoginActivity extends AppCompatActivity {

    private EditText mEtName,mEtId;
    private Button mBtnLogin,mBtnCT,mBtnSN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtId = (EditText) findViewById(R.id.et_pass);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnCT = (Button)findViewById(R.id.btn_ct);
        mBtnSN = (Button)findViewById(R.id.btn_sn);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData(mEtName.getText().toString(),mEtId.getText().toString());
            }
        });

        mBtnCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData("CT","0001");
            }
        });

        mBtnSN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData("SN","0002");
            }
        });
    }

    private void initData(String name,String id) {
        List<OkHttpUtils.Param> parms = new ArrayList<>();
        parms.add(new OkHttpUtils.Param("userId", id));
        parms.add(new OkHttpUtils.Param("name",name));
        parms.add(new OkHttpUtils.Param("portraitUri",Constants.ICON_URL));

        List<OkHttpUtils.Param> headers = RYUtils.createHeader();

        OkHttpUtils.post(Constants.GET_RY_TOKEN, new OkHttpUtils.ResultCallback<String>() {

            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject js = new JSONObject(response);
                    String token = js.getString("token");
                    ((MyApplication)getApplication()).setToken(token);
                    connect(token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.i("MainActivity","----->" + e);
            }
        },parms,headers);
    }

    /**
     * <p>连接服务器，在整个应用程序全局，只需要调用一次，需在 {@link # init(Context)} 之后调用。</p>
     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
     *
     * @param token    从服务端获取的用户身份令牌（Token）。
     * @return RongIM  客户端核心类的实例。
     */
    private void connect(String token) {

        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {

                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    Log.d("LoginActivity", "--onSuccess" + userid);
                    ((MyApplication)getApplication()).setId(userid);
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
        }
    }
}
