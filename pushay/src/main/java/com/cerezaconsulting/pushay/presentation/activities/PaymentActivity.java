package com.cerezaconsulting.pushay.presentation.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.cerezaconsulting.pushay.R;
import com.cerezaconsulting.pushay.core.BaseActivity;
import com.cerezaconsulting.pushay.data.entities.SchedulesEntity;
import com.cerezaconsulting.pushay.data.local.SessionManager;
import com.cerezaconsulting.pushay.data.remote.ServiceFactory;
import com.cerezaconsulting.pushay.data.remote.request.PaymentRequest;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by junior on 17/07/17.
 */

public class PaymentActivity extends BaseActivity implements BillingProcessor.IBillingHandler, Validator.ValidationListener {
    // SAMPLE APP CONSTANTS
    private static final String ACTIVITY_NUMBER = "activity_num";
    private static final String LOG_TAG = "iabv3";

    // PRODUCT & SUBSCRIPTION IDS
    private static final String PRODUCT_ID = "com.cerezaconsulting.pushay.travel";
    private static final String SUBSCRIPTION_ID = "com.cerezaconsulting.pushay.sub";
    private static final String LICENSE_KEY = null; // PUT YOUR MERCHANT KEY HERE;
    // put your Google merchant id here (as stated in public profile of your Payments Merchant Center)
    // if filled library will provide protection against Freedom alike Play Market simulators
    private static final String MERCHANT_ID = null;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_destiny_name)
    TextView tvDestinyName;
    @BindView(R.id.tv_city_name)
    TextView tvCityName;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.tv_hour)
    TextView tvHour;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.btn_buy)
    Button btnBuy;

    @NotEmpty
    @BindView(R.id.et_quantity)
    EditText etQuantity;

    Activity activity;
    private BillingProcessor bp;
    private boolean readyToPurchase = false;


    ProgressDialog progressDialog = null;
    private Validator validator;

    private SchedulesEntity schedulesEntity;
    private String date;
    private SessionManager mSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_buy);
        ButterKnife.bind(this);

        date = getIntent().getExtras().getString("date");
        schedulesEntity = (SchedulesEntity) getIntent().getExtras().getSerializable("schedulesEntity");

        mSessionManager = new SessionManager(getApplicationContext());

        tvDestinyName.setText(schedulesEntity.getDestiny().getName());
        tvHour.setText(schedulesEntity.getHour());
        tvCityName.setText(schedulesEntity.getDestiny().getCity().getName());
        tvDay.setText(schedulesEntity.getDay().getName());
        tvName.setText(mSessionManager.getUserEntity().getFullName());
        //tvDate.setText(getCost());


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Procesando Código");
        progressDialog.setIndeterminate(true);

        validator = new Validator(this);
        validator.setValidationListener(this);
        if (!BillingProcessor.isIabServiceAvailable(this)) {
            showToast("In-app billing service is unavailable, please upgrade Android Market/Play to version >= 3.9.16");
        }
       /* bp = new BillingProcessor(this, LICENSE_KEY, MERCHANT_ID, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(String productId, TransactionDetails details) {
                showLoader(true);
                PaymentRequest paymentRequest = ServiceFactory.createService(PaymentRequest.class);
                Call<Void> call = paymentRequest.createReservation(Integer.valueOf(etQuantity.getText().toString()),
                        false, schedulesEntity.getName());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {

                            showToast("El código ha sido enviado a su correo");
                            finish();
                        } else {
                            showToast("Ha ocurrido un error desconocido");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        progressDialog.dismiss();
                        showToast("Ha ocurrido un error desconocido");
                    }
                });
            }
*/
            /*@Override
            public void onBillingError(int errorCode, Throwable error) {
                showToast("onBillingError: " + Integer.toString(errorCode));
            }

            @Override
            public void onBillingInitialized() {
                // showToast("onBillingInitialized");
                readyToPurchase = true;
            }

            @Override
            public void onPurchaseHistoryRestored() {
                showToast("onPurchaseHistoryRestored");
                for (String sku : bp.listOwnedProducts())
                    Log.d(LOG_TAG, "Owned Managed Product: " + sku);
                for (String sku : bp.listOwnedSubscriptions())
                    Log.d(LOG_TAG, "Owned Subscription: " + sku);

            }
        });
*/
        // or bp = BillingProcessor.newBillingProcessor(this, "YOUR LICENSE KEY FROM GOOGLE PLAY CONSOLE HERE", this);
        // See below on why this is a useful alternative
    }

    // IBillingHandler implementation
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBillingInitialized() {
    /*
    * Called when BillingProcessor was initialized and it's ready to purchase
    */
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
    /*
    * Called when requested PRODUCT ID was successfully purchased
    */


    }

    private void showLoader(boolean active) {

        if (active) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
    /*
    * Called when some error occurred. See Constants class for more details
    *
    * Note - this includes handling the case where the user canceled the buy dialog:
    * errorCode = Constants.BILLING_RESPONSE_RESULT_USER_CANCELED
    */
    }

    @Override
    public void onPurchaseHistoryRestored() {
    /*
    * Called when purchase history was restored and the list of all owned PRODUCT ID's
    * was loaded from Google Play
    */
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {

        if (bp != null) {
            bp.release();
        }
        super.onDestroy();

    }

    @OnClick({R.id.btn_buy})
    public void onViewClicked(View view) {


        switch (view.getId()) {
            case R.id.btn_buy:
                validator.validate();
                break;
        }
    }

    @Override
    public void onValidationSucceeded() {

        if (!readyToPurchase) {
            showToast("Billing not initialized.");
            return;
        }
        bp.purchase(PaymentActivity.this, "android.test.purchased");
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            }
        }
    }

    public String getCost(){
        Float cost = Float.valueOf(etQuantity.getText().toString())*schedulesEntity.getPrice_normal();
        String totalCost = String.valueOf(cost);

        return totalCost;
    }

    @OnClick(R.id.btn_buy)
    public void onViewClicked() {
        validator.validate();
    }
}
