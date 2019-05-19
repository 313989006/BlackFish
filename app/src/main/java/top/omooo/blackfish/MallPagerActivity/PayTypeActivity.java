package top.omooo.blackfish.MallPagerActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.omooo.blackfish.MainActivity;
import top.omooo.blackfish.NewBaseActivity;
import top.omooo.blackfish.R;
import top.omooo.blackfish.aliPay.OrderInfoUtil2_0;
import top.omooo.blackfish.aliPay.PayResult;
import top.omooo.blackfish.view.CustomToast;

/**
 * Created by SSC on 2018/4/17.
 */

public class PayTypeActivity extends NewBaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.rl_lines)
    RelativeLayout mRlLines;
    @BindView(R.id.iv_cash_pay)
    ImageView mIvCashPay;
    @BindView(R.id.rl_cash_pay)
    RelativeLayout mRlCashPay;
    @BindView(R.id.iv_fenqi_pay)
    ImageView mIvFenqiPay;
    @BindView(R.id.rl_fenqi_pay)
    RelativeLayout mRlFenqiPay;
    @BindView(R.id.btn_pay)
    Button mBtnPay;

    private Context mContext;
    private boolean isCashPay = true;

    private static final String TAG = "PayTypeActivity";

    private static final String APP_ID = "2016091000482130";
    private static final String PID = "2088102175054434";
    private static final String TARGET_ID = "epvqod8752@sandbox.com";
    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCq2Ma8ZVZ5lT/8fsuS4Hxoz3hmmDyW2J4KWUk1oUVELpOYS+dtZEV7cH+nMAiROiBQL8FwFA2wxsx8TLEPtyou8YhHi6PS75145G15jwyWjsWWrICs88IgY3Yx1XN3gZgjbBXUbrCw1syMfFgIb8RwHKhYJKV/BdDtzigbucUPxW5ARBt0Nlroc9vO7WztOTa/HmYM4dye2pRDXTnsB1YkoZdOMd5Zm79h88VYcN9+dbmd/b4jY/T0Lhzx7zn/ezGzhnFFdPbxgsz+UB3HbFkP9xX1CzC/fIic3U+um+S0H+IOr4m1K2QXnJmsdMu3GcW5SRYGR7YbEYGZ1Dh1ZG7XAgMBAAECggEADNNpWAtjio0h2JrMPaKMtHGe4FTdd5Wbh7tcO6J3yL6WZgCMxzGX5cR0UODSQ9TRSDPNmdRnkrXZ9Brtz/g4On33dSeOjO9gKws7DL0ofMULIFyi7FkAYd4VqXgUywv3l/EtUl1c9mmpwYI0Oa3qWWBNYxCdPAP7w16OtEYl/SD91RKkJyoz29uq8NNxe+OfzET43jkOzroRWUcIH/f4p9fCCCGkii8WIm7sxnMaKIRYBtIFdRxTqNmZWn33BDgFrVzN+PAeV9Dxnk1lITVWyLsUkgWvChOggWG98MrEUDPPXmpR8Xa424tq2q94dKpmq688pa/kUcnsloCCIxw2AQKBgQDWJj59DaSPQ4c0iLdabaO6nxXS7OZLr8F3E5+w5RqXQa7Dpu1qH2Bl3XOARrxcW8k3cSs9LfyMzG1hi2XxqkNgKLjefckVLvzhbGizUvccGx9lnKb6+e09jY2EeV+c6NIERefQhs0KLrQNu2Cnb/Z08HqnzrXa3992PPodaUaS4QKBgQDMPCFxxFBJChmsCRrlKSb77CtF81e20ynCrxifjp7fXU65BLwYCOyo8bbXUCaJMOeW0s2zyZqRGH5umIXfvOq2NIIOhtWLmO8ozKXiaRcp6KyJyKoWJf5M4Wf43V4l7C6rTRAiTE3/DBFsZ5eEOAECNfm0Tw92aKjDU9hoSI9wtwKBgAuMOGZp6+vFym6syKZZrzCLD5Q2Lhz6t0YCiuPxFsP9SL1ILRlB4+LaVDYgmluon2sb8Sb1ad71zxgpei/T7Jos1/s3cTZxODW3sNWqraSYWdMS5XY3L/HXxj/Ug7FkZJ7SXjPCcSARcKgk6Ite4D1iElsmLGxHEjfABgS7/pBBAoGBALvu8Q+RCBfN1YW68ybAUYEr9z3tCF/Ru/HL0aqxQHxljhUuyC6Nt8hWdFt5A5zmhZUB8gtojnTJxz3Z2Abj/k8fzjWz2TkVx60Rc+v9rDUCSBKZtfmfiHwSRux4NzRKOz+iMCB/ep4g6r+IK6KdLkBsjpRe0+mvsOvccZy0eFnNAoGAIz1BSh90VlNgURzeiWtUnR2tJhqHREDbT+hopqmxWdaxc8cH6JaVU/1+jh3UXWPZnweu1bmc76GEC1ly++snKrNJNWDMMDVTPVPmUSvBN9Zwmtj+e9h+6zaOLIoLughwcJjtE9HXxqQ5Zx7J2JW9QubAseL4YkIzYc3K9XpEl+M=";
    private static final String RSA_PRIVATE = "";
    private static final int SDK_PAY_FLAG = 1;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PayTypeActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        skipActivity(intent);
                    } else {
                        Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        initViews();
        initData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_type_layout;
    }

    @Override
    public void initViews() {
        getWindow().setStatusBarColor(getColor(R.color.colorKeyBoardBg));
        mContext = PayTypeActivity.this;

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_back, R.id.rl_lines, R.id.rl_cash_pay, R.id.rl_fenqi_pay, R.id.btn_pay})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.iv_back:
                finshActivity();
                break;
            case R.id.rl_lines:
                CustomToast.show(mContext, "商城额度");
                break;
            case R.id.rl_cash_pay:
                isCashPay = true;
                mBtnPay.setText("支付59.0元");
                mIvCashPay.setImageResource(R.drawable.icon_checkbox_checked);
                mIvFenqiPay.setImageResource(R.drawable.icon_checkbox_unchecked);
                break;
            case R.id.rl_fenqi_pay:
                isCashPay = false;
                mBtnPay.setText("激活额度分期");
                mIvCashPay.setImageResource(R.drawable.icon_checkbox_unchecked);
                mIvFenqiPay.setImageResource(R.drawable.icon_checkbox_checked);
                break;
            case R.id.btn_pay:
                if (isCashPay) {
                    pay();
                } else {
                    CustomToast.show(mContext, "请先激活分期");
                }
                break;
            default:
                break;
        }
    }

    public void pay() {

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APP_ID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PayTypeActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
