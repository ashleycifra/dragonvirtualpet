package ph.edu.dlsu.mobdeve.solmayor.denzel.mp_cifra_solmayor

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ph.edu.dlsu.mobdeve.solmayor.denzel.mp_cifra_solmayor.databinding.ActivityChooseNameBinding

class ChooseName : AppCompatActivity() {

    private lateinit var binding: ActivityChooseNameBinding
    private lateinit var animationDrawable: AnimationDrawable
    private var databaseHandler = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseNameBinding.inflate(layoutInflater)
        setContentView(binding.root)



        if(databaseHandler.getEggType() == "red")
        {
            binding.eggChosen.setImageResource(R.drawable.animation_young_red_dragon)
            binding.eggChosen.tag = "red"
            animationDrawable = binding.eggChosen.drawable as AnimationDrawable
            animationDrawable.start()
        }

        if(databaseHandler.getEggType() == "yellow")
        {
            binding.eggChosen.setImageResource(R.drawable.animation_young_brass_dragon)
            binding.eggChosen.tag = "yellow"
            animationDrawable = binding.eggChosen.drawable as AnimationDrawable
            animationDrawable.start()
        }


        binding.confirmNameBtn.setOnClickListener {




            if(binding.enterNameText.text.toString() != "") {
                val goToHomeScreen = Intent(this,HomeScreen::class.java)

                databaseHandler.addPetName(binding.enterNameText.text.toString())


                startActivity(goToHomeScreen)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }

            else{
                Toast.makeText(this,"Enter your pet's name!",Toast.LENGTH_SHORT).show()
            }



        }




    }
}