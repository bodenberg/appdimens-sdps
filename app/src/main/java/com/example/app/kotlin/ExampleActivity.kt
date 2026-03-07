package com.example.app.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.appdimens.sdps.code.DimenPhysicalUnits
import com.appdimens.sdps.code.DimenSdp
import com.appdimens.sdps.common.DpQualifier
import com.example.app.databinding.ActivitySdpBinding

/**
 * @author Bodenberg
 * GIT: https://github.com/bodenberg/appdimens-sdps.git
 */
class ExampleActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySdpBinding

    /**
     * EN Called when the activity is first created.
     *
     * PT Chamado quando a atividade é criada pela primeira vez.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // EN Data Binding Setup
        // PT Configuração do Data Binding
        binding = ActivitySdpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example by code
        DimenSdp.getResourceId(this, DpQualifier.SMALL_WIDTH, 25);
        DimenSdp.getResourceId(this, DpQualifier.WIDTH, 25);
        DimenSdp.getResourceId(this, DpQualifier.HEIGHT, 25);

        DimenPhysicalUnits.toDpFromCm(30f, resources);
    }

}