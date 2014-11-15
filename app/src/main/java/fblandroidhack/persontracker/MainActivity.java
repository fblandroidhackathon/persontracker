package fblandroidhack.persontracker;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import com.parrot.freeflight.service.DroneControlService;



public class MainActivity extends Activity {

    private DroneControlService droneControlService;

    private Button takeOffBtn;
    private Button emergencyBtn;

    private boolean isFlying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind drone service
        bindService(new Intent(this, DroneControlService.class), mConnection, Context.BIND_AUTO_CREATE);

        // Load take off button but don't enable until we're connected to drone
        takeOffBtn = (Button) findViewById(R.id.mTakeOffBtn);

        emergencyBtn = (Button) findViewById(R.id.mEmergencyBtn);
        emergencyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                emergencyShutdown();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder service) {
            droneControlService = ((DroneControlService.LocalBinder) service).getService();
            onDroneServiceConnected();
        }

        public void onServiceDisconnected(ComponentName name) {
            droneControlService = null;
        }
    };

    private void onDroneServiceConnected() {
        // Enable buttons
        takeOffBtn.setEnabled(true);
        emergencyBtn.setEnabled(true);

        takeOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFlying = !isFlying;
                if (isFlying) {
                    takeOffBtn.setText(getString(R.string.land));
                } else {
                    takeOffBtn.setText(getString(R.string.take_off));
                }
                droneControlService.triggerTakeOff();
            }
        });
    }

    private void emergencyShutdown() {
        if (droneControlService != null) {
            droneControlService.triggerEmergency();
        }
    }
}
