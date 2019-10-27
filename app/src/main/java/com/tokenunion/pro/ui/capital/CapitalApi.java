package com.tokenunion.pro.ui.capital;

import com.tokenunion.pro.ui.capital.model.AssetInfo;
import com.tokenunion.pro.ui.capital.model.AssetOverviewBean;
import com.tokenunion.pro.ui.capital.model.BannerCommonBean;
import com.tokenunion.pro.ui.capital.model.FinanceListBean;
import com.tokenunion.pro.ui.capital.model.LeaderParticularsBean;
import com.tokenunion.pro.ui.capital.model.NoviceCommonBean;
import com.tokenunion.pro.ui.capital.model.NoviceOrdersListBean;
import com.tokenunion.pro.ui.capital.model.OrdersBean;
import com.tokenunion.pro.ui.capital.model.ProfitParticularsListBean;
import com.tokenunion.pro.ui.capital.model.TimeOrderListBean;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.config.ApiUrl;
import com.tokenunion.pro.ui.capital.model.ListRatioBean;
import com.tokenunion.pro.ui.capital.model.FinanceInfoBean;
import com.tokenunion.pro.ui.capital.model.FixedProductListBean;
import com.tokenunion.pro.ui.capital.model.ProfitListBean;
import com.tokenunion.pro.utils.Md5Utils;
import com.tokenunion.pro.utils.NetRequestUtils;
import com.yidaichu.android.common.http.ActivityFragmentActive;
import com.yidaichu.android.common.http.HttpRequestEntry;

/**
 * Create by: xiaohansong
 * Time:
 * -
 * Description: 财富模块相关接口
 */
public class CapitalApi extends NetRequestUtils {
    /**
     * 个人财富信息接口-理财首页（资产信息）
     *
     * @param tag
     * @param callback
     * @return
     */
    public static void getFinanceInfo(String symbol, ActivityFragmentActive tag, final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_FINANCE_INFO);

