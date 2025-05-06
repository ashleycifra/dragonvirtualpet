package ph.edu.dlsu.mobdeve.solmayor.denzel.mp_cifra_solmayor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import ph.edu.dlsu.mobdeve.solmayor.denzel.mp_cifra_solmayor.databinding.ActivityArenaScreenBinding
import java.util.*

class ArenaScreen : AppCompatActivity() {

    private lateinit var binding: ActivityArenaScreenBinding
    private lateinit var myPet:PetInfo
    private var databaseHandler = DatabaseHandler(this)
    private lateinit var timer: CountDownTimer
    private var ctr: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityArenaScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myPet = databaseHandler.getPetInfo()
        myPet.petBar = databaseHandler.getPetBar()
        val bundle =intent.extras

        ctr = bundle!!.getInt("ctr")


        Log.d("ctr", ctr.toString())
        startTimer(ctr)

        binding.backBtn.setOnClickListener {
            val goToHomeScreen = Intent(this,HomeScreen::class.java)


            val bundle = Bundle()

            timer.cancel()
            bundle.putInt("ctr",ctr)


            goToHomeScreen.putExtras(bundle)
            startActivity(goToHomeScreen)
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
            finish()
        }
    }

    fun startTimer(extraTime:Int)
    {

        var threeMinutes : Long = 180000

        threeMinutes -= extraTime
        //3mins = 180000


        timer = object: CountDownTimer(threeMinutes, 1000){
            override fun onTick(millisUntilFinished: Long) {
                Log.d("OnTick","Timer tick $ctr millis")
                ctr+=1000
            }

            override fun onFinish() {
                Log.d("OnFinish","Timer finished")
                ctr = 0
                myPet.petBar.hygiene -= 10
                myPet.petBar.hunger -= 10
                myPet.petBar.train -= 10
                myPet.petBar.hygiene -= 10

                if(myPet.petBar.hygiene < 0)
                {
                    myPet.petBar.hygiene = 0
                }

                if(myPet.petBar.hunger < 0)
                {
                    myPet.petBar.hunger = 0
                }

                if(myPet.petBar.train < 0)
                {
                    myPet.petBar.train = 0
                }

                if(myPet.petBar.play < 0)
                {
                    myPet.petBar.play = 0
                }

                databaseHandler.updatePetBar(myPet.petBar)
                startTimer(0)
            }

        }.start()




    }
    override fun onDestroy() {

        updateTime()
        super.onDestroy()
    }

    fun updateTime()
    {
        var currentTime = Calendar.getInstance().time.toString()
        databaseHandler.updateTime(currentTime)
    }


}