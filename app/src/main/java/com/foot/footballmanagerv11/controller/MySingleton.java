package com.foot.footballmanagerv11;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static MySingleton mySingleTon;
    private RequestQueue requestQueue;
    private static Context mctx;

    private MySingleton(Context context) {
        this.mctx = context;
        this.requestQueue = getRequestQueue();

    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mctx.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingleton getInstance(Context context) {
        if (mySingleTon == null) {
            mySingleTon = new MySingleton(context);
        }
        return mySingleTon;
    }

    public <T> void addToRequestQue(Request<T> request) {
        requestQueue.add(request);
    }
}

