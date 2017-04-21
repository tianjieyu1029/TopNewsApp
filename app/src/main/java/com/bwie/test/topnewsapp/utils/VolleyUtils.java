package com.bwie.test.topnewsapp.utils;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录注册的网络请求类
 *
 */

public class VolleyUtils {
    //系统常量
    public static final String SYSTEM_TYPE = "android";
    public static final String SYSTEM_VERSION = "V1.0";
    public static final String SYSTEM_SHARE_NAME = "Yokey_Nsg";

    public static final String LINK_MAIN = "http://192.168.23.226/";   // 服务器域名地址

    public static final String LINK_WAP = LINK_MAIN + "wap/";
    public static final String LINK_WAP_FIND_PASSWORD = LINK_WAP + "tmpl/member/find_password.html";

    public static final String LINK_MOBILE = LINK_MAIN + "mobile/index.php?act=";
    public static final String LINK_MOBILE_INDEX = LINK_MOBILE + "index";                                                          //首页 GET
    public static final String LINK_MOBILE_LOGIN = LINK_MOBILE + "login";                                                          //登录 POST
    public static final String LINK_MOBILE_REG = LINK_MOBILE + "login&op=register";                                                //注册 POST
    public static final String LINK_MOBILE_LOGOUT = LINK_MOBILE + "logout";                                                        //注销 POST
    public static final String LINK_MOBILE_USER = LINK_MOBILE + "member_index";                                                    //个人中心 POST
    public static final String LINK_MOBILE_CLASS = LINK_MOBILE + "goods_class";                                                    //分类 GET
    public static final String LINK_MOBILE_CART = LINK_MOBILE + "member_cart&op=cart_list";                                        //购物车 POST
    public static final String LINK_MOBILE_CART_DEL = LINK_MOBILE + "member_cart&op=cart_del";                                     //购物车删除 POST
    public static final String LINK_MOBILE_CART_ADD = LINK_MOBILE + "member_cart&op=cart_add";                                     //购物车添加 POST
    public static final String LINK_MOBILE_AREA = LINK_MOBILE + "area&op=index";                                     //地区列表 POST
    public static final String LINK_MOBILE_ORDER = LINK_MOBILE + "member_order&op=order_list";                                     //所有订单 POST
    public static final String LINK_MOBILE_ORDER_CANCEL = LINK_MOBILE + "member_order&op=order_cancel";                            //取消订单 POST
    public static final String LINK_MOBILE_ORDER_REFOUND = LINK_MOBILE + "member_refund&op=get_refund_list&page=100";              //退款订单 GET
    public static final String LINK_MOBILE_ORDER_REFOUND_INFO = LINK_MOBILE + "member_refund&op=get_refund_info";                  //退款订单详细 GET
    public static final String LINK_MOBILE_ORDER_RETURN = LINK_MOBILE + "member_return&op=get_return_list&page=100";               //退货订单 GET
    public static final String LINK_MOBILE_ADDRESS = LINK_MOBILE + "member_address&op=address_list";                               //收货地址 POST
    public static final String LINK_MOBILE_ADDRESS_ADD = LINK_MOBILE + "member_address&op=address_add";                            //添加收货地址 POST
    public static final String LINK_MOBILE_ADDRESS_DEL = LINK_MOBILE + "member_address&op=address_del";                            //删除收货地址 POST
    public static final String LINK_MOBILE_ADDRESS_EDIT = LINK_MOBILE + "member_address&op=address_edit";


    public static void post(final Context context, String url, final HashMap<String, String> params, final MyCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    /**
     * 接口回调
     */
    public interface MyCallback {
        void onSuccess(String result);

        void onError(String errorMsg);

    }

    public static String get(Context context, String url, final HashMap<String, String> params) {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        final String[] data = new String[1];
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                data[0] = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return params;
            }
        };
        requestQueue.add(stringRequest);
        return data.toString();
    }
}
