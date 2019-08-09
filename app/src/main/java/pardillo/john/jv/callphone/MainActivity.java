package pardillo.john.jv.callphone;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static int REQUEST_CALL = 100;

    private EditText txtPhone;
    private Button btnCall;
    private Intent callIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.txtPhone = this.findViewById(R.id.editText);
        this.btnCall = this.findViewById(R.id.button);

        this.btnCall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        callPhone();
    }


    private void callPhone() {
        String phone = this.txtPhone.getText().toString();
        Uri uriPhone = Uri.parse("tel:" +phone);
        callIntent = new Intent(Intent.ACTION_CALL, uriPhone);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{

                        Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            }
        } else {
            this.startActivity(callIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case REQUEST_CALL:
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    final Dialog dialog = new Dialog(this);
                    dialog.setContentView(R.layout.layout_permission);

                    TextView t1 = dialog.findViewById(R.id.textView);
                    TextView t2 = dialog.findViewById(R.id.textView2);
                    t1.setText("Request Permissions");
                    t2.setText("Please allow permissions if you want this application to perform the task.");

                    Button dialogButton = dialog.findViewById(R.id.button);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                } else {
                    this.startActivity(callIntent);
                }

                break;
        }
    }
}
