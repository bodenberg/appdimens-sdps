/**
 * @author Bodenberg
 * GIT: https://github.com/bodenberg/appdimens-sdps.git
 */
package com.example.app.java;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.appdimens.sdps.code.DimenPhysicalUnits;
import com.appdimens.sdps.code.DimenSdp;
import com.appdimens.sdps.common.DpQualifier;
import com.example.app.databinding.ActivitySdpBinding;

/**
 * EN Main activity demonstrating various features of the AppDimens library.
 *
 * PT Atividade principal que demonstra vários recursos da biblioteca AppDimens.
 */
public class ExampleActivity extends AppCompatActivity {

    private ActivitySdpBinding binding;

    /**
     * EN Called when the activity is first created.
     *
     * PT Chamado quando a atividade é criada pela primeira vez.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // EN Data Binding Configuration
        // PT Configuração do Data Binding
        binding = ActivitySdpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Example by code
        DimenSdp.getResourceId(this, DpQualifier.SMALL_WIDTH, 25);
        DimenSdp.getResourceId(this, DpQualifier.WIDTH, 25);
        DimenSdp.getResourceId(this, DpQualifier.HEIGHT, 25);

        DimenPhysicalUnits.toDpFromCm(30f, getResources());
    }
}