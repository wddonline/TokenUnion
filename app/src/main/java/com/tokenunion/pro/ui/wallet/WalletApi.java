package com.tokenunion.pro.ui.wallet;

import android.text.TextUtils;

import com.tokenunion.pro.ui.wallet.model.AssetBillListBean;
import com.tokenunion.pro.ui.wallet.model.ExchangeConfigBean;
import com.tokenunion.pro.ui.wallet.model.MarketItemBean;
import com.tokenunion.pro.ui.wallet.model.RechargeListBean;
import com.tokenunion.pro.ui.wallet.model.TradeBalanceBean;
import com.tokenunion.pro.ui.wallet.model.WithdrawListBean;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.config.ApiUrl;
import com.tokenunion.pro.utils.Md5Utils;
import com.tokenunion.pro.utils.NetRequestUtils;
import com.yidaichu.android.common.http.ActivityFragmentActive;
import com.yidaichu.android.common.http.HttpRequestEntry;

/**
 * Create by: xiaohansong
 * Time: 2019-08-01 20:02
 * -
 * Description:  钱包相关的api请求
 */
public class WalletApi  extends NetRequestUtils {
    /**
     *
     * 获取 充值请求币种列表
     * @param tag
     * @param callback
     */
    public static void getRechargeList(ActivityFragmentActive tag,
                                       final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_WALLET_RECHARGE_LIST);
        doRequest(tag, requestEntry, RechargeListBean.class, callback);
    }


    /**
     *
     * 获取 提币列表
     * @param tag
     * @param callback
     */
    public static void getWithdrawList(ActivityFragmentActive tag,
                                       final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_WALLET_WITHDRAW_LIST);
        doRequest(tag, requestEntry, WithdrawListBean.class, callback);
    }

    /**
     *
     * 执行 提币
     * @param tag
     * @param callback
     */
    public static void doWithdraw(String symbol, String toAddress, String amount,
                                  String tradePasswd, String googleCode, ActivityFragmentActive tag,
                                  final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_WALLET_WITHDRAW);
        requestEntry.addRequestParam("symbol", symbol);
        requestEntry.addRequestParam("toAddress", toAddress);
        requestEntry.addRequestParam("amount", amount);
        requestEntry.addRequestParam("tradePasswd", Md5Utils.MD5Encode(tradePasswd));
        requestEntry.addRequestParam("googleCode", googleCode);

        doRequest(tag, requestEntry, callback);
    }

    /**
     *
     * 执行 内部转账
     * @param tag
     * @param callback
     */
    public static void doTransfer(String symbol, String userName, String amount,
                                  String tradePasswd, String googleCode, ActivityFragmentActive tag,
                                  final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_WALLET_TRANSFER);
        requestEntry.addRequestParam("symbol", symbol);
        requestEntry.addRequestParam("userName", userName);
        requestEntry.addRequestParam("amount", amount);
        requestEntry.addRequestParam("tradePasswd", Md5Utils.MD5Encode(tradePasswd));
        requestEntry.addRequestParam("googleCode", googleCode);

        doRequest(tag, requestEntry, callback);
    }

    /**
     *
     * 执行 兑换 交易
     * @param tag
     * @param callback
     */
    public static void doExchange(String amount, String fromSymbol, String toSymbol,
                                  String tradePasswd, String googleCode, ActivityFragmentActive tag,
                                  final ApiRequestCallback callback) {
//        {"amount":"100","fromSymbol":"ANY","toSymbol":"ETH","tradePasswd":"25f9e794323b453885f5181f1b624d0b","googleCode":"591091"}
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_WALLET_EXCHANGE);
        requestEntry.addRequestParam("amount", amount);
        requestEntry.addRequestParam("fromSymbol", fromSymbol);
        requestEntry.addRequestParam("toSymbol", toSymbol);
        requestEntry.addRequestParam("tradePasswd", Md5Utils.MD5Encode(tradePasswd));
        requestEntry.addRequestParam("googleCode", googleCode);

        doRequest(tag, requestEntry, callback);
    }

    /**
     *
     * 获取"兑换"相关的配置，最小兑换数量、费率等
     * @param tag
     * @param callback
     */
    public static void getExchangeConfig(ActivityFragmentActive tag,
                                  final ApiRequestCallback callback) {
//        "cont": [
//        {
//            "id": 1,
//                "fromSymbol": "ANY",
//                "toSymbol": "ETH",
//                "dayUsd": 400,
//                "personUsd": 50,
//                "minAmount": 100,//用户最小兑换
//                "feeRate": 0.01//手续费
//        }
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_WALLET_EXCHANGE_CONFIG);

        doRequest(tag, requestEntry, ExchangeConfigBean.class, callback);
    }

    /**
     *
     * 钱包资产查询
     * @param tag
     * @param callback
     */
    public static void getTradeBalance(String symbol, ActivityFragmentActive tag,
                                  final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_WALLET_TRADE_BALANCE);
        if(!TextUtils.isEmpty(symbol)) {
            requestEntry.addRequestParam("symbol", symbol);
        }

        doRequest(tag, requestEntry, TradeBalanceBean.class, callback);
    }

    /**
     * 钱包账单列表
     * @param symbol 币种
     * @param orderType 账单类型。0 全部，1 收入 2 支出
     * @param pageNo 页数
     * @param pageSize 每页行数
     * @param tag
     * @param callback
     */
    public static void getAssetBillPage(String symbol, String orderType, int pageNo, int pageSize,
                                        ActivityFragmentActive tag,
                                       final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_ASSET_BILL_PAGE);
        requestEntry.addRequestParam("symbol", symbol);
        requestEntry.addRequestParam("orderType", orderType);
        requestEntry.addRequestParam("pageNo", pageNo+ "");
        requestEntry.addRequestParam("limit", pageSize+ "");

        doRequest(tag, requestEntry, AssetBillListBean.class, callback);
    }


    /**
     * 获取市场行情数据
     * @param tag
     * @param callback
     */
    public static void getMarketData(ActivityFragmentActive tag,
                                        final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_MARKET_COMMON);
        doRequest(tag, requestEntry, MarketItemBean.class, callback);
    }
}
