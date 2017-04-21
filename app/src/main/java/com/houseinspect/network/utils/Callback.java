package com.houseinspect.network.utils;

import java.net.SocketTimeoutException;

import retrofit.RetrofitError;
import retrofit.client.Response;

public abstract class Callback<T> implements retrofit.Callback<T> {

    public Callback() {

    }

    public final void success(T t, Response response) {
        this.success(new Result(t, response));
    }

    public final void failure(RetrofitError error) {
        if (error.getKind() != null && error.getKind().equals(RetrofitError.Kind.NETWORK)) {
            if (error.getCause() instanceof SocketTimeoutException) {
                onNetworkFail("Time Out!");
            } else {
                onNetworkFail("No internet connection");
            }
        } else if (error.getResponse() == null) {
            this.failure(error, 0);
        }else {
            this.failure(error, error.getResponse().getStatus());
        }
    }

    public abstract void success(Result<T> result);

    public abstract void failure(RetrofitError error, int code);

    public abstract void onNetworkFail(String errorMessage);
}