package com.goga133.hw16

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService


class FireIDService : FirebaseInstanceIdService() {
    override fun onTokenRefresh() {
        super.onTokenRefresh()

        val tkn = FirebaseInstanceId.getInstance().token
        Log.d("Not", "Token [$tkn]");
    }
}