        requestEntry.addRequestParam("symbol", symbol);
        doRequest(tag, requestEntry, FinanceInfoBean.class, callback);
    }


    /**
     * 个人财富信息接口-理财首页（理财币种列表信息）
     *
     * @param tag
     * @param callback
     * @return
     */
    public static void getFinanceList(String symbol, ActivityFragmentActive tag, final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_FINANCE_LIST);

        requestEntry.addRequestParam("symbol", symbol);
        doRequest(tag, requestEntry, FinanceListBean.class, callback);
    }

    /**
     * 个人财富信息接口-详情页-财富收益账单
     *
     * @param tag
     * @param callback
     * @return
     */
    public static void getFinanceProfitList(String symbol, ActivityFragmentActive tag, int limit, int pageNo,
                                            final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_FINANCE_PROFIT_LIST);

        if(null != symbol) {
            requestEntry.addRequestParam("symbol", symbol);
        }
        requestEntry.addRequestParam("limit", limit+ "");
        requestEntry.addRequestParam("pageNo", pageNo+ "");
        doRequest(tag, requestEntry, ProfitListBean.class, callback);
    }

    /**
     * 操作记录
     * @param symbol
     * @param tag
     * @param callback
     */
    public static void getFinanceOperaterList(String symbol, ActivityFragmentActive tag, int limit, int pageNo,
                                              final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_FINANCE_ORDERS_LIST);

        if(null != symbol) {
            requestEntry.addRequestParam("symbol", symbol);
        }
        requestEntry.addRequestParam("limit", limit+ "");
        requestEntry.addRequestParam("pageNo", pageNo+ "");
        doRequest(tag, requestEntry, OrdersBean.class, callback);
    }

    /**
     * 理财个人资产概况-产品详情页（活期理财页）-- 产品详情页数据
     *
     * @param tag
     * @param callback
     * @return
     */
    public static void getFinanceAssetOverview(String symbol, ActivityFragmentActive tag, final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_FINANCE_ASSET_OVERVIEW);

        requestEntry.addRequestParam("symbol", symbol);
        doRequest(tag, requestEntry, AssetOverviewBean.class, callback);
    }

    /**
     * 理财-- "持有资产"列表数据
     *
     * @param tag
     * @param callback
     * @return
     */
    public static void getFinanceTimeOrderList(String symbol, ActivityFragmentActive tag, final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_FINANCE_TIMEORDER_LIST);

        requestEntry.addRequestParam("symbol", symbol);
        doRequest(tag, requestEntry, TimeOrderListBean.class, callback);
    }


    /**
     * 理财个人资产概况-产品详情页（活期理财页）-- "存入"页的数据,资产信息请求接口
     *
     * @param tag
     * @param callback
     * @return
     */
    public static void getFinanceAssetInfo(String symbol, int prodId, ActivityFragmentActive tag, final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_FINANCE_ASSET_DEPOSIT_INFO);

        requestEntry.addRequestParam("symbol", symbol);
        requestEntry.addRequestParam("prodId", prodId+ "");
        doRequest(tag, requestEntry, AssetInfo.class, callback);
    }

    /**
     * 理财个人资产概况-产品详情页（活期理财页）-- "存入"提交数据
     *
     * @param tag
     * @param callback
     * @return
     */
    public static void doFinanceDeposit(String symbol, String prodId, String amount, String tradePasswd,
                                        ActivityFragmentActive tag, final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_FINANCE_DEPOSIT);

        requestEntry.addRequestParam("symbol", symbol);
        requestEntry.addRequestParam("prodId", prodId);
        requestEntry.addRequestParam("amount", amount);
        requestEntry.addRequestParam("tradePasswd", tradePasswd);
        doRequest(tag, requestEntry, callback);
    }

    /**
     * 理财个人资产概况-产品详情页（活期理财页）-- "存入"提交数据
     *
     * @param tag
     * @param callback
     * @return
     */
    public static void doFinanceRedeem(String symbol, String prodId, String amount,
                                       String tradePasswd, String googleCode,
                                         ActivityFragmentActive tag, final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_FINANCE_REDEEM);

        requestEntry.addRequestParam("symbol", symbol);
        requestEntry.addRequestParam("prodId", prodId);
        requestEntry.addRequestParam("amount", amount);
        requestEntry.addRequestParam("tradePasswd", tradePasswd);
        requestEntry.addRequestParam("googleCode", googleCode);
        doRequest(tag, requestEntry, callback);
    }

    /**
     * 理财个人资产概况-产品详情页（活期理财页）-- 持有资产占比
     *
     * @param tag
     * @param callback
     * @return
     */
    public static void getFinanceListRatio(ActivityFragmentActive tag,
                                           final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_FINANCE_LIST_RATIO);

        doRequest(tag, requestEntry, ListRatioBean.class, callback);
    }

    /**
     * 理财资产详情查询接口（定期理财页）-- 定期产品列表
     *
     * @param tag
     * @param callback
     * @return
     */
    public static void getFinanceFixedProductList(String symbol, ActivityFragmentActive tag,
                                                  final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_FINANCE_PRODUCT_LIST);
        requestEntry.addRequestParam("symbol", symbol);

        doRequest(tag, requestEntry, FixedProductListBean.class, callback);
    }

    /**
     * 财富总览--利率规则--利率明细 接口
     *
     * @param tag
     * @param callback
     * @return
     */
    public static void getFinanceProfitParticularsList(ActivityFragmentActive tag,
                                                  final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setMethod(HttpRequestEntry.Method.GET);
        requestEntry.setUrl(ApiUrl.API_FINANCE_PROFIT_PARTICULARS);

        doRequest(tag, requestEntry, ProfitParticularsListBean.class, callback);
    }

    /**
     * 财富总览--利率规则--领导奖励 接口
     *
     * @param tag
     * @param callback
     * @return
     */
    public static void getFinanceLeaderParticulars(ActivityFragmentActive tag,
                                                       final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setMethod(HttpRequestEntry.Method.GET);
        requestEntry.setUrl(ApiUrl.API_FINANCE_LEADER_PARTICULARS);

        doRequest(tag, requestEntry, LeaderParticularsBean.class, callback);
    }

    /**
     * 获取首页banner
     * @param tag
     * @param callback
     */
    public static void getHomeBanners(ActivityFragmentActive tag,
                                                   final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setMethod(HttpRequestEntry.Method.POST);
        requestEntry.setUrl(ApiUrl.API_BANNER_COMMON);
        doRequest(tag, requestEntry, BannerCommonBean.class, callback);
    }


    /**
     * 获取"新用户"活动的订单列表
     * @param tag
     * @param callback
     */
    public static void getNoviceOrdersList(String symbol, int limit, int pageNo,
            ActivityFragmentActive tag,
                                      final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setMethod(HttpRequestEntry.Method.POST);
        requestEntry.setUrl(ApiUrl.API_NEW_USER_HOLDING_LIST);
        requestEntry.addRequestParam("symbol", symbol);
        requestEntry.addRequestParam("limit", limit+ "");
        requestEntry.addRequestParam("pageNo", pageNo+ "");
        doRequest(tag, requestEntry, NoviceOrdersListBean.class, callback);
    }

    /**
     * "新手专享"产品查询
     * @param tag
     * @param callback
     */
    public static void getNoviceCommon(ActivityFragmentActive tag,
                                      final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setMethod(HttpRequestEntry.Method.POST);
        requestEntry.setUrl(ApiUrl.API_NEW_USER_NOVICE_COMMON);
        doRequest(tag, requestEntry, NoviceCommonBean.class, callback);
    }

    /**
     * "新手专享"新手活动锁仓下单
     * @param tag
     * @param callback
     */
    public static void doNoviceDeposit(String amount,
                                        String prodId,
                                        String symbol,
                                        String tradePasswd,
                                        ActivityFragmentActive tag,
                                       final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setMethod(HttpRequestEntry.Method.POST);
        requestEntry.setUrl(ApiUrl.API_NEW_USER_NOVICE_DEPOSIT);
        requestEntry.addRequestParam("amount", amount);
        requestEntry.addRequestParam("prodId", prodId);
        requestEntry.addRequestParam("symbol", symbol);
        requestEntry.addRequestParam("tradePasswd", Md5Utils.MD5Encode(tradePasswd));
        doRequest(tag, requestEntry, callback);
    }
}
