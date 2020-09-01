package com.example.healthieryou;

import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.result.DailyTotalResult;
import com.google.android.gms.location.LocationServices;

import java.util.concurrent.TimeUnit;


public class VerifyDataTask extends AsyncTask<Void, Void, Long> {
    Context context;
    GoogleApiClient mGoogleApiClient;
    long total = 0;

    public VerifyDataTask(Context context) {
        this.context = context;
        this.mGoogleApiClient = mGoogleApiClient;
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addApi(Fitness.HISTORY_API)
                .build();
    }

    protected Long doInBackground(Void... params) {

        PendingResult<DailyTotalResult> result = Fitness.HistoryApi.readDailyTotal(mGoogleApiClient, DataType.TYPE_STEP_COUNT_DELTA);
        DailyTotalResult totalResult = result.await(1, TimeUnit.SECONDS);
        if (totalResult.getStatus().isSuccess()) {
            DataSet totalSet = totalResult.getTotal();
            total = totalSet.isEmpty()
                    ? 0
                    : totalSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();
        } else {

        }

        return total;
    }

    public long getTotal() {
        return total;
    }
}